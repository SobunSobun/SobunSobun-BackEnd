package numble.sobunsobun.childComment.repository;

import numble.sobunsobun.childComment.domain.ChildComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildCommentRepository extends JpaRepository<ChildComment, Long> {
    List<ChildComment> findAllByParentCommentIdOrderByCreatedTimeDesc(Long parentCommentId);
}
