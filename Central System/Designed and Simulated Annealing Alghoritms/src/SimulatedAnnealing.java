import java.util.ArrayList;


public class SimulatedAnnealing {
    public BiggestRepository best;

    public void simulation(BiggestRepository currentSolution) {
        double temperature = 10000;
        double coolingRate = 0.003;
        
        best = currentSolution;
        

        System.out.println("Initial current: " + currentSolution.getDistance());

        long start = System.nanoTime();
        
        while (temperature > 1) {
            try {
            	
                BiggestRepository newSolution = (BiggestRepository) currentSolution.clone();
                newSolution.Solution(currentSolution);
                               
                System.out.println("Before current: " + currentSolution.getDistance());
                System.out.println("Before new: " + newSolution.getDistance());

               // newSolution.Shuffle();
                
                int randomRepositoryIndex1 = (int) (newSolution.getNumberofRepositories() * Math.random());			
                int randomRepositoryIndex2 = (int) (newSolution.getNumberofRepositories() * Math.random()); 

                ArrayList randomRepository1 =  newSolution.getRepository(randomRepositoryIndex1);
                ArrayList randomRepository2 =  newSolution.getRepository(randomRepositoryIndex2);

                int randomindex1 = (int) (randomRepository2.size() * Math.random());

                while (randomindex1 == 0) {
                	randomindex1 = (int) (randomRepository2.size() * Math.random());
                }

                int randomindex2 = (int) (randomRepository1.size() * Math.random());

                while (randomindex2 == 0) {
                	randomindex2 = (int) (randomRepository1.size() * Math.random());
                }

                Object temp = randomRepository2.get(randomindex1);			
                randomRepository2.set(randomindex1, randomRepository1.get(randomindex2));
                randomRepository1.set(randomindex2, temp);
                
                                      
        		
                System.out.println("After current: " + currentSolution.getDistance());
                System.out.println("After new: " + newSolution.getDistance());

                Double currentEnergy = currentSolution.getDistance();
                Double neighbourEnergy = newSolution.getDistance();

                if (acceptanceProbabilty(currentEnergy, neighbourEnergy, temperature) > Math.random()) {
                	try {
                        currentSolution = (BiggestRepository) newSolution.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }                  
                }

                if (currentSolution.getDistance() < best.getDistance()) {
                    best = currentSolution;
                }
                temperature *= 1 - coolingRate;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        long elapsedTime = System.nanoTime() - start;
        System.out.println("Time: " + elapsedTime);	
        System.out.println("Best: " + best.getRepositories());
        System.out.println("Best distance: " + best.getDistance());       
    }

    public BiggestRepository getBest() {
        return best;
    }

    private double acceptanceProbabilty(double currentEnergy, double neighbourEnergy, double temperature) {
        if (neighbourEnergy < currentEnergy)
            return 1;

        return Math.exp((currentEnergy - neighbourEnergy) / temperature);
    }
    
    
}
