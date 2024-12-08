import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    // Счетчики для "красивых" слов
    private static final AtomicInteger countLength3 = new AtomicInteger(0);
    private static final AtomicInteger countLength4 = new AtomicInteger(0);
    private static final AtomicInteger countLength5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        // Создание и запуск потоков для проверки "красивых" слов
        Thread thread3 = new Thread(() -> checkBeautifulNicknames(texts, 3, countLength3));
        Thread thread4 = new Thread(() -> checkBeautifulNicknames(texts, 4, countLength4));
        Thread thread5 = new Thread(() -> checkBeautifulNicknames(texts, 5, countLength5));

        thread3.start();
        thread4.start();
        thread5.start();

        thread3.join();
        thread4.join();
        thread5.join();
        
        System.out.println("Красивых слов с длиной 3: " + countLength3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + countLength4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + countLength5.get() + " шт");
    }

    // Метод для генерации текста
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    // Метод для проверки "красивых" никнеймов
    public static void checkBeautifulNicknames(String[] texts, int length, AtomicInteger counter) {
        for (String text : texts) {
            if (text.length() == length && isBeautiful(text)) {
                counter.incrementAndGet();
            }
        }
    }

    // Метод для проверки, является ли слово "красивым"
    public static boolean isBeautiful(String text) {
        return isPalindrome(text) || isSameLetter(text) || isSorted(text);
    }

    // Проверка на палиндром
    public static boolean isPalindrome(String text) {
        int left = 0;
        int right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // Проверка на однообразие букв
    public static boolean isSameLetter(String text) {
        char firstChar = text.charAt(0);
        for (char c : text.toCharArray()) {
            if (c != firstChar) {
                return false;
            }
        }
        return true;
    }

    // Проверка на сортировку букв
    public static boolean isSorted(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }
}