package srcReloaded.service;

import java.util.Comparator;
import java.util.Map;

public class ItemComparator implements Comparator<Item> {

	private Map<String, Float> boosts;

	@Override
	public int compare(Item o1, Item o2) {
		if (score(o1) == score(o2)) {
			return Integer.valueOf(o2.getInsertedIndex()).compareTo(Integer.valueOf(o1.getInsertedIndex()));
		}
		return Float.valueOf(score(o2)).compareTo(Float.valueOf(score(o1)));
	}

	private float score(Item o1) {
		if (boosts == null) {
			return o1.getScore();
		}
		return o1.getScore() * getBoost(o1.getType().name())*getBoost(o1.getId());
	}

	private float getBoost(String key) {
		if(boosts.containsKey(key)){
			return boosts.get(key);
		}
		return 1f;
	}

	public void setBoosts(Map<String, Float> boostsMap) {
		this.boosts = boostsMap;
	}
}
