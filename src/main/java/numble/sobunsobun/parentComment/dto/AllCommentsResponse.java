package numble.sobunsobun.parentComment.dto;

import lombok.Getter;
import lombok.Setter;
import numble.sobunsobun.childComment.dto.NestedCommentsDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class AllCommentsResponse {
    private Long parentCommentId;
    private String nickname;
    private String content;
    private String profileUrl;
    private LocalDateTime createdAt;
    private List<NestedCommentsDto> childComments;
}
