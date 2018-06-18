package com.jcoadyschaebitz.neon.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {

	private String path;
	public final int SIZE;
	public final int WIDTH, HEIGHT;
	public int[] pixels;

	public static Spritesheet spritesheet1 = new Spritesheet("/textures/sheets/spritesheetx16.png", 256);
	public static Spritesheet x32 = new Spritesheet("/textures/sheets/spritesheetx32.png", 1024, 512);
	public static Spritesheet x24 = new Spritesheet("/textures/sheets/spritesheetx24.png", 768, 768);
	public static Spritesheet x64 = new Spritesheet("/textures/sheets/spritesheetx64.png", 512, 512);
	public static Spritesheet bullets = new Spritesheet("/textures/sheets/projectiles/bullets.png", 48);
	
	public static Spritesheet hCar = new Spritesheet(spritesheet1, 11, 2, 3, 2, 16);
	public static Spritesheet shopFrontA = new Spritesheet(spritesheet1, 8, 2, 4, 3, 16);
	public static Spritesheet shopFrontB = new Spritesheet(spritesheet1, 12, 2, 4, 3, 16);
	public static Spritesheet firstShieldAnimation = new Spritesheet(x32, 0, 6, 8, 1, 32);
	public static Spritesheet firstShieldGlowAnimation = new Spritesheet(x32, 0, 7, 8, 1, 32);
	public static Spritesheet mainShieldAnimation = new Spritesheet(x32, 8, 0, 1, 4, 32);
	public static Spritesheet mainShieldGlowAnimation = new Spritesheet(x32, 8, 4, 1, 4, 32);
	public static Spritesheet bigVent = new Spritesheet(x24, 7, 0, 1, 3, 48);
	
	//UI:
	public static Spritesheet pauseMenuBackground = new Spritesheet("/textures/UI/menubackground.png", 400, 250);
	public static Spritesheet menuOutline = new Spritesheet("/textures/UI/menuoutline.png", 400, 225);

	public static Spritesheet player_right_walking = new Spritesheet(x24, 0, 4, 8, 1, 24);
	public static Spritesheet player_left_walking = new Spritesheet(x24, 0, 5, 8, 1, 24);
	public static Spritesheet player_right_idle = new Spritesheet(x24, 0, 0, 1, 4, 24);
	public static Spritesheet player_left_idle = new Spritesheet(x24, 1, 0, 1, 4, 24);
	public static Spritesheet player_right_damage = new Spritesheet(x24, 8, 4, 4, 1, 24);
	public static Spritesheet player_left_damage = new Spritesheet(x24, 8, 5, 4, 1, 24);
	public static Spritesheet player_right_dying = new Spritesheet(x24, 8, 4, 7, 1, 24);
	public static Spritesheet player_left_dying = new Spritesheet(x24, 8, 5, 7, 1, 24);
	
	public static Spritesheet soldier_right_walking = new Spritesheet(x24, 0, 6, 8, 1, 24);
	public static Spritesheet soldier_left_walking = new Spritesheet(x24, 0, 7, 8, 1, 24);
	public static Spritesheet soldier_right_idle = new Spritesheet(x24, 2, 0, 1, 4, 24);
	public static Spritesheet soldier_left_idle = new Spritesheet(x24, 3, 0, 1, 4, 24);
	public static Spritesheet soldier_right_damage = new Spritesheet(x24, 8, 6, 4, 1, 24);
	public static Spritesheet soldier_left_damage = new Spritesheet(x24, 8, 7, 4, 1, 24);
	public static Spritesheet soldier_right_dying = new Spritesheet(x24, 8, 6, 6, 1, 24);
	public static Spritesheet soldier_left_dying = new Spritesheet(x24, 8, 7, 6, 1, 24);
	
	public static Spritesheet swordEnemy_right_walking = new Spritesheet(x24, 0, 8, 8, 1, 24);
	public static Spritesheet swordEnemy_left_walking = new Spritesheet(x24, 0, 9, 8, 1, 24);
	public static Spritesheet swordEnemy_right_idle = new Spritesheet(x24, 4, 0, 1, 4, 24);
	public static Spritesheet swordEnemy_left_idle = new Spritesheet(x24, 5, 0, 1, 4, 24);
	public static Spritesheet swordEnemy_right_damage = new Spritesheet(x24, 8, 8, 4, 1, 24);
	public static Spritesheet swordEnemy_left_damage = new Spritesheet(x24, 8, 9, 4, 1, 24);
	public static Spritesheet swordEnemy_right_dying = new Spritesheet(x24, 8, 8, 7, 1, 24);
	public static Spritesheet swordEnemy_left_dying = new Spritesheet(x24, 8, 9, 7, 1, 24);
	
	public static Spritesheet swordFlash = new Spritesheet(x32, 0, 4, 3, 1, 64);
	
	public static Spritesheet slowEnemy_right_walking = new Spritesheet(x24, 0, 10, 8, 1, 24);
	public static Spritesheet slowEnemy_left_walking = new Spritesheet(x24, 0, 11, 8, 1, 24);
	public static Spritesheet slowEnemy_right_idle = new Spritesheet(x24, 6, 0, 1, 4, 24);
	public static Spritesheet slowEnemy_left_idle = new Spritesheet(x24, 7, 0, 1, 4, 24);
	public static Spritesheet slowEnemy_right_damage = new Spritesheet(x24, 8, 10, 4, 1, 24);
	public static Spritesheet slowEnemy_left_damage = new Spritesheet(x24, 8, 11, 4, 1, 24);
	public static Spritesheet slowEnemy_right_dying = new Spritesheet(x24, 8, 10, 7, 1, 24);
	public static Spritesheet slowEnemy_left_dying = new Spritesheet(x24, 8, 11, 7, 1, 24);
	
	public static Spritesheet heavy_left_walking = new Spritesheet(x32, 0, 1, 8, 1, 32);
	public static Spritesheet heavy_right_walking = new Spritesheet(x32, 0, 2, 8, 1, 32);
	public static Spritesheet heavy_right_damage = new Spritesheet(x32, 0, 3, 4, 1, 32);
	public static Spritesheet heavy_left_damage = new Spritesheet(x32, 4, 3, 4, 1, 32);
	public static Spritesheet heavy_right_idle = new Spritesheet(x32, 0, 4, 4, 1, 32);
	public static Spritesheet heavy_left_idle = new Spritesheet(x32, 3, 4, 4, 1, 32);
	public static Spritesheet heavy_right_dying = new Spritesheet(x32, 0, 5, 4, 1, 32);
	public static Spritesheet heavy_left_dying = new Spritesheet(x32, 3, 5, 4, 1, 32);
	
	public static Spritesheet fastMeleeRightIdle = new Spritesheet(x32, 18, 0, 1, 4, 32);
	
	public static Spritesheet font1 = new Spritesheet("/fonts/font1.png", 208, 96);
	public static Spritesheet font2 = new Spritesheet("/fonts/font2.png", 104, 48);
	public static Spritesheet font3 = new Spritesheet("/fonts/font3.png", 65, 30);
	public static Spritesheet font1Characters = new Spritesheet(font1, 0, 0, 13, 6, 16);
	public static Spritesheet font2Characters = new Spritesheet(font2, 0, 0, 13, 6, 8);
	public static Spritesheet font3Characters = new Spritesheet(font3, 0, 0, 13, 6, 5);

	public static Spritesheet shotgun_pellet = new Spritesheet(bullets, 0, 1, 8, 2, 6);
	public static Spritesheet bolt = new Spritesheet(bullets, 0, 3, 8, 2, 6);
	public static Spritesheet pistol_bullet_fade = new Spritesheet(Spritesheet.spritesheet1, 8, 7, 1, 3, 16);
	public static Spritesheet fast_bullet_anim = new Spritesheet(Spritesheet.x24, 8, 1, 2, 1, 24);
	
	public static Spritesheet rain = new Spritesheet(bullets, 5, 0, 3, 1, 6);
	public static Spritesheet ventSteam = new Spritesheet(x32, 12, 0, 6, 1, 32);
	
	public static Spritesheet pistolShine = new Spritesheet(Spritesheet.x32, 9, 0, 1, 7, 32);
	public static Spritesheet shotgunShine = new Spritesheet(Spritesheet.x32, 10, 0, 1, 7, 32);
	public static Spritesheet crossbowShine = new Spritesheet(Spritesheet.x32, 11, 0, 1, 7, 32);
	

	private Sprite[] sprites;

	public Spritesheet(Spritesheet sheet, int x, int y, int width, int height, int sprSize) { // all in sprite precision
		int xx = x * sprSize;
		int yy = y * sprSize;
		WIDTH = width * sprSize;
		HEIGHT = height * sprSize;
		if (width == height) SIZE = width;
		else SIZE = -1;
		pixels = new int[WIDTH * HEIGHT];
		for (int y0 = 0; y0 < HEIGHT; y0++) {
			int yp = yy + y0;
			for (int x0 = 0; x0 < WIDTH; x0++) {
				int xp = xx + x0;
				pixels[x0 + y0 * WIDTH] = sheet.pixels[xp + yp * sheet.WIDTH];
			}
		}

		int frame = 0;
		sprites = new Sprite[width * height];
		for (int ya = 0; ya < height; ya++) {
			for (int xa = 0; xa < width; xa++) {
				int spritePixels[] = new int[sprSize * sprSize];
				for (int y0 = 0; y0 < sprSize; y0++) {
					for (int x0 = 0; x0 < sprSize; x0++) {
						spritePixels[x0 + y0 * sprSize] = pixels[(x0 + xa * sprSize) + (y0 + ya * sprSize) * WIDTH];
					}
				}
				Sprite sprite = new Sprite(spritePixels, sprSize, sprSize);
				sprites[frame++] = sprite;
			}
		}
	}

	public Spritesheet(String path, int size) {
		this.path = path;
		SIZE = size;
		WIDTH = size;
		HEIGHT = size;
		pixels = new int[SIZE * SIZE];
		load();
	}

	public Spritesheet(String path, int width, int height) {
		this.path = path;
		SIZE = -1;
		WIDTH = width;
		HEIGHT = height;
		pixels = new int[width * height];
		load();
	}

	public Sprite[] getSprites() {
		return sprites;
	}

	private void load() {
		try {
			BufferedImage image = ImageIO.read(Spritesheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
