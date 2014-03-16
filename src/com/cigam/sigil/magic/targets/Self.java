package com.cigam.sigil.magic.targets;

import com.cigam.sigil.magic.Target;

public class Self extends Target {
	
	@Override
	public String topEvalEffect() {
		return "Invalid to use as top";
	}

	@Override
	public String evalAsTarget() {
		return " the caster";
	}

	@Override
	public double getDuration() {
		// TODO Auto-generated method stub
		return 1.0;
	}

}
