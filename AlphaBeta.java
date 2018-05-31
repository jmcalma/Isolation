import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AlphaBeta {

    public static int alphaBetaPruning(int alpha, int beta, Board state, int depth, boolean isMax, long startTime) {
        int num;
        if(depth == 0) {
            return state.getUtility();
        }
        if(state.getNumPossibleMovesX() == 0) {
            return Integer.MIN_VALUE - 10;

        }
        if(state.getNumPossibleMovesO() == 0) {
            return Integer.MAX_VALUE + 10;
        }
        if(isMax) {
            num = Integer.MIN_VALUE;
            state.createPossibleStatesX();
            for(int i = 0; i < state.possibleStates.size(); i++) {
                num = Math.max(num, alphaBetaPruning(alpha, beta, state.possibleStates.get(i), depth - 1, false, startTime));
                state.setUtility(num);
                alpha = Math.max(alpha, num);
                if(alpha >= beta) {
                    break;
                }
                if(System.currentTimeMillis() - startTime >= 20000.0) {
                    break;
                }
            }
            return num;
        } else {
            num = Integer.MAX_VALUE;
            state.createPossibleStatesO();
            for(int i = 0; i < state.possibleStates.size(); i++) {
                num = Math.min(num, alphaBetaPruning(alpha, beta, state.possibleStates.get(i), depth - 1, true, startTime));
                state.setUtility(num);
                beta = Math.min(beta, num);
                if(alpha >= beta) {
                    break;
                }
                if(System.currentTimeMillis() - startTime >= 20000.0) {
                    break;
                }
            }
            return num;
        }
    }

    public static int[] chooseNextMove(Board state) {
        int[] optimalCoord = new int[2];
        Board optimalBoard = new Board(state, 0);
        Board temp = new Board(state, 0);
        long startTime = System.currentTimeMillis();
        int depth = 4;
        while(System.currentTimeMillis() - startTime < 20000.0) {
            temp.possibleStates = new ArrayList<>();
            alphaBetaPruning(Integer.MIN_VALUE, Integer.MAX_VALUE, temp, depth, true, startTime);
            if((System.currentTimeMillis() - startTime) >= 20000.0) {
                break;
            }
            optimalBoard = temp;
            depth++;
        }
        Collections.sort(optimalBoard.possibleStates, Collections.reverseOrder());
        int index;
        if(optimalBoard.possibleStates.size() > 1) {
            Random random = new Random();
            index = random.nextInt(optimalBoard.possibleStates.size()/2);
        } else {
            index = 0;
        }
        optimalCoord[0] = optimalBoard.possibleStates.get(index).getCurrentRowX();
        optimalCoord[1] = optimalBoard.possibleStates.get(index).getCurrentColX();
        System.out.println();
        return optimalCoord;
    }

}
