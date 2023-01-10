import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.*;


public class CustomExecutor extends ThreadPoolExecutor{

    public static BlockingQueue<Runnable> pbq=new PriorityBlockingQueue(5, Comparator.reverseOrder());

    private static final int numOfCores= Runtime.getRuntime().availableProcessors();
    public CustomExecutor(){
        super(numOfCores/2,numOfCores-1,
                300, TimeUnit.MILLISECONDS,pbq);
    }
        public <T> Future<T> submit(Task<T> task){
//        return super.submit(task.getCall());
            if (task == null) throw new NullPointerException();
            execute(task);
            return task;


    }


    public <T> Future<T> submit(Callable<T> call, TaskType type) {
        Task task= Task.createTask(call,type);
        return submit(task);
    }

    public <T> Future<T> submit(Callable<T> call) {
        Task<T> task= Task.createTask(call);
        return submit(task);
    }


    public void gracefullyTerminate(){
        super.shutdown();
    }

    public int getCurrentMax(){
        return 0;
    }



}


