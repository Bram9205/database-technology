package data;

import java.util.ArrayList;

/**
 *
 * @author Bram
 */
public class Query {
	/**
	 * The relations that define the query
	 */
	private ArrayList<Relation> relations;
	private Relation head;

	public Query(ArrayList<Relation> relations, Relation head){
		this.relations = relations;
		this.head = head;
	}
	
	/**
	 * Add relations to the query later
	 */
	public Query(){
		this.relations = new ArrayList<>();
	}
	
	/**
	 * Adds @code{relation} to the @code{Query}
	 * @param relation 
	 */
	public void addRelation(Relation relation){
		this.relations.add(relation);
	}
	
	public ArrayList<Relation> getRelations(){
		return relations;
	}
	
	public Relation getHead(){
		return this.head;
	}
	
	public void setHead(Relation head){
		this.head = head;
	}
	
	/**
	 * Assumes relations is not empty
	 */
	public void setHeadToFirstRelation(){
		this.head = relations.get(0);
	}
	
	@Override
	public String toString(){
		String result = "";
		result += "Q(";
		for(Attribute a : head.getAttributes()){
			result += a.getType() + ",";
		}
		result += ") :- ";
		for(Relation r : relations){
			result += r.getName() + "(";
			for(Attribute a : r.getAttributes()){
				result += a.getType() + ",";
			}
			result += "), ";
		}
		return result;
	}
}
