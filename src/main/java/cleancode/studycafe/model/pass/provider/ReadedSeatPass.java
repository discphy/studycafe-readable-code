package cleancode.studycafe.model.pass.provider;

import cleancode.studycafe.model.pass.StudyCafePassType;
import cleancode.studycafe.model.pass.seat.StudyCafeSeatPass;

public class ReadedSeatPass {

    private final StudyCafePassType studyCafePassType;
    private final int duration;
    private final int price;
    private final double discountRate;

    private ReadedSeatPass(StudyCafePassType studyCafePassType, int duration, int price, double discountRate) {
        this.studyCafePassType = studyCafePassType;
        this.duration = duration;
        this.price = price;
        this.discountRate = discountRate;
    }

    public static ReadedSeatPass ofLine(String line) {
        String[] values = line.split(",");

        StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
        int duration = Integer.parseInt(values[1]);
        int price = Integer.parseInt(values[2]);
        double discountRate = Double.parseDouble(values[3]);

        return new ReadedSeatPass(studyCafePassType, duration, price, discountRate);
    }

    public StudyCafeSeatPass toSeatPass() {
        return StudyCafeSeatPass.of(studyCafePassType, duration, price, discountRate);
    }
}
