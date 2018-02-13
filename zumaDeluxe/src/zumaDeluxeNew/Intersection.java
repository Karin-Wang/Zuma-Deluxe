package zumaDeluxeNew;

public class Intersection {
	
	/**
	 * Calculates the distance between the centers of marble1 and marble2
	 * @param marble1
	 * @param marble2
	 * @return distance between marbles
	 */
	public static int distanceBetweenMarbles(Marble marble1, Marble marble2) {
		
		return (int) Math.pow(Math.pow(marble1.x - marble2.x, 2) + Math.pow(marble1.y - marble2.y, 2), .5);
		
	}
	
	/**
	 * Calculate the distance between the centers of two marbles
	 * @param marbleX
	 * @param marbleY
	 * @param pointX
	 * @param pointY
	 * @return distance between marbles
	 */
	public static double distanceBetweenMarbles(double marbleX, double marbleY, double pointX, double pointY) {
		
		return (double) Math.pow(Math.pow(marbleX - pointX, 2) + Math.pow(marbleY - pointY, 2), .5);
		
	}

}
