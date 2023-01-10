import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;

public class Task<T> extends FutureTask<T> implements Callable<T>, Comparable<Task<T>> {
    private final TaskType type;

    public Task(@NotNull Callable<T> callable,TaskType type) {
        super(callable);
        this.type=type;

    }

    public TaskType getType() {
        return type;
    }


    public static <T> Task<T> createTask(Callable<T> call, TaskType type){

        return new Task<T>(call,type);
    }
    public static <T> Task<T> createTask(Callable<T> call){

        return new Task<T>(call,TaskType.OTHER);
    }

    @Override
    public T call() throws Exception {
            return this.call();
    }


    @Override
    public int compareTo(@NotNull Task<T> otherTask) {
        if (this.type.getPriorityValue()<otherTask.getType().getPriorityValue()){
            return -1;
        }else if(this.type.getPriorityValue()==otherTask.getType().getPriorityValue()){
            return 0;
        }else return 1;


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


