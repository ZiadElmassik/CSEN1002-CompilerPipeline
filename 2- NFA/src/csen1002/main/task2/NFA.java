package csen1002.main.task2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Write your info here
 * 
 * @name Ziad Amr Elmassik
 * @id 43-3517
 * @labNumber 10
 */
public class NFA{

	HashSet<String> statesNFA = new HashSet<String>();
	HashMap<String, HashSet<String>> EpsilonClosure = new HashMap<String, HashSet<String>>();
	HashMap<String, HashSet<String>> zeroTransitionsNFA = new HashMap<String, HashSet<String>>();
	HashMap<String, HashSet<String>> oneTransitionsNFA = new HashMap<String, HashSet<String>>();
	HashSet<String> goalStatesNFA = new HashSet<String>();

	HashSet<String> startStateDFA = new HashSet<String>();
	HashMap<HashSet<String>, ArrayList<HashSet<String>>> statesDFA = new HashMap<HashSet<String>, ArrayList<HashSet<String>>>();
	HashSet<HashSet<String>> goalsDFA = new HashSet<HashSet<String>>();

	/**
	 * NFA constructor
	 * 
	 * @param description is the string describing a NFA
	 */
	public NFA(String description) {

		//---------------------------- Initialize hashset with all states ----------------------
		String descriptionCopy = description;
		nfaStatesInit(descriptionCopy);
		//// System.out.println(statesNFA.toString());

		//--------------------------- First Split to separate transitions -------------------
		String[] transitionSplit = description.split("#", -1);

		//----------------------------- Goal States of NFA -------------------------------
		String[] goalStates = transitionSplit[3].split(",");

		//// for (int i=0; i<goalStates.length; i++){
		////	System.out.print(" " + goalStates[i]);
		//// }


		//--------------------------- Fill NFA Goals HashSet ------------------------------
		goalStatesNFAInit(goalStates);

		//--------------- Add the NFA's 0 transitions to the right hashmap -----------------
		zeroTransitionsNFAInit(transitionSplit[0]);
		System.out.println("Zero Transitions: " + zeroTransitionsNFA.toString());

		//---------------- Add the NFA's 1 transitions to the right hashmap -----------------
		oneTransitionsNFAInit(transitionSplit[1]);
		System.out.println("One Transitions: " + oneTransitionsNFA.toString());

		//----------------- Add the immediate epsilon transitions first --------------------
		immediateEpsilonTransitionsInit(transitionSplit[2]);
		System.out.println("Immediate Epsilon Transitions: " + EpsilonClosure.toString());

		//----------------- Create the complete epsilon closure --------------------
		findEpsilonClosure();
		System.out.println("Epsilon Closure: " + EpsilonClosure.toString());

		//----------------- Use zero and one transitions with epsilon closure to construct DFA --------------------
		constructDFA();
		System.out.println("Output DFA: " + statesDFA);


	}

	public void constructDFA(){

		//Initialize start state
		startStateDFA = EpsilonClosure.get("0");

		Queue<HashSet<String>> stateCreation = new LinkedList<>();
		stateCreation.add(startStateDFA);

		while (!stateCreation.isEmpty()){

			HashSet<String> currState = new HashSet<String>();
			currState = stateCreation.poll();

			if (!statesDFA.containsKey(currState)){

				checkIfGoalState(currState);

				ArrayList<HashSet<String>> transititions = new ArrayList<HashSet<String>>(2);

				HashSet<String> newStateFromZero = obtainStateFromZeroTransitions(currState);
				HashSet<String> newStateFromOne = obtainStateFromOneTransitions(currState);

				transititions.add(newStateFromZero);
				transititions.add(newStateFromOne);

				HashSet<String> zeroTransitionState = transititions.get(0);
				HashSet<String> oneTransitionState = transititions.get(1);
				//System.out.println(zeroTransitionState.toString());
				//System.out.println(oneTransitionState.toString());

				// Zero Transition Checks :
				// If dead state, add it to hashmap, along with 0, and 1 transitions to itself. DON'T ADD TO QUEUE.
				// If not, add it to queue.
				if (zeroTransitionState.contains("$")){

					ArrayList<HashSet<String>> deadTransitions = new ArrayList<HashSet<String>>(2);
					HashSet<String> deadDest = new HashSet<String>();
					deadDest.add("$");
					deadTransitions.add(deadDest);
					deadTransitions.add(deadDest);
					statesDFA.put(zeroTransitionState, deadTransitions);
					//System.out.println("Dead State Added: " + statesDFA.toString());

				}

				else{
					stateCreation.add(zeroTransitionState);
				}


				// One Transition Checks :
				// If dead state, add it to hashmap, along with 0, and 1 transitions to itself. DON'T ADD TO QUEUE.
				// If not, add it to queue.
				if (oneTransitionState.contains("$")){

					ArrayList<HashSet<String>> deadTransitions = new ArrayList<HashSet<String>>(2);
					HashSet<String> deadDest = new HashSet<String>();
					deadDest.add("$");
					deadTransitions.add(deadDest);
					deadTransitions.add(deadDest);
					statesDFA.put(oneTransitionState, deadTransitions);

				}

				else{
					stateCreation.add(oneTransitionState);
				}

				statesDFA.put(currState, transititions);

			}

		}

		//// System.out.println(statesDFA.toString());
		//// System.out.println(goalsDFA.toString());

	}

