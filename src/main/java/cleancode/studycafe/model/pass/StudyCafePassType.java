package cleancode.studycafe.model.pass;

import cleancode.studycafe.model.pass.seat.StudyCafeSeatPass;

public enum StudyCafePassType {

    HOURLY("시간 단위 이용권"),
    WEEKLY("주 단위 이용권"),
    FIXED("1인 고정석");

    private final String description;

    StudyCafePassType(String description) {
        this.description = description;
    }

    public boolean isSamePassType(StudyCafeSeatPass pass) {
        return this == pass.getPassType();
    }
}
