import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<Volunteer> volunteers = new ArrayList<>();
    private static ArrayList<VolunteerTask> tasks = new ArrayList<>();
    private static ArrayList<VolunteerApplication> applications = new ArrayList<>();

    private static Scanner input = new Scanner(System.in);

    private static int volunteerCounter = 1;
    private static int taskCounter = 1;
    private static int applicationCounter = 1;

    public static void main(String[] args) {
        int choice;

        System.out.println("Smart System for Redirecting Human Surplus");

        do {
            printMenu();
            choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    createVolunteerProfile();
                    break;
                case 2:
                    loadTasksFromFile();
                    break;
                case 3:
                    postVolunteerTask();
                    break;
                case 4:
                    matchVolunteerWithTasks();
                    break;
                case 5:
                    applyForTask();
                    break;
                case 6:
                    displayAllData();
                    break;
                case 7:
                    System.out.println("Exiting system...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 7);

        input.close();
    }

    private static void printMenu() {
        System.out.println("\n==============================");
        System.out.println("1. Create Volunteer Profile");
        System.out.println("2. Load Volunteer Tasks From File");
        System.out.println("3. Post Volunteer Task Manually");
        System.out.println("4. Match Volunteer With Tasks");
        System.out.println("5. Apply For Task");
        System.out.println("6. Display All Data");
        System.out.println("7. Exit");
        System.out.println("==============================");
    }

    // Core Function 1
    private static void createVolunteerProfile() {
        System.out.println("\n--- Create Volunteer Profile ---");

        input.nextLine();

        System.out.print("Enter volunteer name: ");
        String name = input.nextLine();

        System.out.print("Enter skills: ");
        String skills = input.nextLine();

        System.out.print("Enter available time: ");
        String availability = input.nextLine();

        System.out.print("Enter location: ");
        String location = input.nextLine();

        Volunteer volunteer = new Volunteer(volunteerCounter++, name, skills, availability, location);
        volunteers.add(volunteer);

        DataStore.saveVolunteers(volunteers);

        System.out.println("Volunteer profile created successfully.");
        System.out.println(volunteer);
    }

    // Input File Function
    private static void loadTasksFromFile() {
        System.out.println("\n--- Load Volunteer Tasks From File ---");

        input.nextLine();

        System.out.print("Enter input file name, for example tasks_input.txt: ");
        String fileName = input.nextLine();

        try {
            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);

            int loadedCount = 0;

            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length != 6) {
                    System.out.println("Invalid line skipped: " + line);
                    continue;
                }

                String title = parts[0].trim();
                String requiredSkill = parts[1].trim();
                String location = parts[2].trim();
                String time = parts[3].trim();
                int capacity = Integer.parseInt(parts[4].trim());
                double durationHours = Double.parseDouble(parts[5].trim());

                VolunteerTask task = new VolunteerTask(
                        taskCounter++,
                        title,
                        requiredSkill,
                        location,
                        time,
                        capacity,
                        durationHours
                );

                tasks.add(task);
                loadedCount++;
            }

            fileReader.close();
            DataStore.saveTasks(tasks);

            System.out.println(loadedCount + " tasks loaded successfully from file.");

        } catch (FileNotFoundException e) {
            System.out.println("Input file not found.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format inside the input file.");
        }
    }

    // Core Function 2
    private static void postVolunteerTask() {
        System.out.println("\n--- Post Volunteer Task ---");

        input.nextLine();

        System.out.print("Enter task title: ");
        String title = input.nextLine();

        System.out.print("Enter required skill: ");
        String requiredSkill = input.nextLine();

        System.out.print("Enter task location: ");
        String location = input.nextLine();

        System.out.print("Enter task time: ");
        String time = input.nextLine();

        int capacity = readInt("Enter task capacity: ");
        double durationHours = readDouble("Enter duration hours: ");

        VolunteerTask task = new VolunteerTask(
                taskCounter++,
                title,
                requiredSkill,
                location,
                time,
                capacity,
                durationHours
        );

        tasks.add(task);
        DataStore.saveTasks(tasks);

        System.out.println("Volunteer task posted successfully.");
        System.out.println(task);
    }

    // Core Function 3
    private static void matchVolunteerWithTasks() {
        System.out.println("\n--- Match Volunteer With Tasks ---");

        int volunteerId = readInt("Enter volunteer ID: ");
        Volunteer volunteer = findVolunteerById(volunteerId);

        if (volunteer == null) {
            System.out.println("Volunteer not found.");
            return;
        }

        ArrayList<VolunteerTask> matchedTasks =
                MatchingEngine.matchVolunteerWithTasks(volunteer, tasks);

        DataStore.saveMatches(volunteer, matchedTasks);

        if (matchedTasks.isEmpty()) {
            System.out.println("No matching tasks found.");
        } else {
            System.out.println("Recommended tasks:");
            for (VolunteerTask task : matchedTasks) {
                System.out.println("-----------------------------");
                System.out.println(task);
            }
        }
    }

    // Core Function 4
    private static void applyForTask() {
        System.out.println("\n--- Apply For Task ---");

        int volunteerId = readInt("Enter volunteer ID: ");
        int taskId = readInt("Enter task ID: ");

        Volunteer volunteer = findVolunteerById(volunteerId);
        VolunteerTask task = findTaskById(taskId);

        if (volunteer == null) {
            System.out.println("Volunteer not found.");
            return;
        }

        if (task == null) {
            System.out.println("Task not found.");
            return;
        }

        if (task.isFull()) {
            System.out.println("Application rejected. Task capacity is full.");
            return;
        }

        if (!volunteer.getAvailability().equalsIgnoreCase(task.getTime())) {
            System.out.println("Application rejected. Time conflict detected.");
            return;
        }

        VolunteerApplication application =
                new VolunteerApplication(applicationCounter++, volunteerId, taskId, "Submitted");

        applications.add(application);
        task.increaseApplicants();
        volunteer.addVolunteerHours(task.getDurationHours());

        DataStore.saveApplications(applications);
        DataStore.saveTasks(tasks);
        DataStore.saveVolunteers(volunteers);

        System.out.println("Application submitted successfully.");
        System.out.println(application);
    }

    private static void displayAllData() {
        System.out.println("\n--- Volunteers ---");
        if (volunteers.isEmpty()) {
            System.out.println("No volunteers available.");
        } else {
            for (Volunteer volunteer : volunteers) {
                System.out.println("-----------------------------");
                System.out.println(volunteer);
            }
        }

        System.out.println("\n--- Tasks ---");
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            for (VolunteerTask task : tasks) {
                System.out.println("-----------------------------");
                System.out.println(task);
            }
        }

        System.out.println("\n--- Applications ---");
        if (applications.isEmpty()) {
            System.out.println("No applications available.");
        } else {
            for (VolunteerApplication application : applications) {
                System.out.println("-----------------------------");
                System.out.println(application);
            }
        }
    }

    private static Volunteer findVolunteerById(int id) {
        for (Volunteer volunteer : volunteers) {
            if (volunteer.getId() == id) {
                return volunteer;
            }
        }
        return null;
    }

    private static VolunteerTask findTaskById(int id) {
        for (VolunteerTask task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    private static int readInt(String message) {
        System.out.print(message);
        while (!input.hasNextInt()) {
            System.out.print("Invalid input. Enter a number: ");
            input.next();
        }
        return input.nextInt();
    }

    private static double readDouble(String message) {
        System.out.print(message);
        while (!input.hasNextDouble()) {
            System.out.print("Invalid input. Enter a number: ");
            input.next();
        }
        return input.nextDouble();
    }
}