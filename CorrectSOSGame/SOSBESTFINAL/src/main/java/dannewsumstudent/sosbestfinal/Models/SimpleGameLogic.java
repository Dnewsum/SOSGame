package dannewsumstudent.sosbestfinal.Models;

public class SimpleGameLogic extends BaseGameLogic {

    public SimpleGameLogic(int boardSize) {
        super(boardSize);
    }

    @Override
    public boolean checkForSOS(int row, int col) {
        String symbol = grid[row][col];
        if (symbol == null) return false;

        boolean sosFormed = false;

        return sosFormed;
    }

//    private boolean checkPattern(int row, int col, int rowInc, int colInc) {
//        if (isValidPosition(row, col) && isValidPosition(row + rowInc * 2, col + colInc * 2)) {
//            String first = grid[row][col];
//            String second = grid[row + rowInc][col + colInc];
//            String third = grid[row + rowInc * 2][col + colInc * 2];
//
//            return "S".equals(first) && "O".equals(second) && "S".equals(third);
//        }
//        return false;
//    }
//
//    private boolean isValidPosition(int row, int col) {
//        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
//    }
}
