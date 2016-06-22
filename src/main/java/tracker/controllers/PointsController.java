package tracker.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tracker.model.Point;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created on 13.06.16.
 *
 * @author evgen
 */
@RestController
@RequestMapping(value = "api/")
public class PointsController {

    public static final Logger LOGGER = LoggerFactory.getLogger(PointsController.class);

    private Set<Point> points = new LinkedHashSet<>();

    @RequestMapping(value = "/points", method = RequestMethod.GET)
    public Set<Point> getPoints() {
        return points;
    }

    @RequestMapping(value = "/points", method = RequestMethod.POST)
    public void addPoints(@RequestBody List<Point> points) {
        LOGGER.debug("Points: " + points);
        this.points.addAll(points);
    }
}