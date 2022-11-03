import java.io.File;
import java.io.IOException;
import java.util.*;

public class State {
	char [][] board;
	int[] agentX, agentY;
	int[] score;
	int turn;
	int food;
	Vector<String> moves;

    public State(char[][] board, int[] agentX, int[] agentY, int[] score, int turn, int food, Vector<String> moves) {
        this.board = board;
        this.agentX = agentX;
        this.agentY = agentY;
        this.score = score;
        this.turn = turn;
        this.food = food;
        this.moves = moves;
    }

    public int[] getAgentX(){
        return agentX;
    }

    public int[] getAgentY(){
        return agentY;
    }

    public void setAgentX(int[] agentX){
        this.agentX = agentX;
    }

    public void setAgentY(int[] agentY){
        this.agentY = agentY;
    }

    public void read(String file) throws IOException {
        File f = new File(file);
        Scanner reader = new Scanner(f);
        String[] hw = reader.nextLine().split(" ");
        int w = Integer.parseInt(hw[0]);
        int h = Integer.parseInt(hw[1]);
        board = new char[w][h];
        score = new int[]{0,0};

        try {
            while (reader.hasNextLine()) {
                for (int i = 0; i < w; i++) {
                    char[] s = reader.nextLine().toCharArray();
                    for (int j = 0; j < h; j++) {
                        if(s[j] == 'A') { setAgentX(new int[]{i,j}); board[i][j] = ' ';}
                        else if(s[j] == 'B') { setAgentY(new int[]{i, j}); board[i][j] = ' '; }
                        else { board[i][j] = s[j]; }
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
        if(legalMoves(0).isEmpty()) { return true; }
        else return legalMoves(1).isEmpty();
    }

    public Vector<String> legalMoves() {
		return legalMoves(turn);
	}

    /*
        Check the values of the current position of the given agent and the values of the positions around the agent and returns the possible legal moves.
    */
	public Vector<String> legalMoves(int agent) {
        Vector<String> possible_moves = new Vector<>();
		int[] current_agent;
        int[] loopPos;
		if(agent == 0) {
			current_agent = getAgentX();
		}
		else {
			current_agent = getAgentY();
		}
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                loopPos = new int[]{i,j};
                if(Arrays.equals(loopPos, current_agent)){
                    if(i != 0 && board[i - 1][j] != '#') {
                        possible_moves.add("up");
                    }
                    if(j < board[i].length && board[i][j + 1] != '#') {
                        possible_moves.add("right");
                    }
                    if(board[i + 1][j] != '#') {
                        possible_moves.add("down");
                    }
                    if(j != 0 && board[i][j - 1] != '#') {
                        possible_moves.add("left");
                    }
                    if(board[i][j] == '*') {
                        possible_moves.add("eat");
                    }
                    if(Arrays.equals(loopPos, current_agent) && board[i][j] != '#' && board[i][j] != '*'){
                        possible_moves.add("block");
                    }
                }
            }
        }
		return possible_moves;
	}

	public void execute(String action) {
		int[] agent;
        int[] loopPos;
        char agentChar;

		if(turn == 0) {
			agent = getAgentX();
            agentChar = 'A';
		}
		else {
			agent = getAgentY();
            agentChar = 'B';
		}
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
                loopPos = new int[]{i,j};
				if(Arrays.equals(loopPos, agent)){
                    if(action.equals("block")) {
                        board[i][j] = '#';
                        turn = whoisOpponent();
                    }
                    if(i != 0 && action.equals("up")) {
                        setAgentXYpos(i - 1, j);
                        turn = whoisOpponent();
                    }
                    if(action.equals("down")) {
                        setAgentXYpos(i + 1, j);
                        turn = whoisOpponent();
                    }
                    if(action.equals("right")) {
                        setAgentXYpos(i, j + 1);
                        turn = whoisOpponent();
                    }
                    if(action.equals("left")) {
                        setAgentXYpos(i, j - 1);
                        turn = whoisOpponent();
                    }
                    if(action.equals("eat")) {
                        board[i][j] = ' ';
                        food--;
                        score[turn]++;
                        turn = whoisOpponent();
                    }
                }
			}
		}
		moves.add(agentChar + " : " + action + " ");
	}

    private void setAgentXYpos(int i, int j) {
        if(turn == 0) {setAgentX(new int[]{i, j});} else { setAgentY(new int[]{i, j}); }
    }

    public int whoisOpponent(){
        int opponent;
        if(turn == 0) { opponent = 1; } else { opponent = 0; }
        return opponent;
    }

    public int minimaxOpponent(int agent) {
        if(agent == 0) { return 1; } else { return 0; }
    }

    public double value(int agent){
        if(food == 0) {
            return winnerBasedOnScore(agent);
        }

        int opponent = minimaxOpponent(agent);

        if(legalMoves(agent).isEmpty()) { return -1; }
        else if(legalMoves(opponent).isEmpty()) { return 1;}
        else {return 0;}
    }

    private int winnerBasedOnScore(int agent){
        int opponent = minimaxOpponent(agent);

        if(score[turn] > score[opponent]) {return 1;}
        else if (score[opponent] > score[turn]) { return -1; }
        else { return 0; }
    }

    /*
        Return a new State object that has copied all fields of the State input. (Could make this static for a more
        elegant solution BUT the assignment doesn't allow this :( )
    */

    public State copy() {
        return new State(deepCopyBoard(), deepCopyIntArray(agentX), deepCopyIntArray(agentY), deepCopyIntArray(score),
                this.turn, this.food, deepCopyVector());
    }

    private char[][] deepCopyBoard() {
        char[][] newBoard = new char[board[0].length][board.length];
        for (int i = 0; i < board[0].length; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, board.length);
        }
        return newBoard;
    }

    private int[] deepCopyIntArray(int[] array) {
        return new int[]{array[0], array[1]};
    }

    private Vector<String> deepCopyVector() {
        return new Vector<>(moves);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        // Loop through all rows
        for (char[] chars : board) {
            for (char aChar : chars) s.append(aChar).append(" ");
            s.append("\n");
        }
        s.append("Current players turn: ").append(turn).append("\n");
        s.append("Current value of the state: ").append(value(turn)).append("\n");
        s.append("Is state a leaf: ").append(isLeaf()).append("\n");
        s.append("AgentX Position ").append(Arrays.toString(agentX)).append("\n");
        s.append("AgentY Position ").append(Arrays.toString(agentY)).append("\n");
        s.append("Moves made so far: ").append(moves).append("\n");
        s.append("Current players legal moves: ").append(legalMoves(turn)).append("\n");
        s.append("Opponents legal moves: ").append(legalMoves(whoisOpponent())).append("\n");
        s.append("Current score: ").append(Arrays.toString(score)).append("\n");
        s.append("Food left: ").append(food).append("\n");
        return s.toString();
    }
}
