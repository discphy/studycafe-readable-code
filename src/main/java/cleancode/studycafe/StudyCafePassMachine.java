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
                .filter(studyCafePass -> studyCafePass.getPassType() == passType)
                .toList();

        outputHandler.showPassListForSelection(passCandidates);
        return inputHandler.getSelectPass(passCandidates);
    }

    private StudyCafeLockerPass selectedLockerPass(StudyCafePass pass) {
        if (pass.getPassType() != StudyCafePassType.FIXED) {
            return null;
        }

        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
        StudyCafeLockerPass lockerPassCandidates = lockerPasses.stream()
                .filter(option ->
                        option.getPassType() == pass.getPassType()
                                && option.getDuration() == pass.getDuration()
                )
                .findFirst()
                .orElse(null);

        if (lockerPassCandidates == null) {
            return null;
        }

        outputHandler.askLockerPass(lockerPassCandidates);
        if (inputHandler.getLockerSelection()) {
            return lockerPassCandidates;
        }

        return null;
    }

}
