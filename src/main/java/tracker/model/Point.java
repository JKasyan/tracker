package tracker.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created on 17.06.2016
 * @author Kasyan Evgen
 */
@Document(collection = "Point")
public class Point {

    @Id
    private String _id;

    @JsonView
    private double lat, lng;
    private long timestamp;
    private String gadgetNumber;

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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getGadgetNumber() {
        return gadgetNumber;
    }

    public void setGadgetNumber(String gadgetNumber) {
        this.gadgetNumber = gadgetNumber;
    }

    @Override
    public String toString() {
        return "Point{" +
                "_id='" + _id + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", timestamp=" + timestamp +
                ", gadgetNumber='" + gadgetNumber + '\'' +
                '}';
    }
}
