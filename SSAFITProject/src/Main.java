import java.util.Scanner;

import shpark.controller.UserController;
import shpark.controller.VideoController;

public class Main {
    public static void main(String[] args) {
        VideoController videoController = VideoController.getInstance();
        UserController userController = UserController.getInstance();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. List Videos");
            System.out.println("2. Select Video");
            System.out.println("3. Register User");
            System.out.println("4. Login");
            System.out.println("5. Logout");
            System.out.println("6. Display Users");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    videoController.listVideos();
                    break;
                case 2:
                    System.out.print("Enter video number: ");
                    int no = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    videoController.selectVideo(no);
                    break;
                case 3:
                    userController.registerUser();
                    break;
                case 4:
                    userController.login();
                    break;
                case 5:
                    userController.logout();
                    break;
                case 6:
                    userController.displayUsers();
                    break;
                case 7:
                    System.out.println("Exiting program.");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
