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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.cigam.sigil.SigilGame;

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
	
	public PauseScreen(final SigilGame game, final Screen parent) {
		this.game = game;
		this.parent = parent;
		Texture texture = new Texture(Gdx.files.internal("art/screens/pause.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		
		pauseImage = new Sprite(texture);
		
		assetManager = new AssetManager();
	}
	
	public void SetSpell(){
		System.out.println("test");
	}

	@Override
	public void render(float delta) {
		nifty.update();
		nifty.render(true);
		
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
			game.setScreen(parent);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		batchRenderDevice = new BatchRenderDevice(GdxBatchRenderBackendFactory.create());
	    nifty = new Nifty(batchRenderDevice, new GdxSoundDevice(assetManager), new GdxInputSystem(Gdx.input), new AccurateTimeProvider());
		nifty.fromXml("data/sample.xml", "pause");
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
}


