package cleancode.studycafe.model.pass.provider;

import cleancode.studycafe.model.pass.StudyCafePassType;
import cleancode.studycafe.model.pass.locker.StudyCafeLockerPass;

public class ReadedLockerPass {

    private final StudyCafePassType studyCafePassType;
    private final int duration;
    private final int price;

    private ReadedLockerPass(StudyCafePassType studyCafePassType, int duration, int price) {
        this.studyCafePassType = studyCafePassType;
        this.duration = duration;
        this.price = price;
    }

    public static ReadedLockerPass ofLine(String line) {
        String[] values = line.split(",");

        StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
        int duration = Integer.parseInt(values[1]);
        int price = Integer.parseInt(values[2]);

        return new ReadedLockerPass(studyCafePassType, duration, price);
    }

    public StudyCafeLockerPass toLockerPass() {
        return StudyCafeLockerPass.of(studyCafePassType, duration, price);
    }
}
