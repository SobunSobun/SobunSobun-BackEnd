package numble.sobunsobun.apply.repository;

import numble.sobunsobun.apply.domain.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {
    Optional<Apply> findByPostIdAndUserId(Long postId, Long userID);
}
