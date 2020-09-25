package Assignment_06;

import java.util.Arrays;

public class ForTests {
    public static void main(String[] args) {
        int[] resArr1 = numbersAfterLastFour(new int[]{1, 2, 3, 4, 5, 6});
        System.out.println(Arrays.toString(resArr1));
        int[] resArr2 = numbersAfterLastFour(new int[]{1, 2, 3, 7, 5, 6});
        System.out.println(Arrays.toString(resArr2));

        System.out.println(hasOnesAndFours(new int[] {1,1,1,4}));
        System.out.println(hasOnesAndFours(new int[] {1,1,1,1}));
        System.out.println(hasOnesAndFours(new int[] {1,1,4,3}));
        System.out.println(hasOnesAndFours(new int[] {4,4,4,4}));
    }

    public static int[] numbersAfterLastFour(int[] arr) {
        int arrLen = arr.length;

//        try {
            for (int i = arrLen - 1; i >= 0; i--) {
                if (arr[i] == 4) {
                    return Arrays.copyOfRange(arr, i + 1, arrLen);
                }
            }
            throw new RuntimeException("В массиве нет 4");
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    public static boolean hasOnesAndFours(int[] arr) {
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
