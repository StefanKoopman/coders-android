package move4mobile.coders.models;

/**
 * Created by stefankoopman on 19/03/17.
 */

public class GithubUsert {
    private String avatar;
    private long lastUpdate;
    private String message;
    private String name;
    private double orderKey;
    private int score;
    private String repo;

    public GithubUsert() {
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(double orderKey) {
        this.orderKey = orderKey;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }
}
