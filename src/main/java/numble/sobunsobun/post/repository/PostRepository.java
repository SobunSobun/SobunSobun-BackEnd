package numble.sobunsobun.post.repository;

import numble.sobunsobun.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByCategoryAndLocationAndStatusAndIsFullOrderByCreatedTimeDesc(String category, String location, Integer status, Boolean isFull, Pageable pageable);
    Page<Post> findAllByLocationAndStatusAndIsFullOrderByCreatedTimeDesc(String location, Integer status, Boolean isFull, Pageable pageable);
    List<Post> findAllByUserIdAndStatusOrderByCreatedTimeDesc(Long userId, Integer status);
}
