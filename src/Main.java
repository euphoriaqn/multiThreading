
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

/**
 * Created by user22 on 12.09.2017.
 */
public class Main {
    static final Object LIBRARY = new Object();
    private final static Random r = new Random();
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner in = new Scanner(System.in);
        int peopleCount;
        int maxAmount;
        System.out.println("Введіть кількість людей-потоків, що ломляться в бібліотеку");
        peopleCount = in.nextInt();
        System.out.println("Введіть кількість людей-потоків, які одночасно можуть зайти в бібліотеку");
        final Semaphore SEMAPHORE = new Semaphore(in.nextInt());

        for(int i = 0; i < peopleCount; i++) {

            new Thread(()->{
                System.out.printf("Поток № [%d] пришел к входу в библиотеку\n", Thread.currentThread().getId());
                try {
                    if (SEMAPHORE.hasQueuedThreads()) System.out.printf("Поток № [%d] ждет входа в библиотеку\n", Thread.currentThread().getId());
                    SEMAPHORE.acquire();
                    System.out.printf("Поток № [%d] вошел в библиотеку\n", Thread.currentThread().getId());
                    System.out.printf("Поток № [%d] читает книгу\n", Thread.currentThread().getId());
                    int millisecondsToSleep = r.nextInt(4000) + 1000;
                    Thread.sleep(millisecondsToSleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SEMAPHORE.release();
                System.err.printf("Поток № [%d] вішел с библиотеки\n", Thread.currentThread().getId());
            }).start();
//            thread.setPriority(Thread.MAX_PRIORITY);
//            thread.start();
//            thread.join();
        }
        //      Thread.currentThread().join();
    }

}


