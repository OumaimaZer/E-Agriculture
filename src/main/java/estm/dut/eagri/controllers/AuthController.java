package estm.dut.eagri.controllers;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import estm.dut.eagri.model.UserInfo.User;
import estm.dut.eagri.services.UserServiceImpl;


@Controller
@RequestMapping("/eagri-mobile/clients/auth")
public class AuthController {
    @Autowired
private UserServiceImpl userServiceImpl;

@PostMapping("/login")
public ResponseEntity<String> login(@RequestBody User user) {
    User test = userServiceImpl.findByEmail(user.getEmail());

    if (user.getEmail() == null || user.getEmail().isBlank()) {
        return new ResponseEntity<>("Fill the email field.", HttpStatus.BAD_REQUEST);
    }
    if (user.getPassword() == null || user.getPassword().isBlank()) {
        return new ResponseEntity<>("Fill the password field.", HttpStatus.BAD_REQUEST);
    }
    if (test == null) {
        return new ResponseEntity<>("User does not exist.", HttpStatus.BAD_REQUEST);
    }

    if (test.getPassword().equals(user.getPassword())) {
        String string="User "+test.getL_name()+" "+test.getF_name()+" logged in";
        // Login successful, return JWT token
        return new ResponseEntity<>(string, HttpStatus.OK);
    } else {
        return new ResponseEntity<>("Incorrect password.", HttpStatus.UNAUTHORIZED);
    }
}

@PostMapping("/register")
public ResponseEntity<String> register(@RequestBody User user) {
    try {
        // Check for empty fields
        if (user.getF_name() == null || user.getF_name().isBlank()) {
            return new ResponseEntity<>("Fill the first name field.", HttpStatus.BAD_REQUEST);
        }
        if (user.getL_name() == null || user.getL_name().isBlank()) {
            return new ResponseEntity<>("Fill the last name field.", HttpStatus.BAD_REQUEST);
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return new ResponseEntity<>("Fill the email field.", HttpStatus.BAD_REQUEST);
        }
        if (user.getPhone() == null || user.getPhone().isBlank()) {
            return new ResponseEntity<>("Fill the phone field.", HttpStatus.BAD_REQUEST);
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return new ResponseEntity<>("Fill the password field.", HttpStatus.BAD_REQUEST);
        }

        // Check email format
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(user.getEmail());
        if (!matcher.matches()) {
            return new ResponseEntity<>("Please use a valid email.", HttpStatus.BAD_REQUEST);
        }

        // Check phone number format
        Pattern pattern1 = Pattern.compile("^(05|06|07|08)[0-9]{8}");
        Matcher matcher1 = pattern1.matcher(user.getPhone());
        if (!matcher1.matches()) {
            return new ResponseEntity<>("Please use a valid phone number.", HttpStatus.BAD_REQUEST);
        }

        // Check password format
        Pattern pattern2 = Pattern.compile("^(?=.*\\d)(?=.*[a-zA-Z]).{6,20}$");
        Matcher matcher2 = pattern2.matcher(user.getPassword());
        if (!matcher2.matches()) {
            return new ResponseEntity<>("Please use a password between 6 and 20 characters with at least one letter and one number.", HttpStatus.BAD_REQUEST);
        }

        // Check if user already exists
        User existingUser = userServiceImpl.findByEmail(user.getEmail());
        if (existingUser != null) {
            return new ResponseEntity<>("A user with this email already exists.", HttpStatus.CONFLICT);
        }

        // Create the user
        userServiceImpl.CreateUser(user);
        String message = "User " + user.getL_name() + " " + user.getF_name() + " registered successfully.";
        return new ResponseEntity<>(message, HttpStatus.OK);
    } catch (Exception e) {
        String error="An error occurred while processing the request.\n"+e.toString();
        return new ResponseEntity<>(error , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

// Logout endpoint
@PostMapping("/logout")
public ResponseEntity<String> logout() {
    // Perform logout logic
    return new ResponseEntity<>("Logged out successfully.", HttpStatus.OK);
}
}
