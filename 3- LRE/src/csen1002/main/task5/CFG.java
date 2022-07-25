package csen1002.main.task5;

import java.util.*;

/**
 * Write your info here
 * 
 * @name Ziad Elmassik
 * @id 43-3517
 * @labNumber 10
 */
public class CFG {
	/**
	 * CFG constructor
	 * 
	 * @param description is the string describing a CFG
	 */

	ArrayList<String> originalNonTerminals = new ArrayList<String>();
	LinkedHashMap<String, LinkedHashSet<String>> rules = new LinkedHashMap<String, LinkedHashSet<String>>();

	public CFG(String description) {

		String[] rulesArray = description.split(";");


		for (int i=0; i< rulesArray.length; i++){
			String[] rule = rulesArray[i].split(",");
			originalNonTerminals.add(rule[0]);

			LinkedHashSet<String> rightHandSide = new LinkedHashSet<String>();
			for (int j=1; j<rule.length; j++){
				rightHandSide.add(rule[j]);
			}
			rules.put(rule[0], rightHandSide);
		}
		//System.out.println(rules.toString());
	}

	/**
	 * Returns a string of elimnated left recursion.
	 * 
	 * @param input is the string to simulate by the CFG.
	 * @return string of elimnated left recursion.
	 */
	public String lre() {

		for (int i=0; i<originalNonTerminals.size(); i++){
			if (i==0){
				eliminateImmediateLeftRecursion(originalNonTerminals.get(i));
			}
			else{
				for (int j=0; j<i; j++){
					LinkedHashSet<String> rulesOfCurrentNonTerminal = rules.get(originalNonTerminals.get(i));
					LinkedHashSet<String> newRulesOfCurrentNonTerminal = new LinkedHashSet<String>();
					for (String currentRule : rulesOfCurrentNonTerminal){
						String startOfCurrentRule = "" + currentRule.charAt(0);
						if (startOfCurrentRule.equals(originalNonTerminals.get(j))){
							String restOfCurrentRule = currentRule.substring(1, currentRule.length());
							LinkedHashSet<String> rulesOfIndexJ = rules.get(originalNonTerminals.get(j));
							for (String ruleOfJ : rulesOfIndexJ){
								String newRule = ruleOfJ + restOfCurrentRule;
								newRulesOfCurrentNonTerminal.add(newRule);
							}

						}
						else{
							newRulesOfCurrentNonTerminal.add(currentRule);
						}
					}
					rules.put(originalNonTerminals.get(i), newRulesOfCurrentNonTerminal);
				}
				eliminateImmediateLeftRecursion(originalNonTerminals.get(i));
			}

		}
		System.out.println(rules.toString());

		String leftRecursionEliminated = rules.toString();
		String output = leftRecursionEliminated.replaceAll("=","").replaceAll("\\[",",").replaceAll("\\{", "").replaceAll("\\]" + ",", ";").replaceAll("\\}", "").replaceAll(" ","").replaceAll("\\]", "");
		System.out.println(output);
		return output;
	}

	private void eliminateImmediateLeftRecursion(String nonTerminal) {

		LinkedHashSet<String> rightHand = rules.get(nonTerminal);
		LinkedHashSet<String> newRightHandForCurrentNonTerminal = new LinkedHashSet<String>();

		boolean immediateLeftRecursionPresent = checkForImmediateLeftRecursion(nonTerminal);

		if (immediateLeftRecursionPresent){
			String newNonTerminal = nonTerminal + "'";
			LinkedHashSet<String> rightHandForNewNonTerminal = new LinkedHashSet<String>();
			Queue<String> alphaRules = new LinkedList<String>();
			for (String currentRule : rightHand){
				String currentRuleStart = "" + currentRule.charAt(0);
				if (currentRuleStart.equals(nonTerminal)){
					//Aalpha
					String A = "" + currentRule.charAt(0);
					String alpha = "" + currentRule.substring(1, currentRule.length());
					alphaRules.add(alpha);
				}
				else{
					// BETA
					currentRule = currentRule + newNonTerminal;
					newRightHandForCurrentNonTerminal.add(currentRule);
				}
			}

			LinkedHashMap<String, LinkedHashSet<String>> newHashMap = new LinkedHashMap<String, LinkedHashSet<String>>();


			//rules.put(nonTerminal, newRightHandForCurrentNonTerminal);

			while (!alphaRules.isEmpty()){
				String alphaConcatenatedWithNewNonTerminal = alphaRules.poll() + newNonTerminal;
				rightHandForNewNonTerminal.add(alphaConcatenatedWithNewNonTerminal);
			}
			rightHandForNewNonTerminal.add("e");

			rules.forEach((key, value) -> {
				if (!key.equals(nonTerminal)){
					newHashMap.put(key, value);
				}
				else{
					newHashMap.put(key, newRightHandForCurrentNonTerminal);
					newHashMap.put(newNonTerminal, rightHandForNewNonTerminal);
				}
			});

			rules = newHashMap;
			//System.out.println(rules.toString());
		}


	}

	private boolean checkForImmediateLeftRecursion(String nonTerminal) {

		LinkedHashSet<String> rightHand = rules.get(nonTerminal);

		for (String currentRule : rightHand){
			String currentRuleStart = "" + currentRule.charAt(0);
			if (currentRuleStart.equals(nonTerminal)){
				return true;
			}
		}

		return false;

	}
}
