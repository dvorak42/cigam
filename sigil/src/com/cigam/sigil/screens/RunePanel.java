package com.cigam.sigil.screens;

import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.controls.Controller;

public class RunePanel extends PanelBuilder {
	public RunePanel(Controller cont){
		//style("nifty-panel-simple");
		backgroundColor("#5955");
		childLayoutVertical();
        alignCenter();
        height("100%");
        width("23%");
        controller(cont);
       	interactOnRelease("released()");
	}
}
