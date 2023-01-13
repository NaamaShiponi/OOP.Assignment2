import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.concurrent.*;

/**
  * CustomExecutor is a thread pool executor implementation of the {@link ThreadPoolExecutor} class.
  * This class uses a {@link PriorityBlockingQueue} to manage the execution order of tasks,
  * Each task is associated with a priority determined by the TaskType enum. Tasks with higher priority are executed
  * before tasks with lower priority.
  *
  *  @author Ben Dabush and Naana Shiponi
  *  @version 1.0.0 January 12, 2023
  *
  */
public class CustomExecutor extends ThreadPoolExecutor{

    /**
     * An array to store the count of tasks for each priority
     */
    private volatile int[] priorityTaskArray = new int[10];

    /**
     * The number of cores of the machine
     */
    private static final int numOfCores = Runtime.getRuntime().availableProcessors();

    /**
     * Constructor of CustomExecutor
     *
     */
    public CustomExecutor(){
        super(numOfCores/2,numOfCores-1,
                300, TimeUnit.MILLISECONDS,new PriorityBlockingQueue<>(10));
    }

    /**
     * Submits the given task for execution and returns a Future representing that task.
     *
     * @param task the task to submit
     * @return a Future representing pending completion of the task
     * @throws NullPointerException if the task is null
     */
    public <T> Future<T> submit(Task<T> task){
        if (task == null) throw new NullPointerException();
        changePriorityTaskArray(1, task.getType().getPriorityValue()-1);
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
     * Submits the given callable task for execution and returns a Future representing that task,
     * with the default task type (OTHER).
     *
     * @param call the callable task
     * @return a Future representing pending completion of the task
     */
    public <T> Future<T> submit(@NotNull Callable<T> call) {
        Task<T> task= Task.createTask(call);
        return submit(task);
    }

    /**
     * Initiates an orderly shutdown in which previously submitted tasks are executed,
     * but no new tasks will be accepted.
     */
    public void gracefullyTerminate(){
        super.shutdown();
    }

    /**
     * Returns the current maximum priority of the task in the queue.
     * @return the current maximum priority of the task
     */
    public synchronized int getCurrentMax(){
        return changePriorityTaskArray(0, 0);
    }

    /**
     * Method that is called before a task is executed.
     * It is responsible for decrementing the count of tasks for the corresponding priority value.
     *
     * @param t the thread on which the task is about to execute
     * @param r the task that will be executed
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        if(r instanceof Task<?> task)
        {
            changePriorityTaskArray(2, task.getType().getPriorityValue()-1);
        }
    }

    /**
     * A private helper method that changes the priorityTaskArray depending on the whichFunction parameter
     * @param whichFunction 0: get the current max priority,
     *                      1: increment the priority count,
     *                      2: decrement the priority count
     * @param priorityTask the priority of the task
     * @return -1 if whichFunction is 1 or 2, otherwise the max priority in the array
     */
    private int changePriorityTaskArray(int whichFunction, int priorityTask)
    {
        synchronized (Task.class) {
            if (whichFunction == 0) {
                synchronized (Task.class) {
                    for (int priority = 0; priority < 10; priority++) {
                        if (0 < priorityTaskArray[priority]) {
                            return priority + 1;
                        }
                    }
                }
            } else if (whichFunction == 1) {
                synchronized (Task.class) {
                    priorityTaskArray[priorityTask]++;
                }
            } else {
                synchronized (Task.class) {
                    priorityTaskArray[priorityTask]--;
                }
            }
//            System.out.println(Arrays.toString(priorityTaskArray));
            return -1;
        }
    }
}

