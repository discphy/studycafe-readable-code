package cleancode.studycafe.model.order;

import cleancode.studycafe.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.model.pass.seat.StudyCafeSeatPass;

import java.util.Optional;

public class StudyCafeOrder {

    private final StudyCafeSeatPass seatPass;
    private final StudyCafeLockerPass lockerPass;

    private StudyCafeOrder(StudyCafeSeatPass seatPass, StudyCafeLockerPass lockerPass) {
        this.seatPass = seatPass;
        this.lockerPass = lockerPass;
    }

    public static StudyCafeOrder of(StudyCafeSeatPass seatPass, StudyCafeLockerPass lockerPass) {
        return new StudyCafeOrder(seatPass, lockerPass);
    }

    public int getTotalPrice() {
        int lockerPassPrice = lockerPass != null ? lockerPass.getPrice() : 0;
        int totalPassPrice = seatPass.getPrice() + lockerPassPrice;

        return totalPassPrice - seatPass.getDiscountPrice();
    }

    public int getDiscountPrice() {
        return seatPass.getDiscountPrice();
    }

    public StudyCafeSeatPass getSeatPass() {
        return seatPass;
    }

    public Optional<StudyCafeLockerPass> getLockerPass() {
        return Optional.ofNullable(lockerPass);
    }
}
