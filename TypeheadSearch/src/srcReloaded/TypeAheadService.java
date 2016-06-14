package srcReloaded;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class TypeAheadService {
	Map<String, Item> dataCollection;
	private ItemComparator comparator;

	public TypeAheadService() {
		comparator = new ItemComparator();
		dataCollection = new HashMap<String, Item>();
	}

	public void add(Item newItem) {
		dataCollection.put(newItem.getId(), newItem);
	}

	public Item get(String id) {
		return dataCollection.get(id);
	}

	public int size() {
		return dataCollection.size();
	}

	public void delete(String id) {
		dataCollection.remove(id);
	}

	public List<Item> query(int numOfResults, String queryStr) {
		PriorityQueue<Item> reultQueue = new PriorityQueue<Item>(10, comparator);
		boolean found = false;
		for (Item item : dataCollection.values()) {
			found = isQueryStringFound(queryStr, item);
			if (found) {
				reultQueue.add(item);
			}
		}
		ArrayList<Item> result = new ArrayList<Item>(reultQueue);
		int toIndex=numOfResults>result.size()?result.size():numOfResults;
		return result.subList(0, toIndex);
	}

	private boolean isQueryStringFound(String queryStr, Item item) {
		boolean found = true;
		String[] queryStrArray = queryStr.split(" ");

		for (String word : queryStrArray) {
			if (!isCaseInsensitivePrefix(word, item.getData())) {
				found = false;
				break;
			}
		}
		return found;
	}

	private boolean isCaseInsensitivePrefix(String word, String data) {
		String[] dataArray = data.split(" ");
		for (String token : dataArray) {
			if (token.startsWith(word)) {
				return true;
			}
		}
		return false;
	}

	public List<Item> wQuery(int numOfResults, Map<Type, Float> boosts, String queryStr) {
		comparator.setBoosts(boosts);
		List<Item> result = query(numOfResults, queryStr);
		comparator.setBoosts(null);
		return result;
	}

}
