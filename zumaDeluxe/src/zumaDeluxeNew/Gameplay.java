package zumaDeluxeNew;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;


public class Gameplay implements MouseListener, ActionListener, KeyListener{
		
	public JFrame frame;
	public RenderPanel renderPanel;
	
	public Timer timer = new Timer(1, this);
		
	public static int time = 0;
	public static int shotMarbleCount = 0;
	public static int comboFlag = 0;
	public long startTime = 0;
	
	public static String statusString = ""; 
	
	public Random random;
	
	public static boolean paused = true;
	public static boolean shotStopped = true;
	
	public static LevelSystem levelSystem = new LevelSystem();
	public static MarbleQueue marbleQueue = new MarbleQueue();
	public static Shooter shooter = new Shooter();
	public static Marble currentBullet;
	public static Score score = new Score();
	
	/**
	 * Construction the class GamePlay:
	 * Setting the frame with options
	 * Generating a marble chain with a certain amount of marbles
	 * Starting the game
	 */
	public Gameplay() {

		frame = new JFrame("Zuma Deluxe");
		frame.setBounds(0,0,Main.frameWidth, Main.frameHeight);
		frame.setSize(Main.frameWidth, Main.frameHeight);
		frame.setResizable(true);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.add(renderPanel = new RenderPanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addMouseListener(this);
		frame.addKeyListener(this);
		
		
		startGame();

	}
	
	/**
	 * Starting the game with all variables neutralized
	 */
	public void startGame() {
		paused = false;
		time = 0;
		shotMarbleCount = 0;
		
		marbleQueue = new MarbleQueue();
		
		marbleQueue.marbleQueueColors();
		
		createNewBullet();
		
		timer.start();
		
		startTime = System.currentTimeMillis();
		
	}
	
	/**
	 * Method which returns the current state of the shooter (shot or idle)
	 * @return shotStopped
	 */
	public static boolean getShootingState() {
		
		return shotStopped;
		
	}
	
	/**
	 * The user can't shoot a bullet within the first three seconds of a new game
	 * @return true if time elapsed greater than 3 seconds, false if smaller
	 */
	public boolean timeToStart() {
		
		long timeElapsed = System.currentTimeMillis();	
		
		if(timeElapsed-startTime < 3000) {
			
			return false;
			
		}
		
		return true;
		
	}
	
	/**
	 * Generate a new bullet for the shooter
	 */
	public void createNewBullet() {
		
		shotStopped = true;
		
		shotMarbleCount++;
		
		if(marbleQueue.getSize() > 0) {
			
			marbleQueue.marbleQueueColors();
			
		}
		
		currentBullet = shooter.shoot(marbleQueue.getRandomColor());
		
	}
	
	/**
	 * Method which checks if, after a removal of a colored sequence, there exist a new colored sequence which must be removed 
	 */
	public boolean checkQueueForColoredSequence() {
		
		if(marbleQueue.getSize() <= 2) {
			
			return true;
			
		}
			
		for(int i = 0; i < marbleQueue.getSize()-2; i++) {
			
			Color thisMarbleColor = marbleQueue.getFromQueue(i).getColor();
			Color nextMarbleColor = marbleQueue.getFromQueue(i+1).getColor();
			Color nextNextMarbleColor = marbleQueue.getFromQueue(i+2).getColor();
			Color nextNextNextMarbleColor;
			
			int coloredSequenceCount = 1;
			
			if(thisMarbleColor.equals(nextMarbleColor)) {
				
				coloredSequenceCount = 2;
				
				if(nextMarbleColor.equals(nextNextMarbleColor)) {
					
					coloredSequenceCount = 3;
					
					if(i+2 < marbleQueue.getSize()-1) {
					
						nextNextNextMarbleColor = marbleQueue.getFromQueue(i).getColor();
						
						if(nextNextMarbleColor.equals(nextNextNextMarbleColor)) {
							
							coloredSequenceCount = 4;
							
						}
						
					}
					
					int numMarble = coloredSequenceCount;
					
					score.calculateScore(numMarble);
					comboFlag = 1;
					
					removeFromQueue(i+coloredSequenceCount-1,i);
					
					while(!checkQueueForColoredSequence()) {
						
						checkQueueForColoredSequence();
						
					}
					
					
				}
				
			}
			
		}
		
		return true;
		
	}
	
	/**
	 * Removing marbles from the chain with index between indexBiggest and indexSmallest 
	 * @param indexBiggest
	 * @param indexSmallest
	 */
	public void removeFromQueue(int indexBiggest, int indexSmallest) {
		
		int numMarble = 1;
		
		if(indexBiggest == marbleQueue.getSize()-1 || indexSmallest == 0) {
			
			for(int i = indexBiggest; i >= indexSmallest; i--) {
				
				marbleQueue.removeMarble(i);
				numMarble += 1;
				
			}
			
		}else {
			
			int deleteCount = indexBiggest-indexSmallest+1;
			
			for(int i = indexBiggest; i > (deleteCount-1); i--) {
				
				if(i-deleteCount >= 0) {
				
					Marble thisMarble = marbleQueue.getFromQueue(i);
				
					thisMarble.setColor(marbleQueue.getFromQueue(i-deleteCount).getColor());
					
				}
				
			}
			
			if(indexSmallest >= 3) {
			
				for(int i = (deleteCount-1); i >= 0; i--) {

					marbleQueue.removeMarble(i);
					numMarble += 1;
				
				}
				
			}else if(indexSmallest == 2) {
				
				marbleQueue.removeMarble(1);
				marbleQueue.removeMarble(0);
				
			}else if(indexSmallest == 1) {
				
				marbleQueue.removeMarble(0);
			}
			
		}
		
		if(comboFlag == 1){
			
			comboFlag = 0;
			
		}else {
			
			score.calculateScore(numMarble);
			
		}
		
		if(marbleQueue.getSize() > 0) {
			
			createNewBullet();
			
		}
		
	}
	
	/**
	 * Adding a marble to the chain at position index 
	 * @param index
	 */
	public void addToQueue(int index) {
		
		shotStopped = true;
		
		double horizontalPlacement = 0;
		double verticalPlacement = 0;
		
		if(index > marbleQueue.getSize()-1) {
			
			if(marbleQueue.getFromQueue(index-1).movingToRight()) {
				
				horizontalPlacement = -Main.marbleSize;
				
			}else if(marbleQueue.getFromQueue(index-1).movingToLeft()) {
				
				horizontalPlacement = Main.marbleSize;
				
			}else if(marbleQueue.getFromQueue(index-1).movingToTop()) {
				
				verticalPlacement = Main.marbleSize;
				
			}else if(marbleQueue.getFromQueue(index-1).movingToBottom()) {
				
				verticalPlacement = -Main.marbleSize;
				
			}
			
			Marble addMarble = new Marble(currentBullet.getColor(),marbleQueue.getFromQueue(index-1).getX()+horizontalPlacement,marbleQueue.getFromQueue(index-1).getY()+verticalPlacement);
			
			marbleQueue.addMarble(addMarble);
			
		}else {
			
			horizontalPlacement = 0;
			verticalPlacement = 0;
			
			if(marbleQueue.getFromQueue(marbleQueue.getSize()-1).movingToRight()) {
				
				horizontalPlacement = -Main.marbleSize;
				
			}else if(marbleQueue.getFromQueue(marbleQueue.getSize()-1).movingToLeft()) {
				
				horizontalPlacement = Main.marbleSize;
				
			}else if(marbleQueue.getFromQueue(marbleQueue.getSize()-1).movingToTop()) {
				
				verticalPlacement = Main.marbleSize;
				
			}else if(marbleQueue.getFromQueue(marbleQueue.getSize()-1).movingToBottom()) {
				
				verticalPlacement = -Main.marbleSize;
				
			}
			
			Marble insertLastMarble = new Marble(marbleQueue.getFromQueue(marbleQueue.getSize()-1).getColor(),marbleQueue.getFromQueue(marbleQueue.getSize()-1).getX()+horizontalPlacement,marbleQueue.getFromQueue(marbleQueue.getSize()-1).getY()+verticalPlacement); 
			
			for(int i = marbleQueue.getSize()-1; i > index; i--) {
				
				marbleQueue.getFromQueue(i).setColor(marbleQueue.getFromQueue(i-1).getColor());
				
			}
			
			marbleQueue.addMarble(insertLastMarble);
			
			marbleQueue.getFromQueue(index).setColor(currentBullet.getColor());
			
		}
		
		renderPanel.repaint();
		
		if(marbleQueue.getSize() > 0) {
		
			createNewBullet();
			
		}
		
	}

	/**
	 * Detecting intersection and handling the situation; adding to the chain of marbles or deleting marbles from the chain if the shot bullet makes a sequence of three equally colored marbles
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		long timeElapsed = System.currentTimeMillis();
		
		long dt = timeElapsed-startTime;
		
		if(dt == 1000 || dt == 2000 || dt == 3000) {
			
			statusString = "3..2..1..GO";
			
		}else if(dt > 5000){
		
			statusString = "";
			
		}
		
		if(paused) {
			
			return;
			
		}

		if(marbleQueue.getSize() == 0) {
			
			timer.stop();
			
			levelSystem.levelUp();
			
			statusString = "LEVEL " + levelSystem.getLevel();
			
			startGame();
			
		}
		
		if(shotStopped && Intersection.distanceBetweenMarbles(marbleQueue.getFromQueue(0), currentBullet) <= (Main.shooterSize)) {
			
			timer.stop();
			
			levelSystem.looseLive();
			
			if(levelSystem.getLivesRemaining() >= 1) {
				
				statusString = levelSystem.getLivesRemaining() + " LIVES REMAINING";
			
				startGame();
				
			}else {
				
				statusString = "GAME OVER";
				
			}
			
			
			
			return;
			
		}
		
		Color currentBulletColor = currentBullet.getColor();
		Color thisMarbleColor;
		Color nextMarbleColor;
		Color nextNextMarbleColor;
		Color prevMarbleColor;
		Color prevPrevMarbleColor;
		
		for(int index = marbleQueue.getSize()-1; index >= 0; index--) {
			
			if(currentBullet.getX() < 0 || currentBullet.getX() > Main.frameWidth || currentBullet.getY() < 0 || currentBullet.getY() > Main.frameHeight) {
				
				createNewBullet();
				
			}
			
			Marble marble = marbleQueue.getFromQueue(index);
			
			if(Intersection.distanceBetweenMarbles(marble, currentBullet) <= Main.marbleSize) {
				
				if(marbleQueue.getSize() == 1) {
					
					if(marble.movingToRight()) {
						
						if(currentBullet.getX() >= marble.getX()) {
							
							addToQueue(0);
							
						}else {
							
							addToQueue(1);
							
						}
						
					}else if(marble.movingToLeft()) {
						
						if(currentBullet.getX() >= marble.getX()) {
							
							addToQueue(1);
							
						}else {
							
							addToQueue(0);
							
						}
						
					}else if(marble.movingToTop()) {
						
						if(currentBullet.getY() < marble.getY()) {
							
							addToQueue(0);
							
						}else {
							
							addToQueue(1);
						}
						
					}else if(marble.movingToBottom()) {
						
						if(currentBullet.getY() < marble.getY()) {
							
							addToQueue(1);
							
						}else {
							
							addToQueue(0);
							
						}
						
					}
					
					break;
					
				}else if(marbleQueue.getSize() == 2) {
					
					Color firstMarbleColor = marbleQueue.getFromQueue(0).getColor();
					Color lastMarbleColor = marbleQueue.getFromQueue(1).getColor();
										
					if(firstMarbleColor.equals(currentBulletColor) && lastMarbleColor.equals(currentBulletColor)) {
						
						removeFromQueue(1,0);
						
					}else {
					
						if(index == 0) {
							
							double distanceToNextMarble = Intersection.distanceBetweenMarbles(marbleQueue.getFromQueue(index+1),currentBullet);
							
							if(distanceToNextMarble > Main.marbleSize) {
								
								addToQueue(index);
								
							}else {
								
								addToQueue(index+1);
								
							}
							
						}else if(index == 1) {
							
							double distanceToPrevMarble = Intersection.distanceBetweenMarbles(marbleQueue.getFromQueue(index-1),currentBullet);
							
							if(distanceToPrevMarble > Main.marbleSize) {
								
								addToQueue(index+1);
								
							}else {
								
								addToQueue(index);
								
							}
							
						}
						
					}
					
					break;
				
				}else if(marbleQueue.getSize() == 3) {
					
					if(index == 0) {
					
						Color firstMarbleColor = marbleQueue.getFromQueue(0).getColor();
						Color secondMarbleColor = marbleQueue.getFromQueue(1).getColor();
						
						if(firstMarbleColor.equals(currentBulletColor) && secondMarbleColor.equals(currentBulletColor)) {
							
							removeFromQueue(index+1,index);
							
						}else {
						
							double distanceToNextMarble = Intersection.distanceBetweenMarbles(marbleQueue.getFromQueue(index+1),currentBullet);
							
							if(distanceToNextMarble > Main.marbleSize) {
								
								addToQueue(index);
								
							}else {
								
								addToQueue(index+1);
								
							}
							
						}
						
					}else if(index == 1) {
						
						double distanceToNextMarble = Intersection.distanceBetweenMarbles(marbleQueue.getFromQueue(index+1),currentBullet);
						
						if(distanceToNextMarble > Main.marbleSize) {
							
							Color firstMarbleColor = marbleQueue.getFromQueue(0).getColor();
							Color secondMarbleColor = marbleQueue.getFromQueue(1).getColor();
							
							if(firstMarbleColor.equals(currentBulletColor) && secondMarbleColor.equals(currentBulletColor)) {
								
								removeFromQueue(index,index-1);
								
							}else {
							
								addToQueue(index);
								
							}
							
						}else {
							
							Color secondMarbleColor = marbleQueue.getFromQueue(1).getColor();
							Color thirdMarbleColor = marbleQueue.getFromQueue(2).getColor();
							
							if(secondMarbleColor.equals(currentBulletColor) && thirdMarbleColor.equals(currentBulletColor)) {
								
								removeFromQueue(index+1,index);
								
							}else {
							
								addToQueue(index+1);
								
							}
							
						}
						
						
					}else if(index == 2) {
						
						Color secondMarbleColor = marbleQueue.getFromQueue(1).getColor();
						Color thirdMarbleColor = marbleQueue.getFromQueue(2).getColor();
						
						if(secondMarbleColor.equals(currentBulletColor) && thirdMarbleColor.equals(currentBulletColor)) {
							
							removeFromQueue(index,index-1);
							
						}else {
						
							double distanceToPrevMarble = Intersection.distanceBetweenMarbles(marbleQueue.getFromQueue(index-1),currentBullet);
							
							if(distanceToPrevMarble > Main.marbleSize) {
								
								addToQueue(index+1);
								
							}else {
								
								addToQueue(index);
							}
						
						}
						
						
					}
					
					break;
					
				}else if(index == 0) {
					
					nextMarbleColor = marbleQueue.getFromQueue(index).getColor();
					nextNextMarbleColor = marbleQueue.getFromQueue(index+1).getColor();
					
					if(nextMarbleColor.equals(currentBulletColor) && nextNextMarbleColor.equals(currentBulletColor)) {
						
						removeFromQueue(index+1,index);
						
					}else {
						
						double distanceToNextMarble = Intersection.distanceBetweenMarbles(marbleQueue.getFromQueue(index+1),currentBullet);
						
						if(distanceToNextMarble > Main.marbleSize) {
							
							addToQueue(index);
							
						}else {
							
							addToQueue(index+1);
							
						}
						
					}
					
					break;
					
				}else if(index == marbleQueue.getSize()-1) {
					
					prevMarbleColor = marbleQueue.getFromQueue(index).getColor();
					prevPrevMarbleColor = marbleQueue.getFromQueue(index-1).getColor();
					
					if(prevMarbleColor.equals(currentBulletColor) && prevPrevMarbleColor.equals(currentBulletColor)) {
						
						removeFromQueue(index,index-1);
						
					}else {
						
						double distanceToPrevMarble = Intersection.distanceBetweenMarbles(marbleQueue.getFromQueue(index-1),currentBullet);
						
						if(distanceToPrevMarble > Main.marbleSize) {

							addToQueue(index+1);
							
						}else {
							
							addToQueue(index);
							
						}
						
					}
					
					break;
					
				}else if(index == 1) {
					
					thisMarbleColor = marbleQueue.getFromQueue(index).getColor();
					prevMarbleColor = marbleQueue.getFromQueue(index-1).getColor();
					nextMarbleColor = marbleQueue.getFromQueue(index+1).getColor();
					nextNextMarbleColor = marbleQueue.getFromQueue(index+2).getColor();
					
					double distanceToPrevMarble = Intersection.distanceBetweenMarbles(marbleQueue.getFromQueue(index-1),currentBullet);
					double distanceToNextMarble = Intersection.distanceBetweenMarbles(marbleQueue.getFromQueue(index+1),currentBullet);
						
					if(distanceToPrevMarble >= distanceToNextMarble) {
						
						if(thisMarbleColor.equals(currentBulletColor) && nextMarbleColor.equals(currentBulletColor)) {
							
							removeFromQueue(index+1,index);
							
						}else if(nextMarbleColor.equals(currentBulletColor) && nextNextMarbleColor.equals(currentBulletColor)){
						
							removeFromQueue(index+2,index+1);
							
						}else {
							
							addToQueue(index);
						
						}
						
					}else {
						
						if(prevMarbleColor.equals(currentBulletColor) && thisMarbleColor.equals(currentBulletColor)) {
							
							removeFromQueue(index,index-1);
							
						}else {
						
							addToQueue(index-1);
							
						}
						
					}
					
					break;
					
				}else if(index == marbleQueue.getSize()-2) {
					
					thisMarbleColor = marbleQueue.getFromQueue(index).getColor();
					prevMarbleColor = marbleQueue.getFromQueue(index-1).getColor();
					prevPrevMarbleColor = marbleQueue.getFromQueue(index-2).getColor();
					nextMarbleColor = marbleQueue.getFromQueue(index+1).getColor();
					
					double distanceToPrevMarble = Intersection.distanceBetweenMarbles(marbleQueue.getFromQueue(index-1),currentBullet);
					double distanceToNextMarble = Intersection.distanceBetweenMarbles(marbleQueue.getFromQueue(index+1),currentBullet);
						
					if(distanceToPrevMarble >= distanceToNextMarble) {
						
						if(thisMarbleColor.equals(currentBulletColor) && nextMarbleColor.equals(currentBulletColor)) {
							
							removeFromQueue(index+1,index);
							
						}else {
						
							addToQueue(index+1);
							
						}
						
					}else {
						
						if(prevMarbleColor.equals(currentBulletColor) && thisMarbleColor.equals(currentBulletColor)) {
						
							removeFromQueue(index,index-1);
							
						}else if(prevMarbleColor.equals(currentBulletColor) && prevPrevMarbleColor.equals(currentBulletColor)) {
						
							removeFromQueue(index-1,index-2);
							
						}else {
						
							addToQueue(index);
							
						}
						
					}
					
					break;
					
					
				}else {
					
					prevPrevMarbleColor = marbleQueue.getFromQueue(index-2).getColor();
					prevMarbleColor = marbleQueue.getFromQueue(index-1).getColor();
					thisMarbleColor = marbleQueue.getFromQueue(index).getColor();
					nextMarbleColor = marbleQueue.getFromQueue(index+1).getColor();
					nextNextMarbleColor = marbleQueue.getFromQueue(index+2).getColor();
					
					double distanceToPrevMarble = Intersection.distanceBetweenMarbles(marbleQueue.getFromQueue(index-1),currentBullet);
					double distanceToNextMarble = Intersection.distanceBetweenMarbles(marbleQueue.getFromQueue(index+1),currentBullet);
					
						
					if(distanceToPrevMarble >= distanceToNextMarble) {
						
						if(thisMarbleColor.equals(currentBulletColor) && nextMarbleColor.equals(currentBulletColor)) {
							
							removeFromQueue(index+1,index);
							
						}else if(nextMarbleColor.equals(currentBulletColor) && nextNextMarbleColor.equals(currentBulletColor)) {
							
							removeFromQueue(index+2,index+1);
							
						}else{
							
							addToQueue(index+1);
							
						}
						
					}else {
						
						if(thisMarbleColor.equals(currentBulletColor) && prevMarbleColor.equals(currentBulletColor)) {
							
							removeFromQueue(index,index-1);
							
						}else if(prevMarbleColor.equals(currentBulletColor) && prevPrevMarbleColor.equals(currentBulletColor)) {
							
							removeFromQueue(index-1,index-2);
							
						}else {
							
							addToQueue(index);
							
						}
						
					}
					
					break;
					
				}
		
			}
			
		}
		
		while(!checkQueueForColoredSequence()) {
			
			checkQueueForColoredSequence();
			
		}
		
		renderPanel.repaint();	
		
	}

	/**
	 * Mouse press action to shoot a marble.
	 * Get the x and y position of the mouse cursor to determine the shooting angle
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		
		if(!timeToStart() || !shotStopped || marbleQueue.getSize() == 0 || paused) {
			
			return;
			
		}
		
		double x = e.getX();
		double y = e.getY();
		
		double top = y - Main.frameHeight/2;
		double bottom = x - Main.frameWidth/2;
		double angle = Math.toDegrees(Math.atan2(top,bottom));
		
		currentBullet.setAngle(angle);
		
		shotStopped = false;
		
	}
		
	/**
	 * Key press action to shoot a marble (space), rotate the shooter (left/right keys) or pause the game (p)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(!timeToStart() || !shotStopped || marbleQueue.getSize() == 0) {
			
			return;
			
		}
		
		currentBullet.setAngle(Main.shootingAngleKeyboard);
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE && !paused) {
				
				shotStopped = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT && !paused) {
			
			currentBullet.rotateRight();
			
		}

		if(e.getKeyCode() == KeyEvent.VK_LEFT && !paused) {
			
			currentBullet.rotateLeft();
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_P) {
			
			if(paused) {
				
				paused = false;
				
				
			}else {
				
				paused = true;
				
				
			}
					
		}

		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

}
