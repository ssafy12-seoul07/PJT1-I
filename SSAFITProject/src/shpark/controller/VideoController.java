package shpark.controller;

import shpark.coffee.JSONParser;
import shpark.model.Review;
import shpark.model.Video;
import shpark.view.VideoView;

import java.util.List;
import java.util.Scanner;


public class VideoController {
    private List<Video> videoList;
    private VideoView videoView;
    private JSONParser jsonParser;
    private Scanner scanner;

    public VideoController() {
    	jsonParser = JSONParser.getInstance("src/resources/video.json");
        videoList = jsonParser.parseVideos();
        videoView = new VideoView();
        scanner = new Scanner(System.in);
    }

    public void listVideos() {
        videoView.displayVideos(videoList);
    }

    public void selectVideo(int no) {
        for (Video video : videoList) {
            if (video.getNo() == no) {
                videoView.displayVideoDetails(video);
                manageReviews();
                return;
            }
        }
        System.out.println("Video not found.");
    }

    public void manageReviews() {
        System.out.println("1. View Reviews");
        System.out.println("2. Add Review");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        List<Review> reviews = jsonParser.parseReviews();

        switch (choice) {
            case 1:
                videoView.displayReviews(reviews);
                break;
            case 2:
                System.out.print("Enter reviewer name: ");
                String reviewer = scanner.nextLine();
                System.out.print("Enter comment: ");
                String comment = scanner.nextLine();
                System.out.print("Enter rating (1-5): ");
                int rating = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                Review review = new Review(reviewer, comment, rating);
                reviews.add(review);
                jsonParser.writeToJSON("src/resources/reviews.json", reviews);
                System.out.println("Review added successfully.");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
}

