import java.util.ArrayList;

public class Board implements Comparable<Board> {
    public ArrayList<Board> possibleStates;
    private ArrayList<String> board;
    private int currentRowX;
    private int currentColX;
    private int numPossibleMovesX;
    private int currentRowO;
    private int currentColO;
    private int numPossibleMovesO;
    private int depth;
    private int utility;
    private char player;

    public Board() {
        possibleStates = new ArrayList<>();
        board = new ArrayList<String>(8);
        board.add("X-------");
        board.add("--------");
        board.add("--------");
        board.add("--------");
        board.add("--------");
        board.add("--------");
        board.add("--------");
        board.add("-------O");
        currentRowX = 0;
        currentColX = 0;
        numPossibleMovesX = 20;
        currentRowO = 7;
        currentColO = 7;
        numPossibleMovesO = 20;
        depth = 0;
        utility = 0;
        player = 'X';
        
    }

    public Board(Board parent) {
        possibleStates = new ArrayList<>();
        board = new ArrayList<String>(8);
        for(int i = 0; i < 8; i++) {
            board.add(parent.board.get(i));
        }
        currentRowX = parent.currentRowX;
        currentColX = parent.currentColX;
        numPossibleMovesX = getNumPossibleMovesX();
        currentRowO = parent.currentRowO;
        currentColO = parent.currentColO;
        numPossibleMovesO = getNumPossibleMovesO();
        depth = parent.depth + 1;
        utility = numPossibleMovesX - numPossibleMovesO;
        parent.possibleStates.add(this);
        if(parent.player == 'X') {
            player = 'O';
        } else {
            player = 'X';
        }
    }

    public Board(Board parent, int randomNum) {
        possibleStates = new ArrayList<>();
        board = new ArrayList<String>(8);
        for(int i = 0; i < 8; i++) {
            board.add(parent.board.get(i));
        }
        currentRowX = parent.currentRowX;
        currentColX = parent.currentColX;
        numPossibleMovesX = parent.numPossibleMovesX;
        currentRowO = parent.currentRowO;
        currentColO = parent.currentColO;
        numPossibleMovesO = parent.numPossibleMovesO;
        depth = 0;
        utility = parent.utility;
        player = parent.player;
    }

    public boolean isPossibleMoveX(int row, int col) {
        if(row < 0 || row > 7 || col < 0 || col > 7) {
            return false;
        }
        if(row == currentRowX && col == currentColX) {
            return false;
        }
        if(board.get(row).charAt(col) == '#' || board.get(row).charAt(col) == 'X' || board.get(row).charAt(col) == 'O') {
            return false;
        }
        if(row == currentRowX && col > currentColX) { //right
            int newColIndex = col;
            char temp;
            while(newColIndex > currentColX) {
                newColIndex--;
                temp = board.get(currentRowX).charAt(newColIndex);
                if(temp == '#' || temp == 'O') {
                    return false;
                }
            }
            if(board.get(currentRowX).charAt(newColIndex) == 'X') {
                return true;
            }
        }
        if(row == currentRowX && col < currentColX) { //left
            int newColIndex = col;
            char temp;
            while(newColIndex < currentColX) {
                newColIndex++;
                temp = board.get(currentRowX).charAt(newColIndex);
                if(temp == '#' || temp == 'O') {
                    return false;
                }
            }
            if(board.get(currentRowX).charAt(newColIndex) == 'X') {
                return true;
            }
        }
        if(row < currentRowX && col == currentColX) { //up
            int newRowIndex = row;
            char temp;
            while(newRowIndex < currentRowX) {
                newRowIndex++;
                temp = board.get(newRowIndex).charAt(currentColX);
                if(temp == '#' || temp == 'O') {
                    return false;
                }
            }
            if(board.get(newRowIndex).charAt(currentColX) == 'X') {
                return true;
            }
        }
        if(row > currentRowX && col == currentColX) { //down
            int newRowIndex = row;
            char temp;
            while(newRowIndex > currentRowX) {
                newRowIndex--;
                temp = board.get(newRowIndex).charAt(currentColX);
                if(temp == '#' || temp == 'O') {
                    return false;
                }
            }
            if(board.get(newRowIndex).charAt(currentColX) == 'X') {
                return true;
            }
        }
        if(row < currentRowX && col > currentColX) { //up right
            int newRowIndex = row;
            int newColIndex = col;
            char temp;
            while(newRowIndex < currentRowX && newColIndex > currentColX) {
                newRowIndex++;
                newColIndex--;
                temp = board.get(newRowIndex).charAt(newColIndex);
                if(temp == '#' || temp == 'O') {
                    return false;
                }
            }
            if(board.get(newRowIndex).charAt(newColIndex) == 'X') {
                return true;
            }
        }
        if(row > currentRowX && col > currentColX) { //down right
            int newRowIndex = row;
            int newColIndex = col;
            char temp;
            while(newRowIndex > currentRowX && newColIndex > currentColX) {
                newRowIndex--;
                newColIndex--;
                temp = board.get(newRowIndex).charAt(newColIndex);
                if(temp == '#' || temp == 'O') {
                    return false;
                }
            }
            if(board.get(newRowIndex).charAt(newColIndex) == 'X') {
                return true;
            }
        }
        if(row > currentRowX && col < currentColX) { //down left
            int newRowIndex = row;
            int newColIndex = col;
            char temp;
            while(newRowIndex > currentRowX && newColIndex < currentColX) {
                newRowIndex--;
                newColIndex++;
                temp = board.get(newRowIndex).charAt(newColIndex);
                if(temp == '#' || temp == 'O') {
                    return false;
                }
            }
            if(board.get(newRowIndex).charAt(newColIndex) == 'X') {
                return true;
            }
        }
        if(row < currentRowX && col < currentColX) { //up left
            int newRowIndex = row;
            int newColIndex = col;
            char temp;
            while(newRowIndex < currentRowX && newColIndex < currentColX) {
                newRowIndex++;
                newColIndex++;
                temp = board.get(newRowIndex).charAt(newColIndex);
                if(temp == '#' || temp == 'O') {
                    return false;
                }
            }
            if(board.get(newRowIndex).charAt(newColIndex) == 'X') {
                return true;
            }
        }
        return false;
    }

