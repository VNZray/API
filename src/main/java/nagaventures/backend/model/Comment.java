package nagaventures.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "comment") // Specifies the table name in the database
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId")
    private Long commentId;

    @Column(name = "comment", length = 255)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "postId", nullable = false) // Foreign key column for Post
    private Post post;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false) // Foreign key column for User
    private User user;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "updatedAt")
    private String updatedAt;

    public Comment() {
    }

    public Comment(Long commentId, String comment, Post post, User user, String createdAt, String updatedAt) {
        this.commentId = commentId;
        this.comment = comment;
        this.post = post;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
