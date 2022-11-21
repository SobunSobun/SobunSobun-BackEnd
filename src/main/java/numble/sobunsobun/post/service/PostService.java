package numble.sobunsobun.post.service;

import lombok.RequiredArgsConstructor;
import numble.sobunsobun.post.domain.Post;
import numble.sobunsobun.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public void savePost(Post post){
        postRepository.save(post);
    }

    public Post getPostEntity(Long postId){
        Optional<Post> post = postRepository.findById(postId);
        return post.orElse(null);
    }

}
