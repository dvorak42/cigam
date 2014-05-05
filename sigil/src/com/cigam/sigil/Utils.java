package com.cigam.sigil;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.cigam.sigil.Constants.Direction;
import com.cigam.sigil.external.BodyEditorLoader;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.modifiers.AreaToDuration;
import com.cigam.sigil.magic.modifiers.AreaToEffect;
import com.cigam.sigil.magic.modifiers.DurationToArea;
import com.cigam.sigil.magic.modifiers.DurationToEffect;
import com.cigam.sigil.magic.modifiers.EffectToArea;
import com.cigam.sigil.magic.modifiers.EffectToDuration;
import com.cigam.sigil.magic.targets.EnemyRune;
import com.cigam.sigil.magic.targets.SpikeyRune;
import com.cigam.sigil.magic.targets.Empty;
import com.cigam.sigil.magic.targets.StickyRune;
import com.cigam.sigil.magic.targets.SelfRune;
import com.cigam.sigil.magic.verbs.Banish;
import com.cigam.sigil.magic.verbs.Bind;
import com.cigam.sigil.magic.verbs.Create;
import com.cigam.sigil.magic.verbs.Summon;
import com.cigam.sigil.materials.EnemyMat;
import com.cigam.sigil.materials.SpikeyMat;
import com.cigam.sigil.screens.ArgumentController;
import com.cigam.sigil.screens.RunePanel;
import com.cigam.sigil.screens.TargetController;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.elements.Element;

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
                height("59%");
                width("100%");
            }});
            panel(new PanelBuilder(){{
            	alignCenter();
                height("16%");
                width("72%");
                childLayoutHorizontal();
                panel(new PanelBuilder(){{
                	childLayoutCenter();
                    height("100%");
                    width("42%");
                }});
                panel(new RunePanel(new TargetController()));
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
    			alignCenter();
                height("14%");
                width("70%");
            }});
            panel(new PanelBuilder(){{
            	childLayoutHorizontal();
    			alignCenter();
                height("16%");
                width("70%");
                panel(new RunePanel(new ArgumentController()));
                panel(new PanelBuilder(){{
        			childLayoutCenter();
                    height("100%");
                    width("53%");
                }});
                panel(new RunePanel(new ArgumentController()));
            }});
            panel(new PanelBuilder(){{
            	childLayoutHorizontal();
    			alignCenter();
                height("12%");
                width("70%");
            }});
            panel(new PanelBuilder(){{
            	childLayoutCenter();
            	alignCenter();
                height("16%");
                width("70%");
                panel(new RunePanel(new TargetController()));
            }});
            panel(new PanelBuilder(){{
            	childLayoutHorizontal();
    			alignCenter();
                height("10%");
                width("70%");
            }});
            panel(new PanelBuilder(){{
            	childLayoutHorizontal();
            	alignCenter();
                height("16%");
                width("70%");
                panel(new RunePanel(new ArgumentController()));
                panel(new PanelBuilder(){{
                	childLayoutCenter();
                    height("100%");
                    width("53%");
                }});
                panel(new RunePanel(new ArgumentController()));
            }});
		}};
		return gui;
	}
	
	public static HashMap<Class, String> classesToIconPaths;
	static{
		classesToIconPaths = new HashMap<Class, String>();
		classesToIconPaths.put(Banish.class, "UI/cigam/banish512.png");
		classesToIconPaths.put(Bind.class, "UI/cigam/bind512.png");
		classesToIconPaths.put(Create.class, "UI/cigam/create512.png");
		classesToIconPaths.put(Summon.class, "UI/cigam/summon512.png");
		classesToIconPaths.put(StickyRune.class, "UI/cigam/Element1 512.png");
		classesToIconPaths.put(SelfRune.class, "UI/cigam/ElementSelf 512.png");
		classesToIconPaths.put(EnemyRune.class, "UI/cigam/Element3 512.png");
		classesToIconPaths.put(SpikeyRune.class, "UI/cigam/Element2 512.png");
		classesToIconPaths.put(EffectToArea.class, "UI/Expand_placeholder.png");
		classesToIconPaths.put(EffectToDuration.class, "UI/Slow_placeholder.png");
		classesToIconPaths.put(DurationToEffect.class, "UI/Quicken_placeholder.png");
		classesToIconPaths.put(DurationToArea.class, "UI/Lengthen_placeholder.png");
		classesToIconPaths.put(AreaToEffect.class, "UI/Concentrate_placeholder.png");
		classesToIconPaths.put(AreaToDuration.class, "UI/Condense_placeholder.png");


		
	}
	
	public static HashMap<Class, String> classesToMenuPaths;
	static{
		classesToMenuPaths = new HashMap<Class, String>();
		classesToMenuPaths.put(Banish.class, "UI/cigam/banish512 symbol with name.png");
		classesToMenuPaths.put(Bind.class, "UI/cigam/bind512 symbol with name.png");
		classesToMenuPaths.put(Create.class, "UI/cigam/create512 symbol with name.png");
		classesToMenuPaths.put(Summon.class, "UI/cigam/summon512 symbol with name.png");
		classesToMenuPaths.put(StickyRune.class, "UI/cigam/Element1 512.png");
		classesToMenuPaths.put(SelfRune.class, "UI/cigam/ElementSelf 512.png");
		classesToMenuPaths.put(EnemyRune.class, "UI/cigam/Element3 512.png");
		classesToMenuPaths.put(SpikeyRune.class, "UI/cigam/Element2 512.png");
		classesToMenuPaths.put(EffectToArea.class, "UI/Expand_placeholder.png");
		classesToMenuPaths.put(EffectToDuration.class, "UI/Slow_placeholder.png");
		classesToMenuPaths.put(DurationToEffect.class, "UI/Quicken_placeholder.png");
		classesToMenuPaths.put(DurationToArea.class, "UI/Lengthen_placeholder.png");
		classesToMenuPaths.put(AreaToEffect.class, "UI/Concentrate_placeholder.png");
		classesToMenuPaths.put(AreaToDuration.class, "UI/Condense_placeholder.png");
	}
	
	public static void recursiveSet(Element e, String k, Object v) {
		e.setUserData(k, v);
		for(Element e2 : e.getChildren())
			recursiveSet(e2, k, v);
	}
	
	public static void initElement(Nifty n, de.lessvoid.nifty.screen.Screen s, Element e, Spell spell) {
		if(spell == null || spell instanceof Empty)
			return;
		//System.out.println(spell);
		Element f = spell.gui.build(n, s, e);
		e.setUserData("currentSpell", spell);
		recursiveSet(f, "containingSpell", spell);
		if(spell instanceof Create) {
			initElement(n, s, f.getChildren().get(1).getChildren().get(1), spell.target);
		} else if(spell instanceof Banish || spell instanceof Bind || spell instanceof Summon) {
			initElement(n, s, f.getChildren().get(3).getChildren().get(0), spell.target);
			if(spell.arguments.size() > 0)
				initElement(n, s, f.getChildren().get(1).getChildren().get(0), spell.arguments.get(0));
			if(spell.arguments.size() > 1)
				initElement(n, s, f.getChildren().get(1).getChildren().get(2), spell.arguments.get(1));
			if(spell.arguments.size() > 2)
				initElement(n, s, f.getChildren().get(5).getChildren().get(0), spell.arguments.get(2));
			if(spell.arguments.size() > 3)
				initElement(n, s, f.getChildren().get(5).getChildren().get(2), spell.arguments.get(3));
		}
	}

	public static Body createWall(World w, Vector2 start, Polygon p, float scale) {
		BodyDef bd = new BodyDef();
		bd.position.set(start);
		bd.type = BodyType.StaticBody;

		Body body = w.createBody(bd);
		PolygonShape ps = new PolygonShape();
		float[] v = p.getVertices();
		for(int i = 0; i < v.length; i++)
			v[i] *= scale;
		ps.set(v);

		FixtureDef fd = new FixtureDef();
		fd.shape = ps;

		body.createFixture(fd);
		
		return body;
	}
	
	public static Body createWall(World w, Vector2 start, Rectangle r, float scale) {
		BodyDef bd = new BodyDef();
		bd.position.set(start.cpy().add(r.getWidth() * scale / 2, r.getHeight() * scale / 2));
		bd.type = BodyType.StaticBody;

		Body body = w.createBody(bd);
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(r.getWidth() * scale / 2, r.getHeight() * scale / 2);

		FixtureDef fd = new FixtureDef();
		fd.shape = ps;

		body.createFixture(fd);
		
		return body;
	}

	public static Body createLava(World w, Vector2 start, Polygon p, float scale) {
		BodyDef bd = new BodyDef();
		bd.position.set(start);
		bd.type = BodyType.StaticBody;

		Body body = w.createBody(bd);
		PolygonShape ps = new PolygonShape();
		float[] v = p.getVertices();
		for(int i = 0; i < v.length; i++)
			v[i] *= scale;
		ps.set(v);

		FixtureDef fd = new FixtureDef();
		fd.shape = ps;

		body.createFixture(fd);
		body.setUserData(Constants.LAVA);
		
		return body;
	}
	
	public static Body createLava(World w, Vector2 start, Rectangle r, float scale) {
		BodyDef bd = new BodyDef();
		bd.position.set(start.cpy().add(r.getWidth() * scale / 2, r.getHeight() * scale / 2));
		bd.type = BodyType.StaticBody;

		Body body = w.createBody(bd);
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(r.getWidth() * scale / 2, r.getHeight() * scale / 2);

		FixtureDef fd = new FixtureDef();
		fd.shape = ps;

		body.createFixture(fd);
		body.setUserData(Constants.LAVA);
		return body;
	}

	public static Vector2[] initSpellHitBox(float radius, float scaleFactor){
		int granularity = 8;
		Vector2[] hitBox = new Vector2[granularity];
		for(int i = 0; i < granularity; i ++){
			hitBox[i] = new Vector2();
			hitBox[i].set((float) (Math.cos(Math.PI*2*i/granularity)*radius*scaleFactor), (float) (Math.sin(Math.PI*2*i/granularity)*radius/scaleFactor));
		}
		return hitBox;
	}

	public static Direction vecToDir(Vector2 v) {
		if(v.epsilonEquals(0, 0, .001f)){
			return Direction.IDLE;
		} else if(Math.abs(v.y)>Math.abs(v.x)){
			if(v.y>0){
				return Direction.BACKWARD;
			} else {
				return Direction.FORWARD;
			}
		} else {
			if(v.x>0){
				return Direction.RIGHT;
			} else {
				return Direction.LEFT;
			}
		}
	}
	
	public static float dirToAngle(Direction d){
		switch (d) {
			case BACKWARD:
				return 90;
			case FORWARD:
				return 270;
			case LEFT:
				return 180;
			case RIGHT:
				return 0;
			default:
				return 270;
				
		}
	}
		
}

