package cleancode.studycafe.io.provider;

import cleancode.studycafe.model.pass.provider.ReadedSeatPass;
import cleancode.studycafe.model.pass.seat.StudyCafeSeatPass;
import cleancode.studycafe.model.pass.seat.StudyCafeSeatPasses;
import cleancode.studycafe.provider.SeatPassProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SeatPassFileReader implements SeatPassProvider {

    public static final String SEAT_PASS_FILE = "src/main/resources/cleancode/studycafe/pass-list.csv";

    @Override
    public StudyCafeSeatPasses getSeatPasses() {
        List<String> lines = readSeatPassLines();

        List<StudyCafeSeatPass> studyCafeSeatPasses = lines.stream()
                .map(ReadedSeatPass::ofLine)
                .map(ReadedSeatPass::toSeatPass)
                .toList();

        return StudyCafeSeatPasses.of(studyCafeSeatPasses);
    }

    private List<String> readSeatPassLines() {
        try {
            return Files.readAllLines(Paths.get(SEAT_PASS_FILE));
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }
}
