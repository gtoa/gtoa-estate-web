package webserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @RequestMapping(value = "/index", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getIndex() {

        return "ok";
    }
}