    public boolean isPossibleMoveO(int row, int col) {
        if(row < 0 || row > 7 || col < 0 || col > 7) {
            return false;
        }
        if(row == currentRowO && col == currentColO) {
            return false;
        }
        if(board.get(row).charAt(col) == '#' || board.get(row).charAt(col) == 'X' || board.get(row).charAt(col) == 'O') {
            return false;
        }
        if(row == currentRowO && col > currentColO) { //right
            int newColIndex = col;
            char temp;
            while(newColIndex > currentColO) {
                newColIndex--;
                temp = board.get(currentRowO).charAt(newColIndex);
                if(temp == '#' || temp == 'X') {
                    return false;
                }
            }
            if(board.get(currentRowO).charAt(newColIndex) == 'O') {
                return true;
            }
        }
        if(row == currentRowO && col < currentColO) { //left
            int newColIndex = col;
            char temp;
            while(newColIndex < currentColO) {
                newColIndex++;
                temp = board.get(currentRowO).charAt(newColIndex);
                if(temp == '#' || temp == 'X') {
                    return false;
                }
            }
            if(board.get(currentRowO).charAt(newColIndex) == 'O') {
                return true;
            }
        }
        if(row < currentRowO && col == currentColO) { //up
            int newRowIndex = row;
            char temp;
            while(newRowIndex < currentRowO) {
                newRowIndex++;
                temp = board.get(newRowIndex).charAt(currentColO);
                if(temp == '#' || temp == 'X') {
                    return false;
                }
            }
            if(board.get(newRowIndex).charAt(currentColO) == 'O') {
                return true;
            }
        }
        if(row > currentRowO && col == currentColO) { //down
            int newRowIndex = row;
            char temp;
            while(newRowIndex > currentRowO) {
                newRowIndex--;
                temp = board.get(newRowIndex).charAt(currentColO);
                if(temp == '#' || temp == 'X') {
                    return false;
                }
            }
            if(board.get(newRowIndex).charAt(currentColO) == 'O') {
                return true;
            }
        }
        if(row < currentRowO && col > currentColO) { //up right
            int newRowIndex = row;
            int newColIndex = col;
            char temp;
            while(newRowIndex < currentRowO && newColIndex > currentColO) {
                newRowIndex++;
                newColIndex--;
                temp = board.get(newRowIndex).charAt(newColIndex);
                if(temp == '#' || temp == 'X') {
                    return false;
                }
            }
            if(board.get(newRowIndex).charAt(newColIndex) == 'O') {
                return true;
            }
        }
        if(row > currentRowO && col > currentColO) { //down right
            int newRowIndex = row;
            int newColIndex = col;
            char temp;
            while(newRowIndex > currentRowO && newColIndex > currentColO) {
                newRowIndex--;
                newColIndex--;
                temp = board.get(newRowIndex).charAt(newColIndex);
                if(temp == '#' || temp == 'X') {
                    return false;
                }
            }
            if(board.get(newRowIndex).charAt(newColIndex) == 'O') {
                return true;
            }
        }
        if(row > currentRowO && col < currentColO) { //down left
            int newRowIndex = row;
            int newColIndex = col;
            char temp;
            while(newRowIndex > currentRowO && newColIndex < currentColO) {
                newRowIndex--;
                newColIndex++;
                temp = board.get(newRowIndex).charAt(newColIndex);
                if(temp == '#' || temp == 'X') {
                    return false;
                }
            }
            if(board.get(newRowIndex).charAt(newColIndex) == 'O') {
                return true;
            }
        }
        if(row < currentRowO && col < currentColO) { //up left
            int newRowIndex = row;
            int newColIndex = col;
            char temp;
            while(newRowIndex < currentRowO && newColIndex < currentColO) {
                newRowIndex++;
                newColIndex++;
                temp = board.get(newRowIndex).charAt(newColIndex);
                if(temp == '#' || temp == 'X') {
                    return false;
                }
            }
            if(board.get(newRowIndex).charAt(newColIndex) == 'O') {
                return true;
            }
        }
        return false;
    }

