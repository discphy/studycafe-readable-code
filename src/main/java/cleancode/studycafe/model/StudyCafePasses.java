package cleancode.studycafe.model;

import java.util.List;

public class StudyCafePasses {

    private final List<StudyCafePass> passes;

    private StudyCafePasses(List<StudyCafePass> passes) {
        this.passes = passes;
    }

    public static StudyCafePasses of(List<StudyCafePass> passes) {
        return new StudyCafePasses(passes);
    }

    public StudyCafePasses findCandidatesBy(StudyCafePassType passType) {
        List<StudyCafePass> passCandidates = passes.stream()
                .filter(passType::isSamePassType)
                .toList();

        return of(passCandidates);
    }

    public int size() {
        return passes.size();
    }

    public StudyCafePass findBy(int index) {
        return copy().get(index);
    }

    private List<StudyCafePass> copy() {
        return List.copyOf(passes);
    }
}
