package com.cigam.sigil.screens;

import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.controls.Controller;

public class RunePanel extends PanelBuilder {
	public RunePanel(Controller cont, int i){
		this(cont);
        set("argIndex", "" + i);
	}

	public RunePanel(Controller cont){
		//style("nifty-panel-simple");
<<<<<<< HEAD
		backgroundColor("#5950");
=======
		//backgroundColor("#5955");
>>>>>>> e3dabf23b1e30788b0e0c32ca6f9388640f02f6b
		childLayoutVertical();
        alignCenter();
        height("100%");
        width("23%");
        controller(cont);
       	interactOnRelease("released()");
	}
}
