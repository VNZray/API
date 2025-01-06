package nagaventures.backend.service;

import nagaventures.backend.model.Post;
import nagaventures.backend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(Post post, MultipartFile imageFile) throws IOException {
        // Encrypt the password before saving

        // Handling the file
        if (imageFile != null && !imageFile.isEmpty()) {
            post.setImageName(imageFile.getOriginalFilename());
            post.setImageType(imageFile.getContentType());
            post.setImageData(imageFile.getBytes());
        }
        // Save the user entity to the database
        return postRepository.save(post);
    }

    // Fetch post by ID
    public Post findPostsById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElse(null); // Return null if user not found
    }

}
