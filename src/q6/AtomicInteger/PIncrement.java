package q6.AtomicInteger;

import java.util.ArrayList;

import java.util.concurrent.atomic.AtomicInteger;

public class PIncrement implements Runnable{

    static AtomicInteger count;

    public static int getM() {
        return m;
    }

    public static void setM(int m) {
        PIncrement.m = m;
    }

    static int m;

    public static AtomicInteger getCount() {
        return count;
    }

    public static void setCount(AtomicInteger count) {
        PIncrement.count = count;
    }

    public static int getN() {
        return n;
    }

    public static void setN(int n) {
        PIncrement.n = n;
    }

    static int n;

    public static int parallelIncrement(int c, int numThreads){
        // your implementation goes here.
        setN(numThreads);
        setCount(new AtomicInteger(c));
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
        return count.get();
    }

    @Override
    public void run() {
        for(int i = 0; i < (m/n); i++){
            int before = count.get();
            boolean success = count.compareAndSet(before, before+1);
            if(!success) i--;
        }
    }
}
