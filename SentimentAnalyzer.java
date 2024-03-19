import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SentimentAnalyzer {
    // Map to store sentiment values for each word
    private Map<String, Integer> sentimentMap = new HashMap<>();

    // Constructor to initialize sentiment analyzer with a file containing sentiment data
    public SentimentAnalyzer(String sentimentFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(sentimentFilePath))) {
            String line;
            // Read each line from the sentiment file
            while ((line = reader.readLine()) != null) {
                // Split each line into word and sentiment value
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    // Extract word and sentiment value
                    String word = parts[0].trim();
                    int sentimentValue = Integer.parseInt(parts[1].trim());
                    // Store word and sentiment value in the map
                    sentimentMap.put(word, sentimentValue);
                }
            }
        } catch (IOException e) {
            // Handle IO Exception
            System.out.println("Error reading sentiment file: " + e.getMessage());
        }
    }

    // Method to calculate sentiment score of a given text
    public int getSentimentScore(String text) {
        int score = 0;
        // Iterate through each word in the sentiment map
        for (String word : sentimentMap.keySet()) {
            // Check if the word exists in the text
            if (text.contains(word)) {
                // If word exists, add its sentiment value to the score
                score += sentimentMap.get(word);
            }
        }
        // Return the calculated sentiment score
        return score;
    }
}
