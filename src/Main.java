import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static AtomicInteger counter3 = new AtomicInteger(0);
    static AtomicInteger counter4 = new AtomicInteger(0);
    static AtomicInteger counter5 = new AtomicInteger(0);
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        //проверяем на палиндром
        new Thread(() -> {
            for (String text : texts) {
                if (text.equals(new StringBuilder(text).reverse().toString())) {
                    if (text.length() == 3) counter3.incrementAndGet();
                    if (text.length() == 4) counter4.incrementAndGet();
                    if (text.length() == 5) counter5.incrementAndGet();
                }
            }
        }).start();

        //проверяем "на одну букву"
        new Thread(() -> {
            boolean isOneChar;
            for (String text : texts) {
                isOneChar = true;
                for (int i = 1; i < text.length(); i++) {
                    if (text.charAt(i) != text.charAt(i - 1)) {
                        isOneChar = false;
                        break;
                    }
                }
                if (isOneChar) {
                    if (text.length() == 3) counter3.incrementAndGet();
                    if (text.length() == 4) counter4.incrementAndGet();
                    if (text.length() == 5) counter5.incrementAndGet();
                }
            }
        }).start();

        //проверяем "буквы идут по возрастанию"
        new Thread(() -> {
            for (String text : texts) {
                char[] chars = text.toCharArray();
                Arrays.sort(chars);
                String sortedText = new String(chars);
                if (sortedText.equals(text)) {
                    if (text.length() == 3) counter3.incrementAndGet();
                    if (text.length() == 4) counter4.incrementAndGet();
                    if (text.length() == 5) counter5.incrementAndGet();
                }
            }
        }).start();

        System.out.printf("Красивых слов с длиной 3: %s шт\n", counter3.toString());
        System.out.printf("Красивых слов с длиной 4: %s шт\n", counter4.toString());
        System.out.printf("Красивых слов с длиной 5: %s шт\n", counter5.toString());
    }
}