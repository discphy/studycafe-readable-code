package cleancode.studycafe.io.provider;

import cleancode.studycafe.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.model.pass.locker.StudyCafeLockerPasses;
import cleancode.studycafe.model.pass.provider.ReadedLockerPass;
import cleancode.studycafe.provider.LockerPassProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LockerPassFileReader implements LockerPassProvider {

    public static final String LOCKER_PASS_FILE_PATH = "src/main/resources/cleancode/studycafe/locker.csv";

    @Override
    public StudyCafeLockerPasses getLockerPasses() {
        List<String> lines = readLockerPassLines();

        List<StudyCafeLockerPass> lockerPasses = lines.stream()
                .map(ReadedLockerPass::ofLine)
                .map(ReadedLockerPass::toLockerPass)
                .toList();

        return StudyCafeLockerPasses.of(lockerPasses);
    }

    private static List<String> readLockerPassLines() {
        try {
            return Files.readAllLines(Paths.get(LOCKER_PASS_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }
}
