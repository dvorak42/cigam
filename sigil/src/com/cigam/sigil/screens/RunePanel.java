package com.cigam.sigil.screens;

import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.controls.Controller;

public class RunePanel extends PanelBuilder {
	public RunePanel(Controller cont){
		style("nifty-panel-simple");
		childLayoutVertical();
        alignCenter();
        height("100%");
        width("24%");
        controller(cont);
       	interactOnRelease("released()");
	}
}
