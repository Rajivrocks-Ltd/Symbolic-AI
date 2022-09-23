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

	public Vector<String> legalMoves(int agent) {
		return new Vector<>();
	}

	public void execute(String action) {

	}

}
