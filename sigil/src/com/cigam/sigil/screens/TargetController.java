package com.cigam.sigil.screens;

import com.cigam.sigil.magic.Spell;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.controls.Parameters;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;

public class TargetController implements Controller {

	private PauseScreenController p;
	private Element e;
	private Screen s;
	private Nifty n;
	public Spell containingSpell;
	private boolean full;
	@Override
	public void bind(Nifty nifty, Screen screen, Element element,
			Parameters parameter) {
			//System.out.println("targetControllerCreated");
			e = element;
			p = (PauseScreenController) screen.getScreenController();
			s = screen;
			n = nifty;
			full = false;
	}
	
	@Override
	public void init(Parameters parameter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFocus(boolean getFocus) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean inputEvent(NiftyInputEvent inputEvent) {
		// TODO Auto-generated method stub
		return false;
	}
	public TargetController setContainingSpell(Spell c){
		containingSpell = c;
		//System.out.println(containingSpell);
		return this;
	}
	
	public void recursiveSetUserData(String key, Object value, Element target){
		for(Element c:target.getChildren()){
			c.setUserData(key, value);
			recursiveSetUserData(key, value, c);
		}
	}
	
	public void released(){
		if(!full){
			System.out.println("UserData is " + e.getUserData("containingSpell"));
			if(containingSpell == null){
				containingSpell = e.getUserData("containingSpell");
			}
			Object inp = null;
			try {
				if(p.pauseScreen.rMenu.getValue()!=null){
					inp = ((Class)(p.pauseScreen.rMenu.getValue())).newInstance();
				}
			} catch (Exception e) {e.printStackTrace();}
			
			System.out.println("inp = " + inp);
			System.out.println("containtingSpell = " + containingSpell);
			if(inp!=null && inp instanceof Spell && containingSpell!=null){
				containingSpell.addTarget((Spell) inp);
				full = true;
				((Spell) inp).gui.build(n, s, e);
				System.out.println(e.getChildrenCount());
				recursiveSetUserData("containingSpell", ((Spell) inp), e);
				//e.disable();
			}
		}
	}
}
