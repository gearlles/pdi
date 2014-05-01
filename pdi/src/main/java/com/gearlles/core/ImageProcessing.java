package com.gearlles.core;

public class ImageProcessing {
	
	private final int MAXIMUM_GRAY_LEVEL = 255;
	
	public double[][] equalizeHistogram(double[][] image) {
		int pixelsAmmount = image.length * image[0].length;
		
		double[] scalesProbabilities = new double[MAXIMUM_GRAY_LEVEL + 1];
		double[][] equalizatedImage = new double[image.length][image[0].length];
		
		// At this point, scalesProbabilities is the histogram
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				scalesProbabilities[(int) image[i][j]]++;
			}
		}
		
		scalesProbabilities[0] /= pixelsAmmount;
		
		for (int i = 1; i < scalesProbabilities.length; i++) {
			scalesProbabilities[i] = scalesProbabilities[i - 1] + scalesProbabilities[i] / pixelsAmmount;
		}
		
		// gray scale X will be mapped to grayScaleMap[X]
		int[] grayScaleMap = new int[MAXIMUM_GRAY_LEVEL + 1];
		for (int i = 0; i < scalesProbabilities.length; i++) {
			grayScaleMap[i] = (int) Math.round(scalesProbabilities[i] * MAXIMUM_GRAY_LEVEL);
		}
		
		// Mapping
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				equalizatedImage[i][j] = grayScaleMap[(int) image[i][j]];
			}
		}
		
		return equalizatedImage;
	}
}
