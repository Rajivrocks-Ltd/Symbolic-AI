import com.sun.nio.sctp.SctpSocketOption;

import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

public class Game {
	State b;
	public Game() throws IOException {
		char[][] Board = new char[0][0];
		int[] AgentX = new int[2];
		int []AgentY = new int[2];
		int[] Score = new int[]{0,0};
		int Turn = 0;
		int Food = 0;

		b = new State(Board, AgentX, AgentY, Score, Turn, Food);
		b.read("data/board.txt");
	}

	public State minimax(State s, int forAgent, int maxDepth, int depth) {
		if(depth == maxDepth) {
			return s;
		} else if(s.isLeaf()) {
			return s;
		}

		if(forAgent == 0) {
			double best = Double.NEGATIVE_INFINITY;
			State bestState = s.copy();
			for(String move: s.legalMoves(0)) {
				State copyState = s.copy();
				copyState.execute(move);
				State minimaxEvalState = minimax(copyState, 1, maxDepth, depth + 1);
				double minimaxEvalValue = minimaxEvalState.value(forAgent);
				if(minimaxEvalValue > best) {
					best = minimaxEvalValue;
					bestState = minimaxEvalState;
				}
				copyState.turn = 0;
			}
			return bestState;
		} else {
			double best = Double.POSITIVE_INFINITY;
			State bestState = s;
			for(String move: s.legalMoves(1)) {
				State copyState = s.copy();
				copyState.execute(move);
				State minimaxEvalState = minimax(copyState, 0, maxDepth, depth + 1);
				double minimaxEvalValue = minimaxEvalState.value(forAgent);
				if(minimaxEvalValue < best) {
					best = minimaxEvalValue;
					bestState = minimaxEvalState;
				}
				copyState.turn = 1;
			}
			return bestState;
		}
	}

	public void test() {

		State test = minimax(b, b.turn, 11, 0);
		System.out.println(test);
		System.out.println(test.moves);
		System.out.println(Arrays.toString(test.agentX) + " " + Arrays.toString(test.agentY));

//		while (!b.isLeaf()){
//			System.out.println(b.toString());
//			System.out.println("Legal moves for agent with turn:"+b.legalMoves());
//			b.execute(b.legalMoves().get((int)(Math.random()*b.legalMoves().size())));
//		}
	}

	public static String whoWon(double score){
		if(score == -1.0) { return "Lost"; }
		else if(score == 1.0) { return "Win"; }
		else { return "Tie"; }
	}

}
