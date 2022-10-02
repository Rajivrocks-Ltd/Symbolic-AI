import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

public class Main {
	public static void main(String[] args) throws IOException {
//		char [][] Board = new char[0][0];
//		int[] AgentX = new int[2];
//		int []AgentY = new int[2];
//		int[] Score = new int[]{0,0};
//		int Turn = 0;
//		int Food = 0;
//
//
//		State s = new State(Board, AgentX, AgentY, Score, Turn, Food);
//		s.read("data\\board.txt");

//		System.out.println(s.legalMoves(s.turn) + " " + s.turn);
//		System.out.println(s);
//		s.execute("block"); // a
//		System.out.println(s.legalMoves(s.turn) + " " + s.turn);
//		System.out.println(s);
//		s.execute("block"); // b
//		System.out.println(s.legalMoves(s.turn) + " " + s.turn);
//		System.out.println(s);
//		s.execute("right"); // a
//		System.out.println(s.legalMoves(s.turn) + " " + s.turn);
//		System.out.println(s);
//		s.execute("left"); // b
//		System.out.println(s.legalMoves(s.turn) + " " + s.turn);
//		System.out.println(s);
//		s.execute("up"); // a
//		System.out.println(s.legalMoves(s.turn) + " " + s.turn);
//		System.out.println(s);
//		s.execute("block"); // b
//		System.out.println(s.legalMoves(s.turn) + " " + s.turn);
//		System.out.println(s);
//		s.execute("down"); // a
//		System.out.println(s.legalMoves(s.turn) + " " + s.turn);
//		System.out.println(s);
//		s.execute("block"); // b
//		System.out.println(s.legalMoves(s.turn) + " " + s.turn);
//		System.out.println(s);
//		s.execute("eat"); // a
//		System.out.println(s.legalMoves(s.turn) + " " + s.turn);
//		System.out.println(s);
//		System.out.println(s.value(s.turn));
//		System.out.println(s.turn + " " + Arrays.toString(s.score));

//		int count = 0;
//		while(!s.isLeaf()) {
//			System.out.println(s.legalMoves(s.turn) + " " + s.turn);
//			String move = s.legalMoves().get((int)(Math.random()*s.legalMoves().size()));
//			System.out.println(move);
//			System.out.println(s);
//			s.execute(move);
//			System.out.println("Score on turn of Player " + s.turn + " " + Arrays.toString(s.score));
//			count++;
//		}
//		double score = s.value(s.turn);
//		String outcome = whoWon(score);
//		System.out.println("Total moves: " + count);
//		System.out.println(score + " " + s.turn + " " + outcome);

//		Test copying objects.
//		State x = new State(Board, AgentX, AgentY, Score, Turn, Food, moves);
//		State y = x.copy(x);
//		y.food++;
//		System.out.println("State x: \n" + x + x.food + " " + x.hashCode() + "\n");
//		System.out.println("State y: \n" + y + y.food + " " + y.hashCode() + "\n");


		Game g = new Game();
		g.test();
	}

	public static String whoWon(double score){
		if(score == -1.0) { return "Lost"; }
		else if(score == 1.0) { return "Win"; }
		else { return "Tie"; }
	}

}
