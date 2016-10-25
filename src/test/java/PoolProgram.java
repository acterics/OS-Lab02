import com.dreamteam.lab02.benchmark.Pool;
import com.dreamteam.lab02.locks.BakeryBlackAndWhite;
import com.dreamteam.lab02.locks.BakeryLock;


public class PoolProgram {
    public static void main(String[] args) {
        Pool pool;
        System.out.println("Starting...");
        try {
            pool = new Pool(2);
            pool.initThreads(BakeryBlackAndWhite.class);
            pool.runThreads();

            System.out.println("**********************Next test**********************");
            pool.initThreads(BakeryLock.class);
            pool.runThreads();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
