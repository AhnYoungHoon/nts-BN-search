package nts.ntssearch.repository;

import nts.ntssearch.domain.Member;
import nts.ntssearch.domain.closedNum;
import nts.ntssearch.domain.presentNum;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByUserId(String user_id);
    String findForLogin(String user_id, String password);

}
