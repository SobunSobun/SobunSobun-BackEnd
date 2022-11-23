package numble.sobunsobun.childComment.service;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.childComment.domain.ChildComment;
import numble.sobunsobun.childComment.repository.ChildCommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChildCommentService {

    private final ChildCommentRepository childCommentRepository;

    public void registerChildComment(ChildComment childComment){
        childCommentRepository.save(childComment);
    }
}
