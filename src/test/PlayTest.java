package test;

import org.junit.After;
import org.junit.Before;

import sudoku.Generator;
import sudoku.GeneratorInterface;
import sudoku.Play;
import sudoku.Solver;

import org.junit.Test;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Black box testing. Input and output testing by simulating a user.
 */
class PlayTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @Before
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    } 
    
    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @After
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }
    @Test
    void playSolveTest() throws IOException {
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        final String testString = "1\n"+
        						  "2\n"+
                                  "60\n"+
                                  "2\n"+
                                  "2";
        provideInput(testString);
        Play play = new Play();
        GeneratorInterface gen = new MockGenerator(new MockSolver(), new MockDB());
        play.play(1, gen);
        Path path = Path.of("output.txt");
        Path path1 = Path.of("TestSolver.txt");
        System.setOut(systemOut);
        String output = new String(Files.readAllBytes(path));
        String test = new String(Files.readAllBytes(path1));
        assertEquals(output, test);
    }

    @Test
    void playExitTest() throws IOException {
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        final String testString = "10\n"+"2\n";
        provideInput(testString);
        Play play = new Play();
        GeneratorInterface gen = new MockGenerator(new MockSolver(), new MockDB());
        play.play(1, gen);
        Path path = Path.of("output.txt");
        Path path1 = Path.of("TestExit.txt");
        System.setOut(systemOut);
        String output = new String(Files.readAllBytes(path));
        String test = new String(Files.readAllBytes(path1));
        assertEquals(output,test);
    }

    @Test
    void playComplexityByCellsTest() throws IOException {
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        final String testStr =    "1\n"+
        						  "2\n"+
                                  "0\n"+
                                  "2\n"+
                                  "81\n"+
                                  "2\n"+
                                  "B1\n"+
                                  "2\n"+
                                  "-10\n"+
                                  "2\n"+
                                  "100\n";
        provideInput(testStr);
        Play play = new Play();
        GeneratorInterface gen = new MockGenerator(new MockSolver(), new MockDB());
        play.play(1, gen);
        Path path = Path.of("output.txt");
        Path path1 = Path.of("TestComplexityCells.txt");
        System.setOut(systemOut);
        String output = new String(Files.readAllBytes(path));
        String test = new String(Files.readAllBytes(path1));
        assertEquals(output,test);
    }
    
    @Test
    void playComplexityByLevelTest() throws IOException {
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        final String testStr =    "1\n"+
        						  "0\n"+
        						  "1\n"+
                                  "0\n"+
                                  "1\n"+
                                  "1\n"+
                                  "3\n"+
                                  "1\n"+
                                  "1\n"+
                                  "2\n"+
                                  "3\n"+
                                  "1\n"+
                                  "1\n"+
                                  "3\n";
        provideInput(testStr);
        Play play = new Play();
        GeneratorInterface gen = new MockGenerator(new MockSolver(), new MockDB());
        play.play(1, gen);
        Path path = Path.of("output.txt");
        Path path1 = Path.of("TestComplexityLevel.txt");
        System.setOut(systemOut);
        String output = new String(Files.readAllBytes(path));
        String test = new String(Files.readAllBytes(path1));
        assertEquals(output,test);
    }
    
    @Test
    void playEmptyDbTest() throws Exception {
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        final String testStr =    "1\n"+
        						  "1\n"+
        						  "1\n";
        provideInput(testStr);
        Play play = new Play();
        GeneratorInterface gen = new Generator(new Solver(), new MockDBEmpty());
        play.play(1, gen);
        Path path = Path.of("output.txt");
        Path path1 = Path.of("TestEmptyDb.txt");
        System.setOut(systemOut);
        String output = new String(Files.readAllBytes(path));
        String test = new String(Files.readAllBytes(path1));
        assertEquals(output,test);
    }

    @Test
    void playCellParserTest() throws IOException {
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        final String testString = "1\n"+
        						"2\n"+
                                  "10\n"+
                                  "1\n"+
                                  "B1\n"+
                                  "5\n"+
                                  "1\n"+
                                  "1\n"+
                                  "1\n"+
                                  "A1\n"+
                                  "15\n"+
                                  "1\n"+
                                  "A1\n"+
                                  "0\n"+
                                  "1\n"+
                                  "A1\n"+
                                  "1\n"+
                                  "3\n"+
                                  "2";

        provideInput(testString);
        Play play = new Play();
        GeneratorInterface gen = new MockGenerator(new MockSolver(), new MockDB());
        play.play(1, gen);
        Path path = Path.of("output.txt");
        Path path1 = Path.of("TestCellParser.txt");
        System.setOut(systemOut);
        String output = new String(Files.readAllBytes(path));
        String test = new String(Files.readAllBytes(path1));
        assertEquals(output,test);
    }

    @Test
    void playCompleteTest() throws IOException {
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        final String testString = "1\n"+"2\n"+
                                  "10\n"+
                                  "1\n"+ "A1\n"+ "1\n"+
                                  "1\n"+ "B1\n"+ "2\n"+
                                  "1\n"+ "C1\n"+ "3\n"+
                                  "1\n"+ "D1\n"+ "4\n"+
                                  "1\n"+ "E1\n"+ "5\n"+
                                  "1\n"+ "F1\n"+ "6\n"+
                                  "1\n"+ "G1\n"+ "7\n"+
                                  "1\n"+ "H1\n"+ "8\n"+
                                  "1\n"+ "I1\n"+ "9\n"+
                                  "4\n"+ "A2\n"+ "4\n"+
                                  "1\n"+ "A2\n"+ "4\n"+
                                  "2";

        provideInput(testString);
        Play play = new Play();
        GeneratorInterface gen = new MockGenerator(new MockSolver(), new MockDB());
        play.play(1, gen);
        Path path = Path.of("output.txt");
        Path path1 = Path.of("TestComplete.txt");
        System.setOut(systemOut);
        String output = new String(Files.readAllBytes(path));
        String test = new String(Files.readAllBytes(path1));
        assertEquals(output,test);
    }
}