package q5;

import com.sun.jdi.IntegerValue;

import java.util.concurrent.Callable;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Frequency implements Callable<Integer> {

    public int[] getA() {
        return A;
    }

    public void setA(int[] a) {
        A = a;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    int[] A;
    int x;

    public static int parallelFreq(int x, int[] A, int numThreads) {
        //your implementation goes here, return -1 if the input is not valid.
        Integer sum = 0;
        int length = (A.length > numThreads) ? (A.length / numThreads) : A.length;
        System.out.println("length: " + length);
        for(int i = 0; i < numThreads; i++) {
            int start = i * length;
            int end = start + length;
            Frequency c = new Frequency();
            c.setX(x);
            c.setA(Arrays.copyOfRange(A, start, end));
            //System.out.println("i: " + i + " Start: " + start + " end: " + end);
            try {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                Future<Integer> future = executorService.submit(c);
                sum += future.get().intValue();
            }
            catch (Exception e){
                sum = -1;
                return sum;
            }
            if(end == A.length) break;
        }
        return sum;
    }

    @Override
    public Integer call() throws Exception {
        int count = 0;
        System.out.println("A.length: " + A.length);
        for(int i = 0; i < A.length; i++) {
            System.out.println("A[" + i + "]: " + A[i]);
            if(A[i] == x){
                count++;
            }
        }
        return count;
    }
}