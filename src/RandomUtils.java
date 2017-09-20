package multiThreading.src;

import java.util.Random;

public class RandomUtils {
    public static final Random random = new Random();

    public static float range(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }

    public static int range(int min, int max) {
        return random.nextInt((max-min)) + min;
    }
}
