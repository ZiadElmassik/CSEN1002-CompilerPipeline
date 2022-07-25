package csen1002.main.task1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Write your info here
 * 
 * @name Ziad Amr Mahmoud Elmassik
 * @id 43-3517
 * @labNumber 10
 */

public class DFA {
	/**
	 * DFA constructor
	 * 
	 * @param description is the string describing a DFA
	 */
	HashMap<String, String[]> States = new HashMap<String, String[]>();
	HashSet<String> goalStates;

	public DFA(String description) {

		String[] goalSplit = description.split("#");

		String[] goalStatesStringArray = goalSplit[1].split(",");
		List<String> goalList = Arrays.asList(goalStatesStringArray);
		goalStates = new HashSet<String>(goalList);

		String[] stateTransitions = goalSplit[0].split(";");

		for(int i=0; i<stateTransitions.length; i++){
			String currentState = stateTransitions[i];

			String[] currentStateSplit = currentState.split(",");
			String id = currentStateSplit[0];
			String[] transitions = {currentStateSplit[1], currentStateSplit[2]};

			States.put(id, transitions);
		}

	}

	/**
	 * Returns true if the string is accepted by the DFA and false otherwise.
	 * 
	 * @param input is the string to check by the DFA.
	 * @return if the string is accepted or not.
	 */
	public boolean run(String input) {

		String currentState = "0";
		String[] currentTransitions = States.get("0");
		System.out.println(currentState + " " + currentTransitions[0] + " " + currentTransitions[1]);
		for (int i=0; i<input.length();i++){

			int pointer =  Integer.parseInt(("" + input.charAt(i)));
			String nextState = "";

			if (pointer == 0) {
				nextState = currentTransitions[0];
			}
			else{
				nextState = currentTransitions[1];
			}

			currentState = nextState;
			currentTransitions = States.get(nextState);
			System.out.println(currentState + " " + currentTransitions[0] + " " + currentTransitions[1]);
		}

		if (goalStates.contains(currentState))
			return  true;
		else
			return false;
	}
}
