import java.util.ArrayList;
import java.util.Scanner;

public class Isolation {

    public static void main(String [] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("What is the time limit per move: ");
        String time = scan.nextLine();
        while(!time.matches("[1-9][0-9]*")) {
            System.out.print("Please enter a valid integer: ");
            time = scan.nextLine();
        }
        long timeLimit = (long) (Integer.valueOf(time) * 1000);

        System.out.println("\nWho will go first?");
        System.out.println("1. Computer (X)");
        System.out.println("2. You (O)");
        String option = scan.nextLine();
        while(!option.matches("[1-2]")) {
            System.out.print("Please enter a valid integer: ");
            option = scan.nextLine();
        }
        int playerTurn = Integer.valueOf(option);

        Board board = new Board();
        Board temp;
        board.printBoard();
        int row;
        int col;
        int location[] = new int[2];
        ArrayList<String> moveSequence = new ArrayList<String>();

        while(true) {
            if(board.getNumPossibleMovesX() == 0) {
                System.out.println("You WIN");
                break;
            }
            if(board.getNumPossibleMovesO() == 0) {
                System.out.println("You LOSE");
                break;
            }
            if(playerTurn == 1) {
                System.out.println("Computer is making a move.");
                location = AlphaBeta.chooseNextMove(board);
                board.moveX(location[0], location[1]);
                board.printBoard();
                moveSequence.add(((char)(location[0] + 'A')) + "" + (location[1]+1));
                playerTurn = 2;
                printSequence(moveSequence, option);
            }
            if(playerTurn == 2) {
                System.out.println("Computer's move: " + ((char)(location[0] + 'A')) + (location[1]+1));
                System.out.println();
                System.out.print("Enter your move: ");
                long startTime = System.currentTimeMillis();
                String input = scan.nextLine();
                if(input.matches("[A-H][1-8]")) {
                    location[0] = input.charAt(0) - 65;
                    location[1] = Character.getNumericValue(input.charAt(1)) - 1;
                }
                if(System.currentTimeMillis() - startTime < timeLimit) {
                    temp = new Board(board);
                    while(!temp.moveO(location[0], location[1]) && System.currentTimeMillis() - startTime < timeLimit) {
                        board.printBoard();
                        System.out.print("Please enter a valid move: ");
                        input = scan.nextLine();
                        if(input.matches("[A-H][1-8]")) {
                            location[0] = input.charAt(0) - 65;
                            location[1] = Character.getNumericValue(input.charAt(1)) - 1;
                        }
                        temp = new Board(board);
                    }
                    board = temp;
                    board.printBoard();
                    moveSequence.add(((char)(location[0] + 'A')) + "" + (location[1]+1));
                } else {
                    System.out.println("\nYou took too long to make a move.\n");
                    board.setPlayer('X');
                    board.printBoard();
                    moveSequence.add("");
                }
                playerTurn = 1;
                printSequence(moveSequence, option);
            }
        }
    }

    public static void printSequence(ArrayList<String> list, String firstPlayer) {
        int counter = 1;
        if(Integer.valueOf(firstPlayer) == 1)
        	System.out.println("Computer vs. Opponent");
        else
        	System.out.println("Opponent vs. Computer");
        for(int i = 0; i < list.size(); i+=2) {
            if(i == list.size() - 1) {
                System.out.println(counter + ".  " + list.get(i));
            } else {
                System.out.println(counter + ".  " + list.get(i) + "          " + list.get(i+1));
            }
            counter++;
        }
        System.out.println();
    }
    
}

