package tracker.model;

/**
 * Created by 1 on 10/25/2016.
 */
public class GadgetAggregation {

    private long lastActivity;
    private String id;

    public long getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(long lastActivity) {
        this.lastActivity = lastActivity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GadgetAggregation{" +
                "lastActivity=" + lastActivity +
                ", id='" + id + '\'' +
                '}';
    }
}
