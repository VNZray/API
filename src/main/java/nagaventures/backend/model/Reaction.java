package nagaventures.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reaction")
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reactionId")
    private Long reactionId;

    @ManyToOne
    @JoinColumn(name = "postId", nullable = true) // Nullable to allow reactions to comments
    private Post post;

    @ManyToOne
    @JoinColumn(name = "commentId", nullable = true) // Nullable to allow reactions to posts
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false) // User who reacted
    private User user;

    @Column(name = "react", nullable = false) // Type of reaction (e.g., LIKE, LOVE, etc.)
    private Boolean react;

    @Column(name = "createdAt")
    private String createdAt;

    public Reaction() {
    }

    public Long getReactionId() {
        return reactionId;
    }

    public void setReactionId(Long reactionId) {
        this.reactionId = reactionId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getReact() {
        return react;
    }

    public void setReact(Boolean react) {
        this.react = react;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
