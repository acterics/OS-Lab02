import com.dreamteam.lab02.benchmark.Pool;


public class PoolProgram {
    public static void main(String[] args) {
        System.out.println("Starting...");
        try {
            Pool pool = new Pool(10);
            pool.runThreads();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
