package numble.sobunsobun.parentComment.service;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.parentComment.domain.ParentComment;
import numble.sobunsobun.parentComment.repository.ParentCommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ParentCommentService {

    private final ParentCommentRepository parentCommentRepository;

//    public ParentComment getParentCommentEntity(Long postId){
//        List
//    }

    public void registerParentComment(ParentComment parentComment){
        parentCommentRepository.save(parentComment);
    }
}
