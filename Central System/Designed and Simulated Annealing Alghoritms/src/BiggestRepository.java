import java.util.ArrayList;
import java.util.Collections;

public class BiggestRepository implements Cloneable{
	private ArrayList<ArrayList<Location>> repositories = new ArrayList<ArrayList<Location>>();
	
	@Override
    protected Object clone() throws CloneNotSupportedException {
        BiggestRepository cloned = (BiggestRepository) super.clone();
        // Deep copy fields that need to be copied by value instead of reference       
        // ...
        return cloned;
    }
	
	public void addRepository(ArrayList<Location> repository) {
		repositories.add(repository);
	}
	public ArrayList<?> getRepository(int index) {
		return repositories.get(index);
	}
	public int getNumberofRepositories() {
		return repositories.size();
	}
	public void clearLocation() {
		repositories.clear();
	}
	public ArrayList<?> getRepositories() {
		return repositories;
	}
	public double getSubRepLat(int outerIndex, int innerIndex) {
		return repositories.get(outerIndex).get(innerIndex).getLat();
	}
	public double getSubRepLon(int outerIndex, int innerIndex) {
		return repositories.get(outerIndex).get(innerIndex).getLon();
	}
	public double getDistance() {
		double tourDistance = 0.0;
		double last = 0.0;
		ArrayList<Double> distances = new ArrayList<Double>();
		
		for (int i=0; i<=repositories.size()-1;i++) {
			for (int j=0; j<repositories.get(i).size()-1;j++) {			
				tourDistance += distance(getSubRepLat(i, j), getSubRepLon(i, j),getSubRepLat(i, j+1), getSubRepLon(i, j+1));
				last = distance(getSubRepLat(i, 0), getSubRepLon(i, 0),getSubRepLat(i, repositories.get(i).size()-1), getSubRepLon(i, repositories.get(i).size()-1));						
			}
			tourDistance = tourDistance + last;	
			distances.add(tourDistance);
			tourDistance = 0.0;
		}		
		System.out.println(distances);
		Double result = Collections.max(distances);
		distances.clear();
		return result;		
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
	
//	public void Shuffle() {
//		
//		int randomRepositoryIndex1 = (int) (repositories.size() * Math.random());			
//		int randomRepositoryIndex2 = (int) (repositories.size() * Math.random());
//		
//		ArrayList randomRepository1 =  repositories.get(randomRepositoryIndex2);
//		ArrayList randomRepository2 =  repositories.get(randomRepositoryIndex1);
//		
//		int randomindex1 = (int) (repositories.get(randomRepositoryIndex2).size() * Math.random());
//		
//		while (randomindex1 == 0) {
//			randomindex1 = (int) (repositories.get(randomRepositoryIndex2).size() * Math.random());
//		}	
//		
//		int randomindex2 = (int) (repositories.get(randomRepositoryIndex1).size() * Math.random());
//		
//		while (randomindex2 == 0) {
//			randomindex2 = (int) (repositories.get(randomRepositoryIndex1).size() * Math.random());
//		}
//				
//		Location temp = repositories.get(randomRepositoryIndex2).get(randomindex1);			
//		repositories.get(randomRepositoryIndex2).set(randomindex1, repositories.get(randomRepositoryIndex1).get(randomindex2));
//		repositories.get(randomRepositoryIndex1).set(randomindex2, temp);
//											
//	}
	
	public void Shuffle(BiggestRepository x) {
		
		int randomRepositoryIndex1 = (int) (x.getNumberofRepositories() * Math.random());			
		int randomRepositoryIndex2 = (int) (x.getNumberofRepositories() * Math.random()); 
		
		ArrayList randomRepository1 =  x.getRepository(randomRepositoryIndex2);
		ArrayList randomRepository2 =  x.getRepository(randomRepositoryIndex1);
		
		int randomindex1 = (int) (x.getRepository(randomRepositoryIndex2).size() * Math.random());
		
		while (randomindex1 == 0) {
			randomindex1 = (int) (x.getRepository(randomRepositoryIndex2).size() * Math.random());
		}
		
		int randomindex2 = (int) (x.getRepository(randomRepositoryIndex1).size() * Math.random());
		
		while (randomindex2 == 0) {
			randomindex2 = (int) (x.getRepository(randomRepositoryIndex1).size() * Math.random());
		}
		
		Object temp = randomRepository2.get(randomindex1);			
		randomRepository2.set(randomindex1, randomRepository1.get(randomindex2));
		randomRepository1.set(randomindex2, temp);
		
	}
	
	public void setRepository(int index, ArrayList x) {
		this.repositories.set(index, x);
	}
	
	public void setRepositories(ArrayList x) {
		//Collections.copy(repositories, x);
		this.repositories = (ArrayList) x.clone();
	}
	
	public void Solution(BiggestRepository solution) {
	    this.repositories = new ArrayList<ArrayList<Location>>();
	    for (ArrayList<Location> repository : solution.repositories) {
	        this.repositories.add(new ArrayList<Location>(repository));
	    }
	}


	

}


