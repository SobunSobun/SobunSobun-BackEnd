package numble.sobunsobun.myInfo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MyInfoDto {
    private Long userId;
    private String nickname;
    private String email;
    private String location;
    private String profileUrl;
}
