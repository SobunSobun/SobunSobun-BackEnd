package numble.sobunsobun.post;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.post.domain.Post;
import numble.sobunsobun.post.dto.ModifyPostDto;
import numble.sobunsobun.post.dto.RegisterPostDto;
import numble.sobunsobun.post.service.PostService;
import numble.sobunsobun.user.domain.User;
import numble.sobunsobun.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;


    /**
     * 게시글 등록 API
     * */
    @PostMapping("/register")
    public ResponseEntity<String> register(RegisterPostDto registerPostDto){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        UserDetails loginUser = userService.loadUserByUsername(username);
        User user = (User) loginUser;

        Post post = new Post();
        post.setTitle(registerPostDto.getTitle());
        post.setContent(registerPostDto.getContent());
        post.setCreatedTime(LocalDateTime.now());
        post.setRecruitmentNumber(registerPostDto.getRecruitmentNumber());
        post.setCategory(registerPostDto.getCategory());
        post.setMeetingTime(registerPostDto.getMeetingTime());
        post.setMarket(registerPostDto.getMarket());
        post.setUserId(user.getUserId());

        postService.savePost(post);

        return new ResponseEntity<>("게시글 등록 완료", HttpStatus.OK);
    }

    /**
     * 게시글 삭제 API
     * */
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> delete(@PathVariable Long postId){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        UserDetails loginUser = userService.loadUserByUsername(username);
        User user = (User) loginUser;

        Post post = postService.getPostEntity(postId);
        if(post.getUserId().equals(user.getUserId())){
            post.setStatus(0);
            postService.savePost(post);
            return new ResponseEntity<>("게시글 삭제 완료", HttpStatus.OK);
        } else{
            return new ResponseEntity<>("작성자만 삭제 가능", HttpStatus.OK);
        }
    }

    /**
     * 게시글 수정 API
     * */
    @PatchMapping("/{postId}")
    public ResponseEntity<String> modify(@PathVariable Long postId, ModifyPostDto modifyPostDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        UserDetails loginUser = userService.loadUserByUsername(username);
        User user = (User) loginUser;

        Post post = postService.getPostEntity(postId);

        if (post.getUserId().equals(user.getUserId())){
            if (post.getApplyNumber() > 1){
                return new ResponseEntity<>("모집에 참여한 사람이 있어 수정이 불가합니다.", HttpStatus.OK);
            } else{

                post.setTitle(modifyPostDto.getTitle());
                post.setContent(modifyPostDto.getContent());
                post.setModifiedTime(LocalDateTime.now());
                post.setRecruitmentNumber(modifyPostDto.getRecruitmentNumber());
                post.setCategory(modifyPostDto.getCategory());
                post.setMeetingTime(modifyPostDto.getMeetingTime());
                post.setMarket(modifyPostDto.getMarket());

                postService.savePost(post);

                return new ResponseEntity<>("게시글 수정 완료", HttpStatus.OK);
            }
        } else{
            return new ResponseEntity<>("작성자만 수정 가능", HttpStatus.OK);
        }
    }
}
