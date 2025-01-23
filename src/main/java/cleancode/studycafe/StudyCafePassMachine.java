package cleancode.studycafe;

import cleancode.studycafe.exception.AppException;
import cleancode.studycafe.io.InputHandler;
import cleancode.studycafe.io.OutputHandler;
import cleancode.studycafe.io.StudyCafeFileHandler;
import cleancode.studycafe.model.StudyCafeLockerPass;
import cleancode.studycafe.model.StudyCafePass;
import cleancode.studycafe.model.StudyCafePassType;

import java.util.List;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            StudyCafePassType selectedPassType = selectedPassType();
            StudyCafePass selectedPass = selectedPass(selectedPassType);
            StudyCafeLockerPass selectedLockerPass = selectedLockerPass(selectedPass);

            outputHandler.showPassOrderSummary(selectedPass, selectedLockerPass);
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePassType selectedPassType() {
        outputHandler.askPassTypeSelection();
        return inputHandler.getPassTypeSelectingUserAction();
    }

    private StudyCafePass selectedPass(StudyCafePassType passType) {
        List<StudyCafePass> passes = studyCafeFileHandler.readStudyCafePasses();
        List<StudyCafePass> passCandidates = passes.stream()
                .filter(passType::isSamePassType)
                .toList();

        outputHandler.showPassListForSelection(passCandidates);
        return inputHandler.getSelectPass(passCandidates);
    }

    private StudyCafeLockerPass selectedLockerPass(StudyCafePass pass) {
        if (pass.cannotSelectLockerPass()) {
            return null;
        }

        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
        StudyCafeLockerPass lockerPassCandidate = lockerPasses.stream()
                .filter(pass::isCandidate)
                .findFirst()
                .orElse(null);

        if (lockerPassCandidate == null) {
            return null;
        }

        outputHandler.askLockerPass(lockerPassCandidate);
        if (inputHandler.getLockerSelection()) {
            return lockerPassCandidate;
        }

        return null;
    }

}
