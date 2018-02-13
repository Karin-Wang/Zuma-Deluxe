package zumaDeluxeNew;

public class LevelSystem {
	
	private int level;
	private int amountOfMarbles;
	private double chainVelocity;
	private int shotVelocity;
	private int livesRemaining;
	
	public LevelSystem() {
		
		this.level = 1;
		this.amountOfMarbles = 20;
		this.chainVelocity = 0.04;
		this.shotVelocity = 1;
		this.livesRemaining = 3;
	}
	
	public void levelUp() {
		
		this.level++;
		this.amountOfMarbles += 10;
		this.chainVelocity += this.level*0.01;
		
	}
	
	public void looseLive() {
		
		this.livesRemaining--;
		
	}
	
	public int getLevel() {
		
		return this.level;
		
	}
	
	public int getAmountOfMarbles() {
		
		return this.amountOfMarbles;
		
	}
	
	public double getChainVelocity() {
		
		return this.chainVelocity;
		
	}
	
	public int getShotVelocity() {
		
		return this.shotVelocity;
		
	}
	
	public int getLivesRemaining() {
		
		return this.livesRemaining;
		
	}
	
}
