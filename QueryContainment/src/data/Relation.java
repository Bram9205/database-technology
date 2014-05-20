package data;

import java.util.ArrayList;

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
	
	public static String generateRandomName(int length){
		throw new UnsupportedOperationException();
	}
}
