package testReloaded;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import srcReloaded.service.Item;
import srcReloaded.service.Type;
import srcReloaded.service.TypeAheadService;

public class TypeAheadServiceTest {

	TypeAheadService service;
	
	@Before
	public void init(){
		service = new TypeAheadService();
	}

	@Test
	public void shouldAdd() {
		String id = "id";
		addDummyItem(id,Type.user,1,0,"sample data");
		Item actualItem = service.get(id);
		assertNotNull(actualItem);
		assertEquals(Type.user,actualItem.getType());
	}

	private Item addDummyItem(String key, Type type, float score, int insertedIndex,String data) {
		Item newItem=new Item(type,key,score,insertedIndex,data);
		service.add(newItem);
		return newItem;
	}
	
	@Test
	public void shouldDelete(){
		String id = "id";
		addDummyItem(id,Type.user,1,0,"sample data");
		assertEquals(1,service.size());
		service.delete(id);
		assertEquals(0,service.size());
	}
	
	@Test
	public void shouldQuery(){
		String id1="user1";
		String id2="topic1";
		addDummyItem(id1,Type.user,1,0,"sample data");
		addDummyItem(id2,Type.topic,2,1,"samp topic");
		List<Item> actualResult = service.query(2,"sam");
		assertEquals(2,actualResult.size());
		assertEquals("topic1",actualResult.get(0).getId());
		assertEquals("user1",actualResult.get(1).getId());
	}
	
	@Test
	public void shouldQueryScoreTied(){
		String id1="u1";
		String id2="u2";
		String id3="t1";
		String id4="q1";
		String id5="q2";
		Item item1 = addDummyItem(id1,Type.question,1f,0,"Adam D'Angelo");
		Item item2 = addDummyItem(id2,Type.question,1f,1,"Adam Black");
		Item item3 = addDummyItem(id3,Type.question,0.8f,2,"Adam D'Angelo");
		Item item4 = addDummyItem(id4,Type.question,0.5f,3,"What does Adam D'Angelo do at Quora?");
		Item item5 = addDummyItem(id5,Type.question,0.5f,4,"How did Adam D'Angelo learn programming?");
		List<Item> actualResult = service.query(10,"Adam");
		List<Item> expectedResult=new ArrayList<Item>();
		expectedResult.add(item2);
		expectedResult.add(item1);
		expectedResult.add(item3);
		expectedResult.add(item5);
		expectedResult.add(item4);
		assertEquals(expectedResult.size(),actualResult.size());
		assertEquals(expectedResult,actualResult);
	}
	
	@Test
	public void shouldWeightedQuery(){
		String id1="user1";
		String id2="topic1";
		addDummyItem(id1,Type.user,3,0,"sample data");
		addDummyItem(id2,Type.topic,1,1,"samp topic");
		Map<String,Float> boosts=new HashMap<String,Float>();
		boosts.put(Type.topic.name(), 6f);
		boosts.put(id1, 2f);
		List<Item> actualResult = service.wQuery(2,boosts,"sam");
		assertEquals(2,actualResult.size());
		assertEquals("topic1",actualResult.get(0).getId());
		assertEquals("user1",actualResult.get(1).getId());
	}
}
