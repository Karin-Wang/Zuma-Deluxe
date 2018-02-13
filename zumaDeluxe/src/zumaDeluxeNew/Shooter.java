package zumaDeluxeNew;

import java.awt.Color;

public class Shooter {
	
	private final int x = Main.frameWidth/2-Main.shooterSize/2;
	private final int y = Main.frameHeight/2-Main.shooterSize/2;
	
	/**
	 * Constructor of the Shooter class
	 */
	public Shooter() {
		
	}

	/**
	 * Get the x position of the shooter
	 * @return x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Get the y position of the shooter
	 * @return y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Method shoot, where a new random colored (shooting) marble is generated.
	 * @return a marble at position of the shooter, with a shooting angle defined by the user mouse or keyboard
	 */
	public Marble shoot(Color currentBulletColor) {
		
//		Color randomColor = Main.colors[(int)( Math.random() * 4)];
		
		Marble m = new Marble(currentBulletColor, Main.frameWidth/2-Main.marbleSize/2, Main.frameHeight/2-Main.marbleSize/2);
		
		return m;
	}
}
