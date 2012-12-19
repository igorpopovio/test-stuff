package runner;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;

import static runner.GameRunner.createGame;

public class GameRunnerTest {
    private static final String GOLDEN_MASTER_DIR_NAME = "golden-master";

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

    @Test
    public void shouldGenerateGoldenMaster() throws Exception {
        recreateTheGoldenMasterDirectory();

        long seed = 100;
        saveConsoleOutputInFile(new File(getFileNameFor(seed)));
        new GameRunner(createGame(), new Random(seed)).run();
    }
}
