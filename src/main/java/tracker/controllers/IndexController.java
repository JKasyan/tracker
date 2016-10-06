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
@CrossOrigin(origins = "",
        allowedHeaders = {"",
                "Access-Control-Allow-Credentials",
                "Access-Control-Request-Headers",
                "Access-Control-Allow-Methods"})
public class IndexController {

    //Access-Control-Allow-Origin
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletResponse res) {
        res.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        res.setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Content-Length, X-Requested-With");
        return "index";
    }
}
