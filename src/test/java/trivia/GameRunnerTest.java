package trivia;

import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static trivia.GameRunner.createGame;

public class GameRunnerTest {
    private static final String GOLDEN_MASTER_DIR_NAME = "golden-master";
    private static final long DEFAULT_SEED = 100;

    void deleteRecursivelyAllFilesFrom(File parent) {
        if (parent.isDirectory())
            for (File file : parent.listFiles())
                deleteRecursivelyAllFilesFrom(file);
        if (!parent.delete())
            throw new RuntimeException(
                    String.format("Could not delete file \"%s\"!",
                            parent.getAbsolutePath()));
    }

    void recreateTheGoldenMasterDirectory() {
        File goldenMasterDirectory = new File(GOLDEN_MASTER_DIR_NAME);
        deleteRecursivelyAllFilesFrom(goldenMasterDirectory);
        goldenMasterDirectory.mkdirs();
    }

    void saveConsoleOutputInFile(File file) throws FileNotFoundException {
        System.setOut(new PrintStream(file));
    }

    private String getFileNameFor(long seed) {
        return String.format("%s/%d.txt", GOLDEN_MASTER_DIR_NAME, seed);
    }

    private void redirectConsoleOutputTo(ByteArrayOutputStream stream) {
        System.setOut(new PrintStream(stream));
    }

    private String readFromGoldenMasterFor(long seed) throws FileNotFoundException {
        return new Scanner(new File(getFileNameFor(seed))).useDelimiter("\\A").next();
    }

    @Test
    @Ignore
    public void shouldGenerateGoldenMaster() throws Exception {
        recreateTheGoldenMasterDirectory();

        long seed = 100;
        saveConsoleOutputInFile(new File(getFileNameFor(seed)));
        new GameRunner(createGame(), new Random(seed)).run();
    }

    @Test
    public void shouldCheckActualOutputWithGoldenMaster() throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        redirectConsoleOutputTo(stream);

        new GameRunner(createGame(), new Random(DEFAULT_SEED)).run();

        String expectedOutput = readFromGoldenMasterFor(DEFAULT_SEED);
        String actualOutput = stream.toString();
        assertEquals(expectedOutput, actualOutput);
    }
}
