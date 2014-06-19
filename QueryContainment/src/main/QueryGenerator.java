package main;


import data.Attribute;
import data.Query;
import data.Relation;
import java.util.Random;
import tree.Node;
import tree.Tree;

/**
 *
 * @author Bram
 */
public class QueryGenerator {
	public static String alphabet = "ABCDEFGHIJKLM";
	public static String noiseAlphabet = "XYZWVUPQRSTNO";
	/**
	 * 
	 * @param n Number of nodes in the cycle
	 * @param noise	Amount of added noise nodes
	 * @return A cyclic query with n relations
	 */
	public static Query generateCyclicQueryWidth2(int n, int noise){
		boolean noiseVariables = noise > 0;
		Random RNG = new Random();
		Query result = new Query();
		Attribute previous = new Attribute(getAttributeName(0,noiseVariables));
		for(int i = 0; i < n; i++){
			Relation relation = new Relation("s"+i);
			relation.addAttribute(previous);
			Attribute next = new Attribute(getAttributeName(i+1,noiseVariables));
			relation.addAttribute(next);
			result.addRelation(relation);
			previous = next;
		}
		Relation relation = new Relation("s"+n);
		relation.addAttribute(previous);
		relation.addAttribute(result.getRelations().get(0).getAttributes().get(0));
		result.addRelation(relation);
		result.setHeadToFirstRelation();
		
		for(int i = 0; i < noise; i++){
			int x = RNG.nextInt(n+1);
			Attribute noiseStart = new Attribute(getAttributeName(n+1+i,true));
			Attribute noiseEnd = result.getRelations().get(x).getAttributes().get(0);
			Relation noiseRelation = new Relation("n"+i);
			noiseRelation.addAttribute(noiseStart);
			noiseRelation.addAttribute(noiseEnd);
			result.addRelation(noiseRelation);
		}
		return result;
	}
    public static Query generateCyclicQueryWidth2(int n){
        return generateCyclicQueryWidth2(n,0);
    }


    /**
     *
     * @param width   How wide the query width should be
     * @param n       Number of nodes in the cycle. Must be greater than width parameter *2
     * @param noise   Added noise, appendages to the graph.
     * @return
     */
    public static Query generateCyclicQueryWidthN(int width, int n, int noise)  {
        if(n < width * 2){
            System.out.println("N must be at least twice the size of the width parameter. n = " + n + ", Width = "+ width);
            System.out.println("Setting n to: " + width + " * 2 = " + (width*2));
            n = width * 2;
        }
        boolean noiseVariables = noise > 0;
        Random RNG = new Random();
        Query result = new Query();
        Attribute previous = new Attribute(getAttributeName(0,noiseVariables));
        for(int i = 0; i < n; i++){
            Relation relation = new Relation("s"+i);
            relation.addAttribute(previous);
            Attribute next = new Attribute(getAttributeName(i+1,noiseVariables));
            relation.addAttribute(next);
            result.addRelation(relation);
            previous = next;
        }
        for(int i = 1; i < width; i++) {
            Relation relation = new Relation("s" + (n + i - 1));
            //relation.addAttribute(previous);
            relation.addAttribute(result.getRelations().get(n - i).getAttributes().get(0));
            relation.addAttribute(result.getRelations().get(i - 1).getAttributes().get(0));
            result.addRelation(relation);
        }
        result.setHeadToFirstRelation();

        for(int i = 0; i < noise; i++){
            int x = RNG.nextInt(n+1);
            Attribute noiseStart = new Attribute(getAttributeName(n+1+i,true));
            Attribute noiseEnd = result.getRelations().get(x).getAttributes().get(0);
            Relation noiseRelation = new Relation("n"+i);
            noiseRelation.addAttribute(noiseStart);
            noiseRelation.addAttribute(noiseEnd);
            result.addRelation(noiseRelation);
        }
        return result;
    }







    /*public static Query generateCyclicQueryWidth3(int n, int noise){
		boolean noiseVariables = noise > 0;
		Random RNG = new Random();
		Query result = new Query();
		Attribute previous = new Attribute(getAttributeName(0,noiseVariables));
		for(int i = 0; i < n/2-1; i++){
			Relation relation = new Relation("s"+i);
			relation.addAttribute(previous);
			Attribute next = new Attribute(getAttributeName(i+1,noiseVariables));
			relation.addAttribute(next);
			result.addRelation(relation);
			previous = next;
		}
		Relation relation = new Relation("s"+(n/2-1));
		relation.addAttribute(previous);
		relation.addAttribute(result.getRelations().get(0).getAttributes().get(0));
		result.addRelation(relation);
                
                for(int i = n/2; i < n; i++){
			relation = new Relation("s"+(i));
			relation.addAttribute(previous);
			Attribute next = new Attribute(getAttributeName(i,noiseVariables));
			relation.addAttribute(next);
			result.addRelation(relation);
			previous = next;
		}
		relation = new Relation("s"+(n));
		relation.addAttribute(previous);
                relation.addAttribute(result.getRelations().get(((n/2+1))).getAttributes().get(0));
		result.addRelation(relation);
                
		result.setHeadToFirstRelation();
		
                
                /// NOISE GENERATION NOT YET IMPLEMENTED
//		for(int i = 0; i < noise; i++){
//			int x = RNG.nextInt(n+1);
//			Attribute noiseStart = new Attribute(getAttributeName(n+1+i,true));
//			Attribute noiseEnd = result.getRelations().get(x).getAttributes().get(0);
//			Relation noiseRelation = new Relation("n"+i);
//			noiseRelation.addAttribute(noiseStart);
//			noiseRelation.addAttribute(noiseEnd);
//			result.addRelation(noiseRelation);
//		}
		return result;
	}

	
	public static Query generateCyclicQueryWidth3(int n){
		return generateCyclicQueryWidth3(n,0);
	}
	public static Tree generateUncontainedTree(){
		int n = 2;
		Attribute one = new Attribute(getAttributeName(0,true));
		Attribute two = new Attribute(getAttributeName(1,true));
		Node node = new Node(null);
		node.addAttribute(one);
		node.addAttribute(two);
		Tree result = new Tree(node);
		
		one = new Attribute(getAttributeName(1,true));
		two = new Attribute(getAttributeName(2,true));
		Attribute three = new Attribute(getAttributeName(0, true));
		Node node2 = new Node(node);
		node2.addAttribute(one);
		node2.addAttribute(two);
		node2.addAttribute(three);
		result.addNode(node2);
		
		one = new Attribute(getAttributeName(2,true));
		two = new Attribute(getAttributeName(0,true));
		node = new Node(node2);
		node.addAttribute(one);
		node.addAttribute(two);
		result.addNode(node);
		
		//Above is part for A -> B -> C -> A (last connection broken). TODO add second cycle A -> D -> E -> A with last connection broken (A,D;D,E,A;E,A)
		
		return result;
	}
	*/
	private static String getAttributeName(int x, boolean noise){
		String result = "";
		result += (noise) ? noiseAlphabet.charAt(x%13) : alphabet.charAt(x%13);;
		result += x/13;
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
