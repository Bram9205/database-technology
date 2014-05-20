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

	public Query(ArrayList<Relation> relations){
		this.relations = relations;
	}
	
	/**
	 * Add relations to the query later
	 */
	public Query(){
	}
	
	/**
	 * Adds @code{relation} to the @code{Query}
	 * @param relation 
	 */
	public void addRelation(Relation relation){
		this.relations.add(relation);
	}
}
