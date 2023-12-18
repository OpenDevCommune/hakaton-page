package gyber.org.hakaton.page.database;


import gyber.org.hakaton.page.profile.ApplicationForParticipation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.time.ZoneId;
import java.util.logging.Logger;

@Controller
@Profile("dev")
public class DatabaseControllerDev {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Logger logger = Logger.getLogger("DatabaseControllerDev");




    @PostConstruct
    public void initDataBaseStructure(){

        logger.info("INIT DATABASE STRUCTURE ... CREATE DATABASE ... ");


        jdbcTemplate
                .execute("" +
                "CREATE TABLE applications (\n" +
                        "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "    name_user VARCHAR(255),\n" +
                        "    email_user VARCHAR(255) UNIQUE,\n" +
                        "    language VARCHAR(255),\n" +
                        "    about_me TEXT,\n" +
                        "    date_sent_form TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n" +
                        ");");

        logger.info("DATABASE STRUCTURE CREATE SUCCESSFUL. INIT SUCCESSFUL");

    }


    // CRUD

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


@Controller
@Profile("prod")
class  DatabaseControllerProd{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Logger logger = Logger.getLogger("DatabaseControllerProd");




    // CRUD

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



    public ApplicationForParticipation getApplication(Long applicationId){


        ApplicationForParticipation getApp =
                jdbcTemplate.query(
                        "SELECT id , date_sent_form ,  email_user , name_user , language , about_me FROM applications WHERE id = ?" ,
                        (rs , rowNum) -> new ApplicationForParticipation(
                                rs.getLong("id") ,
                                Instant.ofEpochMilli(rs.getDate("date_sent_form").getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime(),
                                rs.getString("name_user") ,
                                rs.getString("email_user") ,
                                rs.getString("about_me") ,
                                rs.getString("language")) ,
                        applicationId
                ).get(0);



        return getApp;
    }




}
