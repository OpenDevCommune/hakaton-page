package gyber.org.hakaton.page.rest;

import gyber.org.hakaton.page.database.DatabaseController;
import gyber.org.hakaton.page.profile.ApplicationForParticipation;
import gyber.org.hakaton.page.validation.ValidateRegistrationFields;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class MainPageController {


    @Autowired
    private DatabaseController databaseController;

    private Logger logger = Logger.getLogger("MainPageController");


    @GetMapping("/")
    public String rootGet(){

        logger.info("ROOT REDIRECT TO '/info'");
        return "redirect:info";
    }

    @GetMapping("/info")
    public String greeting(){
        return "info";
    }


    @GetMapping("/form")
    public String getForm(Model model){
        model
                .addAttribute("submitButton" , "Submit")
                .addAttribute("pageTitle" , "Form");


        return "application";

    }



    @PostMapping("/application/submit")
    public String proccessApplication(
            @RequestParam("email") @NotBlank  @Email String email ,
            @RequestParam("fullName") @ValidateRegistrationFields @NotBlank  String fullName ,
            @RequestParam("aboutMe") @ValidateRegistrationFields String aboutMe ,
            @RequestParam("country") @ValidateRegistrationFields @NotBlank String country , Model model
    ){

        logger.info("POST IN '/application/submit'. CREATE APPLICATION ... ");

        ApplicationForParticipation app = new ApplicationForParticipation(fullName , email , aboutMe , country);

        logger.info("APPLICATION CREATE SUCCESSFUL. PASSING CONTROL TO THE DATABASE MODULE...");
        boolean result = databaseController.saveApplication(app);

        if (!result) {
           logger.log(Level.SEVERE , "ERROR SAVE APPLICATION IN DATABASE. RETURN error_page");

            return "error_page";
        }
        else {
            logger.info("APPLICATION SAVE SUCCESSFUL");
            return "success";
        }


    }


}
