package com.cigam.sigil.magic;

import java.util.*;
import java.util.regex.*;

public class StringLexer {
	private ArrayList<Token> output = new ArrayList<Token>();
	private int location;
	
	public void lex(String input){
		location = 0;
		output = new ArrayList<Token>();
		HashMap<Pattern, Token.Type> patternsToTokens = new HashMap<Pattern, Token.Type>();
		patternsToTokens.put(Pattern.compile("^[Cc]reate$"), Token.Type.CREATE);
		patternsToTokens.put(Pattern.compile("^[Ba]anish$"), Token.Type.BANISH);
		patternsToTokens.put(Pattern.compile("^[Bb]ind$"), Token.Type.BIND);
		patternsToTokens.put(Pattern.compile("^[Ss]ummon$"), Token.Type.SUMMON);
		patternsToTokens.put(Pattern.compile("^fire$"), Token.Type.FIRE);
		patternsToTokens.put(Pattern.compile("^self$"), Token.Type.SELF);
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
