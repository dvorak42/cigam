package com.cigam.sigil;

import org.newdawn.slick.*;

public class Main {
  public static void main(String[] args) throws SlickException {
     AppGameContainer app = new AppGameContainer(new CigamGame());
 
     app.setDisplayMode(Constants.DISPLAY_DIMS[0], Constants.DISPLAY_DIMS[1], false);
     app.start();
  }
}
