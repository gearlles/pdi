package com.gearlles.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class ImageUtils {
	
	private final static int MAXIMUM_GRAY_LEVEL = 255;
	
	public static double[][] normalize(double[][] image) {
		double max = Double.MIN_VALUE;
		double[][] resultImage = new double[image.length][image[0].length];
		
		for (int i = 0; i < image.length; i++) {
			List<Double> list = Arrays.asList(ArrayUtils.toObject(image[i]));
			Double rowMax = Collections.max(list);
			if (max < rowMax){
				max = rowMax;
			}
		}
		
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				resultImage[i][j] = image[i][j] * MAXIMUM_GRAY_LEVEL /  max;
				if (resultImage[i][j] < 0) {
					resultImage[i][j] = 0;
				}
			}
		}
		
		return resultImage;
	}
	
}
