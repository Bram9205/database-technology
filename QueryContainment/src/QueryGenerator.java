
import data.Query;
import java.util.ArrayList;

/**
 *
 * @author Bram
 */
public class QueryGenerator {
	public static final int defaultMinAttributes = 2;
	public static final int defaultMaxAttributes = 6;
	public static final int defaultMinRelations = 100;
	public static final int defaultMaxRelations = 200;
	public static final int defaultAmount = 1;
	
	/**
	 * You can use default{ParameterName} for variables.
	 * @param nrQueries
	 * @param nrRelations
	 * @param minAttributes
	 * @param maxAttributes
	 * @return 
	 */
	public static ArrayList<Query> GenerateQueries(int amount, int minRelations, int maxRelations, int minAttributes, int maxAttributes){
		//For nrQueries, generate query, which contain between min and max relations, which have between min and max attributes
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
}
