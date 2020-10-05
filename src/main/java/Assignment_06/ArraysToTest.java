package Assignment_06;

import java.util.Arrays;

public class ArraysToTest {

    public int[] numbersAfterLastFour(int[] arr) {
        int arrLen = arr.length;

        for (int i = arrLen - 1; i >= 0; i--) {
            if (arr[i] == 4) {
                return Arrays.copyOfRange(arr, i + 1, arrLen);
            }
        }
        throw new RuntimeException("В массиве нет 4");
    }

    public boolean hasOnesAndFours(int[] arr) {
        boolean has1 = false;
        boolean has4 = false;

        for (int i : arr) {
            if      (i == 1) has1 = true;
            else if (i == 4) has4 = true;
            else return false;
        }
        return has1 && has4;
    }
}
