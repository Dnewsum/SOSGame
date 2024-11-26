package dannewsumstudent.sosbestfinal.Models;

public class GeneralGameLogic extends BaseGameLogic {

    public GeneralGameLogic(int boardSize) {
        super(boardSize);
    }

    @Override
    public boolean checkForSOS(int row, int col) {
        String symbol = grid[row][col];
        if (symbol == null) return false;
        boolean sosFormed = false;

        return sosFormed;
    }

//    private boolean checkDirection(int row, int col, int rowDir, int colDir) {
//        int row1 = row + rowDir;
//        int col1 = col + colDir;
//        int row2 = row + 2 * rowDir;
//        int col2 = col + 2 * colDir;
//
//        if (isValidPosition(row1, col1) && isValidPosition(row2, col2)) {
//            String first = grid[row][col];
//            String second = grid[row1][col1];
//            String third = grid[row2][col2];
//
//            return "S".equals(first) && "O".equals(second) && "S".equals(third);
//        }
//        return false;
//    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
    }
}
