package nagaventures.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nagaventures.backend.model.Post;
import nagaventures.backend.model.User;
import nagaventures.backend.repository.PostRepository;
import nagaventures.backend.repository.UserRepository;
import nagaventures.backend.service.PostService;
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
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestPart("post") String userJson,
                                        @RequestPart("imageFile") MultipartFile imageFile) {
        try {
            // Parse the user JSON into a User object
            ObjectMapper mapper = new ObjectMapper();
            Post post = mapper.readValue(userJson, Post.class);

            // Call the service layer to save the user
            Post createPost = postService.createPost(post, imageFile);

            return ResponseEntity.status(HttpStatus.CREATED).body(createPost);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred.");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts() {
        try {
            List<Post> posts = postRepository.findAll();
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred.");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/posts/{id}/image")
    public ResponseEntity<byte[]> getPostImage(@PathVariable Long id) {
        Optional<Post> optionalUser = Optional.ofNullable(postService.findPostsById(id));
        if (optionalUser.isPresent()) {
            Post post = optionalUser.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(post.getImageType()))
                    .body(post.getImageData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PutMapping(value = "/post/react/{id}")
    public ResponseEntity<?> updateReactionCounter(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> requestBody) {
        try {
            Integer reactionCount = requestBody.get("reactionCount");
            if (reactionCount == null) {
                throw new IllegalArgumentException("reactionCount is required.");
            }

            Post updatedPost = postService.updateReactionCounter(id, reactionCount);

            return ResponseEntity.ok(updatedPost);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred.");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }



}
