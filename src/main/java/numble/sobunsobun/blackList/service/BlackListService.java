package numble.sobunsobun.blackList.service;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.blackList.domain.BlackList;
import numble.sobunsobun.blackList.repository.BlackListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BlackListService {

    private final BlackListRepository blackListRepository;

    public BlackList getBlackListEntity(String token){
        Optional<BlackList> blackList = blackListRepository.findByToken(token);
        return blackList.orElse(null);
    }
}
