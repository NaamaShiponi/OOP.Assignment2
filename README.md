# OOP.Assignment2
## Overview
This code provides a Task class and a CustomExecutor class that enable the execution of tasks with different
priorities. The Task class extends the FutureTask class and implements the Comparable interface to allow for sorting
of tasks based on their priority. The CustomExecutor class extends the ThreadPoolExecutor class and provides methods
for submitting tasks for execution, as well as for gracefully shutting down the executor.

### Code Structure
This codebase contains two main classes: Task and CustomExecutor, and one enumeration TaskType.

The Task class is a generic class that wraps a Callable object and assigns a priority to it using the enumeration
TaskType. It also implements Comparable interface, so that it can be sorted according to priority.

The CustomExecutor class extends the ThreadPoolExecutor class, and it provides a way to submit Task objects
and retrieve the current max priority of the tasks in the queue.

The TaskType enumeration defines the different priorities that a task can have.
it defines 3 different priorities: Computational, IO-Bound and Unknown.
It is implemented in such a way that it allows assigning priority to the task in question and also allows
setting new priority values to the task.

#### Interfaces
Comparable<Task<T>>: the Task class implements this interface, this allows sorting the tasks according to
their priority.

#### Classes
Task<T>: This class is a wrapper around a Callable object, it allows giving a priority to the task by using
         TaskType enum.
TaskType: This enum defines the different priorities that a task can have.
CustomExecutor : This class is an implementation of the ThreadPoolExecutor, it allows submitting tasks with priority,
                 you can get the current max priority of the tasks in the queue.

## Usage

### Creating a Task
To create a new task, you can use the createTask method in the Task class.
This method takes a Callable object and a TaskType object as arguments and returns a new Task object.
The Callable object represents the task that should be executed, and the TaskType object represents
the priority of the task. If no TaskType is provided, the task will be given a default TaskType.OTHER priority.

#### An example of the implementation of Creating a Task
// create a new task with a high priority

Callable<String> callable = () -> "Hello World";

TaskType highPriority = TaskType.HIGH;

Task<String> highPriorityTask = Task.createTask(callable, highPriority);


// create a new task with a default priority

Callable<Integer> callable = () -> 42;

Task<Integer> defaultPriorityTask = Task.createTask(callable);


### Executing a Task
To execute a task, you can use the submit method in the CustomExecutor class.
This method takes a Task object as an argument and adds it to the priority queue for execution.

#### An example of the implementation of Executing a Task

CustomExecutor executor = new CustomExecutor();

// submit a task for execution

Future<String> highPriorityFuture = executor.submit(highPriorityTask);

Future<Integer> defaultPriorityFuture = executor.submit(defaultPriorityTask);


### Gracefully Terminating the Executor
To gracefully terminate the executor, you can use the gracefullyTerminate method in the CustomExecutor class.
This method will wait for all currently running tasks to complete before shutting down the executor.

#### An example of the implementation of Gracefully Terminating the Executor

executor.gracefullyTerminate();


### Retrieving the Current Maximum Priority
You can use the getCurrentMax() method in the CustomExecutor class to retrieve the current maximum priority.

#### An example of the implementation of Retrieving the Current Maximum Priority

CustomExecutor executor = new CustomExecutor();

int currentMaxPriority = executor.getCurrentMax();

### Notes
1. The code above is implemented using the factory method pattern where the "createTask" method in the Task class
is considered as factory method, it returns a new instance of the Task class with the given parameters.
Additionally, the "submit" method in the CustomExecutor class also uses the factory method pattern by using
the "createTask" method to create a new Task object with the given parameters before submitting it for execution.

2. The code also tries to implement SOLID principles.
Single Responsibility Principle (SRP): The Task class has a single responsibility of wrapping a Callable object and
assigning a priority to it. The CustomExecutor class has a single responsibility of managing a pool of worker threads
and providing a way to submit tasks with priority.

Open/Closed Principle (OCP): The Task class is open for extension but closed for modification.
It can be extended by other classes to add new functionality, but the existing methods of the class
should not be modified. CustomExecutor class is open for extension as well, it's possible to extend the class to add
new functionality without modifying the existing one.

Liskov Substitution Principle (LSP): The CustomExecutor class is designed to accept any class that implements
the Runnable interface, making it possible to substitute a Task object with any other Runnable object.

Interface Segregation Principle (ISP): The Task class only implements the methods that are necessary to its
functionality, it does not have unnecessary methods.

Dependency Inversion Principle (DIP): The Task class is dependent on the Callable interface and does not depend on any
concrete implementation of Callable. This allows the code to be more flexible and testable, and helps to avoid
tightly-coupled code.
