import Assignment_07.AfterSuite;
import Assignment_07.BeforeSuite;
import Assignment_07.Test;

public class TestClass1 {
    @BeforeSuite
    public static void init() {
        System.out.println("Метод с аннотацией BeforeSuite");
    }

    public static void test0(){
        System.out.println("Тест №0, которому не суждено выполниться");
    }

    @Test(priority = 3)
    public static void test1() {
        System.out.println("Тест №1 c приоритетом 3");
    }

    @Test(priority = 11)
    public static void test2(){
        System.out.println("Тест №2 c приоритетом 2");
    }

    @Test
    public static void test3(){
        System.out.println("Тест №3 c приоритетом 5 (по умолчанию)");
    }

    @Test(priority = 1)
    public static void test4(){
        System.out.println("Тест №4 c приоритетом 1");
    }

    @AfterSuite
    public static void finish() {
        System.out.println("Метод с аннотацией AfterSuite");
    }
}