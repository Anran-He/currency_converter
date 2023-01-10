package com.java.currencyConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;



/**
 * Class aiming to execute transaction and update users' information
 * <p>Include 6 methods
 * <li>readFile(String)    read from "transactions.txt"
 * <li>readFromUserJSONFile(String fileStr)    read from "users.json"
 * <li>readFromUserRATESFile(String fileStr)    read from "fx_rates.json"
 * <li>convert(String fromCurrency, String toCurrency, Double amount)    currency exchange
 * <li>executeTransaction(String transactionFile, String userFile, String rateFile)    execute transactions as per requirement
 * <li>writeToJSONFile(String fileStr, User[] users)    update user's information to "users.json"
 * 
 * @author Anran
 * @version 1.0
 */

public class TransactionProcess {
	/** used for logging */
	private static Logger logger = LogManager.getLogger();
	
	/**
	 * Read from {@code file} and store file content into {@code ArrayList<Converter>} object
	 * @param file    file path
	 * @return convertInfos    an ArrayList of Converter object containing transaction information
	 * @throws FileNotFoundException     if cannot find required file
	 * @throws IOException    if encounter other IO issues 
	 */
	public static ArrayList<Converter> readFile(String file) {
		ArrayList<Converter> convertInfos = new ArrayList<Converter>();
		try {
			Reader reader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = bufferedReader.readLine();
			while(line!=null) {
				Converter convertInfo  = new Converter();
				String[] splitLine = line.split(" ");				
				convertInfo.setName(splitLine[0]);
				convertInfo.setFromCurrency(splitLine[1]);
				convertInfo.setToCurrency(splitLine[2]);
				convertInfo.setAmount(Double.parseDouble(splitLine[3]));
				convertInfos.add(convertInfo);
				line= bufferedReader.readLine();
				//System.out.println(users.get(0).name);
				}
			bufferedReader.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return convertInfos;
	}
	
	
	/**
	 * Read from {@code fileStr} and store content into {@code User[]} object
	 * @param fileStr    file path
	 * @return users    a User[] object containing user's information
	 * @throws IOException    if encounter IO issues
	 */
	public static User[] readFromUserJSONFile(String fileStr) {
		File file = new File(fileStr);
		ObjectMapper mapper = new ObjectMapper();
    	
    	User[] users = null;
    	
    	try {
    		users = mapper.readValue(file, User[].class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return users;
	}
	
	
	/**
	 * Read from {@code fileStr} and store content into {@code Map<String,Map<String,Object>>} object
	 * @param fileStr    file path
	 * @return rates    a Map<String,Map<String,Object>> exchange rates information
	 * @throws IOException    if encounter IO issues
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Map<String,Object>> readFromRatesJSONFile(String fileStr) {
		File file = new File(fileStr);
		ObjectMapper mapper = new ObjectMapper();
    	
    	Map<String, Map<String, Object>> rates = null;
    	
    	try {
    		rates = mapper.readValue(file, Map.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return rates;
	}
	
	/**
	 * Convert specific {@code amount} of {@code fromCurrnecy} to {@code toCurrency}
	 * @param fromCurrency    currency to convert from
	 * @param toCurrency    currency to convert to
	 * @param amount    amount of {@code fromCurrency} to convert
	 * @return convertAmount    amount of {@code toCurrency} after conversion
	 */
	public static Double convert(String fromCurrency, String toCurrency, Double amount) {
		Map<String, Map<String, Object>> rates = readFromRatesJSONFile(".\\src\\main\\resources\\fx_rates.json");
		Double fromRate;
		Double toRate;
		
		if(fromCurrency.equals("usd")) {
			fromRate = 1.0;
		}else {
			fromRate = (Double) rates.get(fromCurrency).get("rate"); 
		}
		
		if(toCurrency.equals("usd")){
			toRate = 1.0;
		}else {
			toRate = (Double) rates.get(toCurrency).get("rate");
		}
		
		Double convertAmount = amount * toRate / fromRate;
		return convertAmount;
	}
	
	/**
	 * Execute transaction as per requirement and update users' information to {@code User[]} object
	 * @param transactionFile    the file that contains transaction information
	 * @param userFile    the file that contains users' information
	 * @param rateFile    the file that contains exchange rate information
	 * @return    users    a User[] object that contains updated users' information
	 */
	public static User[] executeTransaction(String transactionFile, String userFile, String rateFile){
		ArrayList<Converter> convertInfos = readFile(transactionFile);
				
		User[] users = readFromUserJSONFile(userFile);
		
		Map<String, Map<String, Object>> rates = readFromRatesJSONFile(rateFile);

		int transactionNum = 0;
		
		for(Converter convertInfo : convertInfos){
			transactionNum++;
			String name = convertInfo.getName();
			String fromCur = convertInfo.getFromCurrency();
			String toCur = convertInfo.getToCurrency();
			double amount = convertInfo.getAmount();
			for(int i = 0; i < users.length; i++) {				
				if(name.equals(users[i].getName())){
					if(users[i].getWallet().containsKey(fromCur) &&
							users[i].getWallet().get(fromCur) >= amount) {
						Double convertAmount = convert(fromCur,toCur,amount);
						users[i].getWallet().put(fromCur, users[i].getWallet().get(fromCur) - amount);
						users[i].getWallet().put(toCur, users[i].getWallet().getOrDefault(toCur,0.0) + convertAmount);
						break;
					}else if(!users[i].getWallet().containsKey(fromCur)) {
						logger.info("Transaction "+ transactionNum + ": " + users[i].getName() + " doesn't have " + fromCur);
						break;
					}else {
						logger.info("Transaction "+ transactionNum + ": " + users[i].getName() + " doesn't have enough " + fromCur + " balance");
						break;
					}
			}else if(!name.equals(users[i].getName()) && i != users.length - 1) continue;
			 else logger.info("Transaction "+ transactionNum + ": " + name + " doesn't exist");
		}
				
		}
		return users;
}
	
	/**
	 * Update initial "users.json" file
	 * @param fileStr    new file path
	 * @param users    a User[] object containing updated users' information
	 * @throws IOException   if encounter IO issues
	 */
	public static void writeToJSONFile(String fileStr, User[] users) {
		File file = new File(fileStr);
    	@SuppressWarnings("unused")
		User user = null;
       	ObjectMapper mapper = new ObjectMapper();
       	try {
			mapper.writeValue(file, users);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Main method that execute transactions and update file
	 */
	public static void main(String[] args) {
		String transactionFile = ".\\src\\main\\resources\\transactions.txt";
		String userFile = ".\\src\\main\\resources\\users.json";
		String rateFile = ".\\src\\main\\resources\\fx_rates.json";
		
		User[] users = null;
		
		users = executeTransaction(transactionFile, userFile, rateFile);

		//convert back to user json file;
		writeToJSONFile(".\\src\\main\\resources\\users.json", users);
		
	}

}
