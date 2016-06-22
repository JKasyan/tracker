package tracker.model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Created on 17.06.2016
 * @author Kasyan Evgen
 */
public class Point {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (Double.compare(point.lat, lat) != 0) return false;
        if (Double.compare(point.lng, lng) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(lat);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lng);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Point{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
