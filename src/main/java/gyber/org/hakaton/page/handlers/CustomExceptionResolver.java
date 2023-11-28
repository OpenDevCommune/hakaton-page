package gyber.org.hakaton.page.handlers;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.sql.SQLException;

@ControllerAdvice
public class CustomExceptionResolver extends ResponseEntityExceptionHandler {



    @ExceptionHandler(value = DuplicateKeyException.class)
    public void redirectWithSQLException(HttpServletRequest request , HttpServletResponse response) throws IOException {
        System.out.println("Request is : " + request.getRequestURI());
        response.sendRedirect("/error/err?form_page");


    }

}
