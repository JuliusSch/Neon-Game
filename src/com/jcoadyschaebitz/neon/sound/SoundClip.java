package com.jcoadyschaebitz.neon.sound;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundClip implements Runnable {

	private Clip clip;
	public boolean paused, playOnLoop, playing;

	public static List<SoundClip> clips = new ArrayList<SoundClip>();

	public static SoundClip shotgun_shot = new SoundClip("sounds/shotgun-shot-short.wav", false);
	public static SoundClip pistol_shot = new SoundClip("sounds/pistol-shot4.wav", false);
	public static SoundClip crossbow_shot = new SoundClip("sounds/crossbow-shot4.wav", false);
	public static SoundClip laser_sword_slash = new SoundClip("sounds/sword-slash.wav", false);

	public static SoundClip rain = new SoundClip("sounds/rain-shortened.wav", true);

	public SoundClip(String fileName, boolean playOnLoop) {
		try {
			URL url = SoundClip.class.getClassLoader().getResource(fileName);
			AudioInputStream ais = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("sound file failed to load");
		}
		clips.add(this);
		paused = false;
		playing = false;
		this.playOnLoop = playOnLoop;
	}
	
	public void stop() {
		clip.stop();
		clip.flush();
		clip.setFramePosition(0);
		paused = true;
		playing = false;
	}

	public void play() {
		clip.stop();
		clip.flush();
		clip.setFramePosition(0);
		clip.start();
		paused = false;
		playing = true;
	}

	public void pause() {
		if (playing) {
			clip.stop();
			paused = true;
			playing = false;
		}
	}

	public void resume() {
		if (paused) {
			if (playOnLoop) loop();
			else clip.start();
			paused = false;
			playing = true;
		}
	}

	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		playing = true;
	}

	public void loop(int amount) {
		clip.loop(amount);
		playing = true;
	}

	public void changeUniversalVolume(float v) {
		FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.VOLUME);
		volume.setValue(v);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
