package com.cigam.sigil.screens;

import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.targets.Empty;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.controls.Parameters;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;

public class ArgumentController implements Controller {

	private PauseScreenController p;
	private Element e;
	private Screen s;
	private Nifty n;
	public Spell containingSpell;
	public int index;

	public ArgumentController() {
		super();
	}
	
	@Override
	public void bind(Nifty nifty, Screen screen, Element element,
			Parameters parameter) {
			//System.out.println("targetControllerCreated");
			e = element;
			index = Integer.parseInt(parameter.get("argIndex"));
			p = (PauseScreenController) screen.getScreenController();
			s = screen;
			n = nifty;
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
	public ArgumentController setContainingSpell(Spell c){
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
	
	public void released() {
		Object sV = p.pauseScreen.rMenu.getValue();
		Spell newSpell = null;
		boolean delete = false;

		try {
			if(sV != null && sV instanceof Class){
				Object o = ((Class)sV).newInstance();
				if(o instanceof Spell)
					newSpell = (Spell)o;
				else
					delete = true;
			} else if(sV != null)
				delete = true;
		} catch (Exception e) {e.printStackTrace();}
		
		//System.out.println("UserData is " + e.getUserData("containingSpell"));
		if(containingSpell == null){
			containingSpell = e.getUserData("containingSpell");
		}

		boolean full = false;
		
		if(containingSpell != null) {
			Spell a = e.getUserData("currentSpell");
			boolean outerHas = false;
			for(int i = 0; i < containingSpell.arguments.length; i++) {
				if(containingSpell.arguments[i] == a)
					outerHas = true;
			}
			full = a != null && !(a instanceof Empty) && outerHas;
		}

		if(full && delete) {
			//System.out.println("Deleting");
			if(containingSpell != null) {
				containingSpell.removeArgument((Spell)e.getUserData("currentSpell"));
				Element pElement = e.getChildren().get(0);
				pElement.markForRemoval();
				pElement.hide();
				e.layoutElements();
			}
		} else if(full){
			if(containingSpell != null) {
				containingSpell.removeArgument((Spell)e.getUserData("currentSpell"));
				Element pElement = e.getChildren().get(0);
				pElement.markForRemoval();
				pElement.hide();
				e.layoutElements();
			}
			if(newSpell != null && containingSpell != null){
				System.out.println(index);
				containingSpell.addArgument(newSpell, index);
				newSpell.gui.build(n, s, e);
				//System.out.println(e.getChildrenCount());
				e.setUserData("currentSpell", newSpell);
				recursiveSetUserData("containingSpell", newSpell, e);
			}
		}
		else {
			//System.out.println("inp = " + newSpell);
			//System.out.println("containtingSpell = " + containingSpell);
			if(newSpell != null && containingSpell != null){
				System.out.println(index);
				containingSpell.addArgument(newSpell, index);
				newSpell.gui.build(n, s, e);
				//System.out.println(e.getChildrenCount());
				e.setUserData("currentSpell", newSpell);
				recursiveSetUserData("containingSpell", newSpell, e);
			}
		}
	}
}
