package gyber.org.hakaton.page.rest;

import gyber.org.hakaton.page.database.DatabaseController;
import gyber.org.hakaton.page.mail.MailServerClass;
import gyber.org.hakaton.page.profile.ApplicationForParticipation;
import jakarta.servlet.http.HttpServletResponse;
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

    @Autowired
    private MailServerClass mailServerClass;


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
            @RequestParam("email")  String email ,
            @RequestParam("fullName")   String fullName ,
            @RequestParam("aboutMe") String aboutMe ,
            @RequestParam("country")  String country ,
            HttpServletResponse response
    ){

        logger.info("POST IN '/application/submit'. CREATE APPLICATION ... ");

        ApplicationForParticipation app = new ApplicationForParticipation(fullName , email , aboutMe , country);

        logger.info("CHECKING IF THE USER HAS SUBMITTED AN APPLICATION PREVIOUSLY ... ");

        if (databaseController.applicationExist(email)){
            logger.info("USER ALREADY EXIST. REPEATED APPLICATION IS DENIED");

            response.setStatus(409);
            response.setHeader("Server-Response" , "A user with this email has already been registered before; 409 Conflict;");
            return "success";

        }

        logger.info("USER APPLICATION IS NEW. PROCESS APPLICATION ... ");

        logger.info("APPLICATION CREATE SUCCESSFUL. PASSING CONTROL TO THE DATABASE MODULE...");
        boolean result = databaseController.saveApplication(app);

        if (!result) {
           logger.log(Level.SEVERE , "ERROR SAVE APPLICATION IN DATABASE. RETURN error_page");

            response.setStatus(400);
            response.setHeader("Server-Response" , "Error while saving application; Examine the server logs for additional information;");
            return "error_page";
        }
        else {
            logger.info("APPLICATION SAVE SUCCESSFUL. SENT BY E-MAIL ... ");



            mailServerClass.sendMail(email, fullName);

            response.setHeader("Server-Response" , "Application save successful;");

            return "success";

        }


    }


}
