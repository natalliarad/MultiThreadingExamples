package executors;

import java.util.ArrayList;

/**
 * Do not do like that!
 */
public final class BADStreamSample {

    public static void main(final String[] args) {
        final ArrayList<String> list = new ArrayList<>();
        list.add("AAA");
        list.add("BBB");
        list.add("CCC");
        final String s[] = {"D"};
        list.parallelStream().forEach((String e) -> {
            System.out.println(e + s[0]);
            s[0] += "D";
        });
    }
}
