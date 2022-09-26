import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;

public class State {
	char [][] board;
	int[] agentX, agentY;
	int[] score;
	int turn;
	int food;

	public State(char [][] board, int[] agentX,int[] agentY,int[] score, int turn, int food ) {
		this.board = board;
		this.agentX = agentX;
		this.agentY = agentY;
		this.score = score;
		this.turn = turn;
		this.food = food;
	}

	public void read(String file) throws IOException{
		File f = new File(file);
		Scanner reader = new Scanner(f);
		String[] hw = reader.nextLine().split(" ");
		int h = Integer.parseInt(hw[0]);
		int w = Integer.parseInt(hw[1]);
		agentX = new int[2];
		agentY = new int[2];
		board = new char[w][h];
		food = 0;
		turn = 0;
		score = new int[0];

		try {
			while(reader.hasNextLine()){
				for (int i = 0; i < w; i++) {
					char[] s = reader.nextLine().toCharArray();
					for (int j = 0; j < s.length; j++) {
						board[i][j] = s[j];
					}
				}
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

		reader.close();

		for(char[] r: board) {
			for(char c: r){
				if(c == '*') { food++; }
			}
		}
	}

	public String toString(){
		StringBuilder s = new StringBuilder();
		// Loop through all rows
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++)
				s.append(board[i][j]).append(" ");
		return s.toString();
	}

	public boolean isLeaf() {
		return true;
	}

	public Vector<String> currentlegalMoves() {
		Vector<String> possible_moves = new Vector<>();
		possible_moves = legalMoves(this.turn);
		return possible_moves;
	}

    /*
        Check the values of the current position of the given agent and the values of the positions around the agent and returns the possible legal moves.
    */
	public Vector<String> legalMoves(int agent) {
        Vector<String> possible_moves = new Vector<>();
        if(agent == 0){
            for (int i = 0; i < board.length; i++){
                for (int j = 0; j < board[i].length; j++){
                    if(board[i][j] == 'A'){
                        if(board[i][j] == 'A'){
                            possible_moves.add("block");
                        }
                        if(board[i][j] == '*'){
                            possible_moves.add("eat");
                        }
                        if(board[i+1][j] != '#'){
                            possible_moves.add("down");
                            System.out.println(board[i+1][j]);
                        }
                        if(board[i-1][j] != '#'){
                            possible_moves.add("up");
                        }
                        if(board[i][j+1] != '#'){
                            possible_moves.add("right");
                        }
                        if(board[i][j-1] != '#'){
                            possible_moves.add("left");
                        }
                    }
                }
            }
			   
        }
        if(agent == 1){
            for (int i = 0; i < board.length; i++){
                for (int j = 0; j < board[i].length; j++){
                    if(board[i][j] == 'B'){
                        if(board[i][j] == 'B'){
                            possible_moves.add("block");
                        }
                        if(board[i][j] == '*'){
                            possible_moves.add("eat");
                        }
                        if(board[i+1][j] != '#'){
                            possible_moves.add("down");
                            System.out.println(board[i+1][j]);
                        }
                        if(board[i-1][j] != '#'){
                            possible_moves.add("up");
                        }
                        if(board[i][j+1] != '#'){
                            possible_moves.add("right");
                        }
                        if(board[i][j-1] != '#'){
                            possible_moves.add("left");
                        }
                    }
                }
            }
			   
        }
		return possible_moves;
	}

	public void execute(String action) {

	}

}