	public void checkIfGoalState(HashSet<String> currState) {

		for (String state : currState){

			if (goalStatesNFA.contains(state)){

				goalsDFA.add(currState);
				break;

			}

		}

	}

	public HashSet<String> obtainStateFromOneTransitions(HashSet<String> poll){

		boolean createDeadState = true;
		HashSet<String> newState = new HashSet<String>();

		for (String state : poll){

			HashSet<String> oneTransitions = new HashSet<String>();
			oneTransitions = oneTransitionsNFA.get(state);

			if (oneTransitions != null){

				createDeadState = false;
				//// System.out.println(oneTransitions.toString());
				HashSet<String> epsilonClosureOfState = new HashSet<String>();

				for (String destState : oneTransitions){
					epsilonClosureOfState = EpsilonClosure.get(destState);
					newState.addAll(epsilonClosureOfState);
				}

			}
		}

		if (createDeadState)
			newState.add("$");

		return newState;

	}

	public HashSet<String> obtainStateFromZeroTransitions(HashSet<String> poll) {

		boolean createDeadState = true;
		HashSet<String> newState = new HashSet<String>();

		for (String state : poll){

			HashSet<String> zeroTransitions = new HashSet<String>();
			zeroTransitions = zeroTransitionsNFA.get(state);

			if (zeroTransitions != null){

				createDeadState = false;
				//// System.out.println(zeroTransitions.toString());
				HashSet<String> epsilonClosureOfState = new HashSet<String>();

				for (String destState : zeroTransitions){
					epsilonClosureOfState = EpsilonClosure.get(destState);
					newState.addAll(epsilonClosureOfState);
				}

			}
		}

		if (createDeadState)
			newState.add("$");

		return newState;

	}

	public void nfaStatesInit(String descriptionCopy){

		String descriptionWithHashtagsAsCommas = descriptionCopy.replace("#", ",");
		String descriptionWithSemiColonsAsCommas = descriptionWithHashtagsAsCommas.replace(";", ",");
		String[] allStatesWithSomeRepitions = descriptionWithSemiColonsAsCommas.split(",");

		for (int i=0; i< allStatesWithSomeRepitions.length; i++){

			if (!statesNFA.contains(allStatesWithSomeRepitions[i])){

				statesNFA.add(allStatesWithSomeRepitions[i]);

			}
		}
	}

	public void immediateEpsilonTransitionsInit(String epsilonTransitions){

		for (String state : statesNFA){
			HashSet<String> initHash = new HashSet<String>();
			initHash.add(state);
			EpsilonClosure.put(state, initHash);

		}

		if (epsilonTransitions != "") {
			String[] epTransit = epsilonTransitions.split(";");

			for (int i=0; i<epTransit.length; i++){

				String[] statePair = epTransit[i].split(",");
				String source = statePair[0];
				String dest = statePair[1];

				if (EpsilonClosure.containsKey(source)){

					HashSet<String> destStates = EpsilonClosure.get(source);
					if (!destStates.contains(dest)){
						destStates.add(dest);
						EpsilonClosure.replace(source, destStates);
					}
				}

				else{
					HashSet<String> addState = new HashSet<String>();
					addState.add(dest);
					EpsilonClosure.put(source, addState);
				}
			}
		}

		//// System.out.println("Initial Epsilon Closure: " + EpsilonClosure.toString());

	}

