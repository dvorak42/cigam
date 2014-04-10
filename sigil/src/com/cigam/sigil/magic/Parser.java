package com.cigam.sigil.magic;

import java.util.ArrayList;

import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.modifiers.*;
import com.cigam.sigil.magic.targets.*;
import com.cigam.sigil.magic.verbs.*;
import com.cigam.sigil.materials.*;
import com.cigam.sigil.screens.AdventureScreen;

public class Parser {
	private Spell output;
	private ArrayList<Spell> stack;
	private StringLexer lex;
	private Token tok;
	
	public Parser(StringLexer lexer){
		lex = lexer;
		stack = new ArrayList<Spell>();
	}
	
	public Spell parse(PhysicalEntity caster, AdventureScreen s, String input){
		output = new TopLevelSpell(caster, s);
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
				addRuneChild(new MaterialRune(new Fire()));
			} else if(tok.getType() == Token.Type.SELF){
				addRuneChild(new Self());
			} else if(tok.getType() == Token.Type.AREA_TO_DURATION){
				addRuneChild(new AreaToDuration());
			} else if(tok.getType() == Token.Type.DURATION_TO_AREA){
				addRuneChild(new DurationToArea());
			} else if(tok.getType() == Token.Type.AREA_TO_EFFECT){
				addRuneChild(new AreaToEffect());
			} else if(tok.getType() == Token.Type.EFFECT_TO_AREA){
				addRuneChild(new EffectToArea());
			} else if(tok.getType() == Token.Type.DURATION_TO_EFFECT){
				addRuneChild(new DurationToEffect());
			} else if(tok.getType() == Token.Type.EFFECT_TO_DURATION){
				addRuneChild(new EffectToDuration());
			} else if(tok.getType() == Token.Type.EMPTY){
				addRuneChild(new Empty());
			}
		}
		return output;
	}
	
	private void addArrayChild(Spell s){
		//System.out.println("adding " + s + " to " + getCurrent());
		getCurrent().addChild(s);
		stack.add(s);
	}
	private void addRuneChild(Spell t){
		//System.out.println("adding " + t + " to " + getCurrent());
		getCurrent().addChild(t);
	}
	private Spell getCurrent(){
		return stack.get(stack.size()-1);
	}
}
