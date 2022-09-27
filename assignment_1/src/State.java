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
	Vector<String> moves;

    public State(char[][] board, int[] agentX, int[] agentY, int[] score, int turn, int food) {
        this.board = board;
        this.agentX = agentX;
        this.agentY = agentY;
        this.score = score;
        this.turn = turn;
        this.food = food;
    }

    public void read(String file) throws IOException {
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
//        score = new int[0];

        try {
            while (reader.hasNextLine()) {
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

        for (char[] r : board) {
            for (char c : r) {
                if (c == '*') {
                    food++;
                }
            }
        }
    }

	/*
		returns true if game ending conditions are met
		conditions are: if no food is left or if either player no legal moves left
    */
	public boolean isLeaf() {
		if(this.food == 0){
			return true;
		}
        return legalMoves().isEmpty();
    }

	public Vector<String> legalMoves() {
		Vector<String> possible_moves;
		possible_moves = legalMoves(this.turn);
		return possible_moves;
	}

    /*
        Check the values of the current position of the given agent and the values of the positions around the agent and returns the possible legal moves.
    */
	public Vector<String> legalMoves(int agent) {
        Vector<String> possible_moves = new Vector<>();
		char current_agent;
		if(agent == 0){
			current_agent = 'A';
		}
		else{
			current_agent = 'B';
		}
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                if(board[i][j] == current_agent){
                    if(board[i][j] == current_agent){
                        possible_moves.add("block");
                    }
                    if(board[i][j] == '*'){
                        possible_moves.add("eat");
                    }
                    if(board[i+1][j] != '#'){
                        possible_moves.add("down");
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
		return possible_moves;
	}

	public void execute(String action) {
		char agent;
		Vector<int[]> agents = new Vector<>();
		agents.add(this.agentX);
		agents.add(this.agentY);
		if(this.turn == 0){
			agent = 'A';
		}
		else{
			agent = 'B';
		}
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				if(action.equals("block")){
					board[i][j] = '#';
				}
				if(action.equals("up")){
					board[i-1][j] = agent;
					agents.get(this.turn)[0] = i-1;
					agents.get(this.turn)[1] = j;
					board[i][j] = ' ';
				}
				if(action.equals("down")){
					board[i+1][j] = agent;
					agents.get(this.turn)[0] = i+1;
					agents.get(this.turn)[1] = j;
					board[i][j] = ' ';
				}
				if(action.equals("right")){
					board[i][j+1] = agent;
					agents.get(this.turn)[0] = i;
					agents.get(this.turn)[1] = j+1;
					board[i][j] = ' ';
				}
				if(action.equals("left")){
					board[i][j-1] = agent;
					agents.get(this.turn)[0] = i;
					agents.get(this.turn)[1] = j-1;
					board[i][j] = ' ';
				}
				if(action.equals("eat")){
					board[i][j] = agent;
					this.food -=1;
					this.score[this.turn] +=1;
				}
			}
		}
		this.moves.add(agent + ":" + action);
	}

    private int whoisOpponent(){
        int opponent;
        if(turn == 0) { opponent = 1; } else { opponent = 0; }
        return opponent;
    }

    public double value(int agent){
        if(food == 0) {
            return winnerBasedOnScore();
        }

        int opponent = whoisOpponent();

        if(legalMoves(agent).isEmpty()) { return -1; }
        else if(legalMoves(opponent).isEmpty()) { return 1;}
        else {return winnerBasedOnScore();}
    }

    private int winnerBasedOnScore(){
        int opponent = whoisOpponent();

        if(score[turn] > score[opponent]) {return 1;}
        else if (score[opponent] > score[turn]) { return -1; }
        else { return 0; }
    }

    /*
        Return a new State object that has copied all fields of the State input. (Could make this static for a more
        elegant solution BUT the assignment doesn't allow this :( )
    */
    public State copy(State state) {
        return new State(state.board, state.agentX, state.agentY, state.score, state.turn, state.food);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        // Loop through all rows
        for (char[] chars : board) {
            for (char aChar : chars) s.append(aChar).append(" ");
            s.append("\n");
        }
        return s.toString();
    }
}
