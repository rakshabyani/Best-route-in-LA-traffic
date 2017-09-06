import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;


class SundayNode{
	String state;
	int cost;
	public SundayNode() {
		state = null;
		cost = 0;
	}
	public SundayNode(String state, int cost){
		this.state=state;
		this.cost=cost;
	}
}

//Node consists of state vertex and the weight of the edge between source and state

class Node{

	String state;

	int cost ;
	String parent;
	int orderOfOccurance;
	int pathCost;
	int totalCost;

	public Node(String name, int cost, String parent,  int order){
		this.state=name;
		this.parent = parent ;
		this.orderOfOccurance = order;
		this.cost = cost ;
		pathCost = 0;
		totalCost = 0;
	}

	public Node() {
		state = null ;
		parent = null ;
		orderOfOccurance = 0;
		cost = 0 ;
		pathCost = 0;
		totalCost = 0;
	}

	//When enqueueing the initial start state 
	public Node(String source) {
		state = source ;
		parent = null ;
		orderOfOccurance = 0;
		cost = 0;
		pathCost = 0;
		totalCost = 0;
	}

	public String getName() {
		return state;
	}
}

class Graph{

	Map<String, List<Node>> adjacencyList;

	public Graph(){
		adjacencyList = new HashMap<>();
	}

	private boolean isEmpty(List<Node> list) {
		return (list == null || list.isEmpty());
	}

	public void addNode(String source, Node state){

		//add to list of adjacency vertices
		List<Node> children = adjacencyList.get(source);
		if( isEmpty(children) ){
			children = new ArrayList<Node>();
			children.add(state);
		}
		else{
			children.add(state);
		}

		//map the adjacent state vertices to source key
		adjacencyList.put(source, children);
	}
}

public class homework {


