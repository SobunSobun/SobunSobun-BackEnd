package numble.sobunsobun.myPage;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.myPage.service.MyPageService;
import numble.sobunsobun.user.domain.User;
import numble.sobunsobun.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/myPage/{userId}")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;
    private final UserService userService;

    @DeleteMapping("")
    public ResponseEntity<String> deleteUserInfo(){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        UserDetails loginUser = userService.loadUserByUsername(username);
        User user = (User) loginUser;

        myPageService.deleteUser(user.getUserId());

        return new ResponseEntity<>("User withdrawal completed", HttpStatus.OK);
    }
}
