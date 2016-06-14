package tracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 13.06.16.
 *
 * @author evgen
 */
@Controller
public class PointsController {

    private Set<Point> points;

    {
        points = new HashSet<Point>();
        points.add(new Point(50.5328238,30.6105336));
        points.add(new Point(50.5327061,30.6128523));
        points.add(new Point(50.5324589,30.6143453));
        points.add(new Point(50.5320116,30.6151941));
        points.add(new Point(50.5311532,30.6162353));
    }

    @RequestMapping(value = "api/points")
    @ResponseBody
    public Set<Point> getPoints() {
        return points;
    }
}

class Point {

    final double lat, lng;

    public Point(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
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
}
