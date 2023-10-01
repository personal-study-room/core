package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean(OrderServiceImpl.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();
        System.out.println("memberService -> memberRepository1 = " + memberRepository1);
        System.out.println("orderService -> memberRepository2 = " + memberRepository2);

        MemoryMemberRepository memberRepository3 = ac.getBean("memberRepository", MemoryMemberRepository.class);

        // Spring은 singleton 을 완벽하게 보존해주는구나
        assertAll(
                () -> assertEquals(memberRepository1, memberRepository2),
                () -> assertEquals(memberRepository2, memberRepository3),
                () -> assertThat(memberRepository3).isSameAs(memberRepository1)
        );


    }

    @Test
    void configurationDeep() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean.getClass() = " + bean.getClass());
        /* class hello.core.AppConfig$$SpringCGLIB$$0. 뭔가 이상하다.. CGLIB??? */
        /*
            byte코드를 조작하는 라이브러리를 사용!
            AppConfig$$SpringCGLIB$$0는 Appconfig의 자식클래스

      ------------------
            @Bean
            public DiscountPolicy discountPolicy() {
                return new FixDiscountPolicy();
            }

            위의 코드가 예상컨데,

            public DiscountPolicy discountPolicy() {
                if(스프링컨테이너에 있으면,) {
                    return 컨데이너에 있는거 반환
                }
                return new FixDiscountPolicy();
            }
        */
    }
}
