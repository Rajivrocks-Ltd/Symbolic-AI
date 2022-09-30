import com.sun.nio.sctp.SctpSocketOption;

import java.io.IOException;

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

		if(forAgent == s.turn) {
			double best = Double.NEGATIVE_INFINITY;
			State copyState = s.copy(s);
			for(String move: s.legalMoves(copyState.turn)) {
				copyState.execute(move);
				State minimaxState = minimax(copyState, copyState.whoisOpponent(), maxDepth, depth + 1);
				double minimaxValue = minimaxState.value(forAgent);
				best = Math.max(best, minimaxValue);
			}
			return copyState;
		} else {
			double best = Double.POSITIVE_INFINITY;
			State copyState = s.copy(s);
			for(String move: s.legalMoves(copyState.turn)) {
				copyState.execute(move);
				State minimaxState = minimax(copyState, copyState.whoisOpponent(), maxDepth, depth + 1);
				double minimaxValue = minimaxState.value(forAgent);
				best = Math.min(best, minimaxValue);
			}
			return copyState;
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
