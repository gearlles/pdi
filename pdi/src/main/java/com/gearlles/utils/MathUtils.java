package com.gearlles.utils;

public class MathUtils {
	
	// TODO
	@Deprecated
	public static double mean(double[][] image) {
		return 0;
	}
	
	/**
	 * Applies the Discrete Fourier Transform.
	 * 
	 * @param image
	 * @return An array of images. Position 0 is the real part and position 1 is the imaginary part.
	 */
	public static double[][][] DFT(double[][] image) {
		
		double[][] real = new double[image.length][image[0].length];
		double[][] imaginary = new double[image.length][image[0].length];
		double xN = image.length;
		double yN = image[0].length;
		
		for (int u = 0; u < image.length; u++) {
			for (int v = 0; v < image[0].length; v++) {
				for (int x = 0; x < image.length; x++) {
					for (int y = 0; y < image[0].length; y++) {
						real[u][v] += image[x][y] *  dftReal(x, y, u, v, xN, yN);
						imaginary[u][v] += image[x][y] * - dftImaginary(x, y, u, v, xN, yN);
					}
				}
			}
		}
		
		return new double[][][]{real, imaginary};
	}
	
	public static double[][] IDFT(double[][] frequencyImageReal, double[][] frequencyImageImaginary) {
		
		double[][] real = new double[frequencyImageReal.length][frequencyImageReal[0].length];
		double xN = frequencyImageReal.length;
		double yN = frequencyImageReal[0].length;
		
		for (int x = 0; x < frequencyImageReal.length; x++) {
			for (int y = 0; y < frequencyImageReal[0].length; y++) {
				for (int u = 0; u < frequencyImageReal.length; u++) {
					for (int v = 0; v < frequencyImageReal[0].length; v++) {
						real[x][y] += frequencyImageReal[u][v] *  dftReal(u, v, x, y, xN, yN) - frequencyImageImaginary[u][v] *  dftImaginary(u, v, x, y, xN, yN);
					}
				}
			}
		}
		
		for (int i = 0; i < real.length; i++) {
			for (int j = 0; j < real[0].length; j++) {
				real[i][j] /= xN * yN;
			}
		}
		
		return real;
	}
	
	private static double dftReal(double x, double y, double u, double v, double xN, double yN) {
		return Math.cos(2 * Math.PI * (u * x / xN + v * y / yN));
	}
	
	private static double dftImaginary(double x, double y, double u, double v, double xN, double yN) {
		return Math.sin(2 * Math.PI * (u * x / xN + v * y / yN));
	}
	
	public static double euclidianDistance(double x0, double y0, double x1, double y1) {
		return Math.sqrt(Math.pow(x1 - x0, 2) + Math.pow(y1 - y0, 2));
	}
}
