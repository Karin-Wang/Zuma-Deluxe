package zumaDeluxeNew;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

public class RenderPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private TrackGenerator track = new TrackGenerator();

	Marble currentBullet;
	Shooter shooter;
	MarbleQueue marbleQueue;
	int shotMarbleCount;
	
	Image img = Toolkit.getDefaultToolkit().createImage("Mapv1.png");
	
	@Override
	protected void paintComponent(Graphics g) {
		
		currentBullet = Gameplay.currentBullet;
		shooter = Gameplay.shooter;
		marbleQueue = Gameplay.marbleQueue;
		shotMarbleCount = Gameplay.shotMarbleCount;
		
		g.drawImage(img,  0, -23, null);
		
		/**
		 * Drawing the shooter, defined in the class GamePlay
		 */
		drawShooter(shooter, g);
		
		/**
		 * Drawing the current bullet
		 */
		drawCurrentBullet(currentBullet,g);
		
		/**
		 * Drawing the marble chain
		 */
		printMarbleQueue(marbleQueue, g);
		
		/**
		 * Drawing white panel at top for information
		 */
		g.setColor(Color.white);
		g.fillRect(0, 0, Main.frameWidth, 40);
		
		/**
		 * Marbles in game counter
		 */
		g.setColor(Color.black);
		g.setFont(new Font("verdana", Font.BOLD, 14));
		g.drawString("Marbles left: " + marbleQueue.getSize(), 10, 15);
		
		/**
		 * Shot marble counter
		 */
		g.setColor(Color.black);
		g.setFont(new Font("verdana", Font.BOLD, 14));
		g.drawString("Marbles shot: " + shotMarbleCount, 10, 35);
		
		/**
		 * Lives remaining counter
		 */
		g.setColor(Color.black);
		g.setFont(new Font("verdana", Font.BOLD, 14));
		g.drawString("Lives remaining: " + Gameplay.levelSystem.getLivesRemaining(), 300, 15);
		
		/**
		 * Game level counter
		 */
		g.setColor(Color.black);
		g.setFont(new Font("verdana", Font.BOLD, 14));
		g.drawString("Level: " + Gameplay.levelSystem.getLevel(), 300, 35);
		
		/**
		 * Drawing the score in the top right corner
		 */
		g.setColor(Color.black);
		g.setFont(new Font("verdana", Font.BOLD, 20));
		g.drawString("Score: "+ Gameplay.score.getScore(), 550, 30);
		
		g.setColor(Color.red);
		g.setFont(new Font("verdana", Font.BOLD, 20));
		g.drawString(Gameplay.statusString, 600,700);
		
		g.dispose();
	}
	
	/**
	 * Printing a marble on the map
	 * @param marble
	 * @param g
	 */
	public void printMarble(Marble marble, Graphics g) {
		
		marble.track(track);
		g.setColor(marble.getColor());
		g.fillOval((int) marble.getX(),(int) marble.getY(), Main.marbleSize, Main.marbleSize);

	}
	
	/**
	 * Loop to print every marble on the track
	 * @param marbleChain
	 * @param g
	 */
	public void printMarbleQueue(MarbleQueue marbleQueue, Graphics g) {
		
//		for(Marble marble : marbleQueue) {
		
		for(int i = 0; i < marbleQueue.getSize(); i++) {
			
			printMarble(marbleQueue.getFromQueue(i), g);
			
		}
	}

	/**
	 * Drawing the shooter in the middle of the map
	 * @param shooter
	 * @param g
	 */
	public void drawShooter(Shooter shooter, Graphics g) {
		
		g.setColor(Color.GRAY);
		g.fillOval(shooter.getX(), shooter.getY(), Main.shooterSize, Main.shooterSize);
		
	}
	
	/**
	 * Drawing the bullet on the map
	 * If shooting state is idle (true), there will be no movement
	 * @param currentBullet
	 * @param g
	 */
	public void drawCurrentBullet(Marble currentBullet, Graphics g) {
		
		g.setColor(currentBullet.getColor());
		g.fillOval((int) currentBullet.getX(), (int) currentBullet.getY(), Main.marbleSize,Main.marbleSize);
		
		if(!Gameplay.getShootingState()) {
			
			currentBullet.updateCurrentBullet();
			
		}
	}

}
