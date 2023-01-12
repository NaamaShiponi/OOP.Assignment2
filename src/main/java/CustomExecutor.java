import org.jetbrains.annotations.NotNull;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.*;

/**
 * A custom implementation of the {@link ThreadPoolExecutor} class.
 * This class uses a {@link PriorityBlockingQueue} to manage the execution order of tasks, with higher priority tasks being executed first.
 *
 *  @author Ben Dabush and Naana Shiponi
 *  @version 1.0.0 January 12, 2023
 */
public class CustomExecutor extends ThreadPoolExecutor{

    /**
     * This array stores the count of number of tasks submitted for each priority value.
     */
    int[] priorityTask = new int[10];

    /**
     * The number of cores available on the system.
     */
    private static final int numOfCores= Runtime.getRuntime().availableProcessors();

    /**
     * Creates a new CustomExecutor.
     */
    public CustomExecutor(){
        super(numOfCores/2,numOfCores-1,
                300, TimeUnit.MILLISECONDS,new PriorityBlockingQueue<>());
    }

    /**
     * Submits a task for execution and returns a Future representing that task.
     *
     * @param task The task to be submitted.
     * @return A Future representing the task.
     * @throws NullPointerException If the task is null.
     */
        public <T> Future<T> submit(Task<T> task){
//        return super.submit(task.getCall());
            if (task == null) throw new NullPointerException();
            priorityTask[task.getType().getPriorityValue()-1]++;
            execute(task);
            return task;
    }

    /**
     * Submits a task for execution with the specified task type and returns a Future representing that task.
     *
     * @param call The callable that the task will execute.
     * @param type The task type of the task.
     * @return A Future representing the task.
     */
    public <T> Future<T> submit(Callable<T> call, TaskType type) {
        Task<T> task= Task.createTask(call,type);
        return submit(task);
    }

    /**
     * Submits a task for execution with the task type "OTHER" and returns a Future representing that task.
     *
     * @param call The callable that the task will execute.
     * @return A Future representing the task.
     */
    public <T> Future<T> submit(@NotNull Callable<T> call) {
        Task<T> task= Task.createTask(call);
        return submit(task);
    }

    /**
     * Initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks will be accepted.
     *
     */
    public void gracefullyTerminate(){
        super.shutdown();
    }
    /**
     * Returns the current maximum priority value of the task that has been submitted.
     *
     * @return  priority value of the task that has been submitted
     */
    public int getCurrentMax(){
        int priority = 0;
        while ((priorityTask[priority] != 0) && (priority < 10)){
            priority++;
        }
        return priority+1;
    }

    /**
     * Method that is called before a task is executed.
     * It is responsible for decrementing the count of tasks for the corresponding priority value.
     *
     * @param t the thread that will run task
     * @param r the task that will be executed
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        if(r instanceof Task<?> task)
        {
            priorityTask[task.getType().getPriorityValue()-1]--;
        }
    }
}


