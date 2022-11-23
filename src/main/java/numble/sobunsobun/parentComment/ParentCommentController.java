package numble.sobunsobun.parentComment;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.parentComment.domain.ParentComment;
import numble.sobunsobun.parentComment.dto.RegisterDto;
import numble.sobunsobun.parentComment.service.ParentCommentService;
import numble.sobunsobun.user.domain.User;
import numble.sobunsobun.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 1. 댓글 등록
 * */

@RestController
@RequestMapping("parentComment/{postId}")
@RequiredArgsConstructor
public class ParentCommentController {

    private final ParentCommentService parentCommentService;
    private final UserService userService;

    /**
     * 댓글 등록
     * */
    @PostMapping("")
    public ResponseEntity<String> registerParentComment(@PathVariable Long postId, RegisterDto registerDto){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        UserDetails loginUser = userService.loadUserByUsername(username);
        User user = (User) loginUser;

        ParentComment parentComment = new ParentComment();
        parentComment.setContent(registerDto.getContent());
        parentComment.setPostId(postId);
        parentComment.setCreatedTime(LocalDateTime.now());
        parentComment.setUserId(user.getUserId());

        parentCommentService.registerParentComment(parentComment);

        return new ResponseEntity<>("댓글 작성 완료", HttpStatus.OK);
    }
}
