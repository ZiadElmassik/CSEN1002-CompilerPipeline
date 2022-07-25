package csen1002.main.task6;

import java.util.*;

/**
 * Write your info here
 * 
 * @name Ziad ELmassik
 * @id 43-3517
 * @labNumber 10
 */

public class FFCFG {

	/**
	 * Constructs a CFG for which the First and Follow are to be computed
	 * 
	 * @param description A string representation of a CFG as specified in the task
	 *                    description
	 */
	LinkedHashMap<String, ArrayList<String>> rules = new LinkedHashMap<String, ArrayList<String>>();
	LinkedHashSet<String> terminals = new LinkedHashSet<String>();
	LinkedHashSet<String> variables = new LinkedHashSet<String>();

	LinkedHashMap<String, TreeSet<String>> First = new LinkedHashMap<String, TreeSet<String>>();
	LinkedHashMap<String, TreeSet<String>> Follow = new LinkedHashMap<String, TreeSet<String>>();

	HashSet<String> addDollarSign = new HashSet<String>();

	public FFCFG(String description) {

		String[] splitSemiColon = description.split(";");

		for (int i=0; i< splitSemiColon.length; i++){
			String[] splitComma = splitSemiColon[i].split(",");
			ArrayList<String> currRules = new ArrayList<String>();
			String currVariable = "";
			for (int j=0; j<splitComma.length; j++){

				if (j == 0){
					currVariable = splitComma[j];
					variables.add(splitComma[j]);
					TreeSet<String> init = new TreeSet<String>();

					First.put(splitComma[j], init);
					Follow.put(splitComma[j], init);
				}
				String currSetOfRules = "";
				if (j >0){
					currSetOfRules = splitComma[j];
					currRules.add(currSetOfRules);
				}

				for (int k=0; k<currSetOfRules.length(); k++){
					char currentTerminalOrVariable = currSetOfRules.charAt(k);
					String currentTerminalOrVariableAsString = currentTerminalOrVariable + "";
					if (Character.isLowerCase(currentTerminalOrVariable) && !currentTerminalOrVariableAsString.equals("e")){
						terminals.add(currentTerminalOrVariableAsString);
					}
				}
			}
			rules.put(currVariable, currRules);
		}

		System.out.println(First.toString());
		System.out.println(Follow.toString());
		System.out.println(variables.toString());
		System.out.println(terminals.toString());
		System.out.println(rules);

	}

