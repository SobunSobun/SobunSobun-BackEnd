package numble.sobunsobun.myPage.service;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.user.domain.User;
import numble.sobunsobun.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;

    public void deleteUser(Long userId){
        Optional<User> user = userRepository.findByUserIdAndStatus(userId, 1);
        user.ifPresent(value -> value.setStatus(0));
    }
}
