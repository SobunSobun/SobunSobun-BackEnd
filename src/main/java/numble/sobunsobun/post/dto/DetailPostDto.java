package numble.sobunsobun.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DetailPostDto {
    private String title;
    private String content;
    private Integer recruitmentNumber;
    private Integer applyNumber;
    private String category;
    private String meetingTime;
    private String market;
    private String marketAddress;
    private String uploadTime;
    private String nickname;
    private String profileUrl;
    private Integer likeCount;
    private Boolean isLike;
    private Boolean isApply;
    private Boolean isWriter;
}
