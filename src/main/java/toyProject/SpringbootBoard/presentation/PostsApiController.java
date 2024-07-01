package toyProject.SpringbootBoard.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyProject.SpringbootBoard.application.PostsService;
import toyProject.SpringbootBoard.application.dto.PostsDto;
import toyProject.SpringbootBoard.application.dto.UserDto;
import toyProject.SpringbootBoard.application.security.auth.LoginUser;

/**
 * REST API Controller
 */
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    /* CREATE */
    @PostMapping("/posts")
    public ResponseEntity save(@RequestBody PostsDto.Request dto, @LoginUser UserDto.Response user) {
        return ResponseEntity.ok(postsService.save(dto, user.getNickname()));
    }

    /* READ */
    @GetMapping("/posts/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        return ResponseEntity.ok(postsService.findById(id));
    }

    /* UPDATE */
    @PutMapping("/posts/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody PostsDto.Request dto) {
        postsService.update(id, dto);
        return ResponseEntity.ok(id);
    }

    /* DELETE */
    @DeleteMapping("/posts/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        postsService.delete(id);
        return ResponseEntity.ok(id);
    }
}
