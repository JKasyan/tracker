package tracker.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tracker.model.Gadget;
import tracker.service.TrackerService;

import java.util.List;

/**
 * Created by kasyan on 10/23/16.
 */
@RequestMapping(value = "api/")
@RestController
public class GadgetController {

    @Autowired
    private TrackerService trackerService;
    private static final Logger LOGGER = LoggerFactory.getLogger(GadgetController.class);

    @RequestMapping(value = "/gadgets", method = RequestMethod.GET)
    public List<Gadget> getMyGadgets() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        List<Gadget> gadgets = trackerService.getGadgets(email);
        LOGGER.debug("Gadgets = " + gadgets);
        return gadgets;
    }
}
