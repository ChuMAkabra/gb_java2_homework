package Assignment_04;
/**    1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок – ABСABСABС). Используйте wait/notify/notifyAll. */
public class ABCThreads {
    static final Object mon = new Object();
    static volatile char curLetter = 'C';

    public static void main(String[] args){
        createThread('C', 'A');
        createThread('A', 'B');
        createThread('B', 'C');
    }

    private static void createThread(char before, char after) {
        new Thread(() -> {
            try {
                synchronized (mon) {
                    for (int i = 0; i < 5; i++) {
                        while (curLetter != before) {
                            mon.wait();
                        }
                        curLetter = after;
                        System.out.print(curLetter);
                        mon.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
