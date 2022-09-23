import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		char [][] Board = new char[0][0];
		int[] AgentX = new int[2];
		int []AgentY = new int[2];
		int[] Score = new int[0];
		int Turn = 0;
		int Food = 0;
		State s = new State(Board, AgentX, AgentY, Score, Turn, Food);
		s.read("data\\board.txt");
		System.out.println(s);
		System.out.println("Hello World");

		//Game g=new Game();
		//g.test();
	}
}
