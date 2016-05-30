package com.predictionmarketing.RecommenderApp;

import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

public class indicators {
	public static void main(String[] args) throws Exception {
		DataModel model = new FileDataModel(new File("C:/Users/hungpv/Desktop/ratings.csv"));
		System.out.println("number of users is: " + model.getNumUsers());
		System.out.println("number of items is: " + model.getNumItems());
		// create an ItemSimilarity object for testing similarity
		ItemSimilarity similarity = new LogLikelihoodSimilarity(model);
		// Create an Item Based recommender using the model and loglikelihood
		// similarity measure

		Recommender recommender = new GenericItemBasedRecommender(model, similarity);

		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "gpcn").build();
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("192.168.7.205", 9300));

		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index("bigmovie");
		updateRequest.type("film");

		LongPrimitiveIterator itemIter = model.getItemIDs();
		while (itemIter.hasNext()) {
			long itemID = itemIter.nextLong();
			System.out.println("item" + itemID);
			List<RecommendedItem> recommendations = ((GenericItemBasedRecommender) recommender).mostSimilarItems(itemID, 10);
			String str = "";
			for (RecommendedItem recommendation : recommendations) {

				System.out.println(recommendation.getValue());
				GetResponse response = client.prepareGet("bigmovie", "film", Long.toString(recommendation.getItemID())).setFields("title").execute().actionGet();
				String title = (String) response.getField("title").getValue();
				System.out.println(title);
				str = str + title + "|";

			}

			String indicators = str.substring(0, str.length() - 1);
			System.out.println(indicators);
			updateRequest.id(Long.toString(itemID));
			updateRequest.doc(jsonBuilder().startObject().field("indicators", indicators).endObject());
			client.update(updateRequest).get();

		}
		System.out.println("DONE!");
	}
}
