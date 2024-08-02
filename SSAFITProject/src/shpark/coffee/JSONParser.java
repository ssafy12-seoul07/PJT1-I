package shpark.coffee;

import shpark.model.Video;
import shpark.model.Review;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {
    private String videoFilePath;
    private static JSONParser instance;

    private JSONParser(String videoFilePath) {
        this.videoFilePath = videoFilePath;
    }

    public static JSONParser getInstance(String videoFilePath) {
        if (instance == null) {
            instance = new JSONParser(videoFilePath);
        }
        return instance;
    }

    public List<Video> parseVideos() {
        List<Video> videos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(videoFilePath))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line.trim());
            }
            String jsonString = jsonBuilder.toString();
            if (jsonString.startsWith("[") && jsonString.endsWith("]")) {
                jsonString = jsonString.substring(1, jsonString.length() - 1); // Remove the square brackets
                String[] jsonObjects = jsonString.split("(?<=\\}),\\s*(?=\\{)"); // Split at '},{' keeping the braces
                for (String jsonObject : jsonObjects) {
                    try {
                        int no = extractIntValue(jsonObject, "no");
                        String title = extractStringValue(jsonObject, "title");
                        String part = extractStringValue(jsonObject, "part");
                        String url = extractStringValue(jsonObject, "url");

                        Video video = new Video(no, title, part, url);
                        videos.add(video);
                    } catch (Exception e) {
                        System.err.println("Error parsing JSON object: " + jsonObject);
                        e.printStackTrace();
                    }
                }
            } else {
                System.err.println("The JSON file does not contain a valid JSON array.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videos;
    }

    public List<Review> parseReviews() {
        List<Review> reviews = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/resources/reviews.json"))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line.trim());
            }
            String jsonString = jsonBuilder.toString();
            if (jsonString.startsWith("[") && jsonString.endsWith("]")) {
                jsonString = jsonString.substring(1, jsonString.length() - 1); // Remove the square brackets
                String[] jsonObjects = jsonString.split("(?<=\\}),\\s*(?=\\{)"); // Split at '},{' keeping the braces
                for (String jsonObject : jsonObjects) {
                    try {
                        String reviewer = extractStringValue(jsonObject, "reviewer");
                        String comment = extractStringValue(jsonObject, "comment");
                        int rating = extractIntValue(jsonObject, "rating");

                        Review review = new Review(reviewer, comment, rating);
                        reviews.add(review);
                    } catch (Exception e) {
                        System.err.println("Error parsing JSON object: " + jsonObject);
                        e.printStackTrace();
                    }
                }
            } else {
                System.err.println("The JSON file does not contain a valid JSON array.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public void writeToJSON(String filePath, List<Review> reviews) {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write("[");
            for (int i = 0; i < reviews.size(); i++) {
                Review review = reviews.get(i);
                file.write(String.format("{\"reviewer\":\"%s\",\"comment\":\"%s\",\"rating\":%d}", review.getReviewer(), review.getComment(), review.getRating()));
                if (i < reviews.size() - 1) {
                    file.write(",");
                }
            }
            file.write("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int extractIntValue(String json, String key) {
        String stringValue = extractStringValue(json, key);
        return Integer.parseInt(stringValue);
    }

    private String extractStringValue(String json, String key) {
        String keyPattern = "\"" + key + "\":";
        int startIndex = json.indexOf(keyPattern) + keyPattern.length();
        int endIndex;
        if (json.charAt(startIndex) == '"') {
            startIndex++;
            endIndex = json.indexOf("\"", startIndex);
        } else {
            endIndex = json.indexOf(",", startIndex);
            if (endIndex == -1) {
                endIndex = json.indexOf("}", startIndex);
            }
        }
        return json.substring(startIndex, endIndex).trim();
    }
}
