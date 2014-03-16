package com.cigam.sigil.graphics;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.Sound;

public class Assets {
	
	public static boolean DEBUG = true;
	
	public static Map<String, Image> images;
	public static Map<String, Music> musics;
	public static Map<String, Sound> audios;
	public static Image DEFAULT_IMAGE;
	public static Music DEFAULT_MUSIC;
	public static Sound DEFAULT_AUDIO;
	
	public static void load()
	{
		images = new HashMap<String, Image>();
		musics = new HashMap<String, Music>();
		audios = new HashMap<String, Sound>();
		DEFAULT_IMAGE = Assets.loadImage("art/error.png");
		DEFAULT_MUSIC = Assets.loadMusic("art/error.wav");
		DEFAULT_AUDIO = Assets.loadAudio("art/error.wav");
	}
	
	public static Image loadImage(String path)
	{
		try
		{
			if(!images.containsKey(path))
			{
				Image i = new Image(path);
				if(DEBUG)
					System.out.println("Assets.loadImage(\"" + path + "\");");
				images.put(path, i);
			}
		}
		catch(Exception e)
		{
			System.out.println("ERROR LOADING: " + path);
		}
		if(!images.containsKey(path))
			return DEFAULT_IMAGE;
		return images.get(path);
	}

	public static Music loadMusic(String path)
	{
		try
		{
			if(!musics.containsKey(path))
			{
				Music i = new Music(path);
				if(DEBUG)
					System.out.println("Assets.loadMusic(\"" + path + "\");");
				musics.put(path, i);
			}
		}
		catch(Exception e)
		{
			System.out.println("ERROR LOADING: " + path);
		}
		if(!musics.containsKey(path))
			return DEFAULT_MUSIC;
		return musics.get(path);
	}
	
	public static Sound loadAudio(String path)
	{
		try
		{
			if(!audios.containsKey(path))
			{
				Sound i = new Sound(path);
				if(DEBUG)
					System.out.println("Assets.loadAudio(\"" + path + "\");");
				audios.put(path, i);
			}
		}
		catch(Exception e)
		{
			System.out.println("ERROR LOADING: " + path);
		}
		if(!audios.containsKey(path))
			return DEFAULT_AUDIO;
		return audios.get(path);
	}
	
	public static void setVolume(float vol)
	{
		for(Music m : musics.values())
			m.setVolume(vol);
		DEFAULT_MUSIC.setVolume(vol);
	}
}