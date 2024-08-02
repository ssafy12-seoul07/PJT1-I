package shpark.coffee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import shpark.model.User;

public class FileHandler {
    private String filePath;

    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("{") && line.trim().endsWith("}")) {
                    // Simple JSON parsing (assuming one user per line for simplicity)
                    String id = extractValue(line, "id");
                    String name = extractValue(line, "name");
                    String password = extractValue(line, "password");
                    String email = extractValue(line, "email");

                    User user = new User(id, name, password, email);
                    users.add(user);
                }
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

    private String extractValue(String line, String key) {
        int startIndex = line.indexOf("\"" + key + "\":") + key.length() + 3;
        int endIndex = line.indexOf("\"", startIndex);
        return line.substring(startIndex, endIndex);
    }
}
