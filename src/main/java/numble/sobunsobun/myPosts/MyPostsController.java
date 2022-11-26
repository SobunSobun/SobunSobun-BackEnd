package numble.sobunsobun.myPosts;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.apply.domain.Apply;
import numble.sobunsobun.apply.repository.ApplyRepository;
import numble.sobunsobun.myPosts.dto.MyPostDto;
import numble.sobunsobun.post.domain.Post;
import numble.sobunsobun.post.repository.PostRepository;
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
@RequestMapping("/myPosts/{userId}")
@RequiredArgsConstructor
public class MyPostsController {

    private final UserService userService;
    private final PostService postService;
    private final PostRepository postRepository;
    private final ApplyRepository applyRepository;

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
            if(post.getIsFull()){
                continue;
            }
            MyPostDto myPostDto = new MyPostDto();
            myPostDto.setPostId(post.getPostId());
            myPostDto.setNickname(user.getNickname());
            myPostDto.setTitle(post.getTitle());
            myPostDto.setRecruitNumber(post.getRecruitmentNumber());
            myPostDto.setApplyNumber(post.getApplyNumber());
            myPostDto.setCategory(post.getCategory());
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
        List<Post> posts = postRepository.findAllByUserIdAndStatusOrderByCreatedTimeDesc(user.getUserId(), 1);

        for (Post post : posts) {
            if(!post.getIsFull()){
                continue;
            }
            MyPostDto myPostDto = new MyPostDto();
            myPostDto.setPostId(post.getPostId());
            myPostDto.setNickname(user.getNickname());
            myPostDto.setTitle(post.getTitle());
            myPostDto.setRecruitNumber(post.getRecruitmentNumber());
            myPostDto.setApplyNumber(post.getApplyNumber());
            myPostDto.setCategory(post.getCategory());
            myPostDto.setMeetingTime(post.getMeetingTime());
            myPostDto.setMarket(post.getMarket());
            myPostDto.setCreatedAt(post.getCreatedTime());
            myPostDtoList.add(myPostDto);
        }
        return myPostDtoList;
    }

    /**
     * 내가 참여한 게시글 조회 API - 진행중인 소분
     */
    @GetMapping("/ongoing/applied")
    public List<MyPostDto> ongoingAppliedMyPosts(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        UserDetails loginUser = userService.loadUserByUsername(username);
        User user = (User) loginUser;

        List<MyPostDto> myPostDtoList = new ArrayList<>();
        List<Apply> applies = applyRepository.findByUserIdOrderByApplyId(user.getUserId());

        for (Apply apply : applies) {
            Post postEntity = postService.getPostEntity(apply.getPostId());
            User userEntityById = userService.getUserEntityById(postEntity.getUserId());

            if(postEntity.getStatus() == 0 || postEntity.getIsFull()){
                continue;
            }

            MyPostDto myPostDto = new MyPostDto();
            myPostDto.setPostId(postEntity.getPostId());
            myPostDto.setNickname(userEntityById.getNickname());
            myPostDto.setTitle(postEntity.getTitle());
            myPostDto.setRecruitNumber(postEntity.getRecruitmentNumber());
            myPostDto.setApplyNumber(postEntity.getApplyNumber());
            myPostDto.setCategory(postEntity.getCategory());
            myPostDto.setMeetingTime(postEntity.getMeetingTime());
            myPostDto.setMarket(postEntity.getMarket());
            myPostDto.setCreatedAt(postEntity.getCreatedTime());
            myPostDtoList.add(myPostDto);
        }
        return myPostDtoList;
    }

    /**
     * 내가 참여한 게시글 조회 API - 완료된 소분
     */
    @GetMapping("/finished/applied")
    public List<MyPostDto> finishedAppliedMyPosts(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        UserDetails loginUser = userService.loadUserByUsername(username);
        User user = (User) loginUser;

        List<MyPostDto> myPostDtoList = new ArrayList<>();
        List<Apply> applies = applyRepository.findByUserIdOrderByApplyId(user.getUserId());

        for(Apply apply : applies){
            Post postEntity = postService.getPostEntity(apply.getPostId());
            User userEntityById = userService.getUserEntityById(user.getUserId());

            if(postEntity.getStatus() == 0 || !postEntity.getIsFull()){
                continue;
            }

            MyPostDto myPostDto = new MyPostDto();
            myPostDto.setPostId(postEntity.getPostId());
            myPostDto.setNickname(userEntityById.getNickname());
            myPostDto.setTitle(postEntity.getTitle());
            myPostDto.setRecruitNumber(postEntity.getRecruitmentNumber());
            myPostDto.setApplyNumber(postEntity.getApplyNumber());
            myPostDto.setCategory(postEntity.getCategory());
            myPostDto.setMeetingTime(postEntity.getMeetingTime());
            myPostDto.setMarket(postEntity.getMarket());
            myPostDto.setCreatedAt(postEntity.getCreatedTime());
            myPostDtoList.add(myPostDto);
        }
        return myPostDtoList;
    }
}
