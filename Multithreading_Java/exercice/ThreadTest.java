import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadTest {
	public static void main(String[] args) throws InterruptedException{
		SyncronizedBuffer buffer = new SyncronizedBuffer();
		Producer producer = new Producer(buffer);
		Consumer consumer = new Consumer(buffer);
		
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute(producer);
		executor.execute(consumer);
		
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);
		
	}
}
