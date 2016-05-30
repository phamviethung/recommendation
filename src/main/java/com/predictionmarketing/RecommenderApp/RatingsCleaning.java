package com.predictionmarketing.RecommenderApp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RatingsCleaning {
	public static void main(String[] args) throws IOException {

		File file = new File("C:/Users/hungpv/Downloads/Compressed/ml-latest-small/ratings.csv");
		BufferedReader br = new BufferedReader(new FileReader(file));

		File fout = new File("C:/Users/hungpv/Desktop/ratings.csv");
		BufferedWriter bw = new BufferedWriter(new FileWriter(fout));

		String line;
		line = br.readLine(); // this will read the first line

		while ((line = br.readLine()) != null) {
			String[] s = line.split(",");
			String newLine = s[0] + "," + s[1] + "," + s[2];
			bw.write(newLine);
			bw.newLine();
		}
		br.close();
		bw.close();
	}
}
