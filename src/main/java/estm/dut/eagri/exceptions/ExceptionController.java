package estm.dut.eagri.exceptions;

import org.springframework.stereotype.Controller;
import org.springframework.boot.web.servlet.error.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ExceptionController implements ErrorController {
    
    @RequestMapping(value = "/error" )
    public String ErrorHandler(HttpServletRequest req){
        Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status!=null && Integer.valueOf(status.toString())==HttpStatus.NOT_FOUND.value()){
            return "errors/404";
        }else if(status!=null && Integer.valueOf(status.toString())==HttpStatus.INTERNAL_SERVER_ERROR.value()){
            return "errors/500";
        }else if(status!=null && Integer.valueOf(status.toString())==HttpStatus.METHOD_NOT_ALLOWED.value()){
            return "errors/405";
        }
        return "error";
    } 
    

}