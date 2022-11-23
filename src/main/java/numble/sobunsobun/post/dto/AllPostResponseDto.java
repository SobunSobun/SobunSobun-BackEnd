package numble.sobunsobun.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class AllPostResponseDto {
    private Long postId;
    private String nickname;
    private String title;
    private Integer recruitNumber;
    private Integer applyNumber;
    private String meetingTime;
    private String market;
    private String category;
    private LocalDateTime createdAt;
    private boolean isLast;
}
