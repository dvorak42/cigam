package com.cigam.sigil.screens;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.controls.Parameters;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;

public class ButtonController implements Controller {
	private Element e;
	private PauseScreenController p;
	
	@Override
	public void bind(Nifty nifty, Screen screen, Element element,
			Parameters parameter) {
			e = element;
			p = (PauseScreenController) screen.getScreenController();

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
		return false;
	}
	
	public void clicked(){
		p.recentlyClicked = e;
	}

}
