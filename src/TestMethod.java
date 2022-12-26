import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import org.junit.jupiter.api.Test;

public class TestMethod {
    @Test
    public void testGetPrerequisitePath() throws IOException {
        Graph testPath = new Graph();
        testPath = testPath.createGraph();
        String str = testPath.getPrerequisitePath("7");

        //Verify that str contains all the path
        assertTrue(str.contains("0"));
        assertTrue(str.contains("3"));
        assertTrue(str.contains("1"));
        assertTrue(str.contains("7"));
    }
    @Test
    public void testIsPrerequisite() throws IOException {
        Graph testPath = new Graph();
        testPath = testPath.createGraph();
        Boolean answer = testPath.isPrerequisite("3","7");

        //Verify that 3 is a prerequisite of 7
        assertTrue(answer==true);
    }
}