	/**
	 * Calculates the First of each variable in the CFG.
	 * 
	 * @return A string representation of the First of each variable in the CFG,
	 *         formatted as specified in the task description.
	 */
	public String first() {
		for (String terminal : terminals){
			TreeSet<String> firstOfTerminal = new TreeSet<String>();
			firstOfTerminal.add(terminal);
			First.put(terminal, firstOfTerminal);
		}
//		System.out.println("First");
//		System.out.println(First.toString());

		boolean change = true;
		while (change){
			//System.out.println("Change: " + change);
			change = false;
			for (Map.Entry<String, ArrayList<String>> currentVarRules : rules.entrySet()){

				String currVar = currentVarRules.getKey();
				ArrayList<String> currRules = currentVarRules.getValue();

				for (String currRule : currRules){
					boolean epsilonIntersectionAll = true;
					for (int i=0; i<currRule.length(); i++){
						String currTerminalOrVariable = currRule.charAt(i) + "";
						if (!currTerminalOrVariable.equals("e")){
							TreeSet firstOfCurrentTerminalOrVariable = First.get(currTerminalOrVariable);
//							System.out.println(firstOfCurrentTerminalOrVariable.toString());
							if (!firstOfCurrentTerminalOrVariable.contains("e")){
								epsilonIntersectionAll = false;
								break;
							}
						}
						else{
							TreeSet<String> currFirst = First.get(currVar);
							currFirst.add("e");
							if (!currFirst.equals(First.get(currVar))){
								First.put(currVar, currFirst);
								change = true;
							}
						}

					}
					if (epsilonIntersectionAll == true){
						TreeSet<String> currFirst = First.get(currVar);
						currFirst.add("e");
						if (!currFirst.equals(First.get(currVar))){
							First.put(currVar, currFirst);
							change = true;
						}

					}
					for (int j=0; j<currRule.length(); j++){
						if (j == 0){
							String currentTerminalOrVariable = currRule.charAt(j) + "";
							TreeSet<String> firstOfCurrentTerminalOrVariable = First.get(currentTerminalOrVariable);
							if (!currentTerminalOrVariable.equals("e")){
								//System.out.println(firstOfCurrentTerminalOrVariable.toString());
								TreeSet<String> firstOfCurrentTerminalOrVariableWithoutEpsilon = new TreeSet<String>();

								for (String rule : firstOfCurrentTerminalOrVariable){
									if (!rule.equals("e"))
										firstOfCurrentTerminalOrVariableWithoutEpsilon.add(rule);
								}
								TreeSet<String> currFirst = First.get(currVar);
								TreeSet<String> currFirstDeepCopy = new TreeSet<String>();
								for (String deepCopy : currFirst){
									currFirstDeepCopy.add(deepCopy);
								}
								currFirstDeepCopy.addAll(firstOfCurrentTerminalOrVariableWithoutEpsilon);
								//System.out.println("Change happened: " + !currFirstDeepCopy.equals(First.get(currVar)));
								if (!currFirstDeepCopy.equals(First.get(currVar))){
									First.put(currVar, currFirstDeepCopy);
									change = true;
								}
							}
						}
						else{
							int k = j-1;
							boolean epsilonIntersectionAllBefore = true;
							while(k >= 0){
								String currentTerminalOrVariableBefore = currRule.charAt(k) + "";
								TreeSet<String> firstOfCurrentTerminalOrVariableBefore = First.get(currentTerminalOrVariableBefore);
								if(!firstOfCurrentTerminalOrVariableBefore.contains("e")){
									epsilonIntersectionAllBefore = false;
									break;
								}
								k--;
							}
							if (epsilonIntersectionAllBefore == true){
								String currentTerminalOrVariable = currRule.charAt(j) + "";
								TreeSet<String> firstOfCurrentTerminalOrVariable = First.get(currentTerminalOrVariable);
								if (!currentTerminalOrVariable.equals("e")){
									TreeSet<String> firstOfCurrentTerminalOrVariableWithoutEpsilon = new TreeSet<String>();

									for (String rule : firstOfCurrentTerminalOrVariable){
										if (!rule.equals("e"))
											firstOfCurrentTerminalOrVariableWithoutEpsilon.add(rule);
									}
									TreeSet<String> currFirst = First.get(currVar);
									TreeSet<String> currFirstDeepCopy = new TreeSet<String>();
									for (String deepCopy : currFirst){
										currFirstDeepCopy.add(deepCopy);
									}
									currFirstDeepCopy.addAll(firstOfCurrentTerminalOrVariableWithoutEpsilon);
									if (!currFirstDeepCopy.equals(First.get(currVar))){
										First.put(currVar, currFirstDeepCopy);
										change = true;
									}
								}
							}
						}
					}
				}
			}
		}
		System.out.println(First.toString());

		LinkedHashMap<String, TreeSet<String>> outputHash = new LinkedHashMap<String, TreeSet<String>>();

		for (Map.Entry<String, TreeSet<String>> firstEntry : First.entrySet()){
			char key = firstEntry.getKey().charAt(0);
			if (!Character.isLowerCase(key)){
				outputHash.put(firstEntry.getKey(), firstEntry.getValue());
			}
		}
		String outputRemoveEqual = outputHash.toString().replace("=[", ",");
		String outputRemoveRightBracket = outputRemoveEqual.replace("],",";");
		String outputRemoveComma = outputRemoveRightBracket.replace(", ", "");
		String outputRemoveLeftParan = outputRemoveComma.replace("{","");
		String outputRemoveRightParan = outputRemoveLeftParan.replace("]}", "");
		String outputRemoveSpace = outputRemoveRightParan.replace(" ", "");
		System.out.println(outputRemoveSpace);

		return outputRemoveSpace;
	}

	/**
	 * Calculates the Follow of each variable in the CFG.
	 * 
	 * @return A string representation of the Follow of each variable in the CFG,
	 *         formatted as specified in the task description.
	 */
	public String follow() {

		first();

		addDollarSign.add("S");
		boolean change = true;
		while (change){
			change = false;

			for (Map.Entry<String, ArrayList<String>> currVarRule : rules.entrySet()){
				String currVar = currVarRule.getKey();
				ArrayList<String> currRules = currVarRule.getValue();
				for (String currRule : currRules){
					for (int i=0; i<currRule.length(); i++){
						char currentTerminalOrVariable = currRule.charAt(i);
						String currentTerminalOrVariableAsString = currRule.charAt(i) + "";

						if (Character.isUpperCase(currentTerminalOrVariable)){

							TreeSet<String> followOfCurrVariable = new TreeSet<String>();
							followOfCurrVariable = Follow.get(currentTerminalOrVariableAsString);

							if (i < (currRule.length()-1)){
								int j = i+1;
								String nextTerminalOrVariable = "" + currRule.charAt(j);

								if (!nextTerminalOrVariable.equals("e")){

									boolean allFirstsAfterContainEpsilon = true;
									while (j<currRule.length()){
										String first = currRule.charAt(j) + "";
										TreeSet<String> firstOfNextTerminalOrVariable = new TreeSet<String>();
										firstOfNextTerminalOrVariable = First.get(first);

										if (!firstOfNextTerminalOrVariable.contains("e")){
											allFirstsAfterContainEpsilon = false;
											break;
										}
										j++;
									}
									if (allFirstsAfterContainEpsilon){
										TreeSet<String> followOfLHS = new TreeSet<String>();
										followOfLHS = Follow.get(currVar);
										TreeSet<String> followOfRHS = new TreeSet<String>();
										followOfRHS = Follow.get(currentTerminalOrVariableAsString);

										TreeSet<String> followRHSDeepCopy = new TreeSet<String>();
										for (String deepCopy : followOfRHS){
											followRHSDeepCopy.add(deepCopy);
										}
										followRHSDeepCopy.addAll(followOfLHS);
										if (!followOfRHS.equals(followRHSDeepCopy)){
											Follow.put(currentTerminalOrVariableAsString, followRHSDeepCopy);
											change = true;
										}
									}

									int k = i+1;

									boolean firstOfPreviousVariableContainedEpsilon  = false;

									while (k<currRule.length()){

										TreeSet<String> firstOfNextTerminalOrVariable = new TreeSet<String>();
										String first = currRule.charAt(k) + "";
										firstOfNextTerminalOrVariable = First.get(first);
										TreeSet<String> followOfCurrVariableInTheLoop = new TreeSet<String>();
										followOfCurrVariableInTheLoop = Follow.get(currentTerminalOrVariableAsString);

										if (firstOfPreviousVariableContainedEpsilon == true){
											TreeSet<String> firstOfNextTerminalOrVariableWithoutEpsilon = new TreeSet<String>();

											for (String item : firstOfNextTerminalOrVariable){
												if (!item.equals("e")){
													firstOfNextTerminalOrVariableWithoutEpsilon.add(item);
												}
											}
											TreeSet<String> deepCopyOfCurrFollow = new TreeSet<String>();
											for (String deep : followOfCurrVariableInTheLoop){
												if (!deep.equals("e")){
													deepCopyOfCurrFollow.add(deep);
												}
											}
											deepCopyOfCurrFollow.addAll(firstOfNextTerminalOrVariableWithoutEpsilon);
											if (!deepCopyOfCurrFollow.equals(followOfCurrVariableInTheLoop)){
												Follow.put(currentTerminalOrVariableAsString, deepCopyOfCurrFollow);
												change = true;
											}
										}

										if (firstOfNextTerminalOrVariable.contains("e")){

											TreeSet<String> firstOfNextTerminalOrVariableWithoutEpsilon = new TreeSet<String>();

											for (String item : firstOfNextTerminalOrVariable){
												if (!item.equals("e")){
													firstOfNextTerminalOrVariableWithoutEpsilon.add(item);
												}
											}

											TreeSet<String> deepCopyOfCurrFollow = new TreeSet<String>();
											for (String deep : followOfCurrVariableInTheLoop){
												if (!deep.equals("e")){
													deepCopyOfCurrFollow.add(deep);
												}
											}
											deepCopyOfCurrFollow.addAll(firstOfNextTerminalOrVariableWithoutEpsilon);
											if (!deepCopyOfCurrFollow.equals(followOfCurrVariableInTheLoop)){
												Follow.put(currentTerminalOrVariableAsString, deepCopyOfCurrFollow);
												change = true;
											}
											firstOfPreviousVariableContainedEpsilon = true;
										}
										else {

											TreeSet<String> deepCopyOfCurrFollow = new TreeSet<String>();
											for (String deep : followOfCurrVariableInTheLoop){
												if (!deep.equals("e")){
													deepCopyOfCurrFollow.add(deep);
												}
											}
											deepCopyOfCurrFollow.addAll(firstOfNextTerminalOrVariable);
											if (!deepCopyOfCurrFollow.equals(followOfCurrVariableInTheLoop)){
												Follow.put(currentTerminalOrVariableAsString, deepCopyOfCurrFollow);
												change = true;
											}
											firstOfPreviousVariableContainedEpsilon = false;
											break;
										}
										k++;
									}
								}
							}
							else{
								TreeSet<String> followOfLHS = new TreeSet<String>();
								followOfLHS = Follow.get(currVar);
								TreeSet<String> followOfRHS = new TreeSet<String>();
								followOfRHS = Follow.get(currentTerminalOrVariableAsString);

								if (addDollarSign.contains(currVar)){
									addDollarSign.add(currentTerminalOrVariableAsString);
								}
								TreeSet<String> followOfLHSWithoutEpsilon = new TreeSet<String>();

								for (String LHS : followOfLHS){
									if (!LHS.equals("e")){
										followOfLHSWithoutEpsilon.add(LHS);
									}
								}

								TreeSet<String> followRHSDeepCopy = new TreeSet<String>();
								for (String deepCopy : followOfRHS){
									if (!deepCopy.equals("e"))
										followRHSDeepCopy.add(deepCopy);
								}
								followRHSDeepCopy.addAll(followOfLHSWithoutEpsilon);
								if (!followOfRHS.equals(followRHSDeepCopy)){
									Follow.put(currentTerminalOrVariableAsString, followRHSDeepCopy);
									change = true;
								}
							}
						}
					}
				}
			}
		}

		String output = "";
		int i = 0;
		for (Map.Entry<String, TreeSet<String>> entry : Follow.entrySet()){
			ArrayList<String> list = new ArrayList<String>(entry.getValue());
			if (addDollarSign.contains(entry.getKey())){
				list.add("$");
			}
			String listAsStringRemoveLeftBracket = list.toString().replace("[", "");
			String listAsStringRemoveRightBracket = listAsStringRemoveLeftBracket.toString().replace("]", "");
			String listAsStringRemoveCommaAndSpace = listAsStringRemoveRightBracket.toString().replace(", ", "");
			if (i<Follow.size()-1){
				output += entry.getKey() + "," + listAsStringRemoveCommaAndSpace + ";";
			}
			else if (i == Follow.size()-1){
				output += entry.getKey() + "," + listAsStringRemoveCommaAndSpace;
			}
			i++;
		}

		System.out.println("Follow Before Dollar Sign: " + Follow.toString());
		System.out.println("Add Dollar Sign to these: " + addDollarSign.toString());
		System.out.println("Output: " + output);

		return output;
	}

}
