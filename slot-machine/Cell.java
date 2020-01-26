import java.io.File;
import java.util.Random;

import javax.swing.ImageIcon;



public class Cell {
	private final static String IMAGE_PATH = System.getProperty("user.dir") + "/images/";
	private final static File GRAPE = new File(IMAGE_PATH + "grape.png");
	private final static File CHERRY = new File(IMAGE_PATH + "cherry.png");
	private final static File BELL = new File(IMAGE_PATH + "bell.png");
	private final static File LINE = new File(IMAGE_PATH + "line.png");
	
	private static Random generator = new Random();
	
	private ImageIcon icon;
	private String name;
	
	public Cell() {
		changeIcon(true);
	}
	
	private void setIcon(File imageFile){
//		BufferedImage imageBuf = null;
//		try {
//			imageBuf = ImageIO.read(imageFile);
//		} catch (IOException e) {}
		
		icon = new ImageIcon(imageFile.getAbsolutePath());
	}
	
	public void changeIcon(boolean isRegular){
		int rand = generator.nextInt(100);
		System.out.println(rand);
		if(isRegular){
			rand %= 8;
		}
		else{
			rand %= 3;
		}
		
		if(rand == 0){
			setIcon(BELL);
			name = "Bell";
		}
		else if (rand == 1){
			setIcon(GRAPE);
			name = "Grape";
		}
		else if (rand == 2){
			setIcon(CHERRY);
			name = "Cherry";
		}
		else{
			setIcon(LINE);
			name = "Line";
		}
	}
	
	public String getName(){
		return name;
	}
	
	public ImageIcon getIcon(){
		return icon;
	}
	
}
