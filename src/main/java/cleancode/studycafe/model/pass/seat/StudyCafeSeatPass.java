package cleancode.studycafe.model.pass.seat;

import cleancode.studycafe.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.model.pass.StudyCafePass;
import cleancode.studycafe.model.pass.StudyCafePassType;

public class StudyCafeSeatPass implements StudyCafePass {

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;
    private final double discountRate;

    private StudyCafeSeatPass(StudyCafePassType passType, int duration, int price, double discountRate) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
        this.discountRate = discountRate;
    }

    public static StudyCafeSeatPass of(StudyCafePassType passType, int duration, int price, double discountRate) {
        return new StudyCafeSeatPass(passType, duration, price, discountRate);
    }

    public boolean cannotSelectLockerPass() {
        return passType != StudyCafePassType.FIXED;
    }

    public boolean isCandidate(StudyCafeLockerPass lockerPass) {
        return lockerPass.isSamePassType(this.passType) && lockerPass.isSameDuration(this.duration);
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public StudyCafePassType getPassType() {
        return passType;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    public int getDiscountPrice() {
        return (int) (price * discountRate);
    }
}
