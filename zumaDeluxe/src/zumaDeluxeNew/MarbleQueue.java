package zumaDeluxeNew;

import java.awt.Color;
import java.util.ArrayList;

public class MarbleQueue {
	
	private int amount;
	
	private static ArrayList<Marble> marbleQueue;
	private static ArrayList<Color> colorsAvailable = new ArrayList<Color>();
	
	public MarbleQueue() {
		
		this.amount = Gameplay.levelSystem.getAmountOfMarbles();
		marbleQueue = new ArrayList<Marble>();
		
		colorsAvailable.add(Color.RED);
		colorsAvailable.add(Color.GREEN);
		colorsAvailable.add(Color.BLUE);
		colorsAvailable.add(Color.YELLOW);
		
		generateQueue();
		
	}
	
	/** Creates marble chain that will run on the track
	 * @param amount: number of marbles on the chain
	 */
	public void generateQueue() {
		
		marbleQueue.clear();
		
		for(int i = 0; i < amount; i++) {
			
			Color randomColor = getRandomColor();
			
			if(i > 1) {
				while(getFromQueue(i-2).getColor().equals(randomColor) && getFromQueue(i-1).getColor().equals(randomColor)) {
					
					randomColor = getRandomColor();
					
				}
			}
			
			marbleQueue.add(i, new Marble(randomColor, i * -Main.marbleSize, Main.startPointY));
		}
	}
	
	/**
	 * Get a random color, which exists in the marble queue
	 * @return color
	 */
	public Color getRandomColor() {
		
		return colorsAvailable.get((int)(Math.random()*getAvailableColors().size()));
		
	}
	
	/**
	 * Check which colors are left in the marble queue
	 */
	public void marbleQueueColors() {
		
		colorsAvailable.clear();
		
		for(Marble m : marbleQueue) {
			
			if(!getAvailableColors().contains(m.getColor())) {
				
				colorsAvailable.add(m.getColor());
				
			}
			
		}
		
	}
	
	/**
	 * Get the available colors
	 * @return
	 */
	public ArrayList<Color> getAvailableColors() {
		
		return colorsAvailable;
		
	}
	
	public int getSize() {
		
		return marbleQueue.size();
		
	}
	
	public void removeMarble(int index) {
		
		marbleQueue.remove(index);
		
	}
	
	public Marble getFromQueue(int index) {
		
		return marbleQueue.get(index);
		
	}
	
	public void setInQueue(int index, Marble marble) {
		
		marbleQueue.set(index, marble);
		
	}
	
	public void addMarble(Marble marble) {
		
		marbleQueue.add(marble);
		
	}
	

}
