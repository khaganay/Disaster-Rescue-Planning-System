import java.util.ArrayList;

public class SimulatedAnnealing {
	private SingleTour best;
	
	public void simulation( ) {
		int iterationCount = 0;
		
		double temperature = 10000000;
		double coolingRate = 0.03;
		
		SingleTour currentSolution = new SingleTour();
		currentSolution.generateIndividual();
		
		//System.out.println("Initial total distance including returning to starting point = "+ currentSolution.getDistance()+" km");
		
		best = new SingleTour(currentSolution.getTour());
		long start = System.nanoTime();
		
		while( temperature > 1) {
			SingleTour newSolution = new SingleTour(currentSolution.getTour());
						
			int randomIndex1 = (int) (newSolution.getTourSize() * Math.random());
			Location location1 = newSolution.getLocation(randomIndex1);
			
			int randomIndex2= (int) (newSolution.getTourSize() * Math.random());
			Location location2= newSolution.getLocation(randomIndex2);			
			
			newSolution.setLocation(randomIndex2, location1);
			newSolution.setLocation(randomIndex1, location2);
			
			double currentEnergy = currentSolution.getDistance();
			double neighbourEnergy = newSolution.getDistance();
			
			if( acceptanceProbabilty(currentEnergy, neighbourEnergy, temperature) > Math.random() )
				currentSolution = new SingleTour(newSolution.getTour());
			
			if(currentSolution.getDistance() < best.getDistance()) {
				best = new SingleTour(currentSolution.getTour());
				//System.out.println("Current best = "+best);
			}
			temperature *= 1-coolingRate;
			iterationCount++;
		}
		long elapsedTime = System.nanoTime() - start;
		//System.out.println("Iteration Count = "+iterationCount);
		System.out.println("Time elapsed = "+elapsedTime+" nanoseconds" );
	}
	
	public SingleTour getBest() {
		return best;
	}

	private double acceptanceProbabilty(double currentEnergy, double neighbourEnergy, double temperature) {
		
		if(neighbourEnergy < currentEnergy)
		return 1;
		
		return Math.exp((currentEnergy - neighbourEnergy)/temperature);
	}
}
