


import java.util.ArrayList;
import java.util.Collections;


public class SingleTour{
	private ArrayList<Location> tour = new ArrayList<>();
	private double distance = 0.0;
	
//	public SingleTour() {
//		for(int i=0;i<Repository.getNumberofLocations();++i) {
//			tour.add(null);
//		}
//	}
	
	public SingleTour(ArrayList<Location> tour) {
		ArrayList<Location> CurrentTour = new ArrayList<>();
		for(int i=0;i<tour.size();++i) {
			CurrentTour.add(null);
		}
		for(int i=0;i<tour.size();++i) {
			CurrentTour.set(i, tour.get(i));
		}
		this.tour = CurrentTour;
	}
	
	public double getDistance() {
		if (distance == 0) {
			int tourDistance = 0;
			for (int locationIndex=0;locationIndex<getTourSize();++locationIndex) {
				Location fromLocation = getLocation(locationIndex);
				Location destinationLocation;
				
				if (locationIndex+1 < getTourSize())
					destinationLocation = getLocation(locationIndex+1);
				else
					destinationLocation = getLocation(0);
				
				tourDistance += distance(fromLocation.getLat(), fromLocation.getLon(), destinationLocation.getLat(), destinationLocation.getLon());
			}
			this.distance = tourDistance;
		}
		return distance;
	}
	
	private double distance(double lat1, double lon1, double lat2, double lon2) {
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

	public ArrayList<Location> getTour(){
		return this.tour;
	}
//	public void generateIndividual() {
//		for(int locationIndex = 0; locationIndex<Repository.getNumberofLocations();++locationIndex) {
//			setLocation(locationIndex, Repository.getLocation(locationIndex));
//		}
//		Collections.shuffle(tour);
//	}
	
	public void setLocation(int locationIndex, Location location) {
		this.tour.set(locationIndex, location);
		this.distance = 0.0;
	}
	public Location getLocation(int tourPosition) {
		return tour.get(tourPosition);
	}

	public int getTourSize() {
		return this.tour.size();
	}
	
	@Override
	
	public String toString() {
		String s = "";
		
		for(int i=0;i<getTourSize();++i)
			s += getLocation(i).getLat()+", "+getLocation(i).getLon()+" -> "+i+"\n";
	
		return s;
	}
}