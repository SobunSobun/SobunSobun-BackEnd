package numble.sobunsobun.post.service;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.post.domain.Post;
import numble.sobunsobun.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public void savePost(Post post){
        postRepository.save(post);
    }
}
