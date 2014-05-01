package com.gearlles;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import org.apache.log4j.chainsaw.Main;

import com.gearlles.core.ImageProcessing;
import com.gearlles.utils.ImageUtils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	InputStream in = Main.class.getResourceAsStream("/image1.jpg");
    	try {
			double[][] image = ImageUtils.readGrayScaleImage(in);
			ImageProcessing ip = new ImageProcessing();
			
			BufferedImage bi = ImageUtils.convertToBufferedImage(ip.equalizeHistogram(image));
			
			JFrame frame = new JFrame();
			frame.getContentPane().setLayout(new FlowLayout());
			frame.getContentPane().add(new JLabel(new ImageIcon(ImageUtils.convertToBufferedImage(image))));
			frame.getContentPane().add(new JLabel(new ImageIcon(bi)));
			frame.pack();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
