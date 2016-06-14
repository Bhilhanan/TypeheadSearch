package testReloaded;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import srcReloaded.Item;
import srcReloaded.Type;
import srcReloaded.TypeAheadService;

public class SolutionTest {

	TypeAheadService service;
	
	@Before
	public void init(){
		service = new TypeAheadService();
	}

	@Test
	public void shouldAdd() {
		String id = "id";
		addDummyItem(id,Type.USER,1,0,"sample data");
		Item actualItem = service.get(id);
		assertNotNull(actualItem);
		assertEquals(Type.USER,actualItem.getType());
	}

	private void addDummyItem(String key, Type type, int score, int insertedIndex,String data) {
		Item newItem=new Item(type,key,score,insertedIndex,data);
		service.add(newItem);
	}
	
	@Test
	public void shouldDelete(){
		String id = "id";
		addDummyItem(id,Type.USER,1,0,"sample data");
		assertEquals(1,service.size());
		service.delete(id);
		assertEquals(0,service.size());
	}
	
	@Test
	public void shouldQuery(){
		String id1="user1";
		String id2="topic1";
		addDummyItem(id1,Type.USER,1,0,"sample data");
		addDummyItem(id2,Type.TOPIC,2,1,"samp topic");
		List<Item> actualResult = service.query(2,"sam");
		assertEquals(2,actualResult.size());
		assertEquals("user1",actualResult.get(1).getId());
		assertEquals("topic1",actualResult.get(0).getId());
	}
	
	@Test
	public void shouldQueryScoreTied(){
		String id1="user1";
		String id2="topic1";
		addDummyItem(id1,Type.USER,1,0,"sample data");
		addDummyItem(id2,Type.TOPIC,1,1,"samp topic");
		List<Item> actualResult = service.query(2,"sam");
		assertEquals(2,actualResult.size());
		assertEquals("user1",actualResult.get(1).getId());
		assertEquals("topic1",actualResult.get(0).getId());
	}
	
	@Test
	public void shouldWeightedQuery(){
		String id1="user1";
		String id2="topic1";
		addDummyItem(id1,Type.USER,3,0,"sample data");
		addDummyItem(id2,Type.TOPIC,1,1,"samp topic");
		Map<Type,Float> boosts=new HashMap<Type,Float>();
		boosts.put(Type.TOPIC, 5f);
		List<Item> actualResult = service.wQuery(2,boosts,"sam");
		assertEquals(2,actualResult.size());
		assertEquals("user1",actualResult.get(1).getId());
		assertEquals("topic1",actualResult.get(0).getId());
	}
}