    public int getNumPossibleMovesX() {
        int total = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(isPossibleMoveX(i,j)) {
                    total++;
                }
            }
        }
        return total;
    }

    public int getNumPossibleMovesO() {
        int total = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(isPossibleMoveO(i,j)) {
                    total++;
                }
            }
        }
        return total;
    }

    public boolean moveX(int row, int col) {
        if(isPossibleMoveX(row, col)) {
            StringBuilder str = new StringBuilder(board.get(currentRowX));
            str.replace(currentColX, currentColX + 1, "#");
            board.set(currentRowX, str.toString());
            str = new StringBuilder(board.get(row));
            str.replace(col, col + 1, "X");
            board.set(row, str.toString());
            currentRowX = row;
            currentColX = col;
            numPossibleMovesX = getNumPossibleMovesX();
            numPossibleMovesO = getNumPossibleMovesO();
            utility = numPossibleMovesX - numPossibleMovesO;
            depth++;
            player = 'O';
            return true;
        }
        return false;
    }

    public boolean moveO(int row, int col) {
        if(isPossibleMoveO(row, col)) {
            StringBuilder str = new StringBuilder(board.get(currentRowO));
            str.replace(currentColO, currentColO + 1, "#");
            board.set(currentRowO, str.toString());
            str = new StringBuilder(board.get(row));
            str.replace(col, col + 1, "O");
            board.set(row, str.toString());
            currentRowO = row;
            currentColO = col;
            numPossibleMovesX = getNumPossibleMovesX();
            numPossibleMovesO = getNumPossibleMovesO();
            utility = numPossibleMovesX - numPossibleMovesO;
            depth++;
            player = 'X';
            return true;
        }
        return false;
    }

    public ArrayList<String> getPossibleMovesX() {
        ArrayList<String> moves = new ArrayList<>();
        StringBuilder str;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(isPossibleMoveX(i,j)) {
                    str = new StringBuilder("");
                    str.append(i);
                    str.append(j);
                    moves.add(str.toString());
                }
            }
        }
        return moves;
    }

    public void createPossibleStatesX() {
        ArrayList<String> possibleMoves = this.getPossibleMovesX();
        ArrayList<Board> list = new ArrayList<>();
        Board state;
        int row;
        int col;
        for(int i = 0; i < possibleMoves.size(); i++) {
            row = Character.getNumericValue(possibleMoves.get(i).charAt(0));
            col = Character.getNumericValue(possibleMoves.get(i).charAt(1));
            state = new Board(this);
            state.moveX(row, col);
            list.add(state);
        }
        possibleStates = list;
    }

    public ArrayList<String> getPossibleMovesO() {
        ArrayList<String> moves = new ArrayList<>();
        StringBuilder str;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(isPossibleMoveO(i,j)) {
                    str = new StringBuilder("");
                    str.append(i);
                    str.append(j);
                    moves.add(str.toString());
                }
            }
        }
        return moves;
    }

    public void createPossibleStatesO() {
        ArrayList<String> possibleMoves = this.getPossibleMovesO();
        ArrayList<Board> list = new ArrayList<>();
        Board state;
        int row;
        int col;
        for(int i = 0; i < possibleMoves.size(); i++) {
            row = Character.getNumericValue(possibleMoves.get(i).charAt(0));
            col = Character.getNumericValue(possibleMoves.get(i).charAt(1));
            state = new Board(this);
            state.moveO(row, col);
            list.add(state);
        }
        possibleStates = list;
    }

    public void printBoard() {
        System.out.print("\n  ");
        for(int i = 0; i < 8; i++) {
            System.out.print((i + 1) + " ");
        }
        System.out.println();
        for(int i = 0; i < 8; i++) {
            System.out.print((char)(i + 65) + " ");
            for(int j = 0; j < 8; j++) {
                System.out.print(board.get(i).charAt(j) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getCurrentRowX() {
        return currentRowX;
    }

    public int getCurrentColX() {
        return currentColX;
    }

    public int getUtility() {
        return utility;
    }

    public void setUtility(int val) {
        utility = val;
    }

    public void setPlayer(char letter) {
        player = letter;
    }

    @Override
    public int compareTo(Board other) {
        if(utility < other.utility) {
            return 1;
        } else if(utility == other.utility) {
            return 0;
        } else {
            return -1;
        }
    }
}
