package nagaventures.backend.service;

import nagaventures.backend.model.Post;
import nagaventures.backend.model.User;
import nagaventures.backend.repository.PostRepository;
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

        // Handling the file
        if (imageFile != null && !imageFile.isEmpty()) {
            user.setImageName(imageFile.getOriginalFilename());
            user.setImageType(imageFile.getContentType());
            user.setImageData(imageFile.getBytes());
        }

        // Save the user entity to the database
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

    public User updateUserInfo(Long userId, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User existingUser = optionalUser.get();
        // Update fields
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setMiddleName(updatedUser.getMiddleName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setGender(updatedUser.getGender());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setContactNo(updatedUser.getContactNo());
        existingUser.setBirthdate(updatedUser.getBirthdate());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setCountry(updatedUser.getCountry());
        existingUser.setProvince(updatedUser.getProvince());
        existingUser.setHometown(updatedUser.getHometown());
        existingUser.setBrgy(updatedUser.getBrgy());
        existingUser.setBio(updatedUser.getBio());
        existingUser.setPassword(updatedUser.getPassword());

        // Update the password only if it has changed
        if (!passwordEncoder.matches(updatedUser.getPassword(), existingUser.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    public boolean deleteUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
            return true;
        }
        return false;
    }

}
