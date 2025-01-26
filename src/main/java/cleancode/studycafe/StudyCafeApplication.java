package cleancode.studycafe;

import cleancode.studycafe.io.StudyCafeIOHandler;
import cleancode.studycafe.io.provider.LockerPassFileReader;
import cleancode.studycafe.io.provider.SeatPassFileReader;
import cleancode.studycafe.provider.LockerPassProvider;
import cleancode.studycafe.provider.SeatPassProvider;

public class StudyCafeApplication {

    public static void main(String[] args) {
        StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();
        SeatPassProvider seatPassProvider = new SeatPassFileReader();
        LockerPassProvider lockerPassFileProvider = new LockerPassFileReader();

        StudyCafePassMachine studyCafePassMachine = new StudyCafePassMachine(
                ioHandler,
                seatPassProvider,
                lockerPassFileProvider
        );
        studyCafePassMachine.run();
    }

}
