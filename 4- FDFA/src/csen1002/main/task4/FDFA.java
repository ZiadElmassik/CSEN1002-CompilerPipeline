package csen1002.main.task4;

import java.util.*;

/**
 * Write your info here
 * 
 * @name Ziad Elmassik
 * @id 43-3517
 * @labNumber 10
 */
public class FDFA {
	/**
	 * FDFA constructor
	 * 
	 * @param description is the string describing a FDFA
	 */
	HashMap<String, String[]> States = new HashMap<String, String[]>();
	HashSet<String> goalStates;
	HashMap<String, String> Actions = new HashMap<String, String>();


	public FDFA(String description) {

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
			String action = currentStateSplit[3];

			States.put(id, transitions);
			Actions.put(id, action);

			//System.out.println("{" + id + ": " + "[" + transitions[0] +", " + transitions[1] + "]" + "}, ");
		}

		//System.out.println(Actions.toString());

	}

	/**
	 * Returns a string of actions.
	 * 
	 * @param input is the string to simulate by the FDFA.
	 * @return string of actions.
	 */
	public String run(String input) {

		int R = 0;
		int L = 0;
		Stack<String> stack = new Stack<String>();
		stack.push("0");
		String output = "";
		boolean goalMet = false;

		while(!goalMet){

			String Qr = "";
			if (stack.empty()){
				break;
			}

			String currentState = "";
			while (L<input.length()){
				//System.out.println(L);
				String currentInput = "" + input.charAt(L);
				//System.out.println("current input: " + currentInput);
				currentState = stack.peek();

				if (currentInput.equals("0")) {
					//System.out.println("Made it in 0");
					String nextState = States.get(currentState)[0];
					stack.push(nextState);
					//System.out.println("New Stack: " + stack.toString());
				}
				else if (currentInput.equals("1")){
					//System.out.println("Made it in 1");
					String nextState = States.get(currentState)[1];
					stack.push(nextState);
				}
				currentState = stack.peek();
				L++;
			}

			Qr = currentState;

			//System.out.println("Stack after forward: " + stack.toString());
			if (goalStates.contains(currentState)){
				goalMet = true;
				output += input.substring(R, input.length()) + "," + Actions.get(currentState) + ";";
			}

			else{
				boolean goalBackMet = false;
				while (!goalBackMet){
					String backState = stack.pop();
					//System.out.println("Stack: " + stack.toString());

					if (stack.empty()){
						output += input.substring(R, input.length()) + "," + Actions.get(Qr) + ";";
						break;
					}

					if (goalStates.contains(backState)){
						//System.out.println("Back Goal Met");
						goalBackMet = true;
						output += input.substring(R, L) + "," + Actions.get(backState) + ";";
						//System.out.println("Added goal: " + output);
						R = L;
						//System.out.println("L here is: " + L);

						while (!stack.empty()){
							stack.pop();
						}
						stack.push("0");
					}
					else{
							L--;
							//System.out.println("New L: " + L);

						//System.out.println("New L: " + L);
						//System.out.println("R: " + R);
					}
				}
			}


		}
		System.out.println("Final Output: " + output);
		return output;
	}
}
