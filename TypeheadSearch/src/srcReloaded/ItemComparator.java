package srcReloaded;

import java.util.Comparator;
import java.util.Map;

public class ItemComparator implements Comparator<Item> {

	private Map<Type, Float> boosts;

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
		if(!boosts.containsKey(o1.getType())){
			return o1.getScore();
		}
		return o1.getScore()*boosts.get(o1.getType());
	}

	public void setBoosts(Map<Type, Float> boosts) {
		this.boosts = boosts;
	}
}
