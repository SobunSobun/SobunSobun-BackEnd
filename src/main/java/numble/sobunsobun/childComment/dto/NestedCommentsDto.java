package numble.sobunsobun.childComment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class NestedCommentsDto {
    private String nickname;
    private String content;
    private LocalDateTime createdAt;
}
