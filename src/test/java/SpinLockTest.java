

import com.dreamteam.lab02.locks.SpinLock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;


public class SpinLockTest {
    private static final int size = 10000000;
    private static final int threadCount = 10;
    private static final SpinLock spinLock = new SpinLock();
    private static ArrayList<Integer> resource = new ArrayList<>();

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
                            //System.out.print(i + ", ");

                        }
                        resource.sort(Comparator.naturalOrder());
                        System.out.println("Sorted!");
                        System.out.println();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
        for(int i = 0; i < threadCount; ++i) {
            new Task(resource, "task" + i).start();
        }
    }
}
