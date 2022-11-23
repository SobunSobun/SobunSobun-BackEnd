package numble.sobunsobun.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterPostDto {
    private String title;
    private String content;
    private Integer recruitmentNumber;
    private String category;
    private String meetingTime;
    private String market;
    private String marketAddress;
}
