package estm.dut.eagri.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agri/api")
public class UserController {
    @GetMapping("/agri/api")
    public String hello(){
        return "hello";
    }
    
}
