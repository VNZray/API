package nagaventures.backend.model;

import jakarta.persistence.*;

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

    @Column(name = "sharedAt")
    private String sharedAt;

    public Share() {
    }

    public Share(User user, Post post, String sharedAt) {
        this.user = user;
        this.post = post;
        this.sharedAt = sharedAt;
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

    public String getSharedAt() {
        return sharedAt;
    }

    public void setSharedAt(String sharedAt) {
        this.sharedAt = sharedAt;
    }
}
