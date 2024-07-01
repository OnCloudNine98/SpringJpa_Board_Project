package toyProject.SpringbootBoard.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import toyProject.SpringbootBoard.application.UserService;
import toyProject.SpringbootBoard.application.dto.UserDto;
import toyProject.SpringbootBoard.application.security.auth.LoginUser;
import toyProject.SpringbootBoard.application.validator.CustomValidators;

import java.util.Map;

/**
 * 회원 관련 Controller
 */
@RequiredArgsConstructor
@Controller
@Slf4j
public class UserController {

    /*로그인 관련*/
    private final UserService userService;

    private final CustomValidators.EmailValidator EmailValidator;
    private final CustomValidators.NicknameValidator NicknameValidator;
    private final CustomValidators.UsernameValidator UsernameValidator;

    /* 커스텀 유효성 검증을 위해 추가 */

    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(EmailValidator);
        binder.addValidators(NicknameValidator);
        binder.addValidators(UsernameValidator);
    }

    @GetMapping("/auth/join")
    public String join() {
        return "user/user-join";
    }

    /* 회원 가입 */
    @PostMapping("/auth/joinProc")
    public String joinProc(@Valid UserDto.Request dto, Errors errors, Model model) {
        log.info("==========joinProc 진입==========");
        if (errors.hasErrors()) {
            /* 회원가입 실패 시 입력 데이터 값을 유지 */
            model.addAttribute("userDto", dto);

            log.error("Error = "+errors);

            /* 유효성 통과 못한 필드와 메세지를 핸들링 */
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            /* 회원가입 페이지로 다시 리턴 */
            return "user/user-join";
        }
        userService.userJoin(dto);
        return "redirect:/auth/login";
    }

    @GetMapping("/auth/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "user/user-login";
    }

    /* Security에서 로그아웃은 기본적으로 POST지만, GET으로 우회 */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    /* 회원 정보 수정*/
    @GetMapping("/modify")
    public String modify(@LoginUser UserDto.Response user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "user/user-modify";
    }
}
