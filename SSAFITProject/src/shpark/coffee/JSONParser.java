package shpark.coffee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import shpark.model.Review;
import shpark.model.Video;

public class JSONParser {
    private String filePath;

    public JSONParser(String filePath) {
        this.filePath = filePath;
    }

    public List<Video> parseVideos() {
        List<Video> videos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("{") && line.trim().endsWith("}")) {
                    // Simple JSON parsing
                    int no = Integer.parseInt(extractValue(line, "no"));
                    String title = extractValue(line, "title");
                    String part = extractValue(line, "part");
                    String url = extractValue(line, "url");

                    Video video = new Video(no, title, part, url);
                    videos.add(video);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videos;
    }

    public List<Review> parseReviews() {
        List<Review> reviews = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("{") && line.trim().endsWith("}")) {
                    // Simple JSON parsing
                    String reviewer = extractValue(line, "reviewer");
                    String comment = extractValue(line, "comment");
                    int rating = Integer.parseInt(extractValue(line, "rating"));

                    Review review = new Review(reviewer, comment, rating);
                    reviews.add(review);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public void writeToJSON(String file, List<?> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("[\n");
            for (int i = 0; i < data.size(); i++) {
                Object obj = data.get(i);
                if (obj instanceof Video) {
                    Video video = (Video) obj;
                    writer.write("  {\n");
                    writer.write("    \"no\": " + video.getNo() + ",\n");
                    writer.write("    \"title\": \"" + video.getTitle() + "\",\n");
                    writer.write("    \"part\": \"" + video.getPart() + "\",\n");
                    writer.write("    \"url\": \"" + video.getUrl() + "\"\n");
                    writer.write("  }");
                } else if (obj instanceof Review) {
                    Review review = (Review) obj;
                    writer.write("  {\n");
                    writer.write("    \"reviewer\": \"" + review.getReviewer() + "\",\n");
                    writer.write("    \"comment\": \"" + review.getComment() + "\",\n");
                    writer.write("    \"rating\": " + review.getRating() + "\n");
                    writer.write("  }");
                }
                if (i < data.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            writer.write("]\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String extractValue(String line, String key) {
        int startIndex = line.indexOf("\"" + key + "\":") + key.length() + 3;
        int endIndex = line.indexOf("\"", startIndex);
        if (endIndex == -1) {
            endIndex = line.indexOf(",", startIndex);
            if (endIndex == -1) {
                endIndex = line.indexOf("}", startIndex);
            }
        }
        return line.substring(startIndex, endIndex).replaceAll("\"", "").trim();
    }
}
