package numble.sobunsobun.parentComment.repository;

import numble.sobunsobun.parentComment.domain.ParentComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentCommentRepository extends JpaRepository<ParentComment, Long> {
    List<ParentComment> findAllByPostIdOrderByCreatedTimeDesc(Long postId);
}
