import java.io.File;
import java.io.IOException;
import java.util.*;

public class State {
	char [][] board;
	int[] agentX, agentY;
	int[] score;
	int turn;
	int food;
	Vector<String> moves = new Vector<>();

    public State(char[][] board, int[] agentX, int[] agentY, int[] score, int turn, int food) {
        this.board = board;
        this.agentX = agentX;
        this.agentY = agentY;
        this.score = score;
        this.turn = turn;
        this.food = food;
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
//        agentX = new int[2];
//        agentY = new int[2];
//        food = 0;
//        turn = 0;

        try {
            while (reader.hasNextLine()) {
                for (int i = 0; i < w; i++) {
                    char[] s = reader.nextLine().toCharArray();
                    for (int j = 0; j < h; j++) {
                        if(s[j] == 'A') { setAgentX(new int[]{i,j}); board[i][j] = ' ';}
                        else if(s[j] == 'B') { setAgentY(new int[]{i, j}); board[i][j] = ' '; }
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

        System.out.println("Agent x Location: " + agentX[0] + " " + agentX[1]);
        System.out.println("Agent y Location: " + agentY[0] + " " + agentY[1]);

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
		Vector<String> possible_moves;
		possible_moves = legalMoves(turn);
		return possible_moves;
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
                    if(Arrays.equals(loopPos, current_agent) && board[i][j] != '#' && board[i][j] != '*'){
                        possible_moves.add("block");
                    }
                    if(board[i][j] == '*') {
                        possible_moves.add("eat");
                    }
                    if(board[i + 1][j] != '#') {
                        possible_moves.add("down");
                    }
                    if(i != 0 && board[i - 1][j] != '#') {
                        possible_moves.add("up");
                    }
                    if(board[i][j + 1] != '#') {
                        possible_moves.add("right");
                    }
                    if(j != 0 && board[i][j - 1] != '#') {
                        possible_moves.add("left");
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
                        checkNextMove(i - 1, j, i , j, agentChar);
                        turn = whoisOpponent();
                    }
                    if(action.equals("down")) {
                        setAgentXYpos(i + 1, j);
                        checkNextMove(i + 1, j, i, j, agentChar);
                        turn = whoisOpponent();
                    }
                    if(action.equals("right")) {
                        setAgentXYpos(i, j + 1);
                        checkNextMove(i, j + 1, i, j, agentChar);
                        turn = whoisOpponent();
                    }
                    if(action.equals("left")) {
                        setAgentXYpos(i, j - 1);
                        checkNextMove(i, j - 1, i, j, agentChar);
                        turn = whoisOpponent();
                    }
                    if(action.equals("eat")) {
                        board[i][j] = agentChar;
                        food--;
                        score[turn]++;
                        turn = whoisOpponent();
                    }
                }
			}
		}
		moves.add(agentChar + " : " + action + " ");
	}

    private void checkNextMove(int adjustedI, int adjustedJ, int i, int j, char agent) {
        if (board[adjustedI][adjustedJ] == '*' && !prevMoveIsBlock()) {
            board[i][j] = ' ';
        } else if(board[i][j] == '*' && board[adjustedI][adjustedJ] == '*') {
            board[i][j] = board[i][j];
        } else if(board[adjustedI][adjustedJ] == '*' && prevMoveIsBlock()) {
            board[i][j] = board[i][j];
        } else if(board[adjustedI][adjustedJ] != '*' && !prevMoveIsBlock()) {
            if(!isCurrentSpotFood(i, j) && !isCurrentSpotBlock(i, j)) {
                board[adjustedI][adjustedJ] = agent;
                board[i][j] = ' ';
            }  else if(prevMoveIsBlock() && board[adjustedI][adjustedJ] == ' ') {
            board[adjustedI][adjustedJ] = agent;
            } else if(board[i][j] == '*') {
            board[adjustedI][adjustedJ] = agent;
            }
        } else if(prevMoveIsBlock() && board[adjustedI][adjustedJ] == ' ') {
            board[adjustedI][adjustedJ] = agent;
        }
    }

    private boolean isCurrentSpotFood(int i, int j) {
        return board[i][j] == '*';
    }

    private  boolean isCurrentSpotBlock(int i, int j) {
        return board[i][j] == '#';
    }

    private boolean prevMoveIsBlock() {
        return Objects.equals(lastAgentAction(moves), "block");
    }

    private String lastAgentAction(Vector<String> moves) {
        ArrayList<String> movesList = new ArrayList<>(moves);
        if(movesList.size() < 2) { return " "; }
        else {
            String[] action = movesList.get(movesList.size() - 2).split(" ");
            return action[2];
        }
    }

    private void setAgentXYpos(int i, int j) {
        if(turn == 0) {setAgentX(new int[]{i, j});} else { setAgentY(new int[]{i, j}); }
    }

    public int whoisOpponent(){
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
