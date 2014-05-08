package com.cigam.sigil.magic;

import java.util.ArrayList;

import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.targets.Empty;
import com.cigam.sigil.magic.targets.SelfRune;
import com.cigam.sigil.magic.targets.SpikeyRune;
import com.cigam.sigil.magic.verbs.Banish;
import com.cigam.sigil.magic.verbs.Bind;
import com.cigam.sigil.magic.verbs.Create;
import com.cigam.sigil.magic.verbs.Summon;
import com.cigam.sigil.magic.verbs.TopLevelSpell;

public class Parser {
	private Spell output;
	private ArrayList<Spell> stack;
	private StringLexer lex;
	private Token tok;
	
	public Parser(StringLexer lexer){
		lex = lexer;
		stack = new ArrayList<Spell>();
	}
	
	public Spell parse(String input){
		output = new TopLevelSpell();
		stack.add(output);
		lex.lex(input);
		while(lex.hasNext()){
			tok = lex.getNextToken();
			if(tok.getType() == Token.Type.CREATE){
				addArrayChild(new Create());
			} else if(tok.getType() == Token.Type.BANISH){
				addArrayChild(new Banish());
			} else if(tok.getType() == Token.Type.SUMMON){
				addArrayChild(new Summon());
			} else if(tok.getType() == Token.Type.BIND){
				addArrayChild(new Bind());
			} else if(tok.getType() == Token.Type.CLOSE_PAREN){
				if(getCurrent().isDone()){
					stack.remove(stack.size()-1);
				} else {
					Utils.printError("wrong number of inputs for " + getCurrent());
				}
			} else if(tok.getType() == Token.Type.FIRE){
				addRuneChild(new SpikeyRune());
			} else if(tok.getType() == Token.Type.SELF){
				addRuneChild(new SelfRune());
			} else {
				addRuneChild(new Empty());
			}
		}
		return output;
	}
	
	private void addArrayChild(Spell s){
		//System.out.println("adding " + s + " to " + getCurrent());
		getCurrent().addChild(s);
		stack.add(s);
		//System.out.println(stack);
	}
	private void addRuneChild(Spell t){
		//System.out.println("adding " + t + " to " + getCurrent());
		getCurrent().addChild(t);
		//System.out.println(stack);
	}

	private Spell getCurrent(){
		return stack.get(stack.size()-1);
	}
}
