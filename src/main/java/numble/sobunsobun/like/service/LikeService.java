package numble.sobunsobun.like.service;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.like.domain.Like;
import numble.sobunsobun.like.repository.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;

    public Like getLikeEntity(Long postId, Long userId){
        Optional<Like> like = likeRepository.findByPostIdAndUserId(postId, userId);
        return like.orElse(null);
    }

    public void saveLikeEntity(Like like){
        likeRepository.save(like);
    }

    public void deleteLikeEntity(Like like){
        likeRepository.delete(like);
    }

}
