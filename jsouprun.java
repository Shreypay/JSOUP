package main;

import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class jsouprun {
    public static void main(String[] args) {
        // URL of the webpage to scrape
        String url = "https://www.walmart.com/reviews/product/1946294711?page=2";

        try {
            // Connect to the webpage and get the document object
            Document doc = Jsoup.connect(url).get();
            
            // FileWriter for writing reviews containing "camera"
            FileWriter targetWriter = new FileWriter("target.csv");
            
            // FileWriter for writing all reviews
            FileWriter nonTargetWriter = new FileWriter("non-target.csv");

            // Writing headers to both CSV files
            nonTargetWriter.write("reviewer name, review, rating and if verified, date\n");
            targetWriter.write("reviewer name, review, rating and if verified, date\n");

            // Printing headers to console
            System.out.println("reviewer name, review, rating and if verified, date\n");

            // Selecting all review elements
            Elements reviews = doc.select("div.w_DHV_.pv3.mv0");

            // Loop through each review element
            for (Element review : reviews) {
                // Extracting reviewer name
                String reviewerName = review.select("div.f6.gray.pr2.mb2").text();
                
                // Extracting review text
                String reviewText = review.select("span.tl-m.mb3.db-m").text();
                
                // Extracting reviewer rating
                String reviewerRating = review.select("div.flex-grow-1").text();
                
                // Extracting reviewer date
                String reviewerDate = review.select("div.f7.gray.mt1").text();
                
                // Writing to target.csv if review contains "camera"
                if (reviewText.contains("camera")) {
                    targetWriter.write("\"" + reviewerName + "\", \"" + reviewText + "\", \"" + reviewerRating + "\", \"" + reviewerDate + "\"\n");
                }
                
                // Writing to non-target.csv
                nonTargetWriter.write("\"" + reviewerName + "\", \"" + reviewText + "\", \"" + reviewerRating + "\", \"" + reviewerDate + "\"\n");

                // Printing each review to console
                System.out.println("\"" + reviewerName + "\", \"" + reviewText + "\", \"" + reviewerRating + "\", \"" + reviewerDate + "\"\n");
            }

            // Closing file writers
            nonTargetWriter.close();
            targetWriter.close();
            
            // Printing success message
            System.out.println("Successfully scraped and saved reviews.");
        } catch (IOException e) {
            // Handling connection errors
            System.out.println("Error: Unable to connect to the website.");
            System.exit(1);
        }
    }
}
