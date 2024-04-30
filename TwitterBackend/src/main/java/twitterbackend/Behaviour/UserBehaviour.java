package twitterbackend.Behaviour;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitterbackend.Entities.TwitterUser;
import twitterbackend.Repository.UserRepository;

@Service
public class UserBehaviour {

    @Autowired
    private  UserRepository userRepository;

    public String loginUser(String email, String password) {
        TwitterUser user = userRepository.findByEmail(email);
        if (user == null) {
            return "User does not exist";
        }
        if (user.getPassword().equals(password)) {
            return "Login successful";
        }
        return "Username/Password Incorrect";
    }

    public String registerUser(TwitterUser user) {
        TwitterUser existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            return "Forbidden, Account already exists";
        }
        userRepository.save(user);
        return "Account Creation Successful";
    }

    public TwitterUser getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Object getAllUsers() {
        return userRepository.findAll();
    }
}
