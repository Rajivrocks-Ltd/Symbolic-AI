package leidenuniv.symbolicai;

import java.io.File;
import java.util.*;

import leidenuniv.symbolicai.environment.Maze;
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

		// Test UnifiesWith() and Substitute()
		// **********************************************************************

//		HashMap<String, String> hs = new HashMap<>();
//
//		Sentence sent = new Sentence("human(X)");
//		Sentence sent1 = new Sentence("human(joost)");
//		Predicate p = new Predicate(sent);
//		Predicate p1 = new Predicate(sent1);
//
//		hs.put("X", "joost");

//		System.out.println(a.substitute(p, hs));
//		System.out.println(a.unifiesWith(p, p1));

		// **********************************************************************

		//Test findAllSubstitutions()
		// **********************************************************************

//		Vector<Predicate> conditions = new Vector<>();
//		HashMap<String, Predicate> facts = new HashMap<>();
//		Collection<HashMap<String, String>> allSubs = new HashSet<>();
//		HashMap<String, String> subs = new HashMap<>();
//
//		Predicate fact = new Predicate("human(joost)");
//		Predicate fact1 = new Predicate("car(lamborghini)");
//		Predicate fact2 = new Predicate("plane(jetstream)");
//		Predicate fact3 = new Predicate("car(ferrari)");
//
//		facts.put(fact.toString(), fact);
//		facts.put(fact1.toString(), fact1);
//		facts.put(fact2.toString(), fact2);
//		facts.put(fact3.toString(), fact3);
//
//		conditions.add(new Predicate("human(joost)"));
//		conditions.add(new Predicate("car(X)"));
//
//		System.out.println(a.findAllSubstitions(allSubs, subs, conditions, facts));

		// **********************************************************************

		Predicate p = new Predicate("human(joost)");
		Predicate p1 = new Predicate("human(joost)");

		HashMap<String, String> cool = a.unifiesWith(p, p1);
		Predicate test = a.substitute(p, cool);
		System.out.println(cool);



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

	}
}
