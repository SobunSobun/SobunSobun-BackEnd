package numble.sobunsobun.post.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;

    @Column(name = "recruitment_number")
    private Integer recruitmentNumber = 0;

    @Column(name = "apply_number")
    private Integer applyNumber = 1;

    @Column(name = "category")
    private String category;

    @Column(name = "meeting_time")
    private String meetingTime;

    @Column(name = "like_count")
    private Integer likeCount = 0;

    @Column(name = "is_full")
    private Boolean isFull = false;

    @Column(name = "market")
    private String market;

    @Column(name = "status")
    private Integer status = 1;

    @Column(name = "location")
    private String location;

    @Column(name = "market_address")
    private String marketAddress;
}
