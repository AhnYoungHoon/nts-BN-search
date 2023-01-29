package nts.ntssearch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/afterLogin")
    public String afterLoginHome(){
        return "afterLogin/afterLogin";
    }
}
