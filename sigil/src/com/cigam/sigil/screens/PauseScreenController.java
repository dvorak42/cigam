package com.cigam.sigil.screens;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class PauseScreenController implements ScreenController {

	@Override
	public void bind(Nifty nifty, Screen screen) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStartScreen() {
		System.out.println("started");

	}

	@Override
	public void onEndScreen() {
		// TODO Auto-generated method stub

	}
	
	public void setSpell(){
		System.out.println("test");
	}
	public void test(){
		System.out.println();
	}
}
