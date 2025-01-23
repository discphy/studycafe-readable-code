package cleancode.studycafe;

import cleancode.studycafe.exception.AppException;
import cleancode.studycafe.io.StudyCafeFileHandler;
import cleancode.studycafe.io.StudyCafeIOHandler;
import cleancode.studycafe.model.*;

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
        StudyCafePasses passCandidates = findPassCandidates(passType);

        return ioHandler.askPassSelecting(passCandidates);
    }

    private StudyCafePasses findPassCandidates(StudyCafePassType passType) {
        StudyCafePasses allPass = studyCafeFileHandler.readStudyCafePasses();
        return allPass.findCandidatesBy(passType);
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
        StudyCafeLockerPasses allLockerPasses = studyCafeFileHandler.readLockerPasses();
        return allLockerPasses.findCandidateBy(pass);
    }
}
