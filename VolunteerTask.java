public class VolunteerTask {
    private int id;
    private String title;
    private String requiredSkill;
    private String location;
    private String time;
    private int capacity;
    private int currentApplicants;
    private double durationHours;

    public VolunteerTask(int id, String title, String requiredSkill, String location,
                         String time, int capacity, double durationHours) {
        this.id = id;
        this.title = title;
        this.requiredSkill = requiredSkill;
        this.location = location;
        this.time = time;
        this.capacity = capacity;
        this.durationHours = durationHours;
        this.currentApplicants = 0;
    }

    public int getId() {
        return id;
    }

    public String getRequiredSkill() {
        return requiredSkill;
    }

    public String getLocation() {
        return location;
    }

    public String getTime() {
        return time;
    }

    public double getDurationHours() {
        return durationHours;
    }

    public boolean isFull() {
        return currentApplicants >= capacity;
    }

    public void increaseApplicants() {
        currentApplicants++;
    }

    @Override
    public String toString() {
        return "Task ID: " + id
                + "\nTitle: " + title
                + "\nRequired Skill: " + requiredSkill
                + "\nLocation: " + location
                + "\nTime: " + time
                + "\nCapacity: " + capacity
                + "\nCurrent Applicants: " + currentApplicants
                + "\nDuration Hours: " + durationHours;
    }
}