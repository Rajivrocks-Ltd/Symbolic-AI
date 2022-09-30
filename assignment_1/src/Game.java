import com.sun.nio.sctp.SctpSocketOption;

import java.io.IOException;
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
		Vector<String> moves = new Vector<>();

		b = new State(Board, AgentX, AgentY, Score, Turn, Food, moves);
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
			State bestState = s;
			State copyState = s.copy(s);
			for(String move: copyState.legalMoves(copyState.turn)) {
				copyState.execute(move);
				State minimaxEvalState = minimax(copyState, s.whoisOpponent(), maxDepth, depth + 1);
				double minimaxEvalValue = minimaxEvalState.value(forAgent);
				if(minimaxEvalValue > best) {
					best = Math.max(best, minimaxEvalValue);
					bestState = minimaxEvalState;
				}
			}
			return bestState;
		} else {
			double best = Double.POSITIVE_INFINITY;
			State bestState = s;
			State copyState = s.copy(s);
			for(String move: copyState.legalMoves(copyState.turn)) {
				copyState.execute(move);
				State minimaxEvalState = minimax(copyState, s.whoisOpponent(), maxDepth, depth + 1);
				double minimaxEvalValue = minimaxEvalState.value(forAgent);
				if(minimaxEvalValue < best) {
					best = Math.min(best, minimaxEvalValue);
					bestState = minimaxEvalState;
				}
			}
			return bestState;
		}
	}

	public void test() {
		
//		System.out.println(minimax(b, b.turn, 11, 0));
		int count = 0;

		System.out.println(b);

		while (!b.isLeaf()){
			State endState = minimax(b, b.turn, 11, 0);
			for (String moves: endState.moves){
				System.out.println(b.legalMoves(b.turn) + " " + b.turn);
				System.out.println("Current move is: " + moves);
				b.execute(moves);
				System.out.println(b);
			}
//			System.out.println(b.toString());
//			System.out.println("Legal moves for agent with turn:"+b.legalMoves());
//			b.execute(b.legalMoves().get((int)(Math.random()*b.legalMoves().size())));
		}
		System.out.println("Total moves: " + count);
		System.out.println(b.value(b.turn));
	}
}
