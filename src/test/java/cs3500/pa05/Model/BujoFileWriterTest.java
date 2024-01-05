package cs3500.pa05.Model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the methods in the BujoFileWriter class
 */
public class BujoFileWriterTest {

  /**
   * Tests the saveWeek() method in the BujoFileWriter class
   * @throws IOException is thrown when the path of the given file isn't found
   */
  @Test
  public void testSaveWeek() throws IOException {
    Week week = new Week();
    week.changeName("Sample Week");
    week.setMaxEvents(10);
    week.setMaxTasks(5);

    String filePath = createTempFilePath();

    BujoFileWriter.saveWeek(week, filePath);

    String jsonContent = Files.readString(Path.of(filePath));
    ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonNode = mapper.readTree(jsonContent);

    assertEquals("Sample Week", jsonNode.get("name").asText());
    assertEquals(10, jsonNode.get("maxEvents").asInt());
    assertEquals(5, jsonNode.get("maxTasks").asInt());
    assertEquals(3, jsonNode.get("themeList").size());

  }

  private String createTempFilePath() throws IOException {
    File tempFile = File.createTempFile("test", ".json");
    return tempFile.getPath();
  }

}
