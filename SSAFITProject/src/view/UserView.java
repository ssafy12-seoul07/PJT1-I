package view;

import model.User;

import java.util.List;

public class UserView {
    public void displayUserForm() {
        System.out.println("User Registration Form:");
    }

    public void displayLoginForm() {
        System.out.println("Login Form:");
    }

    public void displayUserList(List<User> userList) {
        System.out.println("Registered Users:");
        for (User user : userList) {
            System.out.println(user.getInfo());
        }
    }
}
