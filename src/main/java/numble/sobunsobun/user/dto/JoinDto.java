package numble.sobunsobun.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JoinDto {
    private String email;
    private String password;
    private String nickname;
    private String location;
}
