import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SyncronizedBuffer implements Buffer{
	
	private Lock lock = new ReentrantLock();
	private Condition canWrite = lock.newCondition();//produces will wait when there is no space left in the buffer to write
	private Condition canRead = lock.newCondition();//consumer will wait when there is no element in the buffer to read
	private int occupiedCells = 0;//stores the number of cells written to buffer but not read yet
	private int writeIndex = 0;
	private int readIndex = 0;
	private int[] buffer = {-1,-1,-1} ;
	@Override
	public void blockingPut(int value) throws InterruptedException {
		lock.lock();
		try {
		while(occupiedCells == buffer.length) {//there is no space to write
			System.out.println("Producer tries to write. Buffer is full.");
			canWrite.await();//produces waits until condition is satisfied
		}
		buffer[writeIndex] = value;
		writeIndex = (writeIndex+1)%buffer.length; //this is a circular buffer so writing index is determined by taking mode in buffer length
		System.out.println("Produces wrote "+value);
		occupiedCells++;
		canRead.signalAll();//there is a possibility that consumer waits for a value to read so we inform all threads waiting on this condition
		} finally {//always unlock the lock in the finally to ensure that it is unlocked otherwise starvation or deadlock can occur
			lock.unlock();
		}
	}
	@Override
	public int blockingGet() throws InterruptedException {
		lock.lock();
		try {
			while(occupiedCells==0) {//no values to read
				System.out.println("Consumer tries to read. Buffer is empty");
				canRead.await();
			}
			int value = buffer[readIndex];
			readIndex = (readIndex+1)%buffer.length;
			System.out.println("Consumer reads "+value);
			occupiedCells--;
			canWrite.signalAll();//notify produces waiting for a free place in buffer
			return value;
		} finally {
			lock.unlock();
		}
		
		
	}
	public String toString() {
		return Arrays.toString(buffer);
	}

}
