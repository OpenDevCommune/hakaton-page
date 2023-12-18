package gyber.org.hakaton.page.mail;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MailServerClass {


    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Autowired
    private SpringTemplateEngine engine;

    private Logger logger = Logger.getLogger("MailServerClass");


    public void init() throws MessagingException {
        javaMailSender.testConnection();
    }





    @Async
    public void sendMail(String mailAddress , String username){
        try {

            logger.info("CREATING A LETTER FROM '" + mailAddress + " IN NAME '" + username + "'. CREATE ... ");
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("hakaton@gyber.org");
            helper.setTo(mailAddress);
            helper.setSubject("Participation in the Gybernaty hackathon");
            helper.setText(processFile(username), true); // Второй аргумент 'true' указывает, что это HTML-письмо

            logger.info("LETTER CREATE SUCCESSFUL. SEND LETTER ... ");

            javaMailSender.send(message);

            logger.info("LETTER SEND SUCCESSFUL. ");

        } catch (Exception e) {

            logger.log(Level.SEVERE , "EXCEPTION BY SENT MAIL" , e);
        }

    }

    public String processFile(String username){
        Context context = new Context();
        context.setVariable("username" , "Respectful " + username);

        return engine.process("sentMail" , context);
    }




}


@Configuration
class MailServiceConfigClass{

    @Value("${SMTP_HOST}")
    private String SMTP_SERVER;
    @Value("${MAIL_ADDRESS}")
    private String EMAIL_ADDRESS;

    @Value("${MAIL_PASSWORD}")
    private String EMAIL_PASSWORD;





    @Bean
    public JavaMailSenderImpl javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(SMTP_SERVER);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setUsername(EMAIL_ADDRESS);
        javaMailSender.setPort(25);

        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");


        return javaMailSender;

    }

}


