import com.dreamteam.lab02.benchmark.Pool;
import com.dreamteam.lab02.locks.BakeryBlackAndWhite;
import com.dreamteam.lab02.locks.BakeryLock;
import com.dreamteam.lab02.locks.DekkersLock;
import com.dreamteam.lab02.locks.SpinLock;


public class PoolProgram {
    private static final int POOL_SIZE = 10;
    public static void main(String[] args) {
        testNThreads();
    }
    /**
     * Test for {@code POOL_SIZE} thread
     */
    public static void testNThreads() {
        Pool pool = new Pool(POOL_SIZE);
        System.out.println("Starting...");
        try {
            pool.testLock(BakeryLock.class);
            pool.testLock(BakeryBlackAndWhite.class);
            pool.testLock(SpinLock.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void testTwoThreads() {
        Pool pool = new Pool(2);
        System.out.println("Starting...");
        try {
            pool.testLock(DekkersLock.class);
            pool.testLock(BakeryLock.class);
            pool.testLock(BakeryBlackAndWhite.class);
            pool.testLock(SpinLock.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }




}
