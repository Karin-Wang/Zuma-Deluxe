package zumaDeluxeNew;

import java.awt.Color;

public class Marble {
	
	public Color color;
	public double x, y, xOld, yOld;
	public double angle;
	public int nextPoint;
	public int rotateSpeed = 5;
	public boolean toRight, toLeft, toTop, toBottom;
	
	/**
	 * Construction the Marble class for the marbles in the chain
	 * @param color
	 * @param x
	 * @param y
	 */
	public Marble(Color color, double x, double y) {
		this.color = color;
		this.x = x;
		this.y = y;
		this.xOld = x;
		this.yOld = y;
		this.nextPoint = 1;
		this.toRight = false;
		this.toLeft = false;
		this.toTop = false;
		this.toBottom = false;
	}
	
	/**
	 * Moving the shot marble on the map
	 */
	public void updateCurrentBullet() {
		double newX = Math.cos(-Math.toRadians(this.angle))*Gameplay.levelSystem.getShotVelocity();
		double newY = Math.sin(-Math.toRadians(this.angle))*Gameplay.levelSystem.getShotVelocity();
		
		xOld = this.x;
		yOld = this.y;
		
		this.x += newX;
		this.y -= newY;
		
	}
	
	public void rotateLeft() {
		
		this.angle = Main.shootingAngleKeyboard+rotateSpeed;
		
		Main.shootingAngleKeyboard = (int) this.angle;

	}
	
	public void rotateRight() {
		
		this.angle = Main.shootingAngleKeyboard-rotateSpeed;
		Main.shootingAngleKeyboard = (int) this.angle;
		
	}
	
	public boolean movingToRight() {
		
		if(this.x > xOld) {
			this.toRight = true;
			this.toLeft = false;
		}else if(this.x < xOld) {
			this.toRight = false;
			this.toLeft = true;
		}else {
			this.toRight = false;
			this.toLeft = false;
		}
		
		return this.toRight;
		
	}
	
	public boolean movingToLeft() {
		
		if(this.x > xOld) {
			this.toRight = true;
			this.toLeft = false;
		}else if(this.x < xOld) {
			this.toRight = false;
			this.toLeft = true;
		}else {
			this.toRight = false;
			this.toLeft = false;
		}
		
		return this.toLeft;
		
	}

	public boolean movingToTop() {
	
		if(this.y > yOld) {
			this.toTop = false;
			this.toBottom = true;
		}else if(this.y < yOld) {
			this.toTop = true;
			this.toBottom = false;
		}else {
			this.toTop = false;
			this.toBottom = false;
		}
		
		return this.toTop;
	
	}

	public boolean movingToBottom() {
	
		if(this.y > yOld) {
			this.toTop = false;
			this.toBottom = true;
		}else if(this.y < yOld) {
			this.toTop = true;
			this.toBottom = false;
		}else {
			this.toTop = false;
			this.toBottom = false;
		}
		
		return this.toBottom;
	
	}
	
	/**
	 * Calculate the movement of the marbles by tracing the points of the track and translate them into horizontal and vertical movement
	 * @param track
	 */
	public void track(TrackGenerator track) {
		
		if(nextPoint < track.points.size()) {
			
			double dx = track.points.get(nextPoint).getX() - track.points.get(nextPoint-1).getX();
			double dy = track.points.get(nextPoint).getY() - track.points.get(nextPoint-1).getY();
				
			if(dx == 0) {
					
				if(dy > 0) {
						
					this.y += Gameplay.levelSystem.getChainVelocity();
					
					this.toTop = false; 
					this.toBottom = true;
					this.toRight = false;
					this.toLeft = false;
						
				}else {	

					this.y -= Gameplay.levelSystem.getChainVelocity();
					
					this.toTop = true;
					this.toBottom = false;
					this.toRight = false;
					this.toLeft = false;
				}
					
			}else if(dy == 0) {
					
				if(dx > 0) {
					
					this.x += Gameplay.levelSystem.getChainVelocity();
					
					this.toTop = false;
					this.toBottom = false;
					this.toRight = true;
					this.toLeft = false;
						
				}else {

					this.x -= Gameplay.levelSystem.getChainVelocity();
					
					this.toTop = false;
					this.toBottom = false;
					this.toRight = false;
					this.toLeft = true;
				}
					
			}
			
			
			
			if(Intersection.distanceBetweenMarbles(this.x, this.y, track.points.get(nextPoint).getX(), track.points.get(nextPoint).getY()) <= Main.marbleSize/2 ) {
				
				nextPoint++;
				
			}
		
		}

	}

	/**
	 * Get the color of a marble
	 * @return
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Set the color of a marble
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	public double getAngle() {
		
		return this.angle;
		
	}
	
	/**
	 * Set the angle of the marble
	 * @param angle
	 */
	public void setAngle(double angle) {
		
		this.angle = angle;
		
	}

	/**
	 * Get the x position of the marble
	 * @return x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Get the y position of the marble
	 * @return y
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Setting the x coordinate of the marble
	 * @param newX
	 */
	public void setX(double newX) {
		this.x = newX;
	}
	
	/**
	 * Setting the y coordinate of the marble
	 * @param newY
	 */
	public void setY(double newY) {
		this.y = newY;
	}
	
	/**
	 * Returning the horizontal movement.
	 * @return true if movement is positive, false if negative
	 */
	public boolean getHorizontalMovement() {
		
		return (this.x > 0);
		
	}
	
	/**
	 * Returning the vertical movement.
	 * @return true if movement is positive, false if negative
	 */
	public boolean getVerticalMovement() {
		
		return (this.y > 0);
		
	}

}
