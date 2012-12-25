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
import static trivia.Runner.createGame;

public class RunnerTest {
    private static final String GOLDEN_MASTER_DIR_NAME = "golden-master";
    private static final long DEFAULT_SEED_LIMIT = 100;

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

    private void saveConsoleOutputFor(long seed) throws FileNotFoundException {
        saveConsoleOutputInFile(new File(getFileNameFor(seed)));
        Random random = new Random(seed);
        createGame(random).run();
    }

    private void redirectConsoleOutputTo(ByteArrayOutputStream stream) {
        System.setOut(new PrintStream(stream));
    }

    private String readFromGoldenMasterFor(long seed) throws FileNotFoundException {
        return new Scanner(new File(getFileNameFor(seed))).useDelimiter("\\A").next();
    }

    private void checkConsoleOutputWithGoldenMasterFor(long seed) throws FileNotFoundException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        redirectConsoleOutputTo(stream);

        Random random = new Random(seed);
        createGame(random).run();

        String expectedOutput = readFromGoldenMasterFor(seed);
        String actualOutput = stream.toString();
        assertEquals("Output differs for seed = " + seed, expectedOutput, actualOutput);
    }

    @Test
    @Ignore
    public void shouldGenerateGoldenMaster() throws Exception {
        recreateTheGoldenMasterDirectory();

        for (long seed = 0; seed < DEFAULT_SEED_LIMIT; seed++)
            saveConsoleOutputFor(seed);
    }

    @Test
    public void shouldCheckActualOutputWithGoldenMaster() throws Exception {
        for (long seed = 0; seed < DEFAULT_SEED_LIMIT; seed++)
            checkConsoleOutputWithGoldenMasterFor(seed);
    }
}
