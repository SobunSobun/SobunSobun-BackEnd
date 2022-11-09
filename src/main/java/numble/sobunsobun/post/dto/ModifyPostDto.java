package numble.sobunsobun.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ModifyPostDto {
    private String title;
    private String content;
    private Integer recruitmentNumber;
    private String category;
    private String meetingTime;
    private String market;
}
