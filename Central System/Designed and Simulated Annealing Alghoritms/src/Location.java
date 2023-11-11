
public class Location {
	public double lat;
	public double lon;
	
	public double distance(double lat1, double lon1, double lat2, double lon2) {
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		}
		else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			dist = dist * 1.609344;
			return (dist);
		}
	}
	public Location(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat() {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon() {
		this.lon = lon;
	}
	
}
