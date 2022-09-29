import java.io.IOException;
import java.util.Arrays;

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
	public void test() {
		
		//System.out.println(minimax(b, b.turn, 11, 0));
		
		while (!b.isLeaf()){
			System.out.println(b.toString());
			System.out.println("Legal moves for agent with turn:"+b.legalMoves());
			b.execute(b.legalMoves().get((int)(Math.random()*b.legalMoves().size())));
		}
	}
}
