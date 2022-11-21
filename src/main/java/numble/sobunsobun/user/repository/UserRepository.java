package numble.sobunsobun.user.repository;

import numble.sobunsobun.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNicknameAndStatus(String nickname, Integer status);
    Optional<User> findByEmailAndStatus(String email, Integer Status);
    Optional<User> findByUserIdAndStatus(Long userId, Integer Status);
}
