package numble.sobunsobun.parentComment.service;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.parentComment.domain.ParentComment;
import numble.sobunsobun.parentComment.repository.ParentCommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ParentCommentService {

    private final ParentCommentRepository parentCommentRepository;

    public void registerParentComment(ParentComment parentComment){
        parentCommentRepository.save(parentComment);
    }

    public ParentComment getParentCommentEntity(Long parentCommentId){
        Optional<ParentComment> parentComment = parentCommentRepository.findById(parentCommentId);
        return parentComment.orElse(null);
    }
}
