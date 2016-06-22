package tracker.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    public Point(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
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

    @Override
    public String toString() {
        return "Point{" +
                "id='" + id + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
