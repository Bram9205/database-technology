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
	 * No attributes initially
	 * @param name the name
	 */
	public Relation(String name){
		this.name = name;
		this.attributes = new ArrayList<Attribute>();
	}
	
	/**
	 * 
	 * @param attributes
	 * @param name
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
		this.attributes = new ArrayList<Attribute>();
	}
	
	public void addAttribute(Attribute attribute){
		this.attributes.add(attribute);
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
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
	
	public ArrayList<Attribute> getAttributes(){
		return this.attributes;
	}
	
	@Override
	public String toString(){
		String result = "(";
		for(int i = 0; i< this.attributes.size(); i++){
			result += this.attributes.get(i).getType() + ",";
		}
		return result + ")";
	}
}
