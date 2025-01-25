package cleancode.studycafe.model.pass.seat;

import cleancode.studycafe.model.pass.StudyCafePassType;

import java.util.List;

public class StudyCafeSeatPasses {

    private final List<StudyCafeSeatPass> passes;

    private StudyCafeSeatPasses(List<StudyCafeSeatPass> passes) {
        this.passes = passes;
    }

    public static StudyCafeSeatPasses of(List<StudyCafeSeatPass> passes) {
        return new StudyCafeSeatPasses(passes);
    }

    public StudyCafeSeatPasses findSeatPassCandidatesBy(StudyCafePassType passType) {
        List<StudyCafeSeatPass> passCandidates = passes.stream()
                .filter(passType::isSamePassType)
                .toList();

        return of(passCandidates);
    }

    public int size() {
        return passes.size();
    }

    public StudyCafeSeatPass findBy(int index) {
        return copy().get(index);
    }

    private List<StudyCafeSeatPass> copy() {
        return List.copyOf(passes);
    }
}
