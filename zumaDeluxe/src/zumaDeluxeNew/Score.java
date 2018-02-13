package zumaDeluxeNew;

public class Score {
	
	private int score;
	
	public Score(){
		
		score = 0;
		
	}
	
	public int resetScore(){
		
		return score;
		
	}
	
	public int calculateScore(int numMarble){
		
//		System.out.println("before: "+ this.score);
		score += numMarble * 100 * Gameplay.levelSystem.getLevel();
//		System.out.println("after: "+ this.score);
		
		return score;
		
	}
	
	public int getScore(){
		
		return this.score;
		
	}

}
