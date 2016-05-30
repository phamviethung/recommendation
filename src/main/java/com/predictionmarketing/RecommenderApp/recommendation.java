package com.predictionmarketing.RecommenderApp;

import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
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

public class recommendation {
	public static void main(String[] args) throws Exception {
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "gpcn").build();
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("192.168.7.205", 9300));

		DataModel model = new FileDataModel(new File("C:/Users/hungpv/Desktop/ratings.csv"));
		// SVDRecommender recommender = new SVDRecommender(model, new SVDPlusPlusFactorizer(model, 10, 11));
		SVDRecommender recommender = new SVDRecommender(model, new ALSWRFactorizer(model, 10, 0.05, 10));
		LongPrimitiveIterator userIter = model.getUserIDs();

		while (userIter.hasNext()) {
			long userID = userIter.nextLong();

			List<RecommendedItem> recommendations = recommender.recommend(userID, 10);

			String str = "";
			for (RecommendedItem recommendation : recommendations) {
				// System.out.println(recommendation.getItemID());
				// System.out.println(recommendation.getValue());
				GetResponse response = client.prepareGet("bigmovie", "film", Long.toString(recommendation.getItemID())).setFields("title").execute().actionGet();
				String title = (String) response.getField("title").getValue();
				// System.out.println(title);
				str = str + title + "|";
			}
	
			String recommend = str.substring(0, str.length() - 1);
			System.out.println(recommend);
			IndexResponse indexResponse = client.prepareIndex("recommendation", "user", String.valueOf(userID))
					.setSource(jsonBuilder().startObject().field("user", userID).field("recommend", recommend).endObject()).execute().actionGet();

		}
		System.out.println("DONE!");
		client.close();
	}
}
