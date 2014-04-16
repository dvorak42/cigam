package com.cigam.sigil.screens;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.controls.Parameters;
import de.lessvoid.nifty.controls.dynamic.PanelCreator;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;

public class CenterController implements Controller {

	private PauseScreenController p;
	private Element e;
	private Screen s;
	private Nifty n;
	
	@Override
	public void bind(Nifty nifty, Screen screen, Element element,
			Parameters parameter) {
			e = element;
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
	
	public void released(){
		String inp = p.recentlyClicked.getId();
		if(inp.equals("Create_Button")){
			e.markForRemoval();
			Element parent = e.getParent();
			PanelCreator createPanel = new PanelCreator();
			
			System.out.println("after removal");
		}
	}
}
