package gyber.org.hakaton.page.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorRestController {


    @GetMapping("/err")
    public String getErrorMessage(Model model){

        model.addAttribute("errorText" , "Error . A user with such data is already registered. \nEnter other information or check your email");

        return "application";

    }

    @GetMapping("/404")
    public String get404Page(){

        return "404";

    }
}
