package toyProject.SpringbootBoard.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import toyProject.SpringbootBoard.domain.Comment;
import toyProject.SpringbootBoard.domain.Posts;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    /* 게시글 댓글 목록 가져오기 */
    List<Comment> getCommentByPostsOrderById(Posts posts);

    Optional<Comment> findByPostsIdAndId(Long postId, Long id);
}
