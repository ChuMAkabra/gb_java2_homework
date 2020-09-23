package Assignment_05;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Runnable {
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;

    // позволит только одному гонщику сообщить о победе
    private static final Lock lock = new ReentrantLock(true);

    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            MainClass.lineBarrier.await(); // подождать остальных на линии старта
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        if (lock.tryLock()) {
            try {
                System.out.println(name + " ПОБЕДИЛ!!!");
                // барьер №2: финишная линия. Как только доберутся до финиша, появится сообщение
                MainClass.setLineBarrier("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
                MainClass.lineBarrier.reset(); // сбросить счетчик барьера
                MainClass.lineBarrier.await(); // подождать остальных на финише
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            // отпустить лок, когда никто уже не захочет его захватить
            lock.unlock();
        }
        else {
            try {
                MainClass.lineBarrier.await(); // подождать остальных на финише
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
        // инкремировать счетчик, чтобы еще раз сообщить о завершении гонки
        MainClass.finishCountDown.countDown();
    }
}