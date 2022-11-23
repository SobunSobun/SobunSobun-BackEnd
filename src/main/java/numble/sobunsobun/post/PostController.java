package numble.sobunsobun.post;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.like.domain.Like;
import numble.sobunsobun.like.service.LikeService;
import numble.sobunsobun.post.domain.Post;
import numble.sobunsobun.post.dto.AllPostResponseDto;
import numble.sobunsobun.post.dto.DetailPostDto;
import numble.sobunsobun.post.dto.ModifyPostDto;
import numble.sobunsobun.post.dto.RegisterPostDto;
import numble.sobunsobun.post.repository.PostRepository;
import numble.sobunsobun.post.service.PostService;
import numble.sobunsobun.user.domain.User;
import numble.sobunsobun.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final PostRepository postRepository;
    private final LikeService likeService;


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
        post.setLocation(user.getLocation());
        post.setMarketAddress(registerPostDto.getMarketAddress());

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
                post.setMarketAddress(modifyPostDto.getMarketAddress());

                postService.savePost(post);

                return new ResponseEntity<>("게시글 수정 완료", HttpStatus.OK);
            }
        } else{
            return new ResponseEntity<>("작성자만 수정 가능", HttpStatus.OK);
        }
    }

    /**
     * 상세 게시글 조회 API
     * */
    @GetMapping("/{postId}")
    public ResponseEntity<DetailPostDto> viewDetail(@PathVariable Long postId){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        UserDetails loginUser = userService.loadUserByUsername(username);
        User user = (User) loginUser;

        DetailPostDto detailPostDto = new DetailPostDto();
        Post post = postService.getPostEntity(postId);

        if(post == null || post.getStatus() == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        detailPostDto.setTitle(post.getTitle());
        detailPostDto.setContent(post.getContent());
        detailPostDto.setRecruitmentNumber(post.getRecruitmentNumber());
        detailPostDto.setApplyNumber(post.getApplyNumber());
        detailPostDto.setCategory(post.getCategory());
        detailPostDto.setMeetingTime(post.getMeetingTime());
        detailPostDto.setMarket(post.getMarket());
        detailPostDto.setLikeCount(post.getLikeCount());
        detailPostDto.setNickname(user.getNickname());

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime time;

        if (post.getModifiedTime() == null){
            time = post.getCreatedTime();
        } else{
            time = post.getModifiedTime();
        }

        if ((now.getYear() - time.getYear()) > 0){
            detailPostDto.setUploadTime((now.getYear() - time.getYear()) + "년 전");
        } else {
            int nowMonth = Integer.parseInt(now.format(DateTimeFormatter.ofPattern("MM")));
            int timeMonth = Integer.parseInt(time.format(DateTimeFormatter.ofPattern("MM")));

            if ((nowMonth - timeMonth) > 0){
                detailPostDto.setUploadTime((nowMonth - timeMonth) + "달 전");
            } else if ((nowMonth - timeMonth) < 0) {
                detailPostDto.setUploadTime((nowMonth - timeMonth + 12) + "달 전");
            } else {
                if ((now.getDayOfMonth() - time.getDayOfMonth()) > 0){
                    detailPostDto.setUploadTime((now.getDayOfMonth() - time.getDayOfMonth()) + "일 전");
                } else if ((now.getDayOfMonth() - time.getDayOfMonth()) < 0) {
                    if (timeMonth == 1 || timeMonth == 3 || timeMonth == 5 || timeMonth == 7 || timeMonth == 8 || timeMonth == 10 || timeMonth == 12) {
                        System.out.println((now.getDayOfMonth() - time.getDayOfMonth() + 31) + "일 전");
                        detailPostDto.setUploadTime((now.getDayOfMonth() - time.getDayOfMonth() + 31) + "일 전");
                    } else {
                        detailPostDto.setUploadTime((now.getDayOfMonth() - time.getDayOfMonth() + 30) + "일 전");
                    }
                } else {
                    if ((now.getHour() - time.getHour()) > 0) {
                        detailPostDto.setUploadTime((now.getHour() - time.getHour()) + "시간 전");
                    } else if ((now.getHour() - time.getHour()) < 0) {
                        detailPostDto.setUploadTime((now.getHour() - time.getHour() + 24) + "시간 전");
                    } else {
                        detailPostDto.setUploadTime("1시간 이내");
                    }
                }
            }
        }
        return new ResponseEntity<>(detailPostDto, HttpStatus.OK);
    }

    /**
     * 전체 게시글 조회 API
     * */
    @GetMapping("")
    public ArrayList<AllPostResponseDto> getAllPosts(@RequestParam String category, Pageable pageable){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        UserDetails loginUser = userService.loadUserByUsername(username);
        User user = (User) loginUser;
        ArrayList<AllPostResponseDto> allPostResponseDtoArrayList = new ArrayList<>();

        Page<Post> posts;

        if (category.equals("ALL")){
            posts = postRepository.findAllByLocationAndStatusOrderByCreatedTimeDesc(user.getLocation(), 1, pageable);
        }
        else{
            posts = postRepository.findAllByCategoryAndLocationAndStatusOrderByCreatedTimeDesc(category, user.getLocation(), 1, pageable);
        }

        List<Post> content = posts.getContent();
        boolean isLast = posts.isLast();
        AllPostResponseDto allPostResponseDto = new AllPostResponseDto();

        for (Post post : content) {
            allPostResponseDto.setPostId(post.getPostId());
            allPostResponseDto.setRecruitNumber(post.getRecruitmentNumber());
            allPostResponseDto.setNickname(user.getNickname());
            allPostResponseDto.setCreatedAt(post.getCreatedTime());
            allPostResponseDto.setTitle(post.getTitle());
            allPostResponseDto.setMarket(post.getMarket());
            allPostResponseDto.setMeetingTime(post.getMeetingTime());
            allPostResponseDto.setLast(isLast);
            allPostResponseDto.setCategory(post.getCategory());
            allPostResponseDtoArrayList.add(allPostResponseDto);
            allPostResponseDto = new AllPostResponseDto();
        }
        return allPostResponseDtoArrayList;
    }

    /**
     * 게시글 좋아요, 취소
     * */
    @PostMapping("/{postId}/{userId}/like")
    public ResponseEntity<String> likePost(@PathVariable Long postId, @PathVariable Long userId){
        Like likeEntity = likeService.getLikeEntity(postId, userId);
        Post postEntity = postService.getPostEntity(postId);
        Integer likeCount = postEntity.getLikeCount();
        if(likeEntity == null){
            Like like = new Like();
            like.setPostId(postId);
            like.setUserId(userId);
            likeCount+=1;
            postEntity.setLikeCount(likeCount);
            likeService.saveLikeEntity(like);
            postService.savePost(postEntity);
            return new ResponseEntity<>("좋아요 완료", HttpStatus.OK);
        }
        else{
            likeService.deleteLikeEntity(likeEntity);
            likeCount+=1;
            postEntity.setLikeCount(likeCount);
            postService.savePost(postEntity);
            return new ResponseEntity<>("좋아요 취소", HttpStatus.OK);
        }
    }
}
