package model;

public class Location {
	private final String name;
	private final String Island;

	public Location(String name, String island) {
		this.name = name;
		Island = island;
	}

	public String getName() {
		return name;
	}

	public String getIsland() {
		return Island;
	}
}
