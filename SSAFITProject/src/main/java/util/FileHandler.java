package main.java.util;

import model.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileHandler {
    private String filePath;
    private ObjectMapper objectMapper;

    public FileHandler(String filePath) {
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
    }

    public List<User> loadUsers() {
        try {
            return objectMapper.readValue(new File(filePath), new TypeReference<List<User>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveUsers(List<User> users) {
        try {
            objectMapper.writeValue(new File(filePath), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}