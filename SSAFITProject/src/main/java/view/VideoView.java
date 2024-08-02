package main.java.view;

import model.Video;
import model.Review;

import java.util.List;

public class VideoView {
    public void displayVideos(List<Video> videoList) {
        System.out.println("Video List:");
        for (Video video : videoList) {
            System.out.println(video.getInfo());
        }
    }

    public void displayVideoDetails(Video video) {
        System.out.println("Video Details:");
        System.out.println(video.getInfo());
    }

    public void displayReviews(List<Review> reviews) {
        System.out.println("Reviews:");
        for (Review review : reviews) {
            System.out.println("Reviewer: " + review.getReviewer());
            System.out.println("Comment: " + review.getComment());
            System.out.println("Rating: " + review.getRating());
            System.out.println("------------");
        }
    }
}
