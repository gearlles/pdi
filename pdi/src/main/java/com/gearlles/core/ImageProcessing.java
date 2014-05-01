package com.gearlles.core;

import java.util.Arrays;

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
	
	public double[][] applyFilter(double[][] image, double[][] filter, double multiplier) {
		double[][] resultImage = new double[image.length][image[0].length];
		int radiusX = filter.length/2;
		int radiusY = filter[0].length/2;
		
		for (int i = radiusX; i < image.length - radiusX; i++) {
			for (int j = radiusY; j < image[0].length - radiusY; j++) {
				double sum = 0;
				for (int filterX = 0, neighborX = i - radiusX; filterX < filter.length; filterX++, neighborX++) {
					for (int filterY = 0, neighborY = j - radiusY; filterY < filter[0].length; filterY++, neighborY++) {
						sum += image[neighborX][neighborY] * filter[filterX][filterY];
					}
				}
				resultImage[i][j] = multiplier * sum;
			}
		}
		
		return resultImage;
	}
	
	public double[][] sobel(double[][] image) {
		double[][] resultImage = new double[image.length][image[0].length];
    	double[][] sobelX = {	{1, 2,	1},
								{0,	0, 0},
								{-1, -2, -1}};
    	double[][] sobelY = {	{-1, 0,	1},
								{-2, 0, 2},
								{-1, 0, 1}};

    	
		for (int i = 1; i < image.length - 1; i++) {
			for (int j = 1; j < image[0].length - 1; j++) {
				double sumX = 0;
				double sumY = 0;
				for (int filterX = 0, neighborX = i - 1; filterX < sobelX.length; filterX++, neighborX++) {
					for (int filterY = 0, neighborY = j - 1; filterY < sobelX[0].length; filterY++, neighborY++) {
						sumX += image[neighborX][neighborY] * sobelX[filterX][filterY];
						sumY += image[neighborX][neighborY] * sobelY[filterX][filterY];
					}
				}
				resultImage[i][j] = Math.sqrt(Math.pow(sumX, 2) + Math.pow(sumY, 2));
			}
		}
		return resultImage;
	}
	
	public double[][] median(double[][] image, int windowSize) {
		double[][] resultImage = new double[image.length][image[0].length];
		int radius = windowSize/2;
		
		for (int i = radius; i < image.length - radius; i++) {
			for (int j = radius; j < image[0].length - radius; j++) {
				double[] window = new double[windowSize * windowSize];
				int windowArrayCounter = 0;
				for (int windowX = 0, neighborX = i - radius; windowX < windowSize; windowX++, neighborX++) {
					for (int windowY = 0, neighborY = j - radius; windowY < windowSize; windowY++, neighborY++) {
						window[windowArrayCounter++] = image[neighborX][neighborY];
					}
				}
				
				Arrays.sort(window);
				
				double median;
				if (window.length % 2 == 0) {
				    median = ((double)window[window.length/2] + (double)window[window.length/2 - 1])/2;
				} else {
				    median = (double) window[window.length/2];
				}
				
				resultImage[i][j] = median;
			}
		}
		return resultImage;
	}
	
	public double[][] localHistogramStatistics(double[][] image, int windowSize, double E, double k0, double k1, double k2) {
		int pixelsAmmount = image.length * image[0].length;
		int radius = windowSize/2;
		double[][] resultImage = new double[image.length][image[0].length];
		double globalMean = 0;
		double globalMeanDeviation = 0;
		double[] globalScalesProbabilities =  new double[MAXIMUM_GRAY_LEVEL + 1];
		
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				globalScalesProbabilities[(int) image[i][j]]++;
			}
		}
		
		for (int i = 0; i < globalScalesProbabilities.length; i++) {
			globalScalesProbabilities[i] /= pixelsAmmount;
		}
		
		// Global mean
		for (int i = 0; i < globalScalesProbabilities.length; i++) {
			globalMean += i * globalScalesProbabilities[i];
		}
		
		for (int k = 0; k < globalScalesProbabilities.length; k++) {
			globalMeanDeviation += Math.pow(k - globalMean, 2) * globalScalesProbabilities[k];
		}
		
		globalMeanDeviation = Math.sqrt(globalMeanDeviation);
		
		
		// Applying filter
		for (int i = radius; i < image.length - radius; i++) {
			for (int j = radius; j < image[0].length - radius; j++) {
				double[] scalesProbabilities = new double[MAXIMUM_GRAY_LEVEL + 1];
				double sum = 0;
				double variance = 0;
				
				for (int windowX = 0, neighborX = i - radius; windowX < windowSize; windowX++, neighborX++) {
					for (int windowY = 0, neighborY = j - radius; windowY < windowSize; windowY++, neighborY++) {
						double intensity = image[neighborX][neighborY];
						scalesProbabilities[(int) intensity]++;
					}
				}
				
				for (int k = 0; k < scalesProbabilities.length; k++) {
					scalesProbabilities[k] /= (double) windowSize * windowSize;
				}
				
				for (int k = 0; k < scalesProbabilities.length; k++) {
					sum += k * scalesProbabilities[k];
				}
				
				for (int k = 0; k < scalesProbabilities.length; k++) {
					variance += Math.pow(k - sum, 2) * scalesProbabilities[k];
				}
				
				double pixelIntensity = image[i][j];
				double standardDeviation = Math.sqrt(variance);
				
				if (sum <= k0 * globalMean && k1 * globalMeanDeviation <= standardDeviation && standardDeviation <= k2 * globalMeanDeviation) {
					resultImage[i][j] = E * pixelIntensity;
				} else {
					resultImage[i][j] = pixelIntensity;
				}
			}
		}
		
		return resultImage;
	}
	
	public void homomorphic(double[][] image) {

		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
			
			}
		}
	}
}
