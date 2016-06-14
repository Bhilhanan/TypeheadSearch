package srcReloaded.service;

public enum Type {
	user, topic, question, board;

	public static boolean contains(String type) {
		for (Type choice : values()) {
			if (choice.name().equals(type)) {
				return true;
			}
		}
		return false;
	}
}
