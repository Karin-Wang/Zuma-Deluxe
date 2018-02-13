package zumaDeluxeNew;
import java.io.FileReader;
import java.util.Properties;


public class Main {
	
	public static Gameplay gameplay;
	public static int frameWidth;
	public static int frameHeight;
	public static int marbleSize;
	public static int shooterSize;

	public static int marbleQuantity;
	
	public static double startPointX;
	public static double startPointY;
	
	public static int shootingAngleKeyboard;
	
	public static void main(String[] args) {
		
		try(FileReader reader =  new FileReader("config")) {
	       
    	   		Properties properties = new Properties();
    	   		properties.load(reader);
    		    
    	   		frameWidth = Integer.valueOf(properties.getProperty("frameWidth"));
    		    frameHeight = Integer.valueOf(properties.getProperty("frameHeight"));
    		    marbleSize = Integer.valueOf(properties.getProperty("marbleSize"));
    		    shooterSize = Integer.valueOf(properties.getProperty("shooterSize"));
    		    marbleQuantity = Integer.valueOf(properties.getProperty("marbleQuantity"));
    		    startPointX = Integer.valueOf(properties.getProperty("startPointX"));
    		    startPointY = Integer.valueOf(properties.getProperty("startPointY"));
    		    shootingAngleKeyboard = Integer.valueOf(properties.getProperty("shootingAngleKeyboard"));    
	       
		}catch (Exception e) {;
       	
			e.printStackTrace();
       		frameWidth = 800;
			frameHeight = 800;
			marbleSize = 30;
			shooterSize = 50;
			marbleQuantity = 20;
			startPointX = 0;
			startPointY = 200;
			shootingAngleKeyboard = 270;
			
		}finally{
    	   
    	   		gameplay = new Gameplay();
       
		}
       
		
	}

}
 