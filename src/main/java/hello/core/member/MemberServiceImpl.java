package hello.core.member;

// 문제점 : MemberServiceImpl은 지금 MemoryMemberRepository라는 구현체를 의존하고 있다.
// DIP : 실제 서비스를 반영하는 코드에서는 구현체를 의존하는 것이 아니라, interface를 바라보게 만들어야 한다!
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
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
