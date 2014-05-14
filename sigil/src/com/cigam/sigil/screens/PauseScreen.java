package com.cigam.sigil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.cigam.sigil.SigilGame;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.targets.EnemyRune;
import com.cigam.sigil.magic.targets.SpikeyRune;
import com.cigam.sigil.magic.targets.SelfRune;
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
	
	Texture clickHelp;
	
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
		clickHelp = new Texture(Gdx.files.internal("help/Click.png"));
		pauseImage = new Sprite(texture);
		pauseImage.flip(false,true);
		
		assetManager = new AssetManager();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		
		rMenu = new RadialMenu();
		rMenu.color = Color.BLACK;
		RadialMenu verbMenu = new RadialMenu(Create.class, Banish.class, Bind.class, Summon.class);
		//verbMenu.color = Color.BLUE;
		RadialMenu targetMenu = new RadialMenu(SelfRune.class, SpikeyRune.class, EnemyRune.class);
		//targetMenu.color = Color.WHITE;
		//RadialMenu modifierMenu = new RadialMenu(AreaToDuration.class, AreaToEffect.class, DurationToArea.class, DurationToEffect.class, EffectToArea.class, EffectToDuration.class);
		//modifierMenu.color = Color.GREEN;
		RadialEnd deleteSelected = new RadialEnd(new Integer(-1));
		rMenu.addMenu(verbMenu);
		rMenu.addMenu(targetMenu);
		//rMenu.addMenu(modifierMenu);
		rMenu.addMenu(deleteSelected);
		
	}

	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		nifty.update();
		nifty.render(true);
		
		
		
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
		    try{
	            if(createdSpell.target!=null){
	                createdSpell.resetDiagram();
	                parent.SpellsArray[index] = createdSpell;
	            }
	            //System.out.println(parent.SpellsArray);
	            index++;
	            index = index%10;
	        } catch (Exception e){
	        }
		    game.setScreen(parent);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
		    game.setScreen(parent);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
		    Gdx.app.exit();
		}
		/*
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
			wasDown = true;
		if(wasDown && !Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			wasDown = false;
			//System.out.println(rMenu.getValue());
		}*/
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
        pauseImage.setColor(game.batch.getColor());
        pauseImage.draw(game.batch);
        if(createdSpell == null || createdSpell.target == null)
        	game.batch.draw(clickHelp, Gdx.graphics.getWidth() / 2 - clickHelp.getWidth() / 2, Gdx.graphics.getHeight() / 2 - clickHelp.getHeight() / 2, clickHelp.getWidth(), clickHelp.getHeight(), 0, 0, clickHelp.getWidth(), clickHelp.getHeight(), false, true);
        pauseImage.setColor(Color.WHITE);
        game.batch.end();
		
		rMenu.update(new Vector2(Gdx.input.getX(), Gdx.input.getY()), Gdx.input.isButtonPressed(Input.Buttons.LEFT));
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update(true);
		 
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
	    createdSpell = null;
	    nifty.addScreen("Pause", new ScreenBuilder("Hello Nifty Screen"){{
	    	controller(new PauseScreenController(p));
	    	layer(new LayerBuilder("Layer_0"){{
	    		childLayoutCenter();
	    		panel(new PanelBuilder(){{
	    			//style("nifty-panel-simple");
	    			this.backgroundColor("#000f");
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
	    	createdSpell = new TopLevelSpell();
	    }

	    panel.setUserData("containingSpell", createdSpell);
	    sr = new ShapeRenderer();
		Gdx.input.setCursorPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		Gdx.input.setCursorCatched(false);
		}

	@Override
	public void hide() {
		
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


