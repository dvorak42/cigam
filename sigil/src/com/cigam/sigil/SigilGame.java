package com.cigam.sigil;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cigam.sigil.screens.AdventureScreen;
import com.cigam.sigil.screens.PauseScreen;

public class SigilGame extends Game {
	public PauseScreen pauseScreen;
	public AdventureScreen gameScreen;
	
    public SpriteBatch batch;
    public SpriteBatch hudBatch;
    public BitmapFont font;
		
	@Override
	public void create() {
		hudBatch = new SpriteBatch();
		batch = new SpriteBatch();
		font = new BitmapFont();
		Texture.setEnforcePotImages(false);

		gameScreen = new AdventureScreen(this);
		pauseScreen = new PauseScreen(this, gameScreen);
		setScreen(gameScreen);
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		hudBatch.dispose();
		font.dispose();
	}
}
