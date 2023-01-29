package nts.ntssearch.controller;

import nts.ntssearch.domain.Member;
import nts.ntssearch.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired //스프링 컨테이너에서 가져오는 것
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String register(){
        return "members/register";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        member.setUser_id(form.getUser_id());
        member.setPassword(form.getPassword());

        memberService.join(member);

        return "redirect:/"; //홈화면으로 리다이렉트
    }

    @GetMapping("/members/login")
    public String loginPage(){
        return "members/login";
    }

    @PostMapping("/members/login")
    public String login(LoginForm form, RedirectAttributes attr){
        Member member = new Member();
        member.setUser_id(form.getUser_id());
        member.setPassword(form.getPassword());

        String loginResult = memberService.login(member);

        if(loginResult.equals("login success")){
            attr.addFlashAttribute("user_id", form.getUser_id());
            System.out.println(form.getUser_id());
            return "redirect:/afterLogin";
        }else{
            return "redirect:/members/login";
        }

    }
}
