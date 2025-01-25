package cleancode.studycafe;

import cleancode.studycafe.exception.AppException;
import cleancode.studycafe.io.StudyCafeFileHandler;
import cleancode.studycafe.io.StudyCafeIOHandler;
import cleancode.studycafe.model.order.StudyCafeOrder;
import cleancode.studycafe.model.pass.*;
import cleancode.studycafe.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.model.pass.locker.StudyCafeLockerPasses;
import cleancode.studycafe.model.pass.seat.StudyCafeSeatPass;
import cleancode.studycafe.model.pass.seat.StudyCafeSeatPasses;

import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            ioHandler.showWelcomeMessage();
            ioHandler.showAnnouncement();

            StudyCafeSeatPass selectedSeatPass = selectedPass();
            Optional<StudyCafeLockerPass> optionalLockerPass = selectedLockerPass(selectedSeatPass);

            StudyCafeOrder order = StudyCafeOrder.of(
                    selectedSeatPass,
                    optionalLockerPass.orElse(null)
            );

            ioHandler.showPassOrderSummary(order);
        } catch (AppException e) {
            ioHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            ioHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafeSeatPass selectedPass() {
        StudyCafePassType passType = ioHandler.askPassTypeSelecting();
        StudyCafeSeatPasses passCandidates = findSeatPassCandidates(passType);

        return ioHandler.askPassSelecting(passCandidates);
    }

    private StudyCafeSeatPasses findSeatPassCandidates(StudyCafePassType passType) {
        StudyCafeSeatPasses allPass = studyCafeFileHandler.readStudyCafePasses();
        return allPass.findSeatPassCandidatesBy(passType);
    }

    private Optional<StudyCafeLockerPass> selectedLockerPass(StudyCafeSeatPass seatPass) {
        if (seatPass.cannotSelectLockerPass()) {
            return Optional.empty();
        }

        Optional<StudyCafeLockerPass> lockerPassCandidate = findLockerPassCandidate(seatPass);
        return lockerPassCandidate
                .filter(ioHandler::askLockerPass);
    }

    private Optional<StudyCafeLockerPass> findLockerPassCandidate(StudyCafeSeatPass seatPass) {
        StudyCafeLockerPasses allLockerPasses = studyCafeFileHandler.readLockerPasses();
        return allLockerPasses.findCandidateBy(seatPass);
    }
}
