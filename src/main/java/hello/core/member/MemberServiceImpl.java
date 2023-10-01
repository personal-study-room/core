package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// 문제점 : MemberServiceImpl은 지금 MemoryMemberRepository라는 구현체를 의존하고 있다.
// DIP : 실제 서비스를 반영하는 코드에서는 구현체를 의존하는 것이 아니라, interface를 바라보게 만들어야 한다!
@Component
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) { // 1. 우선 타입으로 자동주입이 된다. ac.getBean(~~~.class) > 이와 같은 논리
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
