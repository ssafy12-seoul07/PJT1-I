package shpark.model;

public class Review {
	private String videoTitle;
    private String reviewer;
    private String comment;
    private int rating;

    public Review(String videoTitle, String reviewer, String comment, int rating) {
    	this.videoTitle = videoTitle;
        this.reviewer = reviewer;
        this.comment = comment;
        this.rating = rating;
    }
    
    public String getVideotitle() {
    	return videoTitle;
    }

    public String getReviewer() {
        return reviewer;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }
}
