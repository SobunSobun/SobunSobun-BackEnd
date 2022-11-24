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

        for(int i=0; i<posts.size(); i++){
            MyPostDto myPostDto = new MyPostDto();
            myPostDto.setPostId(posts.get(i).getPostId());
            myPostDto.setNickname(user.getNickname());
            myPostDto.setTitle(posts.get(i).getTitle());
            myPostDto.setRecruitNumber(posts.get(i).getRecruitmentNumber());
            myPostDto.setApplyNumber(posts.get(i).getApplyNumber());
            myPostDto.setMeetingTime(posts.get(i).getMeetingTime());
            myPostDto.setMarket(posts.get(i).getMarket());
            myPostDto.setCreatedAt(posts.get(i).getCreatedTime());
            myPostDtoList.add(myPostDto);
        }
        return myPostDtoList;
    }

    /**
     * 내가 작성한 게시글 조회 API - 완료된 소분
     * */
}
