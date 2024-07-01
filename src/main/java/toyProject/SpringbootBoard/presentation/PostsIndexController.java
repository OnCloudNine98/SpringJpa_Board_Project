package toyProject.SpringbootBoard.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import toyProject.SpringbootBoard.application.PostsService;
import toyProject.SpringbootBoard.application.dto.CommentDto;
import toyProject.SpringbootBoard.application.dto.PostsDto;
import toyProject.SpringbootBoard.application.dto.UserDto;
import toyProject.SpringbootBoard.application.security.auth.LoginUser;
import toyProject.SpringbootBoard.domain.Posts;

import java.util.List;

/**
 * 화면 연결 Controller
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class PostsIndexController {
    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                        Pageable pageable, @LoginUser UserDto.Response user) {
        Page<Posts> list = postsService.pageList(pageable);

        if (user != null) {
            model.addAttribute("user", user);
        }
        model.addAttribute("posts", list);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());

        return "index";
    }

    /* 글 작성 */
    @GetMapping("/posts/write")
    public String write(@LoginUser UserDto.Response user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "posts/posts-write";
    }

    /* 글 상세보기 */
    @GetMapping("/posts/read/{id}")
    public String read(@PathVariable("id") Long id, @LoginUser UserDto.Response user, Model model) {
        PostsDto.Response dto = postsService.findById(id);
        List<CommentDto.Response> comments = dto.getComments();

        /* 댓글 관련 */
        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }
        /* 사용자 관련 */
        if (user != null) {
            model.addAttribute("user", user);

            /* 게시글 작성자 본인인지 확인 */
            if (dto.getUserId().equals(user.getId())) {
                model.addAttribute("writer", true);
            }

            /* 댓글 작성자 본인인지 확인 */
            if (comments.stream().anyMatch(s -> s.getUserId().equals(user.getId()))) {
                model.addAttribute("isWriter", true);

            }
        }

        postsService.updateView(id); // views ++
        model.addAttribute("posts", dto);

        /* 현재 세션의 Userid 전달 _ 댓글 삭제 */
        model.addAttribute("sessionId", user.getId());
        return "posts/posts-read";
    }

    /*해당 게시물 수정*/

    @GetMapping("/posts/update/{id}")
    public String update(@PathVariable("id") Long id, @LoginUser UserDto.Response user, Model model) {
        PostsDto.Response dto = postsService.findById(id);
        if (user != null) {
            model.addAttribute("user", user);
        }
        model.addAttribute("posts", dto);
        return "posts/posts-update";
    }

    /*게시물 검색*/
    @GetMapping("/posts/search")
    public String search(String keyword, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                         Pageable pageable, @LoginUser UserDto.Response user) {
        Page<Posts> searchList = postsService.search(keyword, pageable);

        if (user != null) {
            model.addAttribute("user", user);
        }
        model.addAttribute("searchList", searchList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", searchList.hasNext());
        model.addAttribute("hasPrev", searchList.hasPrevious());

        return "posts/posts-search";
    }
}
