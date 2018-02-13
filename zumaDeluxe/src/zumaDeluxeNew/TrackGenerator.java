package zumaDeluxeNew;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.awt.Color;

public class TrackGenerator {
	
	public double rows = Main.frameWidth;
	public double cols = Main.frameHeight;
	
	public int steps = 9;
	
	public double[] rowPoints;
	public double[] colPoints;
	
	public double rowStepSize; 
	public double colStepSize;

	public ArrayList<Point> points = new ArrayList<>();
	public ArrayList<Point> allPoints = new ArrayList<>();
	
	public TrackGenerator() {
		
		rowPoints = new double[steps];
		colPoints = new double[steps];
		
		rowPoints[0] = Main.startPointY;
		colPoints[0] = Main.startPointX;
		
		if(Main.startPointY > rows/2) {
			rowStepSize = (rows-Main.startPointY)/((steps-1)/2);
		}else {
			rowStepSize = (rows/2-Main.startPointY)/((steps-1)/2);
		}
		if(Main.startPointX > cols/2) {
			colStepSize = (cols-Main.startPointX)/((steps-1)/2);
		}else {
			colStepSize = (cols/2-Main.startPointX)/((steps-1)/2);
		}
		
		Point startPoint = new Point();
		startPoint.setLocation(Main.startPointX*Main.frameWidth/cols, Main.startPointY*Main.frameHeight/rows);
		allPoints.add(startPoint);
		points.add(startPoint);
		
		for (int i = 1; i < steps; i++) {
			
			if(i % 2 == 0) {
				
				verticalLine(i);
				
			}else {

				horizontalLine(i);
			}
		}
		
	}
	
	/**
	 * Generating vertical lines of the track
	 * @param i
	 */
	public void verticalLine(int i) {
		
		colPoints[i] = colPoints[i-1];
		
		int sign = 1;
		
		if(rowPoints[i-1] < cols/2) {
			rowPoints[i] = rows-rowPoints[i-1]-rowStepSize;
		}else {
			rowPoints[i] = rows-rowPoints[i-1]+rowStepSize;
			sign = -1;
		}
		Point p = new Point();
		p.setLocation(colPoints[i]*(Main.frameWidth/rows), rowPoints[i]*(Main.frameHeight/cols));
		points.add(p);
		
		for(int k = 0; k < Math.abs(rowPoints[i] - rowPoints[i-1]); k++) {
			Point pnt = new Point();
			pnt.setLocation(allPoints.get(allPoints.size()-1).getX(), allPoints.get(allPoints.size()-1).getY()+sign*Main.frameHeight/rows);
			allPoints.add(pnt);
		}
		
	}
	
	/**
	 * Generating horizontal lines of the track
	 * @param i
	 */
	public void horizontalLine(int i) {
		rowPoints[i] = rowPoints[i-1];
		
		int sign = 1;
		
		if(colPoints[i-1] < rows/2) {
			colPoints[i] = cols-colPoints[i-1]-colStepSize;
		}else {
			colPoints[i] = cols-colPoints[i-1]+colStepSize;
			sign = -1;
		}
		Point p = new Point();
		p.setLocation(colPoints[i]*(Main.frameWidth/rows), rowPoints[i]*(Main.frameHeight/cols));
		points.add(p);
		
		for(int k = 0; k < Math.abs(colPoints[i] - colPoints[i-1]); k++) {
			Point pnt = new Point();
			pnt.setLocation(allPoints.get(allPoints.size()-1).getX()+sign*Main.frameWidth/rows, allPoints.get(allPoints.size()-1).getY());
			allPoints.add(pnt);
		}
	}
	
	/**
	 * Drawing of the track on the map
	 * @param g
	 */
	public void draw(Graphics2D g) {
		
		for(int i = 0; i < rowPoints.length-1; i++) { 
					
			g.setColor(Color.white);
			
			Shape p = new Line2D.Double(colPoints[i],rowPoints[i],colPoints[i+1],rowPoints[i+1]); 
			g.draw(p);
		
		}
		
	}

}
