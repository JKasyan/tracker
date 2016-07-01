package tracker.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tracker.model.Point;
import tracker.service.TrackerService;

import java.time.Instant;

/**
 * Created by kasyan on 7/1/16.
 */
//@Controller
public class CoordinateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoordinateController.class);
    @Autowired
    private TrackerService trackerService;

    //@RequestMapping(value = "/", method = RequestMethod.GET)
    public void save(@RequestParam String id, @RequestParam String lat,
                     @RequestParam String lon, @RequestParam String timestamp) {
        LOGGER.debug("id=" + id + ", lat=" + lat + ", lon=" + lon + ", timestamp=" + timestamp);
        Instant instant = Instant.ofEpochMilli(Integer.valueOf(timestamp));
        Point point = new Point(id, Double.valueOf(lat), Double.valueOf(lon), instant);
        //trackerService.savePoint(point);
    }

}
