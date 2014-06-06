
import data.Query;
import tree.Tree;

public class Main {
	
	private static QueryGenerator generator;
	
	private static void treeToSQL(Query query){
		throw new UnsupportedOperationException("not yet implemented");
	}
	
	
	private static void test(){
		Query query = generator.generateCyclicQueryWidth2(2500);
		Tree tree = Tree.createFromCyclicQueryWidth2(query);
		System.out.println(tree.toString());
	}
	
	public static void main(String[] args){
		test();
	}
}
