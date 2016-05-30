package com.predictionmarketing.RecommenderApp;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

public class movies {
	public static void main(String[] args) throws ElasticsearchException, IOException, InterruptedException, ExecutionException {

		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "gpcn").build();
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("192.168.7.205", 9300));

		// Read file
		File file = new File("C:/Users/hungpv/Desktop/movies.csv");
		Scanner scanner = new Scanner(file);
		scanner.nextLine(); // this will read the first line
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] tokens = line.split("::");
			String id = tokens[0];
			String title = tokens[1];
			// String title = tokens[1].replace("\"", "");
			String genre = tokens[2].replace("|", ", ");

			IndexResponse indexResponse = client.prepareIndex("bigmovie", "film", id)
					.setSource(jsonBuilder().startObject().field("movieId", id).field("title", title).field("genre", genre).endObject()).execute().actionGet();

		}
		scanner.close();
		System.out.println("DONE!!!");
		client.close();

	}
}
