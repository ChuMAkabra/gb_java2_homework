package Assignment_01;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String[] strArr = {"may", "the force", "be", "with", "you"};
        outputArray(strArr);
        System.out.println();
        outputArray(swapElements(strArr, 1, 2));
        System.out.println();
        System.out.println(arrayToList(strArr));
        System.out.println();

        Box<Apple> boxNull = new Box<>();
        Box<Apple> boxApple =   new Box<>(new Apple(), new Apple(), new Apple());
        Box<Orange> boxOrange = new Box<>(new Orange(), new Orange());

        System.out.printf("Apples: %fkg\nOranges: %fkg\n", boxApple.getWeight(), boxOrange.getWeight());
        System.out.println(boxApple.compare(boxOrange));

        System.out.println();
        Box<Apple> boxApple1 =  new Box<>(new Apple());
        boxApple.transfer(boxApple1);
        System.out.println(boxApple);
        System.out.println(boxApple1);


    }

    /** 1.	Написать метод, который меняет два элемента массива местами
     * (массив может быть любого ссылочного типа);
     * **/

    public static <T> T[] swapElements(T[] obj, int first, int second) {
        T temp = obj[second];
        obj[second] = obj[first];
        obj[first] = temp;
        return obj;
    }

    /** 2.	Написать метод, который преобразует массив в ArrayList **/
    public static <T> ArrayList<T> arrayToList(T[] arr){
        ArrayList<T> al = new ArrayList<>(Arrays.asList(arr));
        return al;
    }

    private static void outputArray(String[] strArr) {
        for (String s : strArr) {
            System.out.printf("%s ", s);
        }
    }
}
