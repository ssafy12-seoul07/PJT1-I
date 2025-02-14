package shpark.controller;

import shpark.coffee.FileHandler;
import shpark.model.User;
import shpark.view.UserView;

import java.util.List;
import java.util.Scanner;

public class UserController {
    private List<User> userList;
    private UserView userView;
    private FileHandler fileHandler;
    private Scanner scanner;
    private User loggedInUser;
    private static UserController instance = new UserController();
    
    public static UserController getInstance() {
    	return instance;
    }

    private UserController() {
        fileHandler = new FileHandler("src/resources/users.json");
        userList = fileHandler.loadUsers();
        userView = new UserView();
        scanner = new Scanner(System.in);
    }

    public void registerUser() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        User user = new User(id, name, password, email);
        userList.add(user);
        fileHandler.saveUsers(userList);
        System.out.println("User registered successfully.");
    }

    public void login() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        for (User user : userList) {
            if (user.getId().equals(id) && user.getPassword().equals(password)) {
                loggedInUser = user;
                System.out.println("Login successful. Welcome, " + user.getName() + "!");
                return;
            }
        }
        System.out.println("Invalid credentials.");
    }

    public void logout() {
        if (loggedInUser != null) {
            System.out.println("Logged out successfully.");
            loggedInUser = null;
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    public void displayUsers() {
        userView.displayUserList(userList);
    }
}
