package nagaventures.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nagaventures.backend.model.ApiResponse;
import nagaventures.backend.model.User;
import nagaventures.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://127.0.0.1:8080")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestPart("user") String userJson, @RequestPart("imageFile") MultipartFile imageFile) {
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

}
