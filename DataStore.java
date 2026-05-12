import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataStore {

    public static void saveVolunteers(ArrayList<Volunteer> volunteers) {
        try (FileWriter writer = new FileWriter("volunteers.txt")) {
            for (Volunteer volunteer : volunteers) {
                writer.write(volunteer.toString());
                writer.write("\n-----------------------------\n");
            }
        } catch (IOException e) {
            System.out.println("Error while saving volunteers.");
        }
    }

    public static void saveTasks(ArrayList<VolunteerTask> tasks) {
        try (FileWriter writer = new FileWriter("tasks.txt")) {
            for (VolunteerTask task : tasks) {
                writer.write(task.toString());
                writer.write("\n-----------------------------\n");
            }
        } catch (IOException e) {
            System.out.println("Error while saving tasks.");
        }
    }

    public static void saveApplications(ArrayList<VolunteerApplication> applications) {
        try (FileWriter writer = new FileWriter("applications.txt")) {
            for (VolunteerApplication application : applications) {
                writer.write(application.toString());
                writer.write("\n-----------------------------\n");
            }
        } catch (IOException e) {
            System.out.println("Error while saving applications.");
        }
    }

    public static void saveMatches(Volunteer volunteer, ArrayList<VolunteerTask> matchedTasks) {
        try (FileWriter writer = new FileWriter("matches.txt", true)) {
            writer.write("Matched tasks for volunteer: " + volunteer.getName() + "\n");
            writer.write("=============================\n");

            if (matchedTasks.isEmpty()) {
                writer.write("No matching tasks found.\n");
            } else {
                for (VolunteerTask task : matchedTasks) {
                    writer.write(task.toString());
                    writer.write("\n-----------------------------\n");
                }
            }

            writer.write("\n");

        } catch (IOException e) {
            System.out.println("Error while saving matches.");
        }
    }
}