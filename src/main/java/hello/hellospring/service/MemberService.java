package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    private final MemberRepository memberRopository;

    public MemberService(MemberRepository memberRopository) {
        this.memberRopository = memberRopository;
    }


    /**
    * 회원가입
    */
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRopository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRopository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRopository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRopository.findById(memberId);
    }

}
