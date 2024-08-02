package main.java.util;

import main.java.model.Video;
import main.java.model.Review;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JSONParser {
    private String filePath;
    private ObjectMapper objectMapper;

    public JSONParser(String filePath) {
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
    }

    public List<Video> parseVideos() {
        try {
            return objectMapper.readValue(new File(filePath), new TypeReference<List<Video>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Review> parseReviews() {
        try {
            return objectMapper.readValue(new File("src/main/resources/reviews.json"), new TypeReference<List<Review>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writeToJSON(String file, List<?> data) {
        try {
            objectMapper.writeValue(new File(file), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
