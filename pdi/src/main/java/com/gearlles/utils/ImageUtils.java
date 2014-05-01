package com.gearlles.utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageUtils {
	
	public static double[][] readGrayScaleImage(URL path) throws IOException {
		return readGrayScaleImage(path.openStream());
	}
	
	public static double[][] readGrayScaleImage(InputStream imageInputStream) throws IOException {
		// Converting the input image to Gray scale to prevent exceptions
		BufferedImage inputImage = ImageIO.read(imageInputStream);
		BufferedImage bufferedImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = bufferedImage.getGraphics();
		g.drawImage(inputImage, 0, 0, null);
		g.dispose();
		
		double[][] finalImage = new double[bufferedImage.getHeight()][bufferedImage.getWidth()];
		
		for (int i = 0; i < finalImage.length; i++) {
			for (int j = 0; j < finalImage[0].length; j++) {
				double[] tmp = new double[1];
				bufferedImage.getRaster().getPixel(j, i, tmp);
				finalImage[i][j] = tmp[0];
			}
		}
		
		return finalImage;
	}
	
	public static BufferedImage convertToBufferedImage(double[][] image) {
		BufferedImage bi = new BufferedImage(image[0].length, image.length,
				BufferedImage.TYPE_BYTE_GRAY);
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				bi.getRaster().setPixel(j, i, new double[] { image[i][j] });
			}
		}	
		return bi;
	}
}
