package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.awt.Image;
import javax.imageio.ImageIO;

public class MyImage {
	public static Image img_next;
	public static Image img_clock;
	public static Image img_mem;
	public static Image img_reg;
	public static Image img_stop;
	public static Image img_export_mem;
	public static Image img_export_reg;
	public static Image img_a;
	public static Image img_add;
	public static Image img_sub;
	public static Image img_pc;
	public static Image img_load;
	
	
	public static int NEW_WIDTH = 36;
	public static int NEW_HEIGHT = 36;
	
	public static void init_img(){
		MyImage myImage = new MyImage();
//		try {
			img_next = myImage.readImage("/images/next.png").getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH );
			img_clock = myImage.readImage("/images/clock.png").getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH );
			img_mem = myImage.readImage("/images/mem.png").getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH );
			img_reg = myImage.readImage("/images/reg.png").getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH );
			img_stop = myImage.readImage("/images/stop.png").getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH );
			
			img_export_mem = myImage.readImage("/images/export_mem.png").getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH ).getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH );
			img_export_reg = myImage.readImage("/images/export_reg.png").getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH ).getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH );
			img_a = myImage.readImage("/images/a.png").getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH );
			img_add = myImage.readImage("/images/add.png").getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH );
			img_sub = myImage.readImage("/images/sub.png").getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH );
			img_pc = myImage.readImage("/images/pc.png").getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH );
			img_load = myImage.readImage("/images/load.png").getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH );

//			img_next = ImageIO.read(new File("static/images/next.png")).getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH );
//			img_clock = ImageIO.read(new File("static/images/clock.png")).getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH );
//			img_mem = ImageIO.read(new File("static/images/mem.png")).getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH );
//			img_reg = ImageIO.read(new File("static/images/reg.png")).getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH );;
//			img_stop = ImageIO.read(new File("static/images/stop.png")).getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH );
//			
//			img_export_mem = ImageIO.read(new File("static/images/export_mem.png")).getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH );; 
//			img_export_reg = ImageIO.read(new File("static/images/export_reg.png")).getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH );;
//			img_a = ImageIO.read(new File("static/images/a.png")).getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH );;
//			img_add = ImageIO.read(new File("static/images/add.png")).getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH );;
//			img_sub = ImageIO.read(new File("static/images/sub.png")).getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH );;
//			img_pc = ImageIO.read(new File("static/images/pc.png")).getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH );;
//			img_load = ImageIO.read(new File("static/images/load.png")).getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH );;
			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public Image readImage(String path) {
		Image image = null;
		try {
//			System.out.println(path);
			URL url = this.getClass().getResource(path);
//			System.out.println(url);
			image = ImageIO.read(url);
//			.getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH );
		}
		catch (Exception e) {
//			System.out.println("> Error at load image path : " + path);
			e.printStackTrace();
		}
		return image;
	}
}
