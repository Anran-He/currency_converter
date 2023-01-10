package com.java.currencyConverter;

/**
 * Class maintaining currency conversion details, including user's name, currency that to convert from, currency to
 * convert to and convert amount.
 * 
 * @author Anran
 * @version 1.0
 */
public class Converter {
	/** used for user's name */
	private String name;
	/** used for currency that to convert from*/
	private String fromCurrency;
	/** used for currency that to convert to*/
	private String toCurrency;
	/** used for convert amount*/
	private double amount;
	
	/**
	 * Getter method to get {@code name} class attribute of {@code Converter} object
	 * @return name    user's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter method to get {@code fromCurrency} class attribute of {@code Converter} object
	 * @return fromCurrency    currency that to convert from
	 */
	public String getFromCurrency() {
		return fromCurrency;
	}
	
	/**
	 * Getter method to get {@code toCurrency} class attribute of {@code Converter} object
	 * @return toCurrency    currency that to convert to
	 */
	public String getToCurrency() {
		return toCurrency;
	}
	
	/**
	 * Getter method to get {@code amount} class attribute of {@code Converter} object
	 * @return amount    convert amount
	 */
	public double getAmount() {
		return amount;
	}
	
	/**
	 * Setter method to set {@code name} class attribute of {@code Converter} object
	 * @param name    user's name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Setter method to set {@code fromCurrency} class attribute of {@code Converter} object
	 * @param fromCurrency    currency that to convert from
	 */
	public void setFromCurrency(String fromCurrency) {
		this.fromCurrency = fromCurrency;
	}
	
	/**
	 * Setter method to set {@code toCurrency} class attribute of {@code Converter} object
	 * @param toCurrency    currency that to convert to
	 */
	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}
	
	/**
	 * Setter method to set {@code amount} class attribute of {@code Converter} object
	 * @param amount    convert amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

}
