package numble.sobunsobun.user;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.user.domain.User;
import numble.sobunsobun.user.dto.JoinDto;
import numble.sobunsobun.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 1. 회원가입 API
 * 2. 닉네임 중복 확인 API
 * 3. 로그인 API
 */

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(JoinDto joinDto){
        User user = new User();
        user.setEmail(joinDto.getEmail());
        user.setPassword(joinDto.getPassword());    // Spring Security 주입받아서 비밀번호 encrypt 꼭 해줘야함!!!
        user.setNickname(joinDto.getNickname());
        user.setLocation(joinDto.getLocation());

        userService.joinUser(user);
        return new ResponseEntity<>("회원가입 완료", HttpStatus.OK);
    }
}
