package com.cigam.sigil;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.cigam.sigil.external.BodyEditorLoader;
import com.cigam.sigil.magic.targets.FireRune;
import com.cigam.sigil.magic.targets.Self;
import com.cigam.sigil.magic.verbs.*;
import com.cigam.sigil.materials.*;
import com.cigam.sigil.screens.ArgumentController;
import com.cigam.sigil.screens.RunePanel;
import com.cigam.sigil.screens.TargetController;

import de.lessvoid.nifty.builder.PanelBuilder;

public class Utils {
    public static BodyEditorLoader mainBodies = new BodyEditorLoader(Gdx.files.internal("data/main_bodies.json"));

	public static Body createWall(World w, Vector2 start, Vector2 end) {
		BodyDef bd = new BodyDef();
		bd.position.set(start);
		bd.type = BodyType.StaticBody;

		Body body = w.createBody(bd);
		EdgeShape edge = new EdgeShape();
		edge.set(Vector2.Zero, end.sub(start));

		FixtureDef fd = new FixtureDef();
		fd.shape = edge;

		body.createFixture(fd);
		
		return body;
	}
	
	public static void createBounds(World w, int width, int height) {
		createWall(w, Vector2.Zero, new Vector2(width, 0));
		createWall(w, Vector2.Zero, new Vector2(0, height));
		createWall(w, new Vector2(width, 0), new Vector2(width, height));
		createWall(w, new Vector2(0, height), new Vector2(width, height));
	}

	public static Vector2 angleToVector(float angle) {
		return new Vector2(1, 0).rotate(angle);
	}
	
	public static float dist(PhysicalEntity a, PhysicalEntity b){
		return a.body.getPosition().cpy().sub(b.body.getPosition()).len();
	}
	public static void printError(String toPrint){
		System.err.println(toPrint);
	}
	
	public static PanelBuilder makeCreateGui(final String path){
		PanelBuilder gui = new PanelBuilder(){{
			//style("nifty-panel-simple");
			childLayoutVertical();
            height("100%");
            width("100%");
            backgroundImage(path);
            panel(new PanelBuilder(){{
    			childLayoutHorizontal();
                height("33%");
                width("100%");
            }});
            panel(new PanelBuilder(){{
            	childLayoutCenter();
                height("34%");
                width("100%");
                panel(new RunePanel(new TargetController()));
            }});
            panel(new PanelBuilder(){{
            	childLayoutHorizontal();
                height("33%");
                width("100%");
            }});
		}};
		return gui;
	}
	
	public static PanelBuilder makeRuneGui(final String path){
		PanelBuilder gui = new PanelBuilder(){{
			childLayoutVertical();
            height("100%");
            width("100%");
            backgroundImage(path);
		}};
		return gui;
	}
	
	public static PanelBuilder makeVerbGui(final String path){
		PanelBuilder gui = new PanelBuilder(){{
			//style("nifty-panel-simple");
			childLayoutVertical();
            height("100%");
            width("100%");
            backgroundImage(path);
            panel(new PanelBuilder(){{
    			childLayoutHorizontal();
                height("33%");
                width("100%");
                panel(new RunePanel(new ArgumentController()));
                panel(new PanelBuilder(){{
        			childLayoutCenter();
                    height("100%");
                    width("52%");
                }});
                panel(new RunePanel(new ArgumentController()));
            }});
            panel(new PanelBuilder(){{
            	childLayoutCenter();
                height("34%");
                width("100%");
                panel(new RunePanel(new TargetController()));
            }});
            panel(new PanelBuilder(){{
            	childLayoutHorizontal();
                height("33%");
                width("100%");
                panel(new RunePanel(new ArgumentController()));
                panel(new PanelBuilder(){{
                	childLayoutCenter();
                    height("100%");
                    width("52%");
                }});
                panel(new RunePanel(new ArgumentController()));
            }});
		}};
		return gui;
	}
	
	public static HashMap<Class, String> classesToIconPaths;
	static{
		classesToIconPaths = new HashMap<Class, String>();
		classesToIconPaths.put(Banish.class, "UI/banish512.png");
		classesToIconPaths.put(Bind.class, "UI/bind512.png");
		classesToIconPaths.put(Create.class, "UI/create512.png");
		classesToIconPaths.put(Summon.class, "UI/summon512.png");
		classesToIconPaths.put(FireRune.class, "UI/Element1 512.png");
		classesToIconPaths.put(Self.class, "UI/Element2 512.png");

		
	}
}

