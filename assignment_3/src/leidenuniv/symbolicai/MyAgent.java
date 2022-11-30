package leidenuniv.symbolicai;

import java.util.*;

import leidenuniv.symbolicai.logic.KB;
import leidenuniv.symbolicai.logic.Predicate;
import leidenuniv.symbolicai.logic.Sentence;
import leidenuniv.symbolicai.logic.Term;


public class MyAgent extends Agent {



	@Override
	public KB forwardChain(KB kb) {
		//This method should perform a forward chaining on the kb given as argument, until no new facts are added to the KB.
		//It starts with an empty list of facts. When ready, it returns a new KB of ground facts (bounded).
		//The resulting KB includes all deduced predicates, actions, additions and deletions, and goals.
		//These are then processed by processFacts() (which is already implemented for you)
		//HINT: You should assume that forwardChain only allows *bound* predicates to be added to the facts list for now.

		// key(a)>open(X)
		// sent1
		// sent2
		// sent3

		KB cleanKB = new KB();
		Boolean result;

		HashMap<String, Predicate> facts = new HashMap<>();
		Vector<Predicate> factList = new Vector<>();

		for(Sentence sent: kb.rules()) {
			for(Predicate p: sent.conclusions) {
				if(p.bound() && !factList.contains(p) && sent.conditions.isEmpty()) {
					factList.add(p);
					facts.put(p.toString(),p);
				}
			}
		}

		// logic actual forwardChaining.
		while(!factList.isEmpty()){
			for(Sentence sent: kb.rules()){
				Vector<Predicate> conditions = new Vector<>(sent.conditions);

				Collection<HashMap<String, String>> collection = new HashSet<>();
				HashMap<String, String> substitutions = new HashMap<>();
				result = findAllSubstitions(collection, substitutions, conditions, facts);
				if(result){
					for(HashMap<String, String> sub: collection){
						for(Predicate c: sent.conclusions){
							Predicate s = substitute(c, sub);
							if(!facts.containsKey(s.toString()) && !factList.contains(s)) {
								factList.add(s);
								facts.put(s.toString(), s);
							}
						}
					}
				}
				factList.removeIf(kb::contains);
				factList.removeIf(cleanKB::contains);

				for(Predicate f: factList){
					Sentence s = new Sentence(f.toString());
					cleanKB.add(s);
				}
			}
		}
		return cleanKB;
	}

	@Override
	public boolean findAllSubstitions(Collection<HashMap<String, String>> allSubstitutions,
			HashMap<String, String> substitution, Vector<Predicate> conditions, HashMap<String, Predicate> facts) {
		//Recursive method to find *all* valid substitutions for a vector of conditions, given a set of facts
		//The recursion is over Vector<Predicate> conditions (so this vector gets shorter and shorter, the farther you are with finding substitutions)
		//It returns true if at least one substitution is found (can be the empty substitution, if nothing needs to be substituted to unify the conditions with the facts)
		//allSubstitutions is a list of all substitutions that are found, which was passed by reference (so you use it build the list of substitutions)
		//substitution is the one we are currently building recursively.
		//conditions is the list of conditions you  still need to find a subst for (this list shrinks the further you get in the recursion).
		//facts is the list of predicates you need to match against (find substitutions so that a predicate from the conditions unifies with a fact)

		boolean visited = false;
		Vector<Integer> isNegated = new Vector<>();

		if(conditions.isEmpty()){
			allSubstitutions.add(substitution);
			System.out.println(allSubstitutions);
			return !allSubstitutions.isEmpty();
		}
		for (Predicate fact: facts.values()) {
			HashMap<String, String> subCopy = new HashMap<>(substitution);
			Vector<Predicate> copyConditions = new Vector<>(conditions);
			Predicate firstCond = copyConditions.elementAt(0);
			HashMap<String, String> unify = new HashMap<>();
			Predicate sub = substitute(firstCond, subCopy);

			if(firstCond.eql && !visited) {
				isNegated.add(1);
				visited = true;
				if (!sub.eql()) {
					continue;
				}
			} else if(firstCond.not && !visited) {
				isNegated.add(1);
				visited = true;
				if(!sub.not()) {
					continue;
				}
			} else if(firstCond.neg && !visited){
//				visited = true;
				unify = unifiesWith(sub, fact);

				if(unify == null) {
					isNegated.add(0);
				} else {
					isNegated.add(1);
				}
			} else {
				isNegated.add(1);
				unify = unifiesWith(sub, fact);
			}

			if(isNegated.size() == facts.size()){
				System.out.println("Cool");
				if (isNegated.contains(0)) {
					return false;
				}
			}

			if(unify != null){
				if(!sub.neg){
					copyConditions.remove(0);
					subCopy.putAll(unify);
					findAllSubstitions(allSubstitutions, subCopy, copyConditions, facts);
				} else if(isNegated.size() == facts.size()){
					copyConditions.remove(0);
					subCopy.putAll(unify);
					findAllSubstitions(allSubstitutions, subCopy, copyConditions, facts);
				}
			}
		}
		return !allSubstitutions.isEmpty() && !isNegated.contains(0);
	}

