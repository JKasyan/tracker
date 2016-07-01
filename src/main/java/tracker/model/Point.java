package tracker.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * Created on 17.06.2016
 * @author Kasyan Evgen
 */
@Document(collection = "Point")
public class Point {

    @Id
    private String id;

    @JsonView
    private double lat, lng;
    private Instant time;
    private String devId;

    public Point(String devId, double lat, double lng, Instant time) {
        this.lat = lat;
        this.lng = lng;
        this.time = time;
        this.devId = devId;
    }

    public Point() {}

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getTime() {
        return time;
    }

    public String getDevId() {
        return devId;
    }

    @Override
    public String toString() {
        return "Point{" +
                "id='" + id + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", time=" + time +
                ", devId='" + devId + '\'' +
                '}';
    }
}
