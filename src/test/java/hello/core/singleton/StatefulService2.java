package hello.core.singleton;

public class StatefulService2 {

    private final ThreadLocal<Integer> price = new ThreadLocal<>();

    public void order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        this.price.set(price);
    }

    public Integer getPrice() {
        return price.get();
    }
}
