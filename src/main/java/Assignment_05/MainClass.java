package Assignment_05;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
/** в программе двумя способами выведено объявление о завершении гонки */

public class MainClass {
    public static final int CARS_COUNT = 4;
    // выведет сообщение, как только все потоки будут на линии (старта или финиша)
    public static CyclicBarrier lineBarrier;
    // семафор для тоннеля - пропустит дальше не больше половины гонщиков
    public static Semaphore tunnelSemaphore = new Semaphore(CARS_COUNT/2, true);
    // тоже выведет сообщение о завершении гонки
    public static CountDownLatch finishCountDown = new CountDownLatch(CARS_COUNT);

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        // барьер №1: стартовая линия. Как только все будут готовы, появится сообщение
        setLineBarrier("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (Car car : cars) {
            new Thread(car).start();
        }
        // сообщение не появится до тех пор, пока счетчик потоков не равен 0
        try {
            finishCountDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }

    public static void setLineBarrier(String message) {
        lineBarrier = new CyclicBarrier(CARS_COUNT,
            () -> System.out.println(message));
    }
}