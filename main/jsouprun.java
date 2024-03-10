package main;

import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class jsouprun {
	public static void main(String[] args) {
        String url = "https://www.walmart.com/reviews/product/1946294711?page=4";

        try {
            Document doc = Jsoup.connect(url).get();
            FileWriter fileWriter = new FileWriter("amazonReview.csv");
            fileWriter.write("reviewer name, review, rating and if verfied, date\n"); 
            System.out.println("reviewer name, review, rating and if verfied, date\n");

            Elements reviews = doc.select("div.w_DHV_.pv3.mv0");

            for (Element review : reviews) {
                String reviewerName = review.select("div.f6.gray.pr2.mb2").text();
                String reviewText = review.select("span.tl-m.mb3.db-m").text();
                String reviewerRating = review.select("div.flex-grow-1").text();
                String reviewerDate = review.select("div.f7.gray.mt1").text();

                fileWriter.write("\"" + reviewerName + "\", \"" + reviewText + "\", \""+ reviewerRating + "\", \""+ reviewerDate +"  \"\n");
                System.out.println("\"" + reviewerName + "\", \"" + reviewText + "\", \""+ reviewerRating + "\", \""+ reviewerDate +"  \"\n");
            }
    

            fileWriter.close();
            System.out.println("Successfully scraped and saved reviews to amazonReviews.csv");
        } catch (IOException e) {
            System.out.println("Error: Unable to connect to the website.");
            System.exit(1);
        }
    }
}
