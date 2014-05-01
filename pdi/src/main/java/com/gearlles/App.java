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
import com.gearlles.utils.ImageIOUtils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	InputStream in = Main.class.getResourceAsStream("/image1.jpg");
    	
    	double[][] filter = {	{1, 1, 1},
    							{1, 2, 1},
    							{1, 1, 1}	};
    	try {
			double[][] image = ImageIOUtils.readGrayScaleImage(in);
			ImageProcessing ip = new ImageProcessing();
			BufferedImage bi = ImageIOUtils.convertToBufferedImage(ImageUtils.normalize(ip.localHistogramStatistics(image, 3, 10, 0.2, 0.001, 0.5)));
			
			JFrame frame = new JFrame("PDI");
			frame.getContentPane().setLayout(new FlowLayout());
			frame.getContentPane().add(new JLabel(new ImageIcon(ImageIOUtils.convertToBufferedImage(image))));
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
