import java.io.File;
import java.util.ArrayList;

public class Repository {
	
	private ArrayList<Location> locations = new ArrayList<Location>();
	
	public void addLocation(Location location) {
		locations.add(location);
	}
	public Location getLocation(int index) {
		return locations.get(index);
	}
	public int getNumberofLocations() {
		return locations.size();
	}
	public void clearLocation() {
		locations.clear();
	}
	public ArrayList<Location> getLocationArray() {
		return locations;
	}
	
}