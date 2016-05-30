package com.predictionmarketing.RecommenderApp;

import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.File;
import java.util.Scanner;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

public class links {
	public static void main(String[] args) throws Exception {

		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "gpcn").build();
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("192.168.7.205", 9300));

		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index("bigmovie");
		updateRequest.type("film");

		// Read file
		File file = new File("C:/Users/hungpv/Desktop/links.csv");
		Scanner scanner = new Scanner(file);
		scanner.nextLine(); // this will read the first line
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] tokens = line.split(",");
			String movieId = tokens[0];
			String imdbId = tokens[1];
			updateRequest.id(movieId);
			updateRequest.doc(jsonBuilder().startObject().field("imdbId", imdbId).endObject());
			client.update(updateRequest).get();
		}
		scanner.close();
		System.out.println("DONE!");
	}
}
