package numble.sobunsobun.parentComment.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "parent_comment")
public class ParentComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "content")
    private String content;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "is_parent")
    private Integer isParent = 0;
}
