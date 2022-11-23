package numble.sobunsobun.childComment;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.childComment.domain.ChildComment;
import numble.sobunsobun.childComment.dto.RegisterDto;
import numble.sobunsobun.childComment.service.ChildCommentService;
import numble.sobunsobun.parentComment.domain.ParentComment;
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

@RestController
@RequestMapping("childComment/{postId}/{parentCommentId}")
@RequiredArgsConstructor
public class ChildCommentController {

    private final UserService userService;
    private final ChildCommentService childCommentService;
    private final ParentCommentService parentCommentService;

    /**
     * 대댓글 등록
     * */
    @PostMapping("")
    public ResponseEntity<String> registerChildComment(@PathVariable Long postId, @PathVariable Long parentCommentId, RegisterDto registerDto){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        UserDetails loginUser = userService.loadUserByUsername(username);
        User user = (User) loginUser;

        ParentComment parentCommentEntity = parentCommentService.getParentCommentEntity(parentCommentId);

        ChildComment childComment = new ChildComment();
        childComment.setCreatedTime(LocalDateTime.now());
        childComment.setContent(registerDto.getContent());
        childComment.setPostId(postId);
        childComment.setUserId(user.getUserId());
        childComment.setParentCommentId(parentCommentId);
        parentCommentEntity.setIsParent(1);
        childCommentService.registerChildComment(childComment);
        parentCommentService.registerParentComment(parentCommentEntity);

        return new ResponseEntity<>("대댓글 작성 완료", HttpStatus.OK);
    }
}
