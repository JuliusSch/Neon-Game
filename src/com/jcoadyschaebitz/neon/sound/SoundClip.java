package com.jcoadyschaebitz.neon.sound;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundClip implements Runnable {

	private Clip[] clips;
	private int nrOfClips, c;
	public boolean paused, playOnLoop, playing;

	public static List<SoundClip> allClips = new ArrayList<SoundClip>();

	public static SoundClip shotgun_shot = new SoundClip("sounds/shotgun-shot-short.wav", false);
	public static SoundClip pistol_shot = new SoundClip("sounds/pistol-shot4.wav", false, 4);
	public static SoundClip crossbow_shot = new SoundClip("sounds/crossbow-shot4.wav", false);
	public static SoundClip laser_sword_slash = new SoundClip("sounds/sword-slash.wav", false);

	public static SoundClip rain = new SoundClip("sounds/rain-shortened.wav", true);

	public SoundClip(String fileName, boolean playOnLoop) {
		this(fileName, playOnLoop, 1);
	}

	public SoundClip(String fileName, boolean playOnLoop, int nrOfClips) {
		clips = new Clip[nrOfClips];
		this.nrOfClips = nrOfClips;
		c = 0;
		for (int i = 0; i < nrOfClips; i++) {
			try {
				URL url = SoundClip.class.getClassLoader().getResource(fileName);
				AudioInputStream ais = AudioSystem.getAudioInputStream(url);
				clips[i] = AudioSystem.getClip();
				clips[i].open(ais);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("sound file failed to load");
			}

		}
		allClips.add(this);
		paused = false;
		playing = false;
		this.playOnLoop = playOnLoop;
	}
	
	public void play() {
		c++;
		if (c == nrOfClips) c = 0;
		clips[c].stop();
		clips[c].flush();
		clips[c].setFramePosition(0);
		clips[c].start();
		paused = false;
		playing = true;
	}
	
	public void pause() {
		if (playing) {
			clips[c].stop();
			paused = true;
			playing = false;
		}
	}

	public void stop() {
		clips[c].stop();
		clips[c].flush();
		clips[c].setFramePosition(0);
		paused = true;
		playing = false;
	}

	public void resume() {
		if (paused) {
			if (playOnLoop) loop();
			else clips[c].start();
			paused = false;
			playing = true;
		}
	}

	public void loop() {
		clips[c].loop(Clip.LOOP_CONTINUOUSLY);
		playing = true;
	}

	public void loop(int amount) {
		clips[c].loop(amount);
		playing = true;
	}

	public void changeUniversalVolume(float v) {
		FloatControl volume = (FloatControl) clips[c].getControl(FloatControl.Type.VOLUME);
		volume.setValue(v);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
