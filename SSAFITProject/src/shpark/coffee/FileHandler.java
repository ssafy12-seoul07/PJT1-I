package shpark.coffee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import shpark.model.User;
import shpark.model.Video;

public class FileHandler {
    private String filePath;

    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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
                    	String id = extractStringValue(jsonObject, "id");
                        String name = extractStringValue(jsonObject, "name");
                        String password = extractStringValue(jsonObject, "password");
                        String email = extractStringValue(jsonObject, "email");
                        
                        User user = new User(id, name, password, email);
                        users.add(user);
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
        return users;
    }

    public void saveUsers(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("[\n");
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                writer.write("  {\n");
                writer.write("    \"id\": \"" + user.getId() + "\",\n");
                writer.write("    \"name\": \"" + user.getName() + "\",\n");
                writer.write("    \"password\": \"" + user.getPassword() + "\",\n");
                writer.write("    \"email\": \"" + user.getEmail() + "\"\n");
                writer.write("  }");
                if (i < users.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            writer.write("]\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
