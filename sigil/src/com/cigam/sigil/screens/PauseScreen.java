package com.cigam.sigil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.cigam.sigil.SigilGame;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.modifiers.AreaToDuration;
import com.cigam.sigil.magic.modifiers.AreaToEffect;
import com.cigam.sigil.magic.modifiers.DurationToArea;
import com.cigam.sigil.magic.modifiers.DurationToEffect;
import com.cigam.sigil.magic.modifiers.EffectToArea;
import com.cigam.sigil.magic.modifiers.EffectToDuration;
import com.cigam.sigil.magic.targets.FireRune;
import com.cigam.sigil.magic.targets.Self;
import com.cigam.sigil.magic.verbs.Banish;
import com.cigam.sigil.magic.verbs.Bind;
import com.cigam.sigil.magic.verbs.Create;
import com.cigam.sigil.magic.verbs.Summon;
import com.cigam.sigil.magic.verbs.TopLevelSpell;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.gdx.input.GdxInputSystem;
import de.lessvoid.nifty.gdx.render.GdxBatchRenderBackendFactory;
import de.lessvoid.nifty.gdx.sound.GdxSoundDevice;
import de.lessvoid.nifty.render.batch.BatchRenderDevice;
import de.lessvoid.nifty.spi.time.impl.AccurateTimeProvider;

public class PauseScreen implements Screen {
	SigilGame game;
	AdventureScreen parent;
	Sprite pauseImage;
	
	AssetManager assetManager;
	Nifty nifty;

	BatchRenderDevice batchRenderDevice;
	
	ShapeRenderer sr;
	OrthographicCamera camera;
	public int index = 0;
	
	public RadialMenu rMenu;
	public Spell createdSpell;
	boolean wasDown = false;
	
	public PauseScreen(final SigilGame game, final AdventureScreen parent) {
		this.game = game;
		this.parent = parent;
		Texture texture = new Texture(Gdx.files.internal("art/screens/pause.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		pauseImage = new Sprite(texture);
		
		assetManager = new AssetManager();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		
		rMenu = new RadialMenu();
		rMenu.color = Color.BLACK;
		RadialMenu verbMenu = new RadialMenu(Banish.class, Bind.class, Create.class, Summon.class);
		verbMenu.color = Color.BLUE;
		RadialMenu targetMenu = new RadialMenu(FireRune.class, Self.class);
		targetMenu.color = Color.RED;
		RadialMenu modifierMenu = new RadialMenu(AreaToDuration.class, AreaToEffect.class, DurationToArea.class, DurationToEffect.class, EffectToArea.class, EffectToDuration.class);
		modifierMenu.color = Color.GREEN;
		RadialEnd deleteSelected = new RadialEnd(new Integer(-1));
		rMenu.addMenu(verbMenu);
		rMenu.addMenu(targetMenu);
		rMenu.addMenu(modifierMenu);
		rMenu.addMenu(deleteSelected);
		
	}

	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		nifty.update();
		nifty.render(true);
		
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
			game.setScreen(parent);
		/*
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
			wasDown = true;
		if(wasDown && !Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			wasDown = false;
			//System.out.println(rMenu.getValue());
		}*/
		rMenu.update(new Vector2(Gdx.input.getX(), Gdx.input.getY()), Gdx.input.isButtonPressed(Input.Buttons.LEFT));
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update(true);
		game.batch.setProjectionMatrix(camera.combined);
		sr.setProjectionMatrix(camera.combined);
		sr.begin(ShapeType.Filled);
		rMenu.render(sr, game.batch);
		sr.end();
	}

	@Override
	public void resize(int width, int height) {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void show() {
		batchRenderDevice = new BatchRenderDevice(GdxBatchRenderBackendFactory.create());
	    nifty = new Nifty(batchRenderDevice, new GdxSoundDevice(assetManager), new GdxInputSystem(Gdx.input), new AccurateTimeProvider());
	    nifty.loadStyleFile("nifty-default-styles.xml");
	    nifty.loadControlFile("nifty-default-controls.xml");
	    	    
	    // <screen>
	    final PauseScreen p = this;
	    nifty.addScreen("Pause", new ScreenBuilder("Hello Nifty Screen"){{
	    	controller(new PauseScreenController(p));
	    	layer(new LayerBuilder("Layer_0"){{
	    		childLayoutCenter();
	    		panel(new PanelBuilder(){{
	    			//style("nifty-panel-simple");
	    			this.backgroundColor("#099f");
	    			childLayoutCenter();
	                height("95%");
	                width("72%");
	                controller(new TargetController());
	                interactOnRelease("released()");
	    		}});
	    	}});
	    }}.build(nifty));
	    
	    nifty.gotoScreen("Pause");
	    //nifty.fromXml("UI/gui_simple.xml", "pause", new PauseScreenController(this));
	    Element panel = nifty.getCurrentScreen().getLayerElements().get(0).getChildren().get(0);
	    if(createdSpell != null) {
	    	Utils.initElement(nifty, nifty.getCurrentScreen(), panel, createdSpell.target);
	    } else {
	    	createdSpell = new TopLevelSpell(parent.player, parent);
	    }

	    panel.setUserData("containingSpell", createdSpell);
	    sr = new ShapeRenderer();
	}

	@Override
	public void hide() {
		try{
			parent.SpellsArray[index] = createdSpell;
			//System.out.println(parent.SpellsArray);
			index++;
			index = index%10;
		} catch (Exception e){
			
		}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

}


