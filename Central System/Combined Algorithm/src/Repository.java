

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Repository {
	
	private static ArrayList<Location> locations = new ArrayList<Location>();
	
	public static void addLocation(Location location) {
		locations.add(location);
	}
	public static Location getLocation(int index) {
		return locations.get(index);
	}
	public static int getNumberofLocations() {
		return locations.size();
	}
	public static void clearLocation() {
		locations.clear();
	}
	public static ArrayList<Location> getLocationArray() {
		return locations;
	}
}