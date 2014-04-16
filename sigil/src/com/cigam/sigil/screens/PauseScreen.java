package com.cigam.sigil.screens;

import java.util.Properties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Inputs;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.cigam.sigil.SigilGame;
import com.cigam.sigil.magic.modifiers.AreaToDuration;
import com.cigam.sigil.magic.modifiers.AreaToEffect;
import com.cigam.sigil.magic.modifiers.DurationToArea;
import com.cigam.sigil.magic.modifiers.DurationToEffect;
import com.cigam.sigil.magic.modifiers.EffectToArea;
import com.cigam.sigil.magic.modifiers.EffectToDuration;
import com.cigam.sigil.magic.targets.Self;
import com.cigam.sigil.magic.verbs.Banish;
import com.cigam.sigil.magic.verbs.Bind;
import com.cigam.sigil.magic.verbs.Create;
import com.cigam.sigil.magic.verbs.Summon;
import com.cigam.sigil.materials.Fire;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.gdx.input.GdxInputSystem;
import de.lessvoid.nifty.gdx.render.GdxBatchRenderBackendFactory;
import de.lessvoid.nifty.gdx.sound.GdxSoundDevice;
import de.lessvoid.nifty.render.batch.BatchRenderConfiguration;
import de.lessvoid.nifty.render.batch.BatchRenderDevice;
import de.lessvoid.nifty.spi.time.impl.AccurateTimeProvider;

public class PauseScreen implements Screen {
	SigilGame game;
	Screen parent;
	Sprite pauseImage;
	
	AssetManager assetManager;
	Nifty nifty;

	BatchRenderDevice batchRenderDevice;
	
	ShapeRenderer sr;
	OrthographicCamera camera;
	
	RadialMenu rMenu;
	boolean wasDown = false;
	
	public PauseScreen(final SigilGame game, final Screen parent) {
		this.game = game;
		this.parent = parent;
		Texture texture = new Texture(Gdx.files.internal("art/screens/pause.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		pauseImage = new Sprite(texture);
		
		assetManager = new AssetManager();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		rMenu = new RadialMenu();
		rMenu.color = Color.BLACK;
		RadialMenu verbMenu = new RadialMenu(new Banish(), new Bind(), new Create(), new Summon());
		verbMenu.color = Color.BLUE;
		RadialMenu targetMenu = new RadialMenu(new Fire(), new Self());
		targetMenu.color = Color.RED;
		RadialMenu modifierMenu = new RadialMenu(new AreaToDuration(), new AreaToEffect(), new DurationToArea(), new DurationToEffect(), new EffectToArea(), new EffectToDuration());
		modifierMenu.color = Color.GREEN;
		rMenu.addMenu(verbMenu);
		rMenu.addMenu(targetMenu);
		rMenu.addMenu(modifierMenu);
		
	}

	@Override
	public void render(float delta) {
		nifty.update();
		nifty.render(true);
		
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
			game.setScreen(parent);
		
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
			wasDown = true;
		if(wasDown && !Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			wasDown = false;
			System.out.println(rMenu.getValue());
		}
		rMenu.update(new Vector2(Gdx.input.getX(), Gdx.input.getY()), Gdx.input.isButtonPressed(Input.Buttons.LEFT));
		
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update(true);
		sr.setProjectionMatrix(camera.combined);
		sr.begin(ShapeType.Filled);
		rMenu.render(sr);
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
	    nifty.fromXml("UI/gui_final.xml", "pause", new PauseScreenController());
	    sr = new ShapeRenderer();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
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
	
	public void setSpell(){
		System.out.println("test");
	}

}


