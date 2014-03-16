package com.cigam.sigil.magic.targets;

import com.cigam.sigil.magic.Target;

public class Fire extends Target {
	
	@Override
	public String topEvalEffect() {
		return "Invalid to use as top";
	}

	@Override
	public String evalAsTarget() {
		return " fire";
	}

	@Override
	public double getDuration() {
		// TODO Auto-generated method stub
		return 1.0;
	}

}
