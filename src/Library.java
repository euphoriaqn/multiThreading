
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

/**
 * Created by user22 on 12.09.2017.
 */
public class Library {
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
            final int x = i+1;
            new Thread(()->{
                System.out.printf("Поток № [%d] пришел к двери входа в библиотеку c улицы\n", x);
                try {
                    synchronized (LIBRARY) {
                        System.out.printf("Поток № [%d] проходит через дверь внутрь\n", x);
                        Thread.sleep(500);
                    }
                    if (SEMAPHORE.hasQueuedThreads()) System.out.printf("Поток № [%d] ждет входа в библиотеку\n", x);
                    SEMAPHORE.acquire();
                    System.out.printf("Поток № [%d] вошел в библиотеку\n", x);
                    System.out.printf("Поток № [%d] читает книгу\n", x);
                    int millisecondsToSleep = r.nextInt(4000) + 1000;
                    Thread.sleep(millisecondsToSleep);
                    System.out.printf("Поток № [%d] подошел к двери изнутри \n", x);
                    synchronized (LIBRARY) {
                        System.out.printf("Поток № [%d] проходит через дверь наружу\n", x);
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                SEMAPHORE.release();
                System.err.printf("Поток № [%d] вышел с библиотеки\n", x);
            }).start();
//            thread.setPriority(Thread.MAX_PRIORITY);
//            thread.start();
//            thread.join();
        }
        //      Thread.currentThread().join();
    }

}


