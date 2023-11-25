package gyber.org.hakaton.page.rest;

import gyber.org.hakaton.page.database.DatabaseController;
import gyber.org.hakaton.page.profile.ApplicationForParticipation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainPageController {


    @Autowired
    private DatabaseController databaseController;

    @GetMapping("/info")
    public String greeting(Model model){
        model.addAttribute("name" , "Nick");
        return "hello";
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
            @RequestParam("fullName") @NotBlank  String fullName ,
            @RequestParam("aboutMe")  String aboutMe ,
            @RequestParam("country") @NotBlank String country , Model model
    ){

        ApplicationForParticipation app = new ApplicationForParticipation(fullName , email , aboutMe , country);
        boolean result = databaseController.saveApplication(app);

        if (!result) {
            System.out.println("\n\n ERROR TO SAVE USER \n\n");
            model
                    .addAttribute("submitButton" , "Submit")
                    .addAttribute("pageTitle" , "Form")
                    .addAttribute("errorResponseText", "Error save your data , please try again");

            return "application";
        }
        else {
            System.out.println("\n\n USER SUCCESSFUL SAVED \n\n");
            return "success";
        }


    }
}
