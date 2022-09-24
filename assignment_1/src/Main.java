import java.io.IOException;
import java.rmi.dgc.VMID;

public class Main {
	public static void main(String[] args) throws IOException {
		char [][] Board = new char[0][0];
		int[] AgentX = new int[2];
		int []AgentY = new int[2];
		int[] Score = new int[0];
		int Turn = 0;
		int Food = 0;
		State x = new State(Board, AgentX, AgentY, Score, Turn, Food);
		x.read("data\\board.txt");
		// System.out.println(x);

		// Test copying objects.
		State y = new State(x);
		y.food++;
		System.out.println("State x: \n" + x + x.food + " " + x.hashCode() + "\n");
		System.out.println("State y: \n" + y + y.food + " " + y.hashCode() + "\n");

		//Game g=new Game();
		//g.test();
	}
}
