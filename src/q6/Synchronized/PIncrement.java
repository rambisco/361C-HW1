package q6.Synchronized;

import java.util.ArrayList;

public class PIncrement implements Runnable{

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

    public static int getN() {
        return n;
    }

    public static void setN(int n) {
        PIncrement.n = n;
    }

    static Integer count;

    static final Object lock = new Object();

    static int m;

    static int n;

    public static int parallelIncrement(int c, int numThreads){
        setCount(c);
        setM(1200000);
        setN(numThreads);
        ArrayList<Thread> threads = new ArrayList<>();
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
        this.increment();
    }

    public void increment() {
//        System.out.println(this);
//        System.out.println("count: " + count + " is being incremented by: " + this);
        synchronized (lock) {
            for (int i = 0; i < (m/n); i++) {
                count++;
            }
        }
    }

}
