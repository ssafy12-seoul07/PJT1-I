package shpark.view;

import java.util.List;

import shpark.model.Review;
import shpark.model.Video;

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
        	System.out.println("videoTitle: " + review.getVideotitle());
            System.out.println("Reviewer: " + review.getReviewer());
            System.out.println("Comment: " + review.getComment());
            System.out.println("Rating: " + review.getRating());
            System.out.println("------------");
        }
    }
}

