package gyber.org.hakaton.page.database;


import gyber.org.hakaton.page.profile.ApplicationForParticipation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.time.ZoneId;

@Controller
public class DatabaseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;




    @PostConstruct
    public void initDataBaseStructure(){

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

    }


    // CRUD

    public boolean saveApplication(ApplicationForParticipation app){

       int isSuccessful =   jdbcTemplate
                            .update("" +
                                    "INSERT INTO applications (email_user, name_user, about_me, language) \n" +
                                    "VALUES (?, ?, ?, ?);\n" , app.getEmailUser() , app.getNameUser() , app.getAboutUser() , app.getCountry());


        return true;
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
                        rs.getString("country")) ,
                applicationId
        ).get(0);



        return getApp;
    }

    public boolean updateApplication(){
        return false;
    }


    public  boolean deleteApplication(){
        return false;
    }

}
