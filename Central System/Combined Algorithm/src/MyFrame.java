import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class MyFrame extends JFrame implements ActionListener{

	JButton button;
	JButton button1;
	JTextField textField;
	JFrame jFrame;
	JLabel label1;
	final String dbName = "Location";
	 final String userName = "admin", password = "adminadmin";
	 final String url = "jdbc:mysql://disasterrescueplanning-db.cqyyahvjljvp.eu-central-1.rds.amazonaws.com:3306/"+dbName;
	 final String TABLE_NAME = "Survivors";
	 Double Min = 0.0;
	 Double Total = 0.0;
	 Double Last = 0.0;
	 Double initiallat;
	 Double initiallon;
	 Double latitude;
	 Double longitude;
	 ArrayList<Double> DistanceArray = new ArrayList<Double>();
	 ArrayList<Double> SurvivorLat = new ArrayList<Double>();
	 ArrayList<Double> SurvivorLon = new ArrayList<Double>();
	 ArrayList<Double> RescuerLat = new ArrayList<Double>();
	 ArrayList<Double> RescuerLon = new ArrayList<Double>();
	 ArrayList<Double> VisitingArrayLat = new ArrayList<Double>();
	 ArrayList<Double> VisitingArrayLon = new ArrayList<Double>();
	 StringBuilder records = new StringBuilder();
	 StringBuilder newRecords = new StringBuilder();
	
	MyFrame(){
		jFrame = new JFrame();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setTitle("Central System");
		jFrame.setPreferredSize(new Dimension(500,100));
		jFrame.setLayout(new FlowLayout());
		
		label1 = new JLabel("Amount of squads available:");
		
		button = new JButton("Send Designed Algorithm Route");
		button.addActionListener(this);
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(80,20));
		
		button1 = new JButton("Send Combined Route");
		button1.addActionListener(this);
		
		
		
		jFrame.add(button);
		jFrame.add(button1);
		jFrame.add(label1);
		jFrame.add(textField);
		jFrame.pack();
		jFrame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent a) {
		if(a.getSource()==button) {			
			if(Integer.parseInt(textField.getText()) == 0){
				JOptionPane.showMessageDialog(jFrame, "Please enter a valid squad amount");
			}
			else if(Integer.parseInt(textField.getText()) > 10) {
				JOptionPane.showMessageDialog(jFrame, "The helicopters cannot carry more than 10 squads");
			}
			else {
			int howManyNodes = Integer.parseInt(textField.getText());
			try {
	             Class.forName("com.mysql.jdbc.Driver");
	             Connection connection = DriverManager.getConnection(url, userName, password);
	             Statement statement = connection.createStatement();
	             Statement newstatement = connection.createStatement();
	             Statement statement2 = connection.createStatement();
	             
	             ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
	             ResultSet ts = newstatement.executeQuery("SELECT * FROM Rescuers");	            
	             while (rs.next()) {   	 
	                 records.append("Latitude: ").append(rs.getString(1)).append(", Longitude: ").append(rs.getString(2)).append("\n");
	                 SurvivorLat.add(rs.getDouble(1));
	                 SurvivorLon.add(rs.getDouble(2));
	             }
	             while (ts.next()) {   	 
	                 newRecords.append("Latitude: ").append(ts.getString(1)).append(", Longitude: ").append(ts.getString(2)).append("\n");
	                 RescuerLat.add(ts.getDouble(1));
	                 RescuerLon.add(ts.getDouble(2));
	             }
	             
		    	 for(int r = 0; r <= RescuerLat.size()-1; r++ ) {
		    		 latitude = RescuerLat.get(r);
			    	 longitude = RescuerLon.get(r);
			    	 int ID = r + 1;
			    	 initiallat = latitude;
			    	 initiallon = longitude;
			    	 while(SurvivorLat.size() != 0){
			             for(int i = 0; i<= SurvivorLat.size()-1; i++){
			                 DistanceArray.add(distance(latitude,longitude,SurvivorLat.get(i),SurvivorLon.get(i)));
			                 Min = DistanceArray.get(0);
			             }
			             for(int j = 0; j<=DistanceArray.size()-1;j++){
			                 if(Min >= DistanceArray.get(j)){
			                     Min = DistanceArray.get(j);
			                 }
			             }
			             Total = Min + Total;
			            // System.out.println("Minimum = "+Min+" SquadID = "+ID);
			            // System.out.println("Total = "+Total+" SquadID = "+ID);
			             for(int x=0;x<=DistanceArray.size()-1;x++) {
			                 if(DistanceArray.get(x) == Min) {
			                	// System.out.println("Traveling to Minimum = "+Min+" SquadID = "+ID);
			                     VisitingArrayLat.add(SurvivorLat.get(x));
			                     VisitingArrayLon.add(SurvivorLon.get(x));		                     
			                     Double NewLat = SurvivorLat.get(x);
			                     Double NewLon = SurvivorLon.get(x);
			                     if(VisitingArrayLat.size() == howManyNodes || (SurvivorLat.size() < howManyNodes && VisitingArrayLat.size() < howManyNodes)){
			                         Double LastLat = SurvivorLat.get(x);
			                         Double LastLon = SurvivorLon.get(x);
			                         Last = distance(initiallat, initiallon, LastLat, LastLon);
			                        // System.out.println("Last = "+Last+" SquadID = "+ID);
			                     }
			                     SurvivorLat.remove(x);
			                     SurvivorLon.remove(x);
			                     latitude = NewLat;
			                     longitude = NewLon;
			                     DistanceArray.clear();                
			                 }
			                 }
			            // System.out.println(VisitingArrayLat + "SquadID = "+ID+" VISITING LAT");
		            	// System.out.println(VisitingArrayLon + "SquadID = "+ID+" VISITING LON");
		            	// System.out.println(SurvivorLon + "SquadID = "+ID+ " SURVIVOR LON");
		            	// System.out.println(SurvivorLat + "SquadID = "+ID+ " SURVIVOR LAT");
			             if (VisitingArrayLat.size() == howManyNodes) {
			            	 System.out.println(VisitingArrayLat + "SquadID = "+ID);
			            	 System.out.println(VisitingArrayLon + "SquadID = "+ID);
			            	 for(int i = 0; i<= VisitingArrayLat.size()-1; i++){
			     		        statement2.execute("INSERT INTO DesignedRoute (Latitude, Longitude, SquadID) VALUES('" + VisitingArrayLat.get(i) + "', '" + VisitingArrayLon.get(i) + "', '" + ID + "')");			     		      
			     		        }
			            	 Double GrandTotal = Total + Last;
					         System.out.println(GrandTotal + " km SquadID = "+ID);
					         Total = 0.0;
					         Last = 0.0;					        
			            	 VisitingArrayLat.clear();
			            	 VisitingArrayLon.clear();
			            	 break;
			             }
			             if (SurvivorLat.size() == 0 && VisitingArrayLat.size() < howManyNodes) {
			            	 System.out.println(VisitingArrayLat + "SquadID = "+ID);
			            	 System.out.println(VisitingArrayLon + "SquadID = "+ID);
			            	 for(int l = 0; l<= VisitingArrayLat.size()-1; l++){
				     		         statement2.execute("INSERT INTO DesignedRoute (Latitude, Longitude, SquadID) VALUES('" + VisitingArrayLat.get(l) + "', '" + VisitingArrayLon.get(l) + "', '" + ID + "')");			     		         					            					            
				     		        }
			            	 Double GrandTotal = Total + Last;
					         System.out.println(GrandTotal + " km SquadID = "+ID);
					         Total = 0.0;
					         Last = 0.0;					         
			             }
			         }
		    	 }
		    	 RescuerLat.clear();
		    	 SurvivorLat.clear();
		    	 SurvivorLon.clear();		    	
		    	 RescuerLon.clear();
		    	 VisitingArrayLat.clear();
		    	 VisitingArrayLon.clear();
		    	 Total = 0.0;
		    	 Last = 0.0;
		         connection.close();

	         } catch (Exception e) {
	             e.printStackTrace();
	         }
	    	 
		}
		}
		
		if(a.getSource()==button1) {
			if(textField.getText() == "" || Integer.parseInt(textField.getText()) == 0){
				JOptionPane.showMessageDialog(jFrame, "Please enter a valid squad amount");
			}
			else if(Integer.parseInt(textField.getText()) > 10) {
				JOptionPane.showMessageDialog(jFrame, "The helicopters cannot carry more than 10 squads");
			}
			else {
			int howManyNodes = Integer.parseInt(textField.getText());
			SimulatedAnnealing annealing = new SimulatedAnnealing();
			try {
	             Class.forName("com.mysql.jdbc.Driver");
	             Connection connection = DriverManager.getConnection(url, userName, password);
	             Statement statement = connection.createStatement();
	             Statement newstatement = connection.createStatement();
	             Statement statement2 = connection.createStatement();
	             
	             ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
	             ResultSet ts = newstatement.executeQuery("SELECT * FROM Rescuers");	            
	             while (rs.next()) {   	 
	                 records.append("Latitude: ").append(rs.getString(1)).append(", Longitude: ").append(rs.getString(2)).append("\n");
	                 SurvivorLat.add(rs.getDouble(1));
	                 SurvivorLon.add(rs.getDouble(2));
	             }
	             while (ts.next()) {   	 
	                 newRecords.append("Latitude: ").append(ts.getString(1)).append(", Longitude: ").append(ts.getString(2)).append("\n");
	                 RescuerLat.add(ts.getDouble(1));
	                 RescuerLon.add(ts.getDouble(2));
	             }
	             
	             for(int r = 0; r <= RescuerLat.size()-1; r++ ) {
		    		 latitude = RescuerLat.get(r);
			    	 longitude = RescuerLon.get(r);
			    	 int ID = r + 1;
			    	 initiallat = latitude;
			    	 initiallon = longitude;
			    	 while(SurvivorLat.size() != 0){
			             for(int i = 0; i<= SurvivorLat.size()-1; i++){
			                 DistanceArray.add(distance(latitude,longitude,SurvivorLat.get(i),SurvivorLon.get(i)));
			                 Min = DistanceArray.get(0);
			             }
			             for(int j = 0; j<=DistanceArray.size()-1;j++){
			                 if(Min >= DistanceArray.get(j)){
			                     Min = DistanceArray.get(j);
			                 }
			             }
			             Total = Min + Total;
			            // System.out.println("Minimum = "+Min+" SquadID = "+ID);
			            // System.out.println("Total = "+Total+" SquadID = "+ID);
			             for(int x=0;x<=DistanceArray.size()-1;x++) {
			                 if(DistanceArray.get(x) == Min) {
			                	// System.out.println("Traveling to Minimum = "+Min+" SquadID = "+ID);
			                     VisitingArrayLat.add(SurvivorLat.get(x));
			                     VisitingArrayLon.add(SurvivorLon.get(x));		                     
			                     Double NewLat = SurvivorLat.get(x);
			                     Double NewLon = SurvivorLon.get(x);
			                     if(VisitingArrayLat.size() == howManyNodes || (SurvivorLat.size() < howManyNodes && VisitingArrayLat.size() < howManyNodes)){
			                         Double LastLat = SurvivorLat.get(x);
			                         Double LastLon = SurvivorLon.get(x);
			                         Last = distance(initiallat, initiallon, LastLat, LastLon);
			                         //System.out.println("Last = "+Last+" SquadID = "+ID);
			                     }
			                     SurvivorLat.remove(x);
			                     SurvivorLon.remove(x);
			                     latitude = NewLat;
			                     longitude = NewLon;
			                     DistanceArray.clear();                
			                 }
			                 }
			             
			             if (SurvivorLat.size() == 0 && VisitingArrayLat.size() < howManyNodes) {
			            	 for(int l = 0; l<= VisitingArrayLat.size()-1; l++){
			            		 Location location = new Location(VisitingArrayLat.get(l), VisitingArrayLon.get(l));
			         			 Repository.addLocation(location);			     		         					            					            
				     		     }
			            	 Location RescuerLocation = new Location(initiallat, initiallon);
			         		 Repository.addLocation(RescuerLocation);
			         		 annealing.simulation();
			         		 System.out.println("TOTAL DISTANCE TRAVELED INCLUDING RETURNING TO STARTING POINT = "+ annealing.getBest().getDistance() + " km" +" SQUAD-ID = "+ID);
			        		 System.out.println("Best Path Found:"+"\n"+annealing.getBest()+" SQUAD-ID = "+ID);
			        		 for(int k=0; annealing.getBest().getTourSize() - 1 >= k; k++) {
			        			 statement2.execute("INSERT INTO CombinedRoute (Latitude, Longitude, SquadID) VALUES('" + annealing.getBest().getLocation(k).getLat() + "', '" + annealing.getBest().getLocation(k).getLon() + "', '" + ID + "')"); 
			        		 }
			            	 //Double GrandTotal = Total + Last;
					         //System.out.println(GrandTotal + " km SquadID = "+ID);
					         Total = 0.0;
					         Last = 0.0;
					         VisitingArrayLat.clear();
			            	 VisitingArrayLon.clear();			            	 
			         		 Repository.clearLocation();
			         		 
			             }
			             if (VisitingArrayLat.size() == howManyNodes) {			            	 
			            	 for(int i = 0; i<= VisitingArrayLat.size()-1; i++){
			            		 Location location = new Location(VisitingArrayLat.get(i), VisitingArrayLon.get(i));
			         			 Repository.addLocation(location);
			     		        }
			            	 Location RescuerLocation = new Location(initiallat, initiallon);
			         		 Repository.addLocation(RescuerLocation);
			         		 annealing.simulation();			         		
			         		 System.out.println("TOTAL DISTANCE TRAVELED INCLUDING RETURNING TO STARTING POINT = "+ annealing.getBest().getDistance() + " km" +" SQUAD-ID = "+ID);
			        		 System.out.println("Best Path Found:"+"\n"+annealing.getBest()+" SQUAD-ID = "+ID);
			        		 for(int k=0; annealing.getBest().getTourSize() - 1 >= k; k++) {
			        			 statement2.execute("INSERT INTO CombinedRoute (Latitude, Longitude, SquadID) VALUES('" + annealing.getBest().getLocation(k).getLat() + "', '" + annealing.getBest().getLocation(k).getLon() + "', '" + ID + "')"); 
			        		 }
			            	 //Double GrandTotal = Total + Last;
					         //System.out.println(GrandTotal + " km SquadID = "+ID);
					         Total = 0.0;
					         Last = 0.0;					        
			            	 VisitingArrayLat.clear();
			            	 VisitingArrayLon.clear();			            	
			         		 Repository.clearLocation();
			            	 break;
			             }
			         }
		    	 }	             
	             RescuerLat.clear();
		    	 SurvivorLat.clear();
		    	 SurvivorLon.clear();		    	
		    	 RescuerLon.clear();
		    	 VisitingArrayLat.clear();
		    	 VisitingArrayLon.clear();
		    	 Total = 0.0;
		    	 Last = 0.0;     
	        connection.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		}
	}
	
	
	private static double distance(double lat1, double lon1, double lat2, double lon2) {
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
}