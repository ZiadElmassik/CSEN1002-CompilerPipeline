package csen1002.main.task7;

import java.util.*;

/**
 * Write your info here
 * 
 * @name Ziad Elmassik
 * @id 43-3517
 * @labNumber 10
 */
public class LL1CFG {
	/**
	 * LL1 CFG constructor
	 * 
	 * @param description is the string describing an LL(1) CFG, first, and follow as represented in the task description.
	 */
	HashMap<String, ArrayList<String>> Rules = new HashMap<String, ArrayList<String>>();
	HashMap<String, ArrayList<String>> First = new HashMap<String, ArrayList<String>>();
	HashMap<String, ArrayList<String>> Follow = new HashMap<String, ArrayList<String>>();
	HashMap<String, HashMap<String, String>> FirstFromRule = new HashMap<String, HashMap<String, String>>();
	HashSet<String> Variables = new HashSet<String>();
	HashSet<String> Terminals = new HashSet<String>();

	HashMap<String, HashMap<String, String>> parsingTable = new HashMap<String, HashMap<String, String>>();

	public LL1CFG(String description) {

		String[] splitOnHash = description.split("#");

		String[] rulesString = splitOnHash[0].split(";");
		String[] firstString = splitOnHash[1].split(";");
		String[] followString = splitOnHash[2].split(";");

		//initialize rules
		for (String rule : rulesString){
			String[] variableAndProductions = rule.split(",");
			String variable = variableAndProductions[0];
			ArrayList<String> productions = new ArrayList<String>();
			for (int i=1; i< variableAndProductions.length; i++){
				productions.add(variableAndProductions[i]);
			}
			Rules.put(variable, productions);
		}

		//initialize first
		for (String first : firstString){
			String[] variableAndProductions = first.split(",");
			String variable = variableAndProductions[0];
			ArrayList<String> productions = new ArrayList<String>();
			for (int i=1; i< variableAndProductions.length; i++){
				String currFirst = variableAndProductions[i];
				productions.add(currFirst);
//				for (int j=0; j<currFirst.length(); j++){
//					String currItem = currFirst.charAt(j) + "";
//					productions.add(currItem);
//				}
				First.put(variable, productions);
			}

		}

		//initialize follow
		for (String follow : followString){
			String[] variableAndProductions = follow.split(",");
			String variable = variableAndProductions[0];
			ArrayList<String> productions = new ArrayList<String>();
			for (int i=1; i< variableAndProductions.length; i++){
				String currFollow = variableAndProductions[i];
				for (int j=0; j<currFollow.length(); j++){
					String currItem = currFollow.charAt(j) + "";
					productions.add(currItem);
				}
			}
			Follow.put(variable, productions);
		}

		//Parse Table
		for (Map.Entry<String, ArrayList<String>> rule : Rules.entrySet()){
			Variables.add(rule.getKey());
			ArrayList<String> productions = rule.getValue();
			for (String production : productions){
				for (int i=0; i<production.length(); i++){
					if (Character.isLowerCase(production.charAt(i)) && (!(production.charAt(i) + "").equals("e"))){
						Terminals.add(production.charAt(i) + "");
					}
				}
			}
		}

		//Initialize rows and columns
		for (String v : Variables){
			HashMap<String, String> column = new HashMap<String, String>();
			for (String t : Terminals){
				column.put(t, "");
				//System.out.println(column.toString());
			}
			column.put("$", "");
			parsingTable.put(v, column);
		}

		//Fill parse table
//		for (Map.Entry<String, HashMap<String, String>> parseTableEntry : parsingTable.entrySet()){
//			String LHS = parseTableEntry.getKey();
//			for (Map.Entry<String,String> column : parseTableEntry.getValue().entrySet()){
//				String currCol = column.getKey();
//				ArrayList<String> possibleRules = Rules.get(LHS);
//				boolean noRulesWorked = true;
//				for (String currRule : possibleRules){
//					String leftmostVariableOrTerminal = currRule.charAt(0) + "";
//					if (leftmostVariableOrTerminal.equals(currCol) || (Character.isUpperCase(currRule.charAt(0)) && First.get(leftmostVariableOrTerminal).contains(currCol))){
//						parsingTable.get(LHS).put(currCol, currRule);
//						noRulesWorked = false;
//						break;
//					}
//					else if ((Character.isUpperCase(currRule.charAt(0))) && First.get(leftmostVariableOrTerminal).contains("e") && Follow.get(LHS).contains(currCol)){
//						parsingTable.get(LHS).put(currCol, currRule);
//						noRulesWorked = false;
//						break;
//					}
//					else if(leftmostVariableOrTerminal.equals("e") && Follow.get(LHS).contains(currCol)){
//						parsingTable.get(LHS).put(currCol, currRule);
//						noRulesWorked = false;
//						break;
//					}
//				}
//				if (noRulesWorked){
//					parsingTable.get(LHS).put(currCol, "_");
//				}
//			}
//		}

		for (Map.Entry<String, ArrayList<String>> rule : Rules.entrySet()){
			String LHS = rule.getKey();
			ArrayList<String> productions = rule.getValue();
			for (int i=0; i<productions.size(); i++){
				String firstOfRule = First.get(LHS).get(i);
				for (int j=0; j<firstOfRule.length(); j++){
					if(!firstOfRule.equals("e")){
						parsingTable.get(LHS).put(firstOfRule.charAt(j) + "", productions.get(i));
					}
				}
			}
		}


		for (Map.Entry<String, HashMap<String, String>> parseTableEntry : parsingTable.entrySet()){
			String LHS = parseTableEntry.getKey();
			for (Map.Entry<String,String> column : parseTableEntry.getValue().entrySet()){
				String currCol = column.getKey();
				ArrayList<String> possibleRules = Rules.get(LHS);
				for (String currRule : possibleRules){
					String leftmostVariableOrTerminal = currRule.charAt(0) + "";

					if ((Character.isUpperCase(currRule.charAt(0))) && First.get(leftmostVariableOrTerminal).contains("e") && Follow.get(LHS).contains(currCol)){
						parsingTable.get(LHS).put(currCol, currRule);
						break;
					}
					if(leftmostVariableOrTerminal.equals("e") && Follow.get(LHS).contains(currCol)){
						parsingTable.get(LHS).put(currCol, currRule);
						break;
					}
				}
			}
		}


		System.out.println(Variables.toString());
		System.out.println(Terminals.toString());
		System.out.println(Rules.toString());
		System.out.println(First.toString());
		System.out.println(Follow.toString());
		System.out.println(parsingTable.toString());

	}
	/**
	 * Returns A string encoding a derivation is a comma-separated sequence of sentential forms each representing a step in the derivation..
	 * 
	 * @param input is the string to be parsed by the LL(1) CFG.
	 * @return returns a string encoding a left-most derivation.
	 */
	public String parse(String input) {

		LinkedHashSet<String> steps = new LinkedHashSet<String>();
		Stack<String> stack = new Stack<String>();

		stack.push("$");
		stack.push("S");
		input += "$";
		steps.add("S");

		boolean accept = false;
		boolean reject = false;

		String processedInput = "";

		while (!accept && !reject){
			String inputBeginning = input.charAt(0) + "";
			String topOfStack = stack.peek() + "";

//			System.out.println("Top: " +topOfStack);
//			System.out.println("Input :" + inputBeginning);
			if (topOfStack.equals("$") && inputBeginning.equals("$")){
				steps.add(processedInput);
				accept = true;
				break;
			}

			else if (topOfStack.equals("$") && !inputBeginning.equals("$")){
				steps.add("ERROR");
				reject = true;
				break;
			}
			else{
				if (Character.isLowerCase(topOfStack.charAt(0))){
					//Match
					if (topOfStack.equals(input.charAt(0) +"")){
						processedInput += input.charAt(0);
						String ruleInStack = "";
						String currStep = "";
						stack.pop();
						Stack<String> stackToBeUsedInAddingToOutput = new Stack<String>();
						stackToBeUsedInAddingToOutput = (Stack<String>) stack.clone();

						while (stackToBeUsedInAddingToOutput.peek() != "$"){
							ruleInStack += stackToBeUsedInAddingToOutput.pop();
						}

						currStep = processedInput + ruleInStack;
						steps.add(currStep);
						input = input.substring(1);
					}

					else{
						steps.add("ERROR");
						reject = true;
					}
				}

				else{

					String parsingTableEntry = parsingTable.get(topOfStack).get(input.charAt(0) + "");

					if (parsingTableEntry.equals("")){
						steps.add("ERROR");
//						System.out.println(topOfStack);
						reject = true;
					}

					else {
						stack.pop();
						String ruleInStack = "";
						String currStep = "";

						if (!parsingTableEntry.equals("e")){
							for (int i=parsingTableEntry.length()-1; i>=0; i--){
								stack.push(parsingTableEntry.charAt(i) + "");
							}
						}
						Stack<String> stackToBeUsedInAddingToOutput = new Stack<String>();
						stackToBeUsedInAddingToOutput = (Stack<String>) stack.clone();

						while (stackToBeUsedInAddingToOutput.peek() != "$"){
							ruleInStack += stackToBeUsedInAddingToOutput.pop();
						}
						currStep = processedInput + ruleInStack;
						steps.add(currStep);
					}

				}
			}

		}
		String outputRemoveBrackets = steps.toString().replace("[","").replace("]", "");
		String output = outputRemoveBrackets.replace(" ", "");
		System.out.println(steps.toString());

		return output;
	}

}
