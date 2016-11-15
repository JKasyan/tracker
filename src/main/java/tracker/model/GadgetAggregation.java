package tracker.model;

import java.util.List;

/**
 * Created by 1 on 10/25/2016.
 */
public class GadgetAggregation {

    private long lastActivity;
    private String id;
    private double lat, lng;
    private List<Gadget> gadgets;

    public List<Gadget> getGadgets() {
        return gadgets;
    }

    public void setGadgets(List<Gadget> gadgets) {
        this.gadgets = gadgets;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

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
                ", lat=" + lat +
                ", lng=" + lng +
                ", gadgets=" + gadgets +
                '}';
    }
}