	@Override
	public HashMap<String, String> unifiesWith(Predicate p, Predicate f) { // Done
		//Returns the valid substitution for which p predicate unifies with f
		//You may assume that Predicate f is fully bound (i.e., it has no variables anymore)
		//The result can be an empty substitution, if no subst is needed to unify p with f (e.g., if p an f contain the same constants or do not have any terms)
		//Please note because f is bound and p potentially contains the variables, unifiesWith is NOT symmetrical
		//So: unifiesWith("human(X)","human(joost)") returns X=joost, while unifiesWith("human(joost)","human(X)") returns null 
		//If no subst is found it returns null

		HashMap<String, String> results = new HashMap<>();
		Vector<Term> pTerms = p.getTerms();
		Vector<Term> fTerms = f.getTerms();
		int i = 0;
		int j = 0;

		// Handling of negation
		if(p.neg && Objects.equals(p.getName(), f.getName())){
			for(Term pT: pTerms){
				if(Objects.equals(pT.toString(), fTerms.get(j).toString())){
					return null;
				} else {
					j++;
				}
			}
			return results;
		} else if(p.neg){
			return results;
		}

		// Normal unifies with without negation handling.
		if(Objects.equals(p.getName(), f.getName())) {
			for(Term pT: pTerms){
				if(!pT.var && (!Objects.equals(pT.toString(), fTerms.get(i).toString()))){
					return null;
				} if(!Objects.equals(pT.toString(), fTerms.get(i).toString())) {
					results.put(pT.toString(), fTerms.get(i).toString());
				}
				i++;
			}
			return results;
		} else { return null; }
	}

	@Override
	public Predicate substitute(Predicate old, HashMap<String, String> s) { // Done
		// Substitutes all variable terms in predicate <old> for values in substitution <s>
		//(only if a key is present in s matching the variable name of course)
		//Use Term.substitute(s)

		Predicate subPred = new Predicate(old.toString());

		if(s == null) { return null; }

		boolean hasTerms = subPred.hasTerms();
		if(hasTerms){
			for(Term term:subPred.getTerms()){
				term.substitute(s);
			}
		}

		return subPred;
	}

	@Override
	public Plan idSearch(int maxDepth, KB kb, Predicate goal) {
		//The main iterative deepening loop
		//Returns a plan, when the depthFirst call returns a plan for depth d.
		//Ends at maxDepth
		//Predicate goal is the goal predicate to find a plan for.
		//Return null if no plan is found.
		return null;
	}

	@Override
	public Plan depthFirst(int maxDepth, int depth, KB state, Predicate goal, Plan partialPlan) {
		//Performs a depthFirst search for a plan to get to Predicate goal
		//Is a recursive function, with each call a deeper action in the plan, building up the partialPlan
		//Caps when maxDepth=depth
		//Returns (bubbles back through recursion) the plan when the state entails the goal predicate
		//Returns null if capped or if there are no (more) actions to perform in one node (state)
		//HINT: make use of think() and act() using the local state for the node in the search you are in.
		return null;
	}
}
