package expression.parser;

import java.util.Objects;

public class MyTests {
    public static void main(String[] args) {
        int digit = -123456;
        int cnt = 0;
        int parity = 0;
        if (Objects.equals(digit, 0)) {
            System.out.println(32);
            return;
        }
        if (digit < 0) {
            parity = 1;
            digit = -(digit + 1);
        }
        while (digit % 2 == parity) {
            digit /= 2;
            cnt++;
        }
        System.out.println(cnt);

    }
}
