package cs3500.pa05.Model;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javafx.scene.text.Font;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for the BujoFileReader Class
 */
public class BujoFileReaderTest {

  /**
   * Tests the getFile() method in the BujoFileReader Class
   *
   * @throws IOException is thrown if file cannot be found
   */

  @Test
  public void testGetFile() throws IOException {
    String json = "{\"name\":\"Sample Week\"," +
        "\"days\":[{\"tasks\":[" +
        "{\"name\":\"Task 1\",\"description\":\"Description 1\",\"complete\":false}]," +
        "\"events\":[" +
        "{\"name\":\"Event 1\",\"startTime\":\"10pm\"," +
        "\"duration\":\"60 min\",\"description\":\"Description 1\"}]}]," +
        "\"theme\":" +
        "{\"name\":\"Light Theme\",\"font\":{\"name\":\"Calibri\",\"style\":0,\"size\":17}," +
        "\"primaryColor\":{\"red\":230,\"green\":230,\"blue\":230}," +
        "\"secondaryColor\":{\"red\":227,\"green\":217,\"blue\":200}," +
        "\"fontColor\":{\"red\":23,\"green\":22,\"blue\":22}}," +
        "\"maxEvents\":10,\"maxTasks\":5,\"themeList\":[]}";

    File tempFile = createTempFile(json);

    // Read the file using BujoFileReader
    Week week = BujoFileReader.getFile(tempFile);

    assertNotNull(week);

    assertEquals("Sample Week", week.getName());
    assertEquals(1, week.getDays().size());

    Day day = week.getDays().get(0);
    List<Task> tasks = day.getTasks();
    List<Event> events = day.getEvents();

    assertEquals(1, tasks.size());
    assertEquals(1, events.size());

    Task task = tasks.get(0);
    assertEquals("Task 1", task.getName());
    assertEquals("Description 1", task.getDescription());
    assertFalse(task.getComplete());

    Event event = events.get(0);
    assertEquals("Event 1", event.getName());
    assertEquals("10pm", event.getStartTime());
    assertEquals("60 min", event.getDuration());
    assertEquals("Description 1", event.getDescription());

    Theme theme = week.getTheme();
    assertEquals("Light Theme", theme.getName());
    assertEquals(new Font("Calibri", 17), theme.getFont());
    assertEquals(Color.rgb(230, 230, 230), theme.getPrimaryColor());
    assertEquals(Color.rgb(227, 217, 200), theme.getSecondaryColor());
    assertEquals(Color.rgb(23, 22, 22), theme.getFontColor());

    assertEquals(10, week.getMaxEvents());
    assertEquals(5, week.getMaxTasks());

    List<Theme> themeList = week.getThemeList();
    assertEquals(0, themeList.size());

  }

  private File createTempFile(String content) throws IOException {
    File tempFile = File.createTempFile("temp", ".json");
    Files.writeString(Path.of(tempFile.getPath()), content);
    return tempFile;
  }

}
