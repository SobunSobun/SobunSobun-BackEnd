package numble.sobunsobun.myPage;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.myPage.dto.ModifyNicknameDto;
import numble.sobunsobun.myPage.service.MyPageService;
import numble.sobunsobun.s3Image.AwsS3Service;
import numble.sobunsobun.user.domain.User;
import numble.sobunsobun.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/myPage/{userId}")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final UserService userService;
    private final AwsS3Service awsS3Service;

    /**
     * 마이페이지 닉네임 변경
     * */
    @PostMapping("/modifyNickname")
    public ResponseEntity<String> modifyNickname(ModifyNicknameDto modifyNicknameDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        UserDetails loginUser = userService.loadUserByUsername(username);
        User user = (User) loginUser;

        user.setNickname(modifyNicknameDto.getNickname());
        userService.joinUser(user);

        return new ResponseEntity<>("닉네임 변경 완료", HttpStatus.OK);
    }

    /**
     * 회원탈퇴
     * */
    @DeleteMapping("")
    public ResponseEntity<String> deleteUserInfo(){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        UserDetails loginUser = userService.loadUserByUsername(username);
        User user = (User) loginUser;

        myPageService.deleteUser(user.getUserId());

        return new ResponseEntity<>("User withdrawal completed", HttpStatus.OK);
    }

    /**
     * 마이페이지 프로필 사진 변경
     * */
    @PostMapping("/changeProfileUrl")
    public ResponseEntity<String> changeProfileUrl(@PathVariable Long userId, MultipartFile multipartFile){
        User userEntity = userService.getUserEntityById(userId);
        if(multipartFile != null){
            userEntity.setProfileUrl(awsS3Service.uploadFile(multipartFile));
            userService.joinUser(userEntity);
            return new ResponseEntity<>("프로필 사진 변경 완료", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("파일 오류", HttpStatus.FORBIDDEN);
        }
    }
}
