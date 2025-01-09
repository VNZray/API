package nagaventures.backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "share")
public class Share {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shareId")
    private Long shareId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false) // Foreign key to User
    private User user;

    @ManyToOne
    @JoinColumn(name = "postId", nullable = false) // Foreign key to Post
    private Post post;

    @CreationTimestamp
    @Column(name = "sharedAt", updatable = false)
    private LocalDateTime sharedAt;

    public Share() {
    }

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public LocalDateTime getSharedAt() {
        return sharedAt;
    }

    public void setSharedAt(LocalDateTime sharedAt) {
        this.sharedAt = sharedAt;
    }
}
