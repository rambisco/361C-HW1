package q6.Bakery;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BakeryLock implements Lock {

    public static int getN() {
        return n;
    }

    public static void setN(int n) {
        BakeryLock.n = n;
    }

    public static AtomicBoolean[] getChoosing() {
        return choosing;
    }

    public static void setChoosing(AtomicBoolean[] choosing) {
        BakeryLock.choosing = choosing;
    }

    public static AtomicInteger[] getNumber() {
        return number;
    }

    public static void setNumber(AtomicInteger[] number) {
        BakeryLock.number = number;
    }

    static int n;
    static AtomicBoolean[] choosing;
    static AtomicInteger[] number;

    public BakeryLock(int numThreads){
        // your implementation goes here.
        setN(numThreads);
        setChoosing(new AtomicBoolean[n]);
        setNumber(new AtomicInteger[n]);

        for(int i = 0; i < n; i++) {
            choosing[i] = new AtomicBoolean(false);
            number[i] = new AtomicInteger(0);
        }
    }

    @Override
    public void lock(int pid) {
        choosing[pid].set(true);
        for(int i = 0; i < n; i++) {
            if (number[i].get() > number[pid].get()) {
                number[pid].set(number[i].get());
            }
        }
        number[pid].set(number[pid].get()+1);
        choosing[pid].set(false);
        //step 2
        for(int j = 0; j < n; j++){
            while(choosing[j].get()){};
            while((number[j].get() != 0) &&
                    (number[j].get() < number[pid].get()) ||
                    (number[j].get() == number[pid].get()) && (j < pid)){

                    }
        }
    }

    @Override
    public void unlock(int pid) {
        number[pid].set(0);
    }
}
