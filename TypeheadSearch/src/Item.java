import java.util.HashMap;


public class Item implements Comparable<Item>{
	private String type;
	private String id;
	private float score;
	private HashMap<Character, CharNode> dataString;
	private int index;
	
	public Item(String type, String id, float score,
			HashMap<Character, CharNode> data,int index) {
		this.type = type;
		this.id = id;
		this.score = score;
		this.dataString = data;
		this.index=index;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public HashMap<Character, CharNode> getData() {
		return dataString;
	}
	public void setData(HashMap<Character, CharNode> data) {
		this.dataString = data;
	}
	
	public void insertData(String data) {
		
		String[] tokens=data.split(" ");
		
		for(String token:tokens){
			if(!dataString.containsKey(Character.toLowerCase(token.charAt(0)))){
				CharNode rootNode=new CharNode(token.charAt(0));
				dataString.put(Character.toLowerCase(token.charAt(0)), rootNode);
			}
			CharNode tmp=dataString.get(token.charAt(0));
			addToken(token,1,dataString.get(Character.toLowerCase(token.charAt(0))));
		}
		
	}
	
	private void addToken(String token,int startIndex,CharNode node) {
		if(startIndex==token.length()){
			return;
		}
		
		boolean childExist=false;
		char nextToken=token.charAt(startIndex);
		//check if node already contains the child node
		for(CharNode n:node.childNodes){
			if(n.key==nextToken){
				childExist=true;
				break;
			}
		}
		CharNode childNode=null;
		if(!childExist){
			childNode=new CharNode(nextToken);
			node.childNodes.add(childNode);
		}			
		addToken(token,startIndex+1,childNode);
		
	}

	private boolean doesTokenExist(String token,int index, CharNode node){
		if(index==token.length()){
			return true;
		}
		CharNode childNode=null;
		for(CharNode n:node.childNodes){
			if(Character.toLowerCase(n.key)==token.charAt(index)){
				childNode=n;
				if(!doesTokenExist(token,index+1,n)){
					continue;
				}
				else{
					return true;
				}
			}
		}
		return false;
	}
	public boolean doesTokenExist(String token) {
		if(!dataString.containsKey(token.charAt(0))){
			return false;
		}
		CharNode rootNode=dataString.get(token.charAt(0));
		return doesTokenExist(token, 1, rootNode);
	}

	@Override
	public int compareTo(Item item) {
		if(item.score>score){
			return 1;
		}
		if(item.score<score){
			return -1;
		}
		if(item.index>index){
			return 1;
		}
		return 0;
	}
	
}

