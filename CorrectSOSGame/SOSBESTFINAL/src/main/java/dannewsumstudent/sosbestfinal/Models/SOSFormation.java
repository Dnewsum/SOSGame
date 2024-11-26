package dannewsumstudent.sosbestfinal.Models;

import java.util.List;

public class SOSFormation {
    private List<int[]> positions;
    public SOSFormation(List<int[]> positions) {
        this.positions = positions;
    }
    public List<int[]> getPositions() {
        return positions;
    }
}
