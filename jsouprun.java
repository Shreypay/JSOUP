import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SentimentAnalyzer {
    private Map<String, Integer> sentimentMap = new HashMap<>();

    public SentimentAnalyzer(String sentimentFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(sentimentFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String word = parts[0].trim();
                    int sentimentValue = Integer.parseInt(parts[1].trim());
                    sentimentMap.put(word, sentimentValue);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading sentiment file: " + e.getMessage());
        }
    }

    public int getSentimentScore(String text) {
        int score = 0;
        for (String word : sentimentMap.keySet()) {
            if (text.contains(word)) {
                score += sentimentMap.get(word);
            }
        }
        return score;
    }
}

public class JsoupRun {
    public static void main(String[] args) {
        String url = "https://www.walmart.com/reviews/product/1946294711?page=2";
        String sentimentFilePath = "JsoupProject/sentiment.csv"; 

        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer(sentimentFilePath);

        try {
            Document doc = Jsoup.connect(url).get();
            FileWriter fileWriter = new FileWriter("non-target.csv");
            FileWriter Writers = new FileWriter("target.csv");

            fileWriter.write("reviewer name, review, rating and if verified, date, sentiment score\n");
            Writers.write("reviewer name, review, rating and if verified, date, sentiment score\n");

            Elements reviews = doc.select("div.w_DHV_.pv3.mv0");

            for (Element review : reviews) {
                String reviewerName = review.select("div.f6.gray.pr2.mb2").text();
                String reviewText = review.select("span.tl-m.mb3.db-m").text();
                String reviewerRating = review.select("div.flex-grow-1").text();
                String reviewerDate = review.select("div.f7.gray.mt1").text();

                int sentimentScore = sentimentAnalyzer.getSentimentScore(reviewText);

                String line = "\"" + reviewerName + "\", \"" + reviewText + "\", \"" + reviewerRating + "\", \"" + reviewerDate + "\", \"" + sentimentScore + "\"\n";
                if (sentimentScore > 0) {
                    Writers.write(line);
                } else {
                    fileWriter.write(line);
                }
            }

            Writers.close();
            fileWriter.close();
            System.out.println("Successfully scraped and saved reviews to target.csv and non-target.csv");
        } catch (IOException e) {
            System.out.println("Error: Unable to connect to the website.");
            System.exit(1);
        }
    }
}
