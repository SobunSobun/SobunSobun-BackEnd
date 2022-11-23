package numble.sobunsobun.childComment.repository;

import numble.sobunsobun.childComment.domain.ChildComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildCommentRepository extends JpaRepository<ChildComment, Long> {

}
