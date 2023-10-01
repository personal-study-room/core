package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.*;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();


    // 단일 책임 원칙이 엄청 잘 지켜지고 있다. 왜? 할인 정책이 변경이 되더라도, orderservice는 고칠 필요가 전혀 없다.
    // 즉, 역할과 구현을 분리하고 구현체에서는 역할을 바라보게 설계를 하니, 각각이 독립적으로 운영이 될 수 있다는 것! -> SRP(단일책임원칙)이 지켜지고 있다.
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
