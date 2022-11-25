package numble.sobunsobun.childComment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class NestedCommentsDto {
    private Long childCommentId;
    private String nickname;
    private String content;
    private String profileUrl;
    private LocalDateTime createdAt;
}
