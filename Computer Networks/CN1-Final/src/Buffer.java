import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Buffer {
    private ArrayList<Frame> frames;
    private static ReentrantReadWriteLock lock;

    public Buffer(){
        frames = new ArrayList<Frame>();
        lock = new ReentrantReadWriteLock();
    }

    public void push(Frame frame){
        lock.writeLock().lock();
        frames.add(frame);
        lock.writeLock().unlock();
    }

    public Frame read(int index){
        Frame frame;
        lock.readLock().lock();
        try {
            frame = frames.get(index);
        } catch (IndexOutOfBoundsException e) {
            frame = null;
        }
        lock.readLock().unlock();
        return frame;
    }
}
