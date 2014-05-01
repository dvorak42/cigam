package com.cigam.sigil.magic;

import java.util.ArrayList;

import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.modifiers.AreaToDuration;
import com.cigam.sigil.magic.modifiers.AreaToEffect;
import com.cigam.sigil.magic.modifiers.DurationToArea;
import com.cigam.sigil.magic.modifiers.DurationToEffect;
import com.cigam.sigil.magic.modifiers.EffectToArea;
import com.cigam.sigil.magic.modifiers.EffectToDuration;
import com.cigam.sigil.magic.targets.Empty;
import com.cigam.sigil.magic.targets.FireRune;
import com.cigam.sigil.magic.targets.SelfRune;
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
				addRuneChild(new FireRune());
			} else if(tok.getType() == Token.Type.SELF){
				addRuneChild(new SelfRune());
			} else if(tok.getType() == Token.Type.AREA_TO_DURATION){
				addRuneChild(new AreaToDuration());
			} else if(tok.getType() == Token.Type.DURATION_TO_AREA){
				addModifier(new DurationToArea());
			} else if(tok.getType() == Token.Type.AREA_TO_EFFECT){
				addModifier(new AreaToEffect());
			} else if(tok.getType() == Token.Type.EFFECT_TO_AREA){
				addModifier(new EffectToArea());
			} else if(tok.getType() == Token.Type.DURATION_TO_EFFECT){
				addModifier(new DurationToEffect());
			} else if(tok.getType() == Token.Type.EFFECT_TO_DURATION){
				addModifier(new EffectToDuration());
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
		//System.out.println(stack);
	}
	private void addRuneChild(Spell t){
		//System.out.println("adding " + t + " to " + getCurrent());
		getCurrent().addChild(t);
		//System.out.println(stack);
	}
	private void addModifier(Spell m){
		Spell parent = stack.get(stack.size()-2);
		//System.out.println(parent);
		if(parent.target == getCurrent()){
			parent.target = null;
		} else {
			for(int i = 0; i < parent.arguments.size(); i++){
				if(parent.arguments.get(i)==getCurrent()){
					parent.arguments.remove(i);
				}
			}
		}
		parent.addChild(m);
		m.target = getCurrent();
		m.target.addChild(new Empty());
		stack.add(stack.size()-1, m);
		//System.out.println(stack);
		
	}
	private Spell getCurrent(){
		return stack.get(stack.size()-1);
	}
}
