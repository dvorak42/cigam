package com.cigam.sigil.magic.parser;

import java.util.*;
import java.util.regex.*;

import com.cigam.sigil.magic.parser.Token.Type;

public class StringLexer {
	private ArrayList<Token> output = new ArrayList<Token>();
	private int location;
	
	public void lex(String input){
		location = 0;
		output = new ArrayList<Token>();
		HashMap<Pattern, Token.Type> patternsToTokens = new HashMap<Pattern, Token.Type>();
		//Verbs
		patternsToTokens.put(Pattern.compile("^[Cc]reate$"), Token.Type.CREATE);
		patternsToTokens.put(Pattern.compile("^[Ba]anish$"), Token.Type.BANISH);
		patternsToTokens.put(Pattern.compile("^[Bb]ind$"), Token.Type.BIND);
		patternsToTokens.put(Pattern.compile("^[Ss]ummon$"), Token.Type.SUMMON);
		//Targets
		patternsToTokens.put(Pattern.compile("^fire$"), Token.Type.FIRE);
		patternsToTokens.put(Pattern.compile("^self$"), Token.Type.SELF);
		//Modifiers
		patternsToTokens.put(Pattern.compile("^quicken$"), Token.Type.DURATION_TO_EFFECT);
		patternsToTokens.put(Pattern.compile("^slow$"), Token.Type.EFFECT_TO_DURATION);
		patternsToTokens.put(Pattern.compile("^expand$"), Token.Type.EFFECT_TO_AREA);
		patternsToTokens.put(Pattern.compile("^compress$"), Token.Type.AREA_TO_EFFECT);
		patternsToTokens.put(Pattern.compile("^frontload$"), Token.Type.DURATION_TO_AREA);
		patternsToTokens.put(Pattern.compile("^reserve$"), Token.Type.AREA_TO_DURATION);
		//Misc
		patternsToTokens.put(Pattern.compile("^-$"), Token.Type.EMPTY);
		patternsToTokens.put(Pattern.compile("^\\)$"), Token.Type.CLOSE_PAREN);
		
		String[] inpArray = input.split("\\s|\\(|\\)|\\,");
		System.out.println(inpArray);
		for(int i = 0; i < inpArray.length; i++){
			for(Pattern pattern: patternsToTokens.keySet()){
				Matcher matcher = pattern.matcher(inpArray[i].trim());
				if(matcher.matches()){
					output.add(new Token(patternsToTokens.get(pattern), inpArray[i]));
				}
			}
		}
		System.out.println(output);
	}

    public boolean hasNext(){
        return output.size()>location;
    }
    
    public Token getNextToken(){
        Token toReturn =  output.get(location);
        location ++;
        return toReturn;
    }
}
