package toyProject.SpringbootBoard.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyProject.SpringbootBoard.application.dto.CommentDto;
import toyProject.SpringbootBoard.domain.Comment;
import toyProject.SpringbootBoard.domain.Posts;
import toyProject.SpringbootBoard.domain.User;
import toyProject.SpringbootBoard.infrastructure.persistence.CommentRepository;
import toyProject.SpringbootBoard.infrastructure.persistence.PostsRepository;
import toyProject.SpringbootBoard.infrastructure.persistence.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostsRepository postsRepository;

    /* CREATE */
    @Transactional
    public Long save(Long id, String nickname, CommentDto.Request dto) {
        User user = userRepository.findByNickname(nickname);
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + id));
        dto.setUser(user);
        dto.setPosts(posts);

        Comment comment = dto.toEntity();
        commentRepository.save(comment);

        return comment.getId();
    }

    /* READ */
    @Transactional(readOnly = true)
    public List<CommentDto.Response> findAll(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));
        List<Comment> comments = posts.getComments();
        return comments.stream().map(CommentDto.Response::new).collect(Collectors.toList());
    }

    /* UPDATE */
    @Transactional
    public void update(Long postId, Long id, CommentDto.Request dto) {
        Comment comment = commentRepository.findByPostsIdAndId(postId, id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다." + id));
        comment.update(dto.getComment());
    }

    /* DELETE */
    public void delete(Long postId, Long id) {
        Comment comment = commentRepository.findByPostsIdAndId(postId, id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));

        commentRepository.delete(comment);
    }
}