	public static void main(String[] args) {

		long lStartTime = System.currentTimeMillis();
		String algorithm, start_state, goal_state;
		int noOfLines;

		String input_file = ("input.txt");
		String output_file = ("output.txt") ; 

		try 
		{
			//read input from input.txt file
			InputStream in = new FileInputStream(input_file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in)) ;

			List<Node> closed = new ArrayList<>();
			String state, parent;
			int cost ;
			algorithm = reader.readLine().trim();
			start_state = reader.readLine().trim();
			goal_state = reader.readLine().trim();
			noOfLines = Integer.parseInt(reader.readLine().trim()); 
			Graph g = new Graph();
			int order = 1;
			int counter = noOfLines;
			if(goal_state.equals(start_state))
				closed.add(new Node(goal_state));
			else{
				while(counter > 0){

					String liveTraffic = reader.readLine().trim();
					String arr[] = liveTraffic.trim().split("\\s+");
					parent = arr[0];
					state =arr[1];
					cost = Integer.parseInt(arr[2]);
					Node temp = new Node(state,cost,parent,order++);
					g.addNode(arr[0], temp);
					counter--;
				}

				int noOfSundayLines = Integer.parseInt(reader.readLine().trim()); 
				Map<String, Integer> sunTraffic = new HashMap<>();
				while(noOfSundayLines > 0){

					String sunLiveTraffic = reader.readLine().trim();
					String arr[] = sunLiveTraffic.trim().split(" ");
					state =arr[0];
					cost = Integer.parseInt(arr[1]);
					sunTraffic.put(state, cost);
					noOfSundayLines--;
				}
					
				if("BFS".equalsIgnoreCase(algorithm))
					closed = BFS_Search(g,start_state, goal_state);
				else if("DFS".equalsIgnoreCase(algorithm))
					closed = DFS_Search(g,start_state, goal_state);
				else if("UCS".equalsIgnoreCase(algorithm))
					closed = UCS_Search(g,start_state, goal_state);
				else if("A*".equalsIgnoreCase(algorithm))
					closed = A_Search(g,start_state, goal_state,sunTraffic);

			}
			reader.close();


			//Write output in output.txt file
			File file = new File(output_file);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			for(int i = closed.size() - 1 ; i>=0; i--){
				Node n = closed.get(i);
				bw.write(n.state+" "+n.cost+"\r\n");
			}
			bw.close(); 
			long lEndTime = System.currentTimeMillis();
			long difference = lEndTime - lStartTime;

			//System.out.println("Elapsed milliseconds: " + difference);

		} catch (IOException x) {
			System.err.println("Error in input file");
		}
	}

	private static List<Node> A_Search(Graph g, String start_state, String goal_state, Map<String, Integer> sunTraffic) {

		boolean success = false;
		List<Node> open = new ArrayList<>();
		List<Node> closed = new ArrayList<>();
		Node currNode = new Node();
		List<Node> children = new ArrayList<>();
		Node initialState = new Node(start_state);
		open.add(initialState);
		if(open!=null){
			while(!open.isEmpty()){
				for(Node n1 :open){
				/*if(n1.state.equals("H")&& n1.pathCost == 24)
					System.out.println(n1.state + "=" + n1.pathCost);*/
				}
				currNode = open.remove(0);
				//	System.out.println(currNode.state + "=" + currNode.pathCost);
				
				/*for(Node n1 :closed){
					if(n1.state.equals("H"))
					System.out.println(n1.state + "=" + n1.pathCost);
					if(n1.state.equals("H")&& n1.pathCost == 24)
						System.out.println("Yes");
				}*/
				
				closed.add(currNode);
				
				
				
				
				
				/*if(currNode.state.equals("H") && currNode.pathCost == 24)
					System.out.println(currNode.state + "    =   After Adding to closed queue = " + currNode.pathCost);*/
				for(Node n :closed){
					if(n.state.equals("H") && n.pathCost == 24)
						System.out.println("----------------------------------------" + n.orderOfOccurance);
					System.out.print(n.state + "=" + n.pathCost + "      ");
				}
				System.out.println("\n\n");
				if(goal_state.equals(currNode.state)){
					
					
					success = true ;
					break;
				}
				else{
					children = g.adjacencyList.get(currNode.state);
					
					
					if(children!=null){

						
						for(Node child : children){
							//System.out.print("{" + child.state + "=" +child.cost + "}");
							
							child.pathCost = child.cost + currNode.pathCost;
							/*if(child.state.equals("H")){
								System.out.println(child.pathCost);
							}*/
							/*if(currNode.state.equals("H")){
								System.out.println(currNode.cost + " + " + currNode.pathCost + "  "+sunTraffic.get(currNode.state) +"="+currNode.totalCost);
							}*/
							child.totalCost = child.pathCost + sunTraffic.get(child.state);
							int closedNodeIndex = getNodeIndex(closed, child.state);
							int inOpenQueueIndex = getNodeIndex(open, child.state);
							Node inOpenQueue = null;
							if(inOpenQueueIndex != -1 )
								inOpenQueue = open.get(inOpenQueueIndex);
							if( inOpenQueue == null && closedNodeIndex == -1 ){
								open.add(child);

							}

							else if( inOpenQueue != null){
								if(child.totalCost < inOpenQueue.totalCost){
				//					if(child.state.equals("H"))
					//					System.out.println(child.state + "    =   Updating Open = " + child.pathCost);
									
									open.remove(inOpenQueue);
									open.add(child);
									/*System.out.println(child.state + " " + child.pathCost + "  " + child.parent);*/
								}
								
							}

							else if( closedNodeIndex != -1 ){
								Node n = closed.get(closedNodeIndex);
								if(child.totalCost < n.totalCost){
		//							if(child.state.equals("H"))
			//							System.out.println(child.state + "    =   Updating Closed = " + child.pathCost);
									
									closed.remove(n);
									
									open.add(child);
									
								}
							
								
							}	
						
						
						}
						
					
					}
					
					
					sortByTotalCost(open);
					
				
				}
				
				
			}
		}

		
		List<Node> optimalSolution = new ArrayList<>();
		int index = getNodeIndex(closed, goal_state);
		while(index!=0){
			currNode = closed.get(index);
			currNode.cost = currNode.pathCost ;
			optimalSolution.add(currNode);
			//System.out.println(currNode.state + "  " + currNode.cost);
			index=getNodeIndex(closed, currNode.parent);
		}
		optimalSolution.add(initialState);
		//System.out.println(initialState.state + "  " + initialState.cost);

		return optimalSolution;

	}

	/* Method to sort the Queue for A* search */
	private static void sortByTotalCost(List<Node> open) {
		Node ele1,ele2;
		for (int i = 0; i < open.size() ; i++)
		{
			int index = i;
			for (int j = i + 1; j < open.size(); j++){
				ele1 = open.get(j);
				ele2 = open.get(index);
				if(ele1.totalCost < ele2.totalCost)
					index = j;
				/*else if( ele1.totalCost == ele2.totalCost && ele1.orderOfOccurance < ele2.orderOfOccurance)
					index = j;*/
			}
			if(i!=index){
				Node n = open.get(i);
				Node temp = open.get(index);
				open.set(index, n);
				open.set(i, temp);
			}
		}

	}

	private static Node getQueueNode(Queue<Node> open, String childState){
		for(Node temp : open){
			if(childState.equals(temp.state)){
				return temp;
			}
		}
		return null;
	}

	private static int getNodeIndex(List<Node> list, String childState){
		int index = -1;
		int pointerLocation = 0;
		if(list !=  null){
			for(Node temp : list){
				if(childState.equals(temp.state)){
					index = pointerLocation;
					return index;
				}
				pointerLocation++;
			}
		}
		return index ;
	}

	private static Node getStackNode(Stack<Node> open, String childState) {
		if(open!=null){
			if(!open.isEmpty()){
				for(Node temp : open){
					if(childState.equals(temp.state)){
						return temp;
					}	
				}
			}
		}
		return null;
	}


	private static List<Node> UCS_Search(Graph g, String start_state, String goal_state) {

		boolean success = false;
		List<Node> open = new ArrayList<>();
		List<Node> closed = new ArrayList<>();
		Node currNode = new Node();
		List<Node> children = new ArrayList<>();
		Node initialState = new Node(start_state);
		open.add(initialState);
		if(open!=null){
			while(!open.isEmpty()){
				currNode = open.remove(0);
				closed.add(currNode);
				if(goal_state.equals(currNode.state)){
					success = true ;
				}
				else{
					children = g.adjacencyList.get(currNode.state);
					if(children!=null){
						for(Node child : children){
							//Node child = children.get(0) ;
							child.pathCost = child.cost + currNode.pathCost;
							int closedNodeIndex = getNodeIndex(closed, child.state);
							int inOpenQueueIndex = getNodeIndex(open, child.state);
							Node inOpenQueue = null;
							if(inOpenQueueIndex != -1 )
								inOpenQueue = open.get(inOpenQueueIndex);
							if( inOpenQueue == null && closedNodeIndex == -1 ){
								open.add(child);
								//System.out.println(child.state + " " + child.pathCost + "  " + child.parent);
							}

							else if( inOpenQueue != null){
								if(child.pathCost < inOpenQueue.pathCost){
									open.remove(inOpenQueue);
									open.add(child);
									//System.out.println(child.state + " " + child.pathCost + "  " + child.parent);
								}
							}

							else if( closedNodeIndex != -1 ){
								Node n = closed.get(closedNodeIndex);
								if(child.pathCost < n.pathCost){
									closed.remove(n);
									open.add(child);
									//System.out.println(child.state + " " + child.pathCost + "  " + child.parent);
								}
							}	
						}
					}
					sortByPathCost(open);
				}	
			}
		}	
		List<Node> optimalSolution = new ArrayList<>();
		int index = getNodeIndex(closed, goal_state);
		while(index!=0){
			currNode = closed.get(index);
			currNode.cost = currNode.pathCost ;
			optimalSolution.add(currNode);
			//System.out.println(currNode.state + "  " + currNode.cost);
			index=getNodeIndex(closed, currNode.parent);
		}
		optimalSolution.add(initialState);
		//System.out.println(initialState.state + "  " + initialState.cost);

		return optimalSolution;
	}

	private static void sortByPathCost(List<Node> open) {

		Node ele1,ele2;
		for (int i = 0; i < open.size() ; i++)
		{
			int index = i;
			for (int j = i + 1; j < open.size(); j++){
				ele1 = open.get(j);
				ele2 = open.get(index);
				if(ele1.pathCost < ele2.pathCost)
					index = j;
				else if( ele1.pathCost == ele2.pathCost && ele1.orderOfOccurance < ele2.orderOfOccurance)
					index = j;
			}
			if(i!=index){
				Node n = open.get(i);
				Node temp = open.get(index);
				open.set(index, n);
				open.set(i, temp);
			}
		}
	}

	private static List<Node> DFS_Search(Graph g, String start_state, String goal_state) {

		Stack<Node> open = new Stack<>();
		List<Node> closed = new ArrayList<>();
		boolean success = false;
		Node currNode = new Node();
		List<Node> children = new ArrayList<>();
		Node initialState = new Node(start_state);
		open.push(initialState);
		if(open!=null){
			while(!open.isEmpty()){
				currNode = open.pop();
				closed.add(currNode);
				if(goal_state.equals(currNode.state)){
					success=true ;
					break;
				}

				else{
					children = g.adjacencyList.get(currNode.state);
					if(children!=null){
						for(int i = children.size() - 1 ; i>=0 ; i--){
							Node child = children.get(i);
							child.pathCost = currNode.pathCost + 1 ;
							int closedNodeIndex = getNodeIndex(closed, child.state);
							Node inOpenStack = getStackNode(open, child.state);
							if( inOpenStack==null && closedNodeIndex == -1){
								open.add(child);
							}

							else if(inOpenStack !=null){
								if(child.pathCost < inOpenStack.pathCost){
									open.remove(inOpenStack);
									open.push(inOpenStack);
								}
								else if(child.pathCost == inOpenStack.pathCost ){
									int index = open.indexOf(inOpenStack);
									open.add(index+1, child);
								}
							}

							else if( closedNodeIndex != -1 ){
								Node n = closed.get(closedNodeIndex);
								if(child.pathCost < n.pathCost){
									closed.remove(n);
									open.add(child);
								}
							}
						}
					}
				}
			}
		}			

		List<Node> optimalSolution = new ArrayList<>();
		int index = getNodeIndex(closed, goal_state);
		while(index!=0){
			currNode = closed.get(index);
			currNode.cost = currNode.pathCost;
			optimalSolution.add(currNode);
			//System.out.println(currNode.state + "  " + currNode.depth);
			index=getNodeIndex(closed, currNode.parent);
		}
		optimalSolution.add(initialState);
		//System.out.println(initialState.state + "  " + initialState.depth);
		return optimalSolution;
	}


	private static List<Node> BFS_Search(Graph g, String start_state, String goal_state) {

		boolean success = false;
		Queue<Node> open = new LinkedList<>();
		List<Node> closed = new ArrayList<>();
		Node currNode = new Node();
		List<Node> children = new ArrayList<>();
		Node initialState = new Node(start_state);
		open.add(initialState);
		while(!open.isEmpty()){

			currNode = open.remove();
			if(goal_state.equals(currNode.state)){
				success = true ;
			}

			else{
				children = g.adjacencyList.get(currNode.state);
				if(children != null){
					for(Node child:children){
						//Node child = children.get(0) ;
						child.pathCost = currNode.pathCost + 1;
						int closedNodeIndex = getNodeIndex(closed, child.state);
						Node inOpenQueue = getQueueNode(open, child.state);
						if( inOpenQueue == null && closedNodeIndex == -1 ){
							open.add(child);
						}

						else if( inOpenQueue != null){
							if(child.pathCost < inOpenQueue.pathCost){
								open.remove(inOpenQueue);
								open.add(child);
							}
						}

						else if( closedNodeIndex != -1 ){
							Node n = closed.get(closedNodeIndex);
							if(child.pathCost < n.pathCost){
								closed.remove(n);
								open.add(child);
							}
						}	
					}
				}
			}
			closed.add(currNode);
		}	
		List<Node> optimalSolution = new ArrayList<>();
		int index = getNodeIndex(closed, goal_state);
		while(index!=0){
			currNode = closed.get(index);
			currNode.cost = currNode.pathCost;
			optimalSolution.add(currNode);
			index=getNodeIndex(closed, currNode.parent);
		}
		optimalSolution.add(initialState);
		return optimalSolution;
	}

}
