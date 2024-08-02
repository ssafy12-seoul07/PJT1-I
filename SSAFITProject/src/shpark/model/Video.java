package shpark.model;

public class Video {
    private int no;
    private String title;
    private String part;
    private String url;

    public Video(int no, String title, String part, String url) {
        this.no = no;
        this.title = title;
        this.part = part;
        this.url = url;
    }

    public int getNo() {
        return no;
    }

    public String getTitle() {
        return title;
    }

    public String getPart() {
        return part;
    }

    public String getUrl() {
        return url;
    }

    public String getInfo() {
        return String.format("No: %d, Title: %s, Part: %s, URL: %s", no, title, part, url);
    }
}

