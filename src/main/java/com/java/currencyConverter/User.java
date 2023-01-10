package com.java.currencyConverter;

import java.util.Map;

/**
 * Class maintaining user details, including name and wallet.
 * 
 * 
 * @author Anran
 * @version 1.0
 *
 * 
 */
public class User {
	/** used for user's name */
	private String name;
	/** used for user's wallet */
	private Map<String,Double> wallet;
	
	/**
	 * Getter method to get {@code name} class attribute of {@code User} object
	 * @return name    user's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter method to get {@code wallet} class attribute of {@code User} object
	 * @return wallet   user's wallet
	 */
	public Map<String,Double> getWallet() {
		return wallet;
	}
	
	/**
	 * Setter method to set {@code name} class attribute of {@code User} object
	 * @param name    user's name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Setter method to set {@code wallet} class attribute of {@code User} object
	 * @param wallet    user's wallet
	 */
	public void setWallet(Map<String,Double> wallet) {
		this.wallet = wallet;
	}
}