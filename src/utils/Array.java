package utils;

import java.util.List;
import java.util.function.Predicate;

public class Array {
    public static <T> int findIndex(T[] array, Predicate<T> condition) {
        for (int i = 0; i < array.length; i++) {
            if (condition.test(array[i])) {
                return i;
            }
        }
        return -1;
    }
}
