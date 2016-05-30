package com.predictionmarketing.RecommenderApp;

import java.io.*;

public class preprocess {
	public static void main(String[] args) throws IOException {
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
	}
}
