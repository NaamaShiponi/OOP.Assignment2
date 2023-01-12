import org.jetbrains.annotations.NotNull;
import java.util.concurrent.*;

/**
 * A Task class that extends {@link FutureTask} and implements {@link Comparable}.
 *
 * @author Ben Dabush and Naana Shiponi
 * @version 1.0.0 January 12, 2023
 */
public class Task<T> extends FutureTask<T> implements Comparable<Task<T>> {
    private final TaskType type;

    /**
     * Constructor for the Task class.
     *
     * @param callable The {@link Callable} object that the task will execute.
     * @param type The {@link TaskType} of the task.
     */
    private Task(@NotNull Callable<T> callable,TaskType type) {
        super(callable);
        this.type=type;
    }

    /**
     * Returns the {@link TaskType} of the task.
     *
     * @return The type of the task.
     */
    public TaskType getType() {
        return type;
    }

    /**
     * A factory method for creating a Task object with a {@link Callable} and {@link TaskType}.
     *
     * @param call The {@link Callable} that the task will execute.
     * @param type The {@link TaskType} of the task.
     * @return A new Task object with the given {@link Callable} and {@link TaskType}.
     */
    public static <T> Task<T> createTask(Callable<T> call, TaskType type){
        return new Task<T>(call,type);
    }

    /**
     * A factory method for creating a Task object with a {@link Callable} with {@link TaskType} "OTHER".
     *
     * @param call The {@link Callable} that the task will execute.
     * @return A new Task object with the given {@link Callable} and {@link TaskType} "OTHER".
     */
    public static <T> Task<T> createTask(Callable<T> call){
        return new Task<T>(call,TaskType.OTHER);
    }

    /**
     * Compares this task to another task based on the priority value of their respective {@link TaskType}.
     *
     * @param otherTask The task to compare this task to.
     * @return A negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(@NotNull Task<T> otherTask) {
        return Integer.compare(this.type.getPriorityValue(), otherTask.getType().getPriorityValue());
    }

    /**
     * Indicates whether some other object is "equal to" this task.
     *
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * Returns a hash code value for the task.
     *
     * @return a hash code value for this task.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

//    @Override
//    public int compareTo(@NotNull Task otherTask) {
//        return Integer.compare(this.type.getPriorityValue(),otherTask.getType().getPriorityValue());
//    }
}




//public class Task<T> extends FutureTask<T> implements Callable<T> ,Comparable<Task> {
//
//    private Callable<T> call;
//    private TaskType type;
//    public TaskType getType() {
//        return type;
//    }
//    public Callable<T> getCall() {
//        return call;
//    }
//
//
//    private Task(Callable<T> call,TaskType type){
//        super(call);
//        this.call=call;
//        this.type=type;
//
//    }
//
//    public static Task createTask(Callable call, TaskType type){
//
//        return new Task(call,type);
//    }
//    public static Task createTask(Callable call){
//
//        return new Task(call,TaskType.OTHER);
//    }
//
//
//
//    @Override
//    public T call() throws Exception {
//            return this.call.call();
//    }
//
//    @Override
//    public int compareTo(@NotNull Task otherTask) {
//        return Integer.compare(this.type.getPriorityValue(),otherTask.getType().getPriorityValue());
//    }
//}


