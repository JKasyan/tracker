package tracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * Created on 17.06.2016
 *
 * @author Kasyan Evgen
 */
@Controller
public class IndexController {

    //Access-Control-Allow-Origin
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String index() {
        return "main";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
}
