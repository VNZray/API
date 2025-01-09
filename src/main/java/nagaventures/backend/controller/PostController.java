package nagaventures.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nagaventures.backend.model.ApiResponse;
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

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
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
    public ResponseEntity<ApiResponse> getAllPosts() {
        try {
            // Retrieve all posts
            List<Post> posts = postService.findAllPosts();

            if (posts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse(HttpStatus.NOT_FOUND.value(), "No Posts found", null));
            }

            // Prepare the response for all posts
            List<Map<String, Object>> postData = new ArrayList<>();

            for (Post post : posts) {
                // Construct posts details (excluding sensitive data)
                Map<String, Object> postResponse = new HashMap<>();
                postResponse.put("postId", post.getPostId());
                postResponse.put("caption", post.getCaption());
                postResponse.put("users", post.getUser());
                postResponse.put("postedAt", post.getCreatedAt());
                postResponse.put("shareCount", post.getShareCount());
                postResponse.put("commmentCount", post.getCommentCount());

                // Add post image picture (if available)
                if (post.getImageData() != null && post.getImageType() != null) {
                    String image = "data:" + post.getImageType() + ";base64," + Base64.getEncoder().encodeToString(post.getImageData());
                    postResponse.put("postImage", image);
                } else {
                    postResponse.put("postImage", null);
                }
                // Add the post data to the list
                postData.add(postResponse);
            }

            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Posts retrieved successfully", postData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", e.getMessage()));
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
