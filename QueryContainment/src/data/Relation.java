package data;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Bram
 */
public class Relation {
	private ArrayList<Attribute> attributes;
	private String name;
	private static final int defaultNameLength = 5;
	
	/**
	 * 
	 * @param attributes
	 * @param relationName 
	 */
	public Relation(ArrayList<Attribute> attributes, String name){
		this.attributes = attributes;
		this.name = name;
	}
	
	/**
	 * Name generated randomly
	 * @param attributes 
	 */
	public Relation(ArrayList<Attribute> attributes){
		this.attributes = attributes;
		this.name = generateRandomName(defaultNameLength);
	}
	
	/**
	 * Name generated randomly, no attributes
	 */
	public Relation(){
		this.name = generateRandomName(defaultNameLength);
	}
	
	public void addAttribute(Attribute attribute){
		this.attributes.add(attribute);
	}
	
	public String getName(){
		return this.name;
	}
	
	public static String generateRandomName(int length){
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		char[] result = new char[length];
		Random random = new Random();
		for(int i = 0; i < length; i++){
			result[i] = alphabet.charAt(random.nextInt(52));
		}
		return new String(result);
	}
	
	public static String generateRandomName(){
		return generateRandomName(defaultNameLength);
	}
}
