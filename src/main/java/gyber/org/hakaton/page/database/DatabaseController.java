package gyber.org.hakaton.page.database;


import gyber.org.hakaton.page.profile.ApplicationForParticipation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;

import java.util.logging.Logger;

@Controller
public class DatabaseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Logger logger = Logger.getLogger("DatabaseControllerDev");




    @PostConstruct
    public void initDataBaseStructure(){

        logger.info("INIT DATABASE STRUCTURE ... CREATE DATABASE ... ");



        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS applications (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name_user VARCHAR(255), " +
                "email_user VARCHAR(255) UNIQUE, " +
                "language VARCHAR(255), " +
                "about_me TEXT, " +
                "date_sent_from TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");");


        logger.info("DATABASE STRUCTURE CREATE SUCCESSFUL. INIT SUCCESSFUL");

    }


    public boolean saveApplication(ApplicationForParticipation app){

        logger.info("REQUEST TO SAVE APPLICATION IN DATABASE. CREATE AND EXECUTE QUERY ... ");



        int isSuccessful =   jdbcTemplate
                            .update("" +
                                    "INSERT INTO applications (email_user, name_user, about_me, language) \n" +
                                    "VALUES (?, ?, ?, ?);\n" , app.getEmailUser() , app.getNameUser() , app.getAboutUser() , app.getLanguage());

       logger.info("APPLICATION SAVE SUCCESSFUL. RETURN CONTROL");

        return true;
    }

    public boolean applicationExist(String emailUser){
        String sql = "SELECT COUNT(*) FROM applications WHERE email_user = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{emailUser}, Integer.class);
        return count != null && count > 0;
    }







}

