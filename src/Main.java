import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Task> allTask = new ArrayList<>();
        List<Task> doneTask = new ArrayList<>();
        List<Task> toDoTask = new ArrayList<>();
        List<Task> inProgressTask = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean state = true;
        while (state) {
            System.out.print("task-cli: ");
            String option = scanner.nextLine();
            switch (option) {
                case "add":
                    addTasks(scanner, allTask, toDoTask, doneTask, inProgressTask);

                    break;
                case "delete":
                    removeTask(scanner, allTask, toDoTask, doneTask, inProgressTask);

                    break;
                case "mark-in-progress":
                    markInProgress(scanner, allTask, toDoTask, doneTask, inProgressTask);
                    break;
                case "mark-done":
                    markDone(scanner, allTask, toDoTask, doneTask, inProgressTask);
                    break;
                case "list":
                    showAllTask(allTask);
                    break;
                case "list done":
                    showDoneTask(doneTask);
                    break;
                case "list todo":
                    showToDoTask(toDoTask);
                    break;
                case "list in-progress":
                    showInProgressTask(inProgressTask);
                    break;
                case "exit":
                    state = false;
                    break;
                default:
                    System.out.println("none option correct");
            }
        }
    }

    public static void saveTasksToJsonFile(List<Task> allTasks, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write("[\n");
            for (int i = 0; i < allTasks.size(); i++) {
                fileWriter.write(allTasks.get(i).toJson());
                if (i < allTasks.size() - 1) {
                    fileWriter.write(",\n");
                }
            }
            fileWriter.write("\n]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addTasks(Scanner scanner, List<Task> allTask,
                                List<Task> toDoTask, List<Task> doneTask, List<Task> inProgressTask) {

        int ID = 1;
        boolean check = true;
        int i = 0;
        while (check) {
            //System.out.println("\nEnter details for task " + (i + 1));
            System.out.print("Description: ");
            String description = scanner.nextLine();
            //System.out.print("Status (todo/in-progress/done): ");
            String status = "todo";
            LocalDateTime now = LocalDateTime.now();
            Task task = new Task(ID, description, status, now, now);
            allTask.add(task);
            toDoTask.add(task);
//            if (status.equals("todo")) {
//                toDoTask.add(task);
//            } else if (status.equals("done")) {
//                doneTask.add(task);
//            } else if (status.equals("in-progress")) {
//                inProgressTask.add(task);
//            } else {
//                System.out.println("check the input status");
//            }
            boolean found = false;
            for (int s = 0; s < allTask.size(); s++) {
                if (allTask.get(s).getId() == ID) {
                    allTask.set(s, task);
                    found = true;
                    System.out.println("Existing task with ID " + ID + " was updated.");
                    break;
                }
            }
            if (!found) {
                allTask.add(task);
                System.out.println("New task added.");
            }
            System.out.println("if you want add more project write yes else write any thing");
            String addProject = scanner.nextLine();
            if (!(addProject.equals("yes"))) {
                break;
            }
            i++;
            ID++;
//        }
//        try (FileWriter fileWriter = new FileWriter("Task.json")) {
//            fileWriter.write("[\n");
//            for (int j = 0; j < allTask.size(); j++) {
//                fileWriter.write(allTask.get(j).toJson());
//                if (j < allTask.size() - 1)
//                    fileWriter.write(",\n");
//            }
//            fileWriter.write("\n]");
//        } catch (IOException ioException) {
//            ioException.printStackTrace();
//        }
        }
        saveTasksToJsonFile(allTask, "Task.json");
    }

    public static void removeTask(Scanner scanner, List<Task> allTask,
                                  List<Task> toDoTask, List<Task> doneTask, List<Task> inProgressTask) {
        int remove = scanner.nextInt();
        for (int i = 0; i < allTask.size(); i++) {
            if (remove == allTask.get(i).getId()) {
                if (allTask.get(i).getStatus().equals("todo")) {
                    allTask.remove(i);
                    toDoTask.remove(i);
                } else if (allTask.get(i).getStatus().equals("done")) {
                    allTask.remove(i);
                    doneTask.remove(i);
                } else if (allTask.get(i).getStatus().equals("in-progress")) {
                    allTask.remove(i);
                    inProgressTask.remove(i);
                } else {
                    System.out.println("no correct status");
                }
            } else {
                System.out.println("task not found");
            }
//
//            try (FileWriter fileWriter = new FileWriter("Task.json")) {
//                fileWriter.write("[\n");
//                for (int j = 0; j < allTask.size(); j++) {
//                    fileWriter.write(allTask.get(j).toJson());
//                    if (j < allTask.size() - 1)
//                        fileWriter.write(",\n");
//                }
//                fileWriter.write("\n]");
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
        }
        saveTasksToJsonFile(allTask, "Task.json");

    }

    public static void showAllTask(List<Task> allTask) {
        for (Task t : allTask) {
            System.out.println(t.toString());
        }
    }

    public static void showDoneTask(List<Task> doneTask) {
        for (Task t : doneTask) {
            System.out.println(t.toString());
        }
    }

    public static void showToDoTask(List<Task> toDoTask) {
        for (Task t : toDoTask) {
            System.out.println(t.toString());
        }
    }

    public static void showInProgressTask(List<Task> inProgress) {
        for (Task t : inProgress) {
            System.out.println(t.toString());
        }
    }

    public static void markDone(Scanner scanner, List<Task> allTask,
                                List<Task> toDoTask, List<Task> doneTask, List<Task> inProgressTask) {
        System.out.println("ID for task");
        int ID = scanner.nextInt();
        boolean found = false;
        for (Task t : allTask) {
            if (ID == t.getId()) {
                t.setStatus("Done");
                t.setUpdateAt(LocalDateTime.now());
                doneTask.add(t);
                found = true;
                toDoTask.remove(t);
                inProgressTask.remove(t);
            }
            if (!found) {
                System.out.println("not found task");
            }
        }
        saveTasksToJsonFile(allTask, "Task.json");
    }

    public static void markInProgress(Scanner scanner, List<Task> allTask,
                                      List<Task> toDoTask, List<Task> doneTask, List<Task> inProgressTask) {
        System.out.println("ID for task");
        int ID = scanner.nextInt();
        boolean found = false;
        for (Task t : allTask) {
            if (ID == t.getId()) {
                t.setStatus("in-progress");
                t.setUpdateAt(LocalDateTime.now());
                inProgressTask.add(t);
                toDoTask.remove(t);
                found = true;
                doneTask.remove(t);
            }
        }
        if (!found) {
            System.out.println("not found task");
        }
        saveTasksToJsonFile(allTask, "Task.json");
    }
}
