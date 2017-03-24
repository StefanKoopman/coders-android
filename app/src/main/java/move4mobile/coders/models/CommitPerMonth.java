package move4mobile.coders.models;

/**
 * Created by stefankoopman on 19/03/17.
 */

public class CommitPerMonth {

    public CommitPerMonth() {

    }

    private int score;
    private String orderKey;
    private long lastUpdate;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
