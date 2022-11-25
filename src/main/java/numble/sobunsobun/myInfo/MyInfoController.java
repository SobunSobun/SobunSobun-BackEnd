package numble.sobunsobun.myInfo;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.post.dto.RegisterPostDto;
import numble.sobunsobun.user.domain.User;
import numble.sobunsobun.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/myInfo")
@RequiredArgsConstructor
public class MyInfoController {

    private final UserService userService;

    /**
     * 내 정보 반환 API
     * */
    @GetMapping("")
    public ResponseEntity<MyInfoDto> getMyInfo(){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        UserDetails loginUser = userService.loadUserByUsername(username);
        User user = (User) loginUser;

        MyInfoDto myInfoDto = new MyInfoDto();
        myInfoDto.setUserId(user.getUserId());
        myInfoDto.setEmail(user.getEmail());
        myInfoDto.setNickname(user.getNickname());
        myInfoDto.setLocation(user.getLocation());
        myInfoDto.setProfileUrl(user.getProfileUrl());

        return new ResponseEntity<>(myInfoDto, HttpStatus.OK);
    }

}
