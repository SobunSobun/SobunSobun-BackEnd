package numble.sobunsobun.parentComment;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.childComment.domain.ChildComment;
import numble.sobunsobun.childComment.dto.NestedCommentsDto;
import numble.sobunsobun.childComment.repository.ChildCommentRepository;
import numble.sobunsobun.parentComment.domain.ParentComment;
import numble.sobunsobun.parentComment.dto.AllCommentsResponse;
import numble.sobunsobun.parentComment.dto.RegisterDto;
import numble.sobunsobun.parentComment.repository.ParentCommentRepository;
import numble.sobunsobun.parentComment.service.ParentCommentService;
import numble.sobunsobun.user.domain.User;
import numble.sobunsobun.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. 댓글 등록
 * */

@RestController
@RequestMapping("parentComment/{postId}")
@RequiredArgsConstructor
public class ParentCommentController {

    private final ParentCommentService parentCommentService;
    private final UserService userService;
    private final ParentCommentRepository parentCommentRepository;
    private final ChildCommentRepository childCommentRepository;

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

    /**
     * 전체 댓글 조회
     * */
    @GetMapping("")
    public ResponseEntity<List<AllCommentsResponse>> getAllComments(@PathVariable Long postId){

        List<AllCommentsResponse> allCommentsResponses = new ArrayList<>();
        List<ParentComment> commentList = parentCommentRepository.findAllByPostIdOrderByCreatedTimeDesc(postId);
        System.out.println(commentList);

        for (ParentComment parentComment : commentList) {
            User user = userService.getUserEntityById(parentComment.getUserId());
            AllCommentsResponse allCommentsResponse = new AllCommentsResponse();
            allCommentsResponse.setNickname(user.getNickname());
            allCommentsResponse.setContent(parentComment.getContent());
            allCommentsResponse.setCreatedAt(parentComment.getCreatedTime());
            if (parentComment.getIsParent() == 0) {
                allCommentsResponse.setChildComments(null);
            } else {
                List<NestedCommentsDto> nestedCommentsDtoList = new ArrayList<>();
                List<ChildComment> childCommentList = childCommentRepository.findAllByParentCommentIdOrderByCreatedTimeDesc(parentComment.getCommentId());
                for (ChildComment childComment : childCommentList) {
                    User childUser = userService.getUserEntityById(childComment.getUserId());
                    NestedCommentsDto nestedCommentsDto = new NestedCommentsDto();
                    nestedCommentsDto.setNickname(childUser.getNickname());
                    nestedCommentsDto.setContent(childComment.getContent());
                    nestedCommentsDto.setCreatedAt(childComment.getCreatedTime());
                    nestedCommentsDtoList.add(nestedCommentsDto);
                }
                allCommentsResponse.setChildComments(nestedCommentsDtoList);
            }
            allCommentsResponses.add(allCommentsResponse);
        }
        return new ResponseEntity<>(allCommentsResponses,HttpStatus.OK);
    }
}
