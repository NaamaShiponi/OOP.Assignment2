import org.jetbrains.annotations.NotNull;
import java.util.concurrent.*;

/**

 * Class representing a task that can be executed and has a specific type.
 * Extends {@link FutureTask} and implements {@link Comparable}.
 *
 * The task's type is specified by an instance of the {@link TaskType} enumeration,
 * which has a priority value that determines the task's relative importance.
 * Tasks can be created using the {@link #createTask(Callable, TaskType)} or
 * {@link #createTask(Callable)} factory methods,
 * where the former allows specifying a specific task type, and the latter defaults to the {@link TaskType#OTHER} type.
 *
 * The {@link #compareTo(Task)} method compares the tasks based on their priority values,
 * and the {@link #getType()} method retrieves the task's current type.
 *
 * The {@link #equals(Object)}, {@link #hashCode()} and {@link #toString()} methods are overridden
 * from the {@link FutureTask} and {@link Object} classes
 * @param <T> the type of the result returned by the task's {@link Callable}
 *
 * @author Ben Dabush and Naana Shiponi
 * @version 1.0.0 January 12, 2023
 */
public class Task<T> extends FutureTask<T> implements Comparable<Task<T>> {
    private final TaskType type;
    /**
     * Constructor for the Task class, which takes a {@link Callable} and a {@link TaskType} as arguments.
     * @param callable the {@link Callable} that is used to compute the value of the task
     * @param type the type of the task, specified as an instance of the {@link TaskType} enumeration
     */
    private Task(@NotNull Callable<T> callable, TaskType type) {
        super(callable);
        this.type = type;
    }
    /**
     * Get the current task type of the task.
     * @return the current task type of the task
     */
    public TaskType getType() {
        return type;
    }

    /**
     * Factory method that creates and returns a new task with the given callable and task type.
     * @param call the {@link Callable} that is used to compute the value of the task
     * @param type the type of the task, specified as an instance of the {@link TaskType} enumeration
     * @param <T> the type of the result returned by the task's {@link Callable}
     * @return a new task
     */
    public static <T> Task<T> createTask(Callable<T> call, TaskType type) {
        return new Task<T>(call, type);
    }

    /**
     * Factory method that creates and returns a new task with the given callable and default task
     * type {@link TaskType#OTHER}.
     * @param call the {@link Callable} that is used to compute the value of the task
     * @param <T> the type of the result returned by the task's {@link Callable}
     * @return a new task
     */
    public static <T> Task<T> createTask(Callable<T> call) {
        return new Task<T>(call, TaskType.OTHER);
    }

    /**
    * Compares this task to another task based on their priority values.
    @param otherTask the other task to be compared to

    @return a negative integer, zero, or a positive integer as this task's priority value is
    less than, equal to, or greater than the other task's priority value.
    */
    @Override
    public int compareTo(@NotNull Task<T> otherTask) {
        return Integer.compare(this.type.getPriorityValue(), otherTask.getType().getPriorityValue());
    }

    /**
     Indicates whether some other object is "equal to" this one.
     @param otherTask the reference object with which to compare.
     @return true if this object is the same as the otherTask argument; false otherwise.
     */
    @Override
    public boolean equals(Object otherTask) {
        return super.equals(otherTask);
    }

    /**
     Returns a hash code value for the object.
     @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     Returns a string representation of the object.
     @return a string representation of the object.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}

//
///**
// * A Task class that extends {@link FutureTask} and implements {@link Comparable}.
// *
// * @author Ben Dabush and Naana Shiponi
// * @version 1.0.0 January 12, 2023
// */
//public class Task<T> extends FutureTask<T> implements Comparable<Task<T>> {
//    private final TaskType type;
//
//    /**
//     * Constructor for the Task class.
//     *
//     * @param callable The {@link Callable} object that the task will execute.
//     * @param type     The {@link TaskType} of the task.
//     */
//    private Task(@NotNull Callable<T> callable, TaskType type) {
//        super(callable);
//        this.type = type;
//    }
//
//    /**
//     * Returns the {@link TaskType} of the task.
//     *
//     * @return The type of the task.
//     */
//    public TaskType getType() {
//        return type;
//    }
//
//    /**
//     * A factory method for creating a Task object with a {@link Callable} and {@link TaskType}.
//     *
//     * @param call The {@link Callable} that the task will execute.
//     * @param type The {@link TaskType} of the task.
//     * @return A new Task object with the given {@link Callable} and {@link TaskType}.
//     */
//    public static <T> Task<T> createTask(Callable<T> call, TaskType type) {
//        return new Task<T>(call, type);
//    }
//
//    /**
//     * A factory method for creating a Task object with a {@link Callable} with {@link TaskType} "OTHER".
//     *
//     * @param call The {@link Callable} that the task will execute.
//     * @return A new Task object with the given {@link Callable} and {@link TaskType} "OTHER".
//     */
//    public static <T> Task<T> createTask(Callable<T> call) {
//        return new Task<T>(call, TaskType.OTHER);
//    }
//
//    /**
//     * Compares this task to another task based on the priority value of their respective {@link TaskType}.
//     *
//     * @param otherTask The task to compare this task to.
//     * @return A negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
//     */
//    @Override
//    public int compareTo(@NotNull Task<T> otherTask) {
//        return Integer.compare(this.type.getPriorityValue(), otherTask.getType().getPriorityValue());
//    }
//
//    /**
//     * Indicates whether some other object is "equal to" this task.
//     *
//     * @param otherTask the reference object with which to compare.
//     * @return true if this object is the same as the obj argument; false otherwise.
//     */
//    @Override
//    public boolean equals(Object otherTask) {
//        return super.equals(otherTask);
//    }
//
//    /**
//     * Returns a hash code value for the task.
//     *
//     * @return a hash code value for this task.
//     */
//    @Override
//    public int hashCode() {
//        return super.hashCode();
//    }
//
//    @Override
//    public String toString() {
//        return super.toString();
//    }
//}
//
