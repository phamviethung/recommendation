package com.predictionmarketing.RecommenderApp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MoviesCleaning {
	public static void main(String[] args) throws IOException {

		File file = new File("C:/Users/hungpv/Downloads/Compressed/ml-latest-small/movies.csv");
		BufferedReader br = new BufferedReader(new FileReader(file));

		File fout = new File("C:/Users/hungpv/Desktop/movies.csv");
		BufferedWriter bw = new BufferedWriter(new FileWriter(fout));

		String line;
		line = br.readLine(); // this will read the first line
		bw.write(line);
		bw.newLine();
		while ((line = br.readLine()) != null) {
			if (line.contains("\"")) {
				String[] s = line.split("\"");
				String newLine = "";
				for (int i = 0; i < s.length; i++) {
					s[0] = s[0].replace(",", "::");
					s[s.length - 1] = s[s.length - 1].replace(",", "::");
					newLine += s[i];
				}
				System.out.println(newLine);
				bw.write(newLine);
				bw.newLine();
			} else {
				line = line.replace(",", "::");
				System.out.println(line);
				bw.write(line);
				bw.newLine();
			}
		}
		br.close();
		bw.close();
	}
}
