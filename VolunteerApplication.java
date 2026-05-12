public class VolunteerApplication {
    private int applicationId;
    private int volunteerId;
    private int taskId;
    private String status;

    public VolunteerApplication(int applicationId, int volunteerId, int taskId, String status) {
        this.applicationId = applicationId;
        this.volunteerId = volunteerId;
        this.taskId = taskId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Application ID: " + applicationId
                + "\nVolunteer ID: " + volunteerId
                + "\nTask ID: " + taskId
                + "\nStatus: " + status;
    }
}