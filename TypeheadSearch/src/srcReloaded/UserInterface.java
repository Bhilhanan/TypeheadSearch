package srcReloaded;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import srcReloaded.service.Item;
import srcReloaded.service.Type;
import srcReloaded.service.TypeAheadService;

public class UserInterface {

	private static int insertedIndex = 0;

	public static void main(String[] args) {
		TypeAheadService service = new TypeAheadService();
		Scanner console = new Scanner(System.in);
		if (!console.hasNext()) {
			console.close();
			return;
		}
		int numOfCommands = Integer.parseInt(console.nextLine());
		String line;
		String command;
		while (numOfCommands > 0) {
			line = console.nextLine();
			command = line.substring(0, line.indexOf(" "));
			switch (Command.valueOf(command)) {
			case ADD:
				addItem(service, line);
				break;
			case DEL:
				deleteItem(service, line);
				break;
			case QUERY:
				queryItem(service, line);
				break;
			case WQUERY:
				weightedQueryItem(service, line);
				break;
			default:
				return;
			}
			numOfCommands--;
		}
		console.close();
	}

	private static void weightedQueryItem(TypeAheadService service, String line) {
		List<Item> result;
		String[] params;
		params = line.split(" ", 3);
		int numOfRes = Integer.parseInt(params[1]);
		int numOfBoosts = Integer.parseInt(params[2].substring(0, params[2].indexOf(" ")));
		String[] boosts = params[2].split(" ", numOfBoosts + 2);
		String queryStr = boosts[boosts.length - 1];
		Map<String, Float> boostsMap = prepareBoostMap(boosts, numOfBoosts);
		result = service.wQuery(numOfRes, boostsMap, queryStr);
		printQueryResult(result);
	}

	private static void printQueryResult(List<Item> result) {
		for (Item item : result) {
			System.out.print(item.getId() + " ");
		}
		System.out.println("");
	}

	private static Map<String, Float> prepareBoostMap(String[] boosts, int numOfBoosts) {
		Map<String, Float> boostsMap=new HashMap<String, Float>();
		String[] boost;
		for (int i = 1; i < boosts.length - 1; i++) {
			boost = boosts[i].split(":");
			boostsMap.put(boost[0], Float.parseFloat(boost[1]));
		}
		return boostsMap;
	}

	private static void queryItem(TypeAheadService service, String line) {
		List<Item> result;
		String[] params;
		params = line.split(" ", 3);
		result = service.query(Integer.parseInt(params[1]), params[2].toLowerCase());
		printQueryResult(result);
	}

	private static void deleteItem(TypeAheadService service, String line) {
		String[] params;
		params = line.split(" ");
		service.delete(params[1]);
	}

	private static void addItem(TypeAheadService service, String line) {
		String[] params;
		params = line.split(" ", 5);
		Item newItem = new Item(Type.valueOf(params[1]), params[2], Float.valueOf(params[3]), insertedIndex++,
				params[4]);
		service.add(newItem);
	}
}
