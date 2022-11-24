package numble.sobunsobun.myPosts;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.myPosts.dto.MyPostDto;
import numble.sobunsobun.post.domain.Post;
import numble.sobunsobun.post.repository.PostRepository;
import numble.sobunsobun.user.domain.User;
import numble.sobunsobun.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/myPosts/{userId}")
@RequiredArgsConstructor
public class MyPostsController {

    private final UserService userService;
    private final PostRepository postRepository;

    /**
     * 내가 작성한 게시글 조회 API - 진행중인 소분
     * */
    @GetMapping("/ongoing")
    public List<MyPostDto> ongoingMyPosts(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        UserDetails loginUser = userService.loadUserByUsername(username);
        User user = (User) loginUser;

        List<MyPostDto> myPostDtoList = new ArrayList<>();
        List<Post> posts = postRepository.findAllByUserIdAndStatusOrderByCreatedTimeDesc(user.getUserId(), 1);

        for (Post post : posts) {
            MyPostDto myPostDto = new MyPostDto();
            myPostDto.setPostId(post.getPostId());
            myPostDto.setNickname(user.getNickname());
            myPostDto.setTitle(post.getTitle());
            myPostDto.setRecruitNumber(post.getRecruitmentNumber());
            myPostDto.setApplyNumber(post.getApplyNumber());
            myPostDto.setMeetingTime(post.getMeetingTime());
            myPostDto.setMarket(post.getMarket());
            myPostDto.setCreatedAt(post.getCreatedTime());
            myPostDtoList.add(myPostDto);
        }
        return myPostDtoList;
    }

    /**
     * 내가 작성한 게시글 조회 API - 완료된 소분
     * */
    @GetMapping("/finished")
    public List<MyPostDto> finishedMyPosts(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        UserDetails loginUser = userService.loadUserByUsername(username);
        User user = (User) loginUser;

        List<MyPostDto> myPostDtoList = new ArrayList<>();
        List<Post> posts = postRepository.findAllByUserIdAndStatusOrderByCreatedTimeDesc(user.getUserId(), 0);

        for (Post post : posts) {
            MyPostDto myPostDto = new MyPostDto();
            myPostDto.setPostId(post.getPostId());
            myPostDto.setNickname(user.getNickname());
            myPostDto.setTitle(post.getTitle());
            myPostDto.setRecruitNumber(post.getRecruitmentNumber());
            myPostDto.setApplyNumber(post.getApplyNumber());
            myPostDto.setMeetingTime(post.getMeetingTime());
            myPostDto.setMarket(post.getMarket());
            myPostDto.setCreatedAt(post.getCreatedTime());
            myPostDtoList.add(myPostDto);
        }
        return myPostDtoList;
    }
}