	public void findEpsilonClosure(){

		boolean changeDetected = true;

		while (changeDetected){

			changeDetected = false;
			//System.out.println("I just entered while loop");
			for (HashMap.Entry<String, HashSet<String>> entry : EpsilonClosure.entrySet()){
				HashSet<String> closure = entry.getValue();
				//System.out.println(closure.toString());
				HashSet<String> closureCopy = new HashSet<String>();
				closureCopy.addAll(closure);
				String[] closureArray = new String[closureCopy.size()];
				closureCopy.toArray(closureArray);

				for (int i=0; i<closureArray.length;i++){

					String state = closureArray[i];
					if (state != entry.getKey()){

						HashSet<String> potentialNewClosure = closureCopy;
						HashSet<String> transitionsForUnion = EpsilonClosure.get(state);

						potentialNewClosure.addAll(transitionsForUnion);

						if (potentialNewClosure.size() != closure.size()){
							entry.setValue(potentialNewClosure);
							changeDetected = true;
							break;
						}

					}

				}

			}

		}

	}

	public void oneTransitionsNFAInit(String oneTransitions){

		if (oneTransitions != "") {
			String[] oneTransit = oneTransitions.split(";");

			for (int i=0; i<oneTransit.length; i++){

				String[] statePair = oneTransit[i].split(",");
				String source = statePair[0];
				String dest = statePair[1];

				if (oneTransitionsNFA.containsKey(source)){

					HashSet<String> destStates = oneTransitionsNFA.get(source);
					if (!destStates.contains(dest)){
						destStates.add(dest);
						oneTransitionsNFA.replace(source, destStates);
					}
				}

				else{
					HashSet<String> addState = new HashSet<String>();
					addState.add(dest);
					oneTransitionsNFA.put(source, addState);
				}
			}
		}
	}

	public void zeroTransitionsNFAInit(String zeroTransitions){

		if (zeroTransitions != "") {
			String[] zeroTransit = zeroTransitions.split(";");

			for (int i=0; i<zeroTransit.length; i++){

				String[] statePair = zeroTransit[i].split(",");
				String source = statePair[0];
				String dest = statePair[1];

				if (zeroTransitionsNFA.containsKey(source)){

					HashSet<String> destStates = zeroTransitionsNFA.get(source);
					if (!destStates.contains(dest)){
						destStates.add(dest);
						zeroTransitionsNFA.replace(source, destStates);
					}
				}

				else{
					HashSet<String> addState = new HashSet<String>();
					addState.add(dest);
					zeroTransitionsNFA.put(source, addState);
				}
			}
		}
	}

	public void goalStatesNFAInit(String[] goalStates){

		for(int i= 0; i<goalStates.length; i++){
			//System.out.println("Current: " + goalStates[i]);
			goalStatesNFA.add(goalStates[i]);
		}
	}

	/**
	 * Returns true if the string is accepted by the NFA and false otherwise.
	 * 
	 * @param input is the string to check by the NFA.
	 * @return if the string is accepted or not.
	 */
	public boolean run(String input) {

		HashSet<String> currState = startStateDFA;
		ArrayList<HashSet<String>> zeroOne = new ArrayList<HashSet<String>>(2);
		zeroOne = statesDFA.get(currState);

		for (int i=0; i< input.length(); i++){
			int pointer =  Integer.parseInt(("" + input.charAt(i)));
			HashSet<String> nextState = new HashSet<String>();
			if (pointer == 0){
				nextState = zeroOne.get(0);
			}

			else if(pointer == 1){
				nextState = zeroOne.get(1);
			}

			currState = nextState;
			zeroOne = statesDFA.get(nextState);

		}

		if (goalsDFA.contains(currState)){
			return true;
		}

		else{
			return false;
		}

	}



}
