package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA: A사용자가 10000원 주문
        statefulService1.order("userA", 10000);
        // ThreadB: A사용자가 10000원 주문
        statefulService2.order("userB", 20000);

        // ThreadA: 사용자A 가 주문 금액을 조회
        int price = statefulService1.getPrice();
        System.out.println("price = " + price);

        assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    @Test
    void statefulServiceSingleton2() throws InterruptedException {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        Runnable runnable = () -> {
            StatefulService2 service = ac.getBean(StatefulService2.class);
            service.order("userA", 10000);
        };

        Runnable runnable2 = () -> {
            StatefulService2 service = ac.getBean(StatefulService2.class);
            System.out.println("service.getPrice() = " + service.getPrice());
            service.order("userB", 20000);
            System.out.println("service.getPrice() = " + service.getPrice());
        };

        Thread thread1 = new Thread(runnable, "threadA");
        Thread thread2 = new Thread(runnable2, "threadB");

        thread1.start();
        Thread.sleep(1000);
        thread2.start();
    }

    @Configuration
    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }

        @Bean
        public StatefulService2 statefulService2() {
            return new StatefulService2();
        }
    }

}