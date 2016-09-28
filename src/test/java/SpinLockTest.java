import com.dreamteam.lab02.SpinLock;

import java.util.ArrayList;
import java.util.Random;


public class SpinLockTest {
    static final int size = 10;
    static final SpinLock spinLock = new SpinLock();
    static ArrayList<Integer> resource = new ArrayList<>();
    private static class Task implements Runnable {
        private ArrayList<Integer> resource;
        private String name;
        public Task(ArrayList<Integer> resource, String name) {
            this.resource = resource;
            this.name = name;
        }

        @Override
        public void run() {
            while (true)  {
                if(spinLock.isLocked()) continue;
                try(SpinLock.Lock lock = spinLock.lock()) {
                    System.out.println(name + " is working with resource");
                    try {
                        for(Integer i : resource) {
                            i = new Random().nextInt(size);
                            System.out.print(i + ", ");
                        }
                        System.out.println();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //lock.close();
                }

            }

        }

        public void start() {
            Thread thread = new Thread(this);
            thread.start();
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < size; ++i) {
            resource.add(i);
        }
        System.out.println("Creating tasks");
        Task task1 = new Task(resource, "task1");
        Task task2 = new Task(resource, "task2");

        task1.start();
        task2.start();
    }
}
