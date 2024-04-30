package twitterbackend.Manager;

import twitterbackend.Entities.TwitterUser;
import twitterbackend.Behaviour.UserBehaviour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserManager {

    @Autowired
    private UserBehaviour userBehaviour;

    // Wrapper function for user login
    private ResponseEntity<String> loginUser(String email, String password) {
        // Call the userBehaviour to perform user login
        String information = userBehaviour.loginUser(email, password);
        // If login is successful, return a success response
        if (information.equals("Login successful")) {
            return ResponseEntity.ok(information);
        } else {
            // If login fails, return a bad request response with the error message
            return ResponseEntity.badRequest().body(information);
        }
    }

    // Wrapper function for user registration
    private ResponseEntity<String> registerUser(String email, String password, String name) {
        // Create a TwitterUser object with provided information
        TwitterUser profile = new TwitterUser();
        profile.setEmail(email);
        profile.setPassword(password);
        profile.setName(name);

        // Call the userBehaviour to register the user
        String information = userBehaviour.registerUser(profile);
        // If registration is successful, return a success response
        if (information.equals("Account Creation Successful")) {
            return ResponseEntity.ok(information);
        } else {
            // If registration fails, return a bad request response with the error message
            return ResponseEntity.badRequest().body(information);
        }
    }

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<String> loginProfile(@RequestBody Map<String, String> structure) {
        // Extract email and password from request body
        String email = structure.get("email");
        String password = structure.get("password");
        // Delegate login request to the loginUser wrapper function
        return loginUser(email, password);
    }

    // Endpoint for user registration
    @PostMapping("/signup")
    public ResponseEntity<String> registerProfile(@RequestBody Map<String, String> structure) {
        // Extract email, password, and name from request body
        String email = structure.get("email");
        String password = structure.get("password");
        String name = structure.get("name");
        // Delegate registration request to the registerUser wrapper function
        return registerUser(email, password, name);
    }

    // Endpoint to get user profile details by ID
    @GetMapping("/user")
    public ResponseEntity<?> getProfileDetailsByID(@RequestParam long userID) {
        // Call userBehaviour to get user profile by ID
        TwitterUser profile = userBehaviour.getUserById(userID);
        // If user profile is not found, return a not found response
        if (profile == null) {
            return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
        }
        // If user profile is found, return the profile data
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    // Endpoint to get all user profiles
    @GetMapping("/users")
    public ResponseEntity<?> getProfilesAll() {
        // Call userBehaviour to get all user profiles
        return ResponseEntity.ok(userBehaviour.getAllUsers());
    }
}
