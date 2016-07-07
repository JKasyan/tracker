package tracker.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tracker.model.Point;
import tracker.service.TrackerService;

import java.time.Instant;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(PointsController.class);

    @Autowired
    private TrackerService trackerService;

    @RequestMapping(value = "/points", method = RequestMethod.GET)
    public List<Point> getPoints() {
        return trackerService.allPoints();
    }

    @RequestMapping(value = "/points", method = RequestMethod.POST)
    public void addPoints(@RequestBody List<Point> points) {
        LOGGER.debug("Points: " + points);
    }

    @RequestMapping(value = "/points/from={from}/to={to}", method = RequestMethod.GET)
    public List<Point> getPoints(@PathVariable long from,@PathVariable long to) {
        LOGGER.debug("From: " + from + ", to: " + to);
        return trackerService.getPoints(from, to);
    }

    @RequestMapping(value = "/points/quantity={quantity}", method = RequestMethod.GET)
    public List<Point> lastPoints(@PathVariable int quantity) {
        LOGGER.debug("Quantity: " + quantity);
        return trackerService.allPoints(quantity);
    }
}