package gyber.org.hakaton.page.profile;

import lombok.*;

import java.time.LocalDateTime;

public class ApplicationForParticipation {


    private Long id;

    private LocalDateTime dateToSentForm;

    private String nameUser;
    private String emailUser;

    private String aboutUser;

    private String country;


    public ApplicationForParticipation(String nameUser, String emailUser, String aboutUser , String country) {
        this.dateToSentForm = LocalDateTime.now();
        this.nameUser = nameUser;
        this.emailUser = emailUser;
        this.aboutUser = aboutUser;
        this.country = country;
    }


    public ApplicationForParticipation(){}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateToSentForm() {
        return dateToSentForm;
    }

    public void setDateToSentForm(LocalDateTime dateToSentForm) {
        this.dateToSentForm = dateToSentForm;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getAboutUser() {
        return aboutUser;
    }

    public void setAboutUser(String aboutUser) {
        this.aboutUser = aboutUser;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
