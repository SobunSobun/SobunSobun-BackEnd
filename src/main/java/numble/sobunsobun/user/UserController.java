package numble.sobunsobun.user;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.user.domain.User;
import numble.sobunsobun.user.dto.JoinDto;
import numble.sobunsobun.user.dto.LoginDto;
import numble.sobunsobun.user.service.UserService;
import numble.sobunsobun.utils.JwtTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 1. 회원가입 API
 * 2. 이메일 중복 확인 API
 * 3. 닉네임 중복 확인 API
 * 4. 로그인 API
 */

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 회원가입 API
     * */
    @PostMapping("/join")
    public ResponseEntity<String> join(JoinDto joinDto){
        User user = new User();
        user.setEmail(joinDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(joinDto.getPassword()));
        user.setNickname(joinDto.getNickname());
        user.setLocation(joinDto.getLocation());
        user.setLat(joinDto.getLat());
        user.setLon(joinDto.getLon());

        userService.joinUser(user);
        return new ResponseEntity<>("회원가입 완료", HttpStatus.OK);
    }

    /**
     * 이메일 중복 확인 API
     * */
    @PostMapping("/join/emailDuplicateCheck")
    public ResponseEntity<String> emailDuplicate(String email){
        if(userService.getUserEntity(email) == null){
            return new ResponseEntity<>("가입 가능한 이메일", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("중복 이메일", HttpStatus.OK);
        }
    }

    /**
     * 닉네임 중복 확인 API
     * */
    @PostMapping("/join/nicknameDuplicateCheck")
    public ResponseEntity<String> nicknameDuplicate(String nickname){
        if(userService.isNicknameAvailable(nickname)){
            return new ResponseEntity<>("가입 가능한 닉네임", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("중복 닉네임", HttpStatus.OK);
        }
    }

    /**
     * 로그인 API
     * */
    @PostMapping("/login")
    public ResponseEntity<String> login(LoginDto loginDto){

        if(userService.getUserEntity(loginDto.getEmail()) == null){
            return new ResponseEntity<>("아이디 틀림", HttpStatus.OK);
        }
        else{
            User user = userService.getUserEntity(loginDto.getEmail());
            if(bCryptPasswordEncoder.matches(loginDto.getPassword(), user.getPassword())){
                return new ResponseEntity<>(jwtTokenService.createJWT(loginDto.getEmail()), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("비밀번호 틀림", HttpStatus.OK);
            }
        }
    }
}
