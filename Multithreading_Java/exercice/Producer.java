import java.security.SecureRandom;

public class Producer implements Runnable{
	private final Buffer sharedLocation;
	private final SecureRandom generator = new SecureRandom();
	public Producer(Buffer sharedLocation) {
		this.sharedLocation =  sharedLocation;
	}
	@Override
	public void run() {
		int sum = 0;
		for(int i = 0; i<=10; i++) {
			try {
				Thread.sleep(generator.nextInt(1000));//for only demonstration purposes
				sharedLocation.blockingPut(i);
				sum+=i;
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		System.out.println("Thread 1 finished. Sum: "+sum); //for only demonstartion purposes
	}

	
}
