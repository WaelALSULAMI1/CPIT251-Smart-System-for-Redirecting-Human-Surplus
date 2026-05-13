import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class SmartVolunteerSystemTest {

    @Test
    public void testCreateVolunteerProfile() {
        Volunteer volunteer = new Volunteer(
                1,
                "saud",
                "teaching",
                "monday 5pm",
                "jeddah"
        );

        assertEquals(1, volunteer.getId());
        assertEquals("saud", volunteer.getName());
        assertEquals("teaching", volunteer.getSkills());
        assertEquals("monday 5pm", volunteer.getAvailability());
        assertEquals("jeddah", volunteer.getLocation());
        assertEquals(0.0, volunteer.getTotalVolunteerHours(), 0.01);
    }

    @Test
    public void testPostVolunteerTask() {
        VolunteerTask task = new VolunteerTask(
                1,
                "School Tutoring",
                "Teaching",
                "Jeddah",
                "Monday 5PM",
                3,
                2.0
        );

        assertEquals(1, task.getId());
        assertEquals("Teaching", task.getRequiredSkill());
        assertEquals("Jeddah", task.getLocation());
        assertEquals("Monday 5PM", task.getTime());
        assertFalse(task.isFull());
    }

    @Test
    public void testMatchingFunction() {
        Volunteer volunteer = new Volunteer(
                1,
                "saud",
                "teaching",
                "monday 5pm",
                "jeddah"
        );

        VolunteerTask task = new VolunteerTask(
                1,
                "School Tutoring",
                "Teaching",
                "Jeddah",
                "Monday 5PM",
                3,
                2.0
        );

        ArrayList<VolunteerTask> tasks = new ArrayList<>();
        tasks.add(task);

        ArrayList<VolunteerTask> matchedTasks =
                MatchingEngine.matchVolunteerWithTasks(volunteer, tasks);

        assertEquals(1, matchedTasks.size());
        assertEquals(1, matchedTasks.get(0).getId());
    }

    @Test
    public void testApplyForTask() {
        Volunteer volunteer = new Volunteer(
                1,
                "saud",
                "teaching",
                "monday 5pm",
                "jeddah"
        );

        VolunteerTask task = new VolunteerTask(
                1,
                "School Tutoring",
                "Teaching",
                "Jeddah",
                "Monday 5PM",
                3,
                2.0
        );

        VolunteerApplication application =
                new VolunteerApplication(1, volunteer.getId(), task.getId(), "Submitted");

        task.increaseApplicants();
        volunteer.addVolunteerHours(task.getDurationHours());

        assertNotNull(application);
        assertFalse(task.isFull());
        assertEquals(2.0, volunteer.getTotalVolunteerHours(), 0.01);
    }
}