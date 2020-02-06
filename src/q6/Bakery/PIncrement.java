package q6.Bakery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class PIncrement implements Runnable{

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        PIncrement.count = count;
    }

    public static int getN() {
        return n;
    }

    public static void setN(int n) {
        PIncrement.n = n;
    }

    public static int getM() {
        return m;
    }

    public static void setM(int m) {
        PIncrement.m = m;
    }

    static int count;
    static int n;
    static int m;
    static HashMap<Long, Integer> threads;
    static ArrayList<Thread> ts;
    static BakeryLock lock;


    public static int parallelIncrement(int c, int numThreads){

        setCount(c);
        setN(numThreads);
        setM(1200000);
        lock = new BakeryLock(numThreads);
        threads = new HashMap<>();
        ts = new ArrayList<>();

        for(int i = 0; i < numThreads; i++){
            Thread t = new Thread(new PIncrement());
            threads.put(t.getId(), i);
            ts.add(t);
            t.start();
        }
        for(Thread t : ts){
            try {
                t.join();
            }
            catch(Exception e){
                System.out.println("bad stuff");
            }
        }


        // your implementation goes here

        return count;
    }

    @Override
    public void run() {
        int pid = threads.get(Thread.currentThread().getId());
        System.out.println("index: " + pid);
        for(int i = 0; i < (m/n); i++){
            lock.lock(pid);
            setCount(getCount()+1);
            lock.unlock(pid);
        }
    }
}
