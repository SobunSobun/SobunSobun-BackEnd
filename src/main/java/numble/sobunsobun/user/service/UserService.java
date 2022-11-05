package numble.sobunsobun.user.service;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.user.domain.User;
import numble.sobunsobun.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public void joinUser(User user){
        userRepository.save(user);
    }

    public boolean isNicknameAvailable(String nickname){
        Optional<User> user = userRepository.findByNicknameAndStatus(nickname, 1);
        return user.isEmpty();
    }
}
