
import data.Attribute;
import data.Query;
import data.Relation;
import java.util.ArrayList;

/**
 *
 * @author Bram
 */
public class QueryGenerator {
	public static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	/**
	 * 
	 * @param n Number of nodes in the cycle
	 * @return A cyclic query with n relations
	 */
	public static Query generateCyclicQueryWidth2(int n){
		Query result = new Query();
		Attribute previous = new Attribute(getAttributeName(0));
		for(int i = 0; i < n; i++){
			Relation relation = new Relation("s"+n);
			relation.addAttribute(previous);
			Attribute next = new Attribute(getAttributeName(i+1));
			relation.addAttribute(next);
			result.addRelation(relation);
			previous = next;
		}
		Relation relation = new Relation("s"+(n+1));
		relation.addAttribute(previous);
		relation.addAttribute(result.getRelations().get(0).getAttributes().get(0));
		result.addRelation(relation);
		result.setHeadToFirstRelation();
		return result;
	}
	
	private static String getAttributeName(int x){
		String result = "";
		result += alphabet.charAt(x%26);
		result += x/26;
		return result;
	}
	
	public static void main(String[] args){
		Query result = generateCyclicQueryWidth2(3);
		System.out.print("Q"+result.getHead().toString()+":-");
		for(int i = 0; i < result.getRelations().size(); i++){
			System.out.print(result.getRelations().get(i).toString() + ", ");
		}
	}

}
