package com.cigam.sigil.screens;

import com.cigam.sigil.magic.Spell;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.elements.Element;


public class PauseScreenController implements ScreenController {
	public Element recentlyClicked;
	public Spell spell;
	public PauseScreen pauseScreen;
	
	public PauseScreenController(PauseScreen p){
		pauseScreen = p;
	}
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
	
	public void clicked(){
		System.out.println(recentlyClicked);
	}
}
