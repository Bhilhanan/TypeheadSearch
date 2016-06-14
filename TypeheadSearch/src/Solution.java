import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {

	HashMap<String,Item> itemCollection=new HashMap<String,Item>();
	
		void add(String type,String id,float score,String data,int index){
		if(type==null||id==null||data==null){
			return;
		}
		HashMap<Character,CharNode> newData=new HashMap<Character, CharNode>();
		Item newItem=new Item(type,id,score,newData,index);
		newItem.insertData(data);
		itemCollection.put(id,newItem);
		
	}
	
	

	void deleteItem(String id){
		if(itemCollection.size()==0){
			return;
		}
		Item itemDeleted=itemCollection.remove(id);
		if(itemDeleted==null){
			//unable to delete or the entity delete had null as value
		}
	}
	
	void query(int numOfResults,String queryStr,
			HashMap<String,Float> typeBoostMap,HashMap<String,Float> idBoostMap){
		
		ArrayList<Item> searchResult=new ArrayList<Item>();
		
		if(queryStr==null || queryStr.equals("") || numOfResults==0){
			return;
		}
		
		String[] tokens=queryStr.split(" ");
		boolean flag=false;
		for(Map.Entry<String, Item> entry:itemCollection.entrySet()){
			flag=false;
			Item item=entry.getValue();
			for(String token:tokens){
				if(!item.doesTokenExist(token)){
					flag=true;
					break;
				}				
			}
			if(flag){
				continue;
			}
			if(typeBoostMap==null && idBoostMap==null){
				searchResult.add(item);
				continue;
			}
			
			if(typeBoostMap.containsKey(item.getType()) && idBoostMap.containsKey(item.getId())){
				Item newItem=item;
				newItem.setScore(item.getScore()*typeBoostMap.get(item.getType())*idBoostMap.get(item.getId()));
			}
			else if(typeBoostMap.containsKey(item.getType())){
					Item newItem=item;
					newItem.setScore(item.getScore()*typeBoostMap.get(item.getType()));
					item=newItem;
				}
			else if(idBoostMap.containsKey(item.getId())){
					Item newItem=item;
					newItem.setScore(item.getScore()*idBoostMap.get(item.getId()));
					item=newItem;
				
			}
			searchResult.add(item);
			
			
		}

		Collections.sort(searchResult);
		for(int i=0;i<numOfResults && i<searchResult.size();i++){
			System.out.print(searchResult.get(i).getId());
		}
	}
	
	
	public static void main(String[] args){
		
		Solution service=new Solution();
		Scanner console = null;
		int count=0;
		//try{
			//get input
			console=new Scanner(System.in);
			
			if(!console.hasNext()){
				return;
			}
			
			int numOfCommands=Integer.parseInt(console.nextLine());
			String line;
			String command;
			String[] params;
			String[] boostFor;
			String boost;
			while(numOfCommands>0){
				line=console.nextLine();
				command=line.substring(0, line.indexOf(" "));
				switch(command){
				case "ADD":
					params=line.split(" ", 5);
					service.add(params[1], params[2], Float.parseFloat(params[3]), params[4].trim(), count++);
					break;
				case "DEL":
					params=line.split(" ");
					service.deleteItem(params[1]);
					break;
				case "QUERY":
					params=line.split(" ", 3);
					service.query(Integer.parseInt(params[1]), params[2].toLowerCase(),null,null);
					break;
				case "WQUERY":
					params=line.split(" ", 3);
					int numOfBoosts=Integer.parseInt(params[2].substring(0, params[2].indexOf(" ")));
					HashMap<String,Float> typeBoostMap=new HashMap<String, Float>();
					HashMap<String, Float> idBoostMap=new HashMap<String, Float>();
					String[] boosts=params[2].split(" ", numOfBoosts+1);
					String queryData=boosts[boosts.length-1];
					for(int i=0;i<boosts.length-1;i++){
						
						boost=boosts[i];
						boostFor=boost.split(":");
						if(boostFor.equals("topic") || boostFor.equals("user") || 
								boostFor.equals("question") || boostFor.equals("board")){
							typeBoostMap.put(boostFor[0], Float.parseFloat(boostFor[1]));
						}
						else{
							idBoostMap.put(boostFor[0], Float.parseFloat(boostFor[1]));
						}
					}
					service.query(Integer.parseInt(params[1]), queryData, typeBoostMap, idBoostMap);
					break;
				}
				numOfCommands--;
			}
		//}catch(Exception e){
			//System.out.println(e);
		//}finally{
			console.close();
		//}
		
	}
}


