package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blog.dto.user.UserReq.JoinReqDto;
import shop.mtcoding.blog.dto.user.UserReq.LoginReqDto;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @PostMapping("/join")
    public String join(JoinReqDto joinReqDto){
        System.out.println(joinReqDto.getUsername());
        System.out.println(joinReqDto.getPassword());
        System.out.println(joinReqDto.getEmail());

        if(joinReqDto.getUsername() == null || joinReqDto.getUsername().isEmpty()){
            throw new CustomException("username을 작성해주세요");
        }
        if (joinReqDto.getPassword() == null || joinReqDto.getPassword().isEmpty()) {
            throw new CustomException("password를 작성해주세요");
        }
        if (joinReqDto.getEmail() == null || joinReqDto.getEmail().isEmpty()) {
            throw new CustomException("email을 작성해주세요");
        } 

        userService.회원가입(joinReqDto); // 받아서 서비스에서 처리하게 넘기면 됨
      
        return "redirect:/loginForm";
    }


    @PostMapping("/login")
    public String login(LoginReqDto loginReqDto){
        // 원래는 한글이 작성안되게 막아야함, 정규표현식 사용
        if (loginReqDto.getUsername() == null || loginReqDto.getUsername().isEmpty()) {
            throw new CustomException("username을 작성해주세요");
        }
        if (loginReqDto.getPassword() == null || loginReqDto.getPassword().isEmpty()) {
            throw new CustomException("password를 작성해주세요");
        }
        User principal = userService.로그인(loginReqDto);
        
        session.setAttribute("principal", principal);
        return "redirect:/";
    }


    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/logout") // 로그아웃한다는건 jsession아이디를 초기화 한다는 것
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }
}
