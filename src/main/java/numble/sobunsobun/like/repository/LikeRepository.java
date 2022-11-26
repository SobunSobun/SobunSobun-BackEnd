package numble.sobunsobun.like.repository;

import numble.sobunsobun.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPostIdAndUserId(Long postId, Long userID);
    List<Like> findByUserIdOrderByLikeIdDesc(Long userId);
}
