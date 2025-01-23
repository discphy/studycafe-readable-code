package cleancode.studycafe;

import cleancode.studycafe.exception.AppException;
import cleancode.studycafe.io.StudyCafeFileHandler;
import cleancode.studycafe.io.StudyCafeIOHandler;
import cleancode.studycafe.model.StudyCafeLockerPass;
import cleancode.studycafe.model.StudyCafePass;
import cleancode.studycafe.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            ioHandler.showWelcomeMessage();
            ioHandler.showAnnouncement();

            StudyCafePass selectedPass = selectedPass();
            Optional<StudyCafeLockerPass> selectedLockerPass = selectedLockerPass(selectedPass);

            selectedLockerPass.ifPresentOrElse(
                    lockerPass -> ioHandler.showPassOrderSummary(selectedPass, lockerPass),
                    () -> ioHandler.showPassOrderSummary(selectedPass)
            );
        } catch (AppException e) {
            ioHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            ioHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePass selectedPass() {
        StudyCafePassType passType = ioHandler.askPassTypeSelecting();
        List<StudyCafePass> passCandidates = findPassCandidates(passType);


        return ioHandler.askPassSelecting(passCandidates);
    }

    private List<StudyCafePass> findPassCandidates(StudyCafePassType passType) {
        List<StudyCafePass> allPass = studyCafeFileHandler.readStudyCafePasses();
        return allPass.stream()
                .filter(passType::isSamePassType)
                .toList();
    }

    private Optional<StudyCafeLockerPass> selectedLockerPass(StudyCafePass pass) {
        if (pass.cannotSelectLockerPass()) {
            return Optional.empty();
        }

        Optional<StudyCafeLockerPass> lockerPassCandidate = findLockerPassCandidate(pass);
        return lockerPassCandidate
                .filter(ioHandler::askLockerPass);
    }

    private Optional<StudyCafeLockerPass> findLockerPassCandidate(StudyCafePass pass) {
        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
        return lockerPasses.stream()
                .filter(pass::isCandidate)
                .findFirst();
    }
}
