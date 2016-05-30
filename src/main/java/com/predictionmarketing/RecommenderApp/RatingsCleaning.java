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
		
		// If using ml-latest FULL
		/*
		File input = new File("C:/Users/hungpv/Desktop/ratings.dat");
		File output = new File("C:/Users/hungpv/Desktop/dataset.dat");

		FileReader fr = new FileReader(input);
		BufferedReader br = new BufferedReader(fr);
		// this will read the first line
		//br.readLine();
		FileWriter fw = new FileWriter(output);
		BufferedWriter bw = new BufferedWriter(fw);

		String line;
		while ((line = br.readLine()) != null) {
			StringBuilder sb = new StringBuilder();
			String[] parts = line.split("::");
			for (int i = 0; i < parts.length - 1; i++) {
				sb.append(parts[i] + ",");

			}

			bw.write(sb.substring(0, sb.length() - 1));
			bw.newLine();

		}
		bw.close();
		System.out.println("done!");
		*/
		
	}
}
