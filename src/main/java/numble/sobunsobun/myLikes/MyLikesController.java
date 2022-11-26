package numble.sobunsobun.myLikes;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.like.domain.Like;
import numble.sobunsobun.like.repository.LikeRepository;
import numble.sobunsobun.post.domain.Post;
import numble.sobunsobun.post.service.PostService;
import numble.sobunsobun.user.domain.User;
import numble.sobunsobun.user.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/myLikes")
@RequiredArgsConstructor
public class MyLikesController {

    private final UserService userService;
    private final LikeRepository likeRepository;
    private final PostService postService;

    /**
     * 내가 좋아요 누른 게시물 전체 조회 API
     * */

    @GetMapping("")
    public List<MyLikesPostDto> myLikesPostList(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        UserDetails loginUser = userService.loadUserByUsername(username);
        User user = (User) loginUser;

        List<MyLikesPostDto> myLikesPostDtoList = new ArrayList<>();
        List<Like> likes = likeRepository.findByUserIdOrderByLikeIdDesc(user.getUserId());

        for(Like like : likes){
            Post postEntity = postService.getPostEntity(like.getPostId());
            User userEntityById = userService.getUserEntityById(user.getUserId());
            if(postEntity.getStatus() == 0){
                continue;
            }
            MyLikesPostDto myLikesPostDto = new MyLikesPostDto();
            myLikesPostDto.setPostId(postEntity.getPostId());
            myLikesPostDto.setNickname(userEntityById.getNickname());
            myLikesPostDto.setTitle(postEntity.getTitle());
            myLikesPostDto.setRecruitNumber(postEntity.getRecruitmentNumber());
            myLikesPostDto.setApplyNumber(postEntity.getApplyNumber());
            myLikesPostDto.setMeetingTime(postEntity.getMeetingTime());
            myLikesPostDto.setMarket(postEntity.getMarket());
            myLikesPostDto.setCreatedAt(postEntity.getCreatedTime());
            myLikesPostDtoList.add(myLikesPostDto);
        }
        return myLikesPostDtoList;
    }
}
