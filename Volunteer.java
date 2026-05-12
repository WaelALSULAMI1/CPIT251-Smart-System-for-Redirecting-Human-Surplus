public class Volunteer {
    private int id;
    private String name;
    private String skills;
    private String availability;
    private String location;
    private double totalVolunteerHours;

    public Volunteer(int id, String name, String skills, String availability, String location) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.availability = availability;
        this.location = location;
        this.totalVolunteerHours = 0.0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSkills() {
        return skills;
    }

    public String getAvailability() {
        return availability;
    }

    public String getLocation() {
        return location;
    }

    public double getTotalVolunteerHours() {
        return totalVolunteerHours;
    }

    public void addVolunteerHours(double hours) {
        if (hours > 0) {
            totalVolunteerHours += hours;
        }
    }

    @Override
    public String toString() {
        return "Volunteer ID: " + id
                + "\nName: " + name
                + "\nSkills: " + skills
                + "\nAvailability: " + availability
                + "\nLocation: " + location
                + "\nTotal Hours: " + totalVolunteerHours;
    }
}