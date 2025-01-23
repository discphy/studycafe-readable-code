package cleancode.studycafe;

import cleancode.studycafe.exception.AppException;
import cleancode.studycafe.io.InputHandler;
import cleancode.studycafe.io.OutputHandler;
import cleancode.studycafe.io.StudyCafeFileHandler;
import cleancode.studycafe.model.StudyCafeLockerPass;
import cleancode.studycafe.model.StudyCafePass;
import cleancode.studycafe.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            StudyCafePass selectedPass = selectedPass();
            Optional<StudyCafeLockerPass> selectedLockerPass = selectedLockerPass(selectedPass);

            selectedLockerPass.ifPresentOrElse(
                    lockerPass -> outputHandler.showPassOrderSummary(selectedPass, lockerPass),
                    () -> outputHandler.showPassOrderSummary(selectedPass)
            );
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePass selectedPass() {
        StudyCafePassType passType = selectedPassType();
        List<StudyCafePass> passCandidates = findPassCandidates(passType);

        outputHandler.showPassListForSelection(passCandidates);
        return inputHandler.getSelectPass(passCandidates);
    }

    private List<StudyCafePass> findPassCandidates(StudyCafePassType passType) {
        List<StudyCafePass> allPass = studyCafeFileHandler.readStudyCafePasses();
        return allPass.stream()
                .filter(passType::isSamePassType)
                .toList();
    }

    private StudyCafePassType selectedPassType() {
        outputHandler.askPassTypeSelection();
        return inputHandler.getPassTypeSelectingUserAction();
    }

    private Optional<StudyCafeLockerPass> selectedLockerPass(StudyCafePass pass) {
        if (pass.cannotSelectLockerPass()) {
            return Optional.empty();
        }

        Optional<StudyCafeLockerPass> lockerPassCandidate = findLockerPassCandidate(pass);
        return lockerPassCandidate
                .filter(this::isSelectedLockerPass);
    }

    private Optional<StudyCafeLockerPass> findLockerPassCandidate(StudyCafePass pass) {
        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
        return lockerPasses.stream()
                .filter(pass::isCandidate)
                .findFirst();
    }

    private boolean isSelectedLockerPass(StudyCafeLockerPass lockerPass) {
        outputHandler.askLockerPass(lockerPass);
        return inputHandler.getLockerSelection();
    }
}
