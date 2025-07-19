import java.awt.*;
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
        //    LocalDateTime now = LocalDateTime.now();
        Scanner scanner = new Scanner(System.in);
        boolean check = true;
        int i = 0;
        while (check) {
            System.out.println("\nEnter details for task " + (i + 1));

            System.out.print("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Description: ");
            String description = scanner.nextLine();

            System.out.print("Status (todo/in-progress/done): ");
            String status = scanner.nextLine();

            LocalDateTime now = LocalDateTime.now();

            Task task = new Task(id, description, status, now, now);
            allTask.add(task);
            if (status.equals("todo")) {
                toDoTask.add(task);
            } else if (status.equals("done")) {
                doneTask.add(task);
            } else if (status.equals("in-progress")) {
                inProgressTask.add(task);
            } else {
                System.out.println("check the input status");
            }

            boolean found = false;
            for (int s = 0; s < allTask.size(); s++) {
                if (allTask.get(s).getId() == id) {
                    allTask.set(s, task);
                    found = true;
                    System.out.println("Existing task with ID " + id + " was updated.");
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
        }


        try (FileWriter fileWriter = new FileWriter("Task.json")) {
            fileWriter.write("[\n");
            for (int j = 0; j < allTask.size(); j++) {
                fileWriter.write(allTask.get(j).toJson());
                if (j < allTask.size() - 1)
                    fileWriter.write(",\n");
            }
            fileWriter.write("\n]");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}