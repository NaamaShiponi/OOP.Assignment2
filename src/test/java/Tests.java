import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.*;

public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);
    @Test
    public void partialTest()
    {
        CustomExecutor customExecutor = new CustomExecutor();

            var task = Task.createTask(() -> {
                int sum = 0;
                for (int i = 1; i <= 10000; i++) {
                    sum += i;
                }
                return sum;
            }, TaskType.COMPUTATIONAL);
            var sumTask = customExecutor.submit(task);
            final int sum;
            try {
                sum = sumTask.get(1, TimeUnit.MILLISECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                throw new RuntimeException(e);
            }
            logger.info(() -> "Sum of 1 through 10 = " + sum);

            Callable<Double> callable1 = () -> {
                return 1000 * Math.pow(1.02, 10);
            };
            Callable<String> callable2 = () -> {
                StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
                return sb.reverse().toString();
            };
            // var is used to infer the declared type automatically
            var priceTask = customExecutor.submit(() -> {
                return 1000 * Math.pow(1.02, 20);
            }, TaskType.COMPUTATIONAL);
            var reverseTask = customExecutor.submit(callable2, TaskType.IO);
            final Double totalPrice;
            final String reversed;
            try {
                totalPrice = priceTask.get();
                reversed = reverseTask.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            logger.info(() -> "Reversed String = " + reversed);
            logger.info(() -> String.valueOf("Total Price = " + totalPrice));
        logger.info(()-> "Current maximum priority = " + customExecutor.getCurrentMax());
        customExecutor.gracefullyTerminate();
    }


    @Test
    public void getCurrentMaxTest(){

        CustomExecutor customExecutor = new CustomExecutor();

        Task<Integer> task1 = Task.createTask(() -> {
            int sum = 0;
            for (int i = 1; i <= 1; i++) {
                sum += i;
            }
            return sum;
        }, TaskType.COMPUTATIONAL);
        Task<Integer> task2 = Task.createTask(() -> {
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        }, TaskType.IO);
        Task<Integer> task3 = Task.createTask(() -> {
            int sum = 0;
            for (int i = 1; i <= 100; i++) {
                sum += i;
            }
            return sum;
        }, TaskType.OTHER);

        System.out.println("task1.getType() = "+ task1.getType().getPriorityValue());
        System.out.println("task2.getType() = "+ task2.getType().getPriorityValue());
        System.out.println("task3.getType() = "+ task3.getType().getPriorityValue());

        for(int i =0 ; i < 10000; i++)
        {
            var sumTask1 = customExecutor.submit(task1);
            var sumTask2 = customExecutor.submit(task2);
            var sumTask3 = customExecutor.submit(task3);


            final int sum;
            try {
                Integer sum1 = sumTask1.get(100, TimeUnit.MILLISECONDS);
                Integer sum2 = sumTask2.get(100, TimeUnit.MILLISECONDS);
                Integer sum3 = sumTask3.get(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                throw new RuntimeException(e);
            }
            if (i%100==0){

                logger.info(()-> "Current maximum priority = " + customExecutor.getCurrentMax());
            }
        }
        customExecutor.gracefullyTerminate();
    }
}