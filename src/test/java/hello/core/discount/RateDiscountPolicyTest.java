package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {

    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다")
    void vip_o() {
        Member member = new Member(1L, "memberA", Grade.VIP);

        int discount = discountPolicy.discount(member, 20000);

        Assertions.assertThat(discount).isEqualTo(2000);
    }

    @Test
    @DisplayName("VIP가 아니면 적용 되는게 없어야 한다!")
    void vip_x() {
        Member member = new Member(1L, "memberA", Grade.BASIC);

        int discount = discountPolicy.discount(member, 20000);

        Assertions.assertThat(discount).isEqualTo(0);
    }
}