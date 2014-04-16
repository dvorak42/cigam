package com.cigam.sigil.magic.parser;

public class Token {
	public static enum Type {
	CREATE, SUMMON, BANISH, BIND,
	FIRE, SELF, CLOSE_PAREN, EMPTY,
	DURATION_TO_EFFECT, EFFECT_TO_DURATION,
	EFFECT_TO_AREA, AREA_TO_EFFECT,
	DURATION_TO_AREA, AREA_TO_DURATION
	}
	private final Type type;
	private final String string;
	
	public Token(Type type, String string){
		this.type = type;
		this.string = string;
	}

	public Type getType() {
		return type;
	}

	public String getString() {
		return string;
	}
	
    public String toString(){
    	return "Type: " + type;
    }
}
