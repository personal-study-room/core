package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {


    // ============
    // field DI : 필드를 통해 주입한다.
    // 특징 - Field injection is not recommended <- intellij 에서 추천하지 않는다는 문구.
    // 테스트 할 때,
    @Autowired private MemberRepository memberRepository;
    @Autowired private DiscountPolicy discountPolicy;

//    // ============
//    // setter DI : setter 메서드를 통해 주입된다.
//    // 특징 - 변경 가능성이 존재하는 의존성에 사용 된다!
//    //     - 생성자 주입보다 나중에 일어난다. (당연)
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("2. 세터 주입");
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        System.out.println("3. 세터 주입");
//        this.discountPolicy = discountPolicy;
//    }
//
//    // =============
//    // constructor DI : 생성자 주입
//    // 특징 - 한번 주입이 일어나면, 변경 불가능 (불가능)
//    //    @Autowired  <- 생성자가 1개만 존재할 경우, @Autowired 생략 가능
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        System.out.println("1. OrderServiceImpl.OrderServiceImpl");
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }


    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
    // 테스트 용도
    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }
}
