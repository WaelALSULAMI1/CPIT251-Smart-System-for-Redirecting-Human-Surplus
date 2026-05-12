import java.util.ArrayList;

public class MatchingEngine {

    public static ArrayList<VolunteerTask> matchVolunteerWithTasks(
            Volunteer volunteer, ArrayList<VolunteerTask> tasks) {

        ArrayList<VolunteerTask> matchedTasks = new ArrayList<>();

        for (VolunteerTask task : tasks) {
            boolean skillMatch = volunteer.getSkills().toLowerCase()
                    .contains(task.getRequiredSkill().toLowerCase());

            boolean locationMatch = volunteer.getLocation()
                    .equalsIgnoreCase(task.getLocation());

            boolean timeMatch = volunteer.getAvailability()
                    .equalsIgnoreCase(task.getTime());

            if (skillMatch && locationMatch && timeMatch && !task.isFull()) {
                matchedTasks.add(task);
            }
        }

        return matchedTasks;
    }
}