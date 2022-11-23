package numble.sobunsobun.like.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likes_id")
    private Long likeId;

    @Column(name = "users_id")
    private Long userId;

    @Column(name = "posts_id")
    private Long postId;
}
