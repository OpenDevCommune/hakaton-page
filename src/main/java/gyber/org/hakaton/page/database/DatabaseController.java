package gyber.org.hakaton.page.database;


import gyber.org.hakaton.page.profile.ApplicationForParticipation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class DatabaseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;



    @PostConstruct
    public void initDataBaseStructure(){

        jdbcTemplate
                .execute("" +
                "CREATE TABLE applications (\n" +
                        "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "    name_user VARCHAR(255),\n" +
                        "    email_user VARCHAR(255) UNIQUE,\n" +
                        "    country VARCHAR(255),\n" +
                        "    about_me TEXT,\n" +
                        "    date_sent_from TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n" +
                        ");");

    }


    // CRUD

    public boolean saveApplication(ApplicationForParticipation app){

       int isSuccessful =   jdbcTemplate
                            .update("" +
                                    "INSERT INTO applications (email_user, name_user, about_me, country) \n" +
                                    "VALUES (?, ?, ?, ?);\n" , app.getEmailUser() , app.getNameUser() , app.getAboutUser() , app.getCountry());


        return true;
    }



    public boolean getApplication(Long applicationId){
        return false;
    }

    public boolean updateApplication(){
        return false;
    }


    public  boolean deleteApplication(){
        return false;
    }

}
