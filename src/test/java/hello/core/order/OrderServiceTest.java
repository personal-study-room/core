package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


// 순수하게 자바 코드로 테스르를 하고 있다는 것을 알 수 있다.
// POJO를 이용해서 테스트를 할 수 있어야 한다.!
public class OrderServiceTest {

    MemberService memberService ;
    OrderService orderService ;

    @BeforeEach
    public void setup() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void createOrder() {
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        Assertions.assertThat(order.calculatePrice()).isEqualTo(9000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

//    @Test
//    void fieldInjectionTest() {
//        OrderService orderService = new OrderServiceImpl();
//        orderService.createOrder(1L, "itemA", 10000);
//        // 순수 자바 코드로 test를 해보고 싶을 때, 이렇게 필드 주입을 하면, 불가능하다!
//        // 왜? 스프링은 @Autowired를 통해서 주입해주었지만, 실제로 내가 인스턴스를 호출해서 뭔가 테스트를 할 때, 불가능 하다.
//        // 이것을 가능하게 하려면, getter/setter 를 열어주어서 세팅해주어야 한다.
//    }
}
