package toyProject.SpringbootBoard.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyProject.SpringbootBoard.application.CommentService;
import toyProject.SpringbootBoard.application.dto.CommentDto;
import toyProject.SpringbootBoard.application.dto.UserDto;
import toyProject.SpringbootBoard.application.security.auth.LoginUser;

import java.util.List;

/**
 * REST API Controller
 */
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
@Slf4j
public class CommentApiController {
    private final CommentService commentService;

    /* CREATE */
    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<Long> save(@PathVariable("id") Long id, @RequestBody CommentDto.Request dto,
                                     @LoginUser UserDto.Response userSessionDto) {
        return ResponseEntity.ok(commentService.save(id, userSessionDto.getNickname(), dto));
    }

    /* READ */
    @GetMapping("/posts/{id}/comments")
    public List<CommentDto.Response> read(@PathVariable("id") Long id) {
        return commentService.findAll(id);
    }

    /* UPDATE */
    @PutMapping({"/posts/{postsId}/comments/{id}"})
    public ResponseEntity<Long> update(@PathVariable("postsId")Long postsId, @PathVariable("id")Long id, @RequestBody CommentDto.Request dto) {
        log.info("==========update전==========");
        log.info("postsId="+postsId +", id="+id+", dto="+dto);
        commentService.update(postsId, id, dto);
        log.info("==========update후==========");

        return ResponseEntity.ok(id);
    }

    /* DELETE */
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<Long> delete(@PathVariable("postId") Long postsId, @PathVariable("id") Long id) {
        commentService.delete(postsId, id);
        return ResponseEntity.ok(id);
    }
}
