package com.cigam.sigil.magic;

public class Token {
	public static enum Type{
	CREATE, SUMMON, BANISH, BIND, FIRE, SELF, CLOSE_PAREN, EMPTY
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
