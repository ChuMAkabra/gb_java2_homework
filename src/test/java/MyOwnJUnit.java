import Assignment_07.AfterSuite;
import Assignment_07.BeforeSuite;
import Assignment_07.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Создать класс, который может выполнять «тесты». В качестве тестов выступают классы с
 * наборами методов с аннотациями @Test. Для этого у него должен быть статический метод start(),
 * которому в качестве параметра передается или объект типа Class, или имя класса. Из «класса-теста»
 * вначале должен быть запущен метод с аннотацией @BeforeSuite, если такой имеется. Далее запущены
 * методы с аннотациями @Test, а по завершении всех тестов – метод с аннотацией @AfterSuite.
 * К каждому тесту необходимо добавить приоритеты (int числа от 1 до 10), в соответствии с которыми
 * будет выбираться порядок их выполнения. Если приоритет одинаковый, то порядок не имеет значения.
 * Методы с аннотациями @BeforeSuite и @AfterSuite должны присутствовать в не более чем в одном
 * экземпляре, иначе необходимо бросить RuntimeException при запуске «тестирования».
 * Это домашнее задание никак не связано с темой тестирования через JUnit и использованием этой
 * библиотеки: проект пишется с нуля.
 */

public class MyOwnJUnit {

    public static void main(String[] args) {
        // первый набор тестов имеет static методы, второй - нет
        MyOwnJUnit.start(TestClass1.class);
        MyOwnJUnit.start(TestClass2.class);
    }

    public static void start(Class className) {
        // получить все методы тестового класса
        Method[] declaredMethods = className.getDeclaredMethods();
        Method beforeMethod = null;
        Method afterMethod = null;
        Set<Integer> priorities = new TreeSet<>();
        ArrayList<Method> testMethods = new ArrayList<>();

        // проверить уникальность методов с аннотациями BeforeSuite и AfterSuite
        for (Method declaredMethod : declaredMethods) {

            if (declaredMethod.isAnnotationPresent(BeforeSuite.class)) {
                if (beforeMethod != null)
                    throw new RuntimeException("Несколько методов имеют аннотацию BeforeSuite");
                else beforeMethod = declaredMethod;
            } else if (declaredMethod.isAnnotationPresent(AfterSuite.class)) {
                if (afterMethod != null)
                    throw new RuntimeException("Несколько методов имеют аннотацию AfterSuite");
                else afterMethod = declaredMethod;
            } else if (declaredMethod.isAnnotationPresent(Test.class)) {
                priorities.add(declaredMethod.getAnnotation(Test.class).priority());
                testMethods.add(declaredMethod);
            }
        }

        // сначала выполнить BeforeSuite
        if (beforeMethod != null) {
            try {
                beforeMethod.invoke(className.newInstance());
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }

        // затем выполнить методы в порядке приоритета от 1 до 10
        for (Integer priority : priorities) {
            for (Method testMethod : testMethods) {
                if (testMethod.getAnnotation(Test.class).priority() == priority) {
                    try {
                        if (priority < 1 || priority > 10)
                            System.out.printf("Тест %s с некорректным приоритетом не будет выполнен\n",
                                    testMethod.getName());
                        else testMethod.invoke(className.newInstance());
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (afterMethod != null) {
            try {
                afterMethod.invoke(className.newInstance());
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}