import java.security.SecureRandom;

public class Consumer implements Runnable{
	private final SecureRandom generator = new SecureRandom();
	private final Buffer sharedBuffer;
	
	public Consumer(Buffer sharedBuffer) {
		this.sharedBuffer = sharedBuffer;
	}
	@Override
	public void run() {
		int sum = 0;
		for(int i = 0; i<=10; i++) {
			try {
				Thread.sleep(generator.nextInt(1000));//for demonstration purposes
				int value = sharedBuffer.blockingGet();
				sum+=value;
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				
			}
		}
		System.out.println("Thread 2 finished. Sum: "+sum);//for demonstration purposes
	}
	

}
