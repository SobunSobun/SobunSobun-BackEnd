package numble.sobunsobun.apply.service;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.apply.domain.Apply;
import numble.sobunsobun.apply.repository.ApplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplyService {

    private final ApplyRepository applyRepository;

    public Apply getApplyEntity(Long postId, Long userId){
        Optional<Apply> apply = applyRepository.findByPostIdAndUserId(postId, userId);
        return apply.orElse(null);
    }

    public void saveApplyEntity(Apply apply){
        applyRepository.save(apply);
    }

    public void deleteApplyEntity(Apply apply){
        applyRepository.delete(apply);
    }
}
