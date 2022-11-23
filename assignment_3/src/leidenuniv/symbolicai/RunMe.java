package leidenuniv.symbolicai;

import java.io.Console;
import java.io.File;
import java.sql.SQLSyntaxErrorException;
import java.util.*;

import leidenuniv.symbolicai.environment.Maze;
import leidenuniv.symbolicai.logic.KB;
import leidenuniv.symbolicai.logic.Predicate;
import leidenuniv.symbolicai.logic.Sentence;

public class RunMe {
	//This is our main program class
	// It loads a world, makes an agent and then keeps the agent alive by allowing it to complete it's sense think act cycle 
	public static void main(String[] args) {
		//Load a world
		Maze w=new Maze(new File("data/prison.txt"));
		//Create an agent
		//Agent a=new MyAgent();
		Agent a=new MyAgent();
		//Load the rules and static knowledge for the different steps in the agent cycle
		a.loadKnowledgeBase("percepts", new File("data/percepts.txt"));
		a.loadKnowledgeBase("program", new File("data/program.txt"));
		a.loadKnowledgeBase("actions", new File("data/actions.txt"));
		
		//If you need to test on a simpler file, you may use this one and comment out all the other KBs:
		//a.loadKnowledgeBase("program", new File("data/family.txt"));


		//findAllSubs
		//**************************************************************************
		KB kb = new KB();

		HashMap<String, Predicate> facts = new HashMap<>();
		HashMap<String, String> substitution = new HashMap<>();
		Vector<Predicate> conditions = new Vector<>();
		Collection<HashMap<String, String>> collection = new HashSet<>();

		//Facts
		Predicate fact1 = new Predicate("mens(henk,dutch)");
		Predicate fact2 = new Predicate("mens(joost,dutch)");
		Predicate fact3 = new Predicate("mens(sacha,dutch)");
		Predicate fact4 = new Predicate("nationality(joost,dutch)");
		Predicate fact6 = new Predicate("nationality(joost,Belgian)");
		Predicate fact5 = new Predicate("nationality(sacha,dutch)");

		Sentence sent1 = new Sentence(fact1.toString());
		Sentence sent2 = new Sentence(fact2.toString());
		Sentence sent3 = new Sentence(fact3.toString());
		Sentence sent4 = new Sentence(fact4.toString());
		Sentence sent5 = new Sentence(fact5.toString());
		Sentence sent6 = new Sentence(fact6.toString());
		kb.add(sent1);
		kb.add(sent2);
		kb.add(sent3);
		kb.add(sent4);
		kb.add(sent5);
		kb.add(sent6);

		facts.put(fact1.toString(), fact1);
		facts.put(fact2.toString(), fact2);
		facts.put(fact3.toString(), fact3);
		facts.put(fact4.toString(), fact4);
		facts.put(fact5.toString(), fact5);
		facts.put(fact6.toString(), fact6);

		//All predicates for the condition
		Predicate first = new Predicate("mens(X,Z)");
		Predicate second = new Predicate("nationality(X,Y)");
		Predicate third = new Predicate("!=(Z,Y)");
		conditions.add(0, first);
		conditions.add(1, second);
		conditions.add(2, third);

		Sentence condition = new Sentence("mens(X,Z)&nationality(X,Y)&=(Z,Y)>citizen(X,Z)");
		kb.add(condition);
		KB result = a.forwardChain(kb);
		System.out.println("===================================================");
		System.out.println(result.rules());

//		a.findAllSubstitions(collection, substitution, conditions, facts);
//		System.out.println(collection);

		//**************************************************************************

//		Scanner io= new Scanner(System.in);
//
//		while (true) {
//			//have the agent run the sense-think-act loop.
//			a.cycle(w);
//
//			//wait for an enter
//			System.out.println("Press <enter> in the java console to continue next cycle");
//			String input = io.nextLine();
//		}
//
	}
}
