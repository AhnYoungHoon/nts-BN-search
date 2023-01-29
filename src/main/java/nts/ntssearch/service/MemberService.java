package nts.ntssearch.service;

import nts.ntssearch.domain.Member;
import nts.ntssearch.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String join(Member member){
        //같은 이름이 있는 중복회원x
        //ctrl alt m -> extractmethod
            validateDuplicateMember(member);
            memberRepository.save(member);
            return member.getUser_id();

    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByUserId(member.getUser_id())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public String login(Member member){
        String loginResult = memberRepository.findForLogin(member.getUser_id(), member.getPassword());

        if(loginResult.equals("login success")){
            return "login success";
        } else {
            return "login fail";
        }
    }

}
