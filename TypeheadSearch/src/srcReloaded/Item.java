package srcReloaded;

public class Item {

	private Type type;
	private String id;
	private float score;
	private String data;
	private int insertedIndex;

	public Item(Type type, String id, float score, int insertedIndex, String data) {
		this.type = type;
		this.id = id;
		this.score = score;
		this.data = data;
		this.insertedIndex = insertedIndex;
	}

	public String getData() {
		return data;
	}

	public String getId() {
		return id;
	}

	public float getScore() {
		return score;
	}

	public Type getType() {
		return type;
	}

	public int getInsertedIndex() {
		return insertedIndex;
	}
	
	@Override
	public String toString(){
		return "type,id,score,index="+type+","+id+","+score+","+insertedIndex;
	}
}
