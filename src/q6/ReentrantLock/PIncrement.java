package q6.ReentrantLock;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class PIncrement implements Runnable{

    public static int getN() {
        return n;
    }

    public static void setN(int n) {
        PIncrement.n = n;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        PIncrement.count = count;
    }

    public static int getM() {
        return m;
    }

    public static void setM(int m) {
        PIncrement.m = m;
    }

    static int n;
    static int count;
    static int m;
    static ReentrantLock lock;

    public static int parallelIncrement(int c, int numThreads){
        // your implementation goes here.
        lock = new ReentrantLock();
        setN(numThreads);
        setCount(c);
        setM(1200000);

        ArrayList<Thread> threads = new ArrayList<Thread>();
        for(int i = 0; i < numThreads; i++){
            Thread t = new Thread(new PIncrement());
            threads.add(t);
            t.start();
        }
        for(Thread t : threads){
            try {
                t.join();
            }
            catch(Exception e){
                System.out.println("bad stuff");
            }
        }

        return count;
    }

    @Override
    public void run() {
        for(int i = 0; i < (m/n); i++){
            lock.lock();
            count++;
            lock.unlock();
        }
    }
}
