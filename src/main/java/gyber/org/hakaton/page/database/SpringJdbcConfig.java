package gyber.org.hakaton.page.database;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Profile("prod")
@Configuration
public class SpringJdbcConfig {


    private  String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";


    @Value("${DATABASE_URL}")
    private  String DATABASE_URL;

    @Value("${DATABASE_PASS}")
    private String DATABASE_PASS;


    @Value("${DATABASE_USR}")
    private String DATABASE_USR;

    @Bean
    public DataSource dataSource(){

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DATABASE_DRIVER);
        dataSource.setUrl(DATABASE_URL);
        dataSource.setPassword(DATABASE_PASS);
        dataSource.setUsername(DATABASE_USR);

        return dataSource;
    }



}

@Profile("dev")
@Configuration
class SpringTestJdbcConfig{

    @Bean
    public DataSource dataSource(){
        return  new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

}