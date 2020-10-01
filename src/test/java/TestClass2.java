import Assignment_07.AfterSuite;
import Assignment_07.BeforeSuite;
import Assignment_07.Test;

public class TestClass2 {
    @BeforeSuite
    public void init() {
        System.out.println("Метод с аннотацией BeforeSuite из набора тестов №2");
    }

//    @BeforeSuite
//    public void init2() {
//        System.out.println("Метод с аннотацией BeforeSuite из набора тестов №2");
//    }    

    @Test(priority = 3)
    public void test1() {
        System.out.println("Тест №1 c приоритетом 3 из набора тестов №2");
    }

    @Test(priority = 3)
    public void test2(){
        System.out.println("Тест №2 c приоритетом 3 из набора тестов №2");
    }
    
    @Test
    public void test3(){
        System.out.println("Тест №3 c приоритетом 5 (по умолчанию) из набора тестов №2");
    }

    @Test(priority = 1)
    public void test4(){
        System.out.println("Тест №4 c приоритетом 1 из набора тестов №2");
    }

    @AfterSuite
    public void finish() {
        System.out.println("Метод с аннотацией AfterSuite из набора тестов №2");
    }

//    @AfterSuite
//    public void finish2() {
//        System.out.println("Метод с аннотацией AfterSuite");
//    }    
}