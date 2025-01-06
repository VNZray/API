package nagaventures.backend.service;

import nagaventures.backend.model.User;
import nagaventures.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user, MultipartFile imageFile) throws IOException {
        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setImageName(imageFile.getOriginalFilename());
        user.setImageType(imageFile.getContentType());
        user.setImageData(imageFile.getBytes());
        return userRepository.save(user);
    }

    // Method to authenticate user
    public String loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            // Check if the password matches
            if (passwordEncoder.matches(password, user.get().getPassword())) {
                return "Login successful";
            } else {
                return "Invalid credentials";
            }
        } else {
            return "User not found";
        }
    }

    // Fetch all users
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // Fetch user by ID
    public User findUserByUserId(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null); // Return null if user not found
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

}
