package nagaventures.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nagaventures.backend.model.ApiResponse;
import nagaventures.backend.model.Post;
import nagaventures.backend.model.User;
import nagaventures.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestPart("user") String userJson,
                                        @RequestPart("imageFile") MultipartFile imageFile) {
        try {
            // Parse the user JSON into a User object
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(userJson, User.class);

            // Call the service layer to save the user
            User createdUser = userService.createUser(user, imageFile);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred.");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody User loginRequest) {
        String response = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
        if (response.equals("Login successful")) {
            User loggedInUser = userService.findUserByEmail(loginRequest.getEmail());
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), loggedInUser.getUserId(), "Login successful",
                    loggedInUser), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED.value(), null, "Invalid credentials",
                null), HttpStatus.UNAUTHORIZED);
    }

    // Endpoint to get all users
    @GetMapping("/users")
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NO_CONTENT.value(),null, "No users found"),HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(),"Users retrieved successfully", users), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse> getUserDetails(@PathVariable Long id) {
        try {
            Optional<User> optionalUser = Optional.ofNullable(userService.findUserByUserId(id));
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                // Construct a response DTO to exclude sensitive data
                Map<String, Object> userResponse = new HashMap<>();
                userResponse.put("userId", user.getUserId());
                userResponse.put("firstName", user.getFirstName());
                userResponse.put("middleName", user.getMiddleName());
                userResponse.put("lastName", user.getLastName());
                userResponse.put("age", user.getAge());
                userResponse.put("birthdate", user.getBirthdate().toString());
                userResponse.put("contactNo", user.getContactNo());
                userResponse.put("gender", user.getGender());
                userResponse.put("country", user.getCountry());
                userResponse.put("province", user.getProvince());
                userResponse.put("hometown", user.getHometown());
                userResponse.put("brgy", user.getBrgy());
                userResponse.put("email", user.getEmail());
                userResponse.put("bio", user.getBio());
                userResponse.put("password", user.getPassword());

                return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "User retrieved successfully", userResponse));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse(HttpStatus.NOT_FOUND.value(), "User not found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", e.getMessage()));
        }
    }

    @GetMapping("/user/profile/{id}")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable Long id) {
        Optional<User> optionalUser = Optional.ofNullable(userService.findUserByUserId(id));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(user.getImageType()))
                    .body(user.getImageData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping(value = "/user/{id}")
    public ResponseEntity<?> updateUserInfo(
            @PathVariable Long id,
            @RequestBody User updatedUser) {
        try {
            User user = userService.updateUserInfo(id, updatedUser);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred.");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred.");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        try {
            boolean isDeleted = userService.deleteUserById(id);

            if (isDeleted) {
                return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "User deleted successfully", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse(HttpStatus.NOT_FOUND.value(), "User not found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", e.getMessage()));
        }
    }

}
