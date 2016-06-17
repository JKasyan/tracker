package tracker.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tracker.model.Point;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 13.06.16.
 *
 * @author evgen
 */
@RestController
@RequestMapping(value = "api/")
public class PointsController {

    private Set<Point> points;

    {
        points = new HashSet<>();
        points.add(new Point(50.5328238,30.6105336));
        points.add(new Point(50.5327061,30.6128523));
        points.add(new Point(50.5324589,30.6143453));
        points.add(new Point(50.5320116,30.6151941));
        points.add(new Point(50.5311532,30.6162353));
    }

    @RequestMapping(value = "/points", method = RequestMethod.GET)
    public Set<Point> getPoints() {
        return points;
    }
}