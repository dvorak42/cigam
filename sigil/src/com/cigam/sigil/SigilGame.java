package com.cigam.sigil;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cigam.sigil.screens.AdventureScreen;
import com.cigam.sigil.screens.HelpScreen1;
import com.cigam.sigil.screens.PauseScreen;

public class SigilGame extends Game {
    public PauseScreen pauseScreen;
    public AdventureScreen gameScreen;
    public HelpScreen1 helpScreen;
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
	helpScreen = new HelpScreen1(this, gameScreen);
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
