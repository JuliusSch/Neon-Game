package com.jcoadyschaebitz.neon.graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Spritesheet {

	private String path;
	public final int SIZE;
	public final int WIDTH, HEIGHT;
	public int[] pixels, alphas;

	public static Spritesheet x16 = new Spritesheet("/textures/sheets/spritesheetx16.png", 512);
	public static Spritesheet x32 = new Spritesheet("/textures/sheets/spritesheetx32.png", 1024, 512);
	public static Spritesheet x24 = new Spritesheet("/textures/sheets/spritesheetx24.png", 768, 768);
	public static Spritesheet x64 = new Spritesheet("/textures/sheets/spritesheetx64.png", 512, 512);
	public static Spritesheet x64UI = new Spritesheet("/textures/sheets/spritesheetx64UI.png", 512, 512);
	public static Spritesheet bullets = new Spritesheet("/textures/sheets/projectiles/bullets.png", 48);
	public static Spritesheet cables = new Spritesheet(x32, 24, 0, 8, 8, 32);

	public static Spritesheet tarmac = new Spritesheet(x16, 13, 9, 3, 3, 16);
	public static Spritesheet PlainWall = new Spritesheet(x16, 11, 13, 3, 3, 16);
	public static Spritesheet poshBoards = new Spritesheet(x16, 0, 21, 3, 3, 16);
	
	public static Spritesheet hCar = new Spritesheet(x16, 11, 2, 3, 2, 16);
	public static Spritesheet shopFrontA = new Spritesheet(x16, 8, 2, 4, 3, 16);
	public static Spritesheet shopFrontB = new Spritesheet(x16, 12, 2, 4, 3, 16);
//	public static Spritesheet initShieldAnimationLayer1 = new Spritesheet(x32, 0, 8, 1, 6, 16, 32);
//	public static Spritesheet initShieldAnimationLayer2 = new Spritesheet(x32, 1, 8, 1, 6, 16, 32);
//	public static Spritesheet initShieldAnimationLayer3 = new Spritesheet(x32, 2, 8, 1, 6, 16, 32);
//	public static Spritesheet initShieldAnimationLayer4 = new Spritesheet(x32, 3, 8, 1, 6, 16, 32);
//	public static Spritesheet initShieldAnimationLayer5 = new Spritesheet(x32, 4, 8, 1, 6, 16, 32);
//	public static Spritesheet initShieldAnimationLayer6 = new Spritesheet(x32, 5, 8, 1, 6, 16, 32);
//	public static Spritesheet initShieldAnimationLayer7 = new Spritesheet(x32, 6, 8, 1, 6, 16, 32);
//	public static Spritesheet initShieldAnimationLayer8 = new Spritesheet(x32, 7, 8, 1, 6, 16, 32);
//	public static Spritesheet initShieldAnimationLayer9 = new Spritesheet(x32, 8, 8, 1, 6, 16, 32);
//	public static Spritesheet initShieldAnimationLayer10 = new Spritesheet(x32, 9, 8, 1, 6, 16, 32);
//	public static Spritesheet initShieldAnimationLayer11 = new Spritesheet(x32, 10, 8, 1, 6, 16, 32);
//	public static Spritesheet initShieldAnimationLayer12 = new Spritesheet(x32, 11, 8, 1, 6, 16, 32);
	
	public static Spritesheet shield2 = new Spritesheet(x32, 0, 7, 32, 1, 8, 64);

	public static Spritesheet firstShieldGlowAnimation = new Spritesheet(x32, 0, 7, 8, 1, 32);
	public static Spritesheet mainShieldAnimation = new Spritesheet(x32, 0, 8, 12, 1, 16, 32);
	public static Spritesheet mainShieldGlowAnimation = new Spritesheet(x32, 8, 4, 1, 4, 32);
	
	//UI:
	public static Spritesheet pauseMenuBackground = new Spritesheet("/textures/UI/menubackground.png", 400, 250);
	public static Spritesheet menuOutline = new Spritesheet("/textures/UI/menuoutline.png", 400, 225);
	public static Spritesheet loadMenuOutline = new Spritesheet("/textures/UI/loadMenuOutline.png", 244, 176);
	public static Spritesheet mainMenuBackground = new Spritesheet("/textures/UI/main_menu.png", 640, 360);
	public static Spritesheet buttonCorners = new Spritesheet(x16, 20, 22, 2, 2, 8);
	public static Spritesheet buttonCornersHighlight = new Spritesheet(x16, 24, 22, 2, 2, 8);

	//	Entity animations:
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
	
	public static Spritesheet swordEnemyRightWalking = new Spritesheet(x24, 0, 8, 8, 1, 24);
	public static Spritesheet swordEnemyLeftWalking = new Spritesheet(x24, 0, 9, 8, 1, 24);
	public static Spritesheet swordEnemyRightIdle = new Spritesheet(x24, 4, 0, 1, 4, 24);
	public static Spritesheet swordEnemyLeftIdle = new Spritesheet(x24, 5, 0, 1, 4, 24);
	public static Spritesheet swordEnemyRightDamage = new Spritesheet(x24, 8, 8, 4, 1, 24);
	public static Spritesheet swordEnemyLeftDamage = new Spritesheet(x24, 8, 9, 4, 1, 24);
	public static Spritesheet swordEnemyRightDying = new Spritesheet(x24, 8, 8, 7, 1, 24);
	public static Spritesheet swordEnemyLeftDying = new Spritesheet(x24, 8, 9, 7, 1, 24);
	
	public static Spritesheet swordFlash = new Spritesheet(x32, 0, 3, 3, 1, 64);
	
	public static Spritesheet slowEnemyRightWalking = new Spritesheet(x24, 0, 10, 8, 1, 24);
	public static Spritesheet slowEnemyLeftWalking = new Spritesheet(x24, 0, 11, 8, 1, 24);
	public static Spritesheet slowEnemyRightIdle = new Spritesheet(x24, 6, 0, 1, 4, 24);
	public static Spritesheet slowEnemyLeftIdle = new Spritesheet(x24, 7, 0, 1, 4, 24);
	public static Spritesheet slowEnemyRightDamage = new Spritesheet(x24, 8, 10, 4, 1, 24);
	public static Spritesheet slowEnemyLeftDamage = new Spritesheet(x24, 8, 11, 4, 1, 24);
	public static Spritesheet slowEnemyRightDying = new Spritesheet(x24, 8, 10, 7, 1, 24);
	public static Spritesheet slowEnemyLeftDying = new Spritesheet(x24, 8, 11, 7, 1, 24);
	
	public static Spritesheet heavyLeftWalking = new Spritesheet(x32, 0, 1, 8, 1, 32);
	public static Spritesheet heavyRightWalking = new Spritesheet(x32, 0, 2, 8, 1, 32);
	public static Spritesheet heavyRightDamage = new Spritesheet(x32, 0, 3, 4, 1, 32);
	public static Spritesheet heavyLeftDamage = new Spritesheet(x32, 4, 3, 4, 1, 32);
	public static Spritesheet heavyRightIdle = new Spritesheet(x32, 0, 4, 4, 1, 32);
	public static Spritesheet heavyLeftIdle = new Spritesheet(x32, 3, 4, 4, 1, 32);
	public static Spritesheet heavyRightDying = new Spritesheet(x32, 0, 5, 4, 1, 32);
	public static Spritesheet heavyLeftDying = new Spritesheet(x32, 3, 5, 4, 1, 32);
	
	public static Spritesheet fastMeleeLeftIdle = new Spritesheet(x64, 6, 4, 1, 4, 64, 64);
	public static Spritesheet fastMeleeRightIdle = new Spritesheet(x64, 7, 4, 1, 4, 64, 64);
	public static Spritesheet fastMeleeWindUpLeft = new Spritesheet(x64, 0, 0, 8, 2, 64, 64);
	public static Spritesheet fastMeleeLeftWalking = new Spritesheet(x64, 0, 4, 4, 1, 64, 64);
	public static Spritesheet fastMeleeRightWalking = new Spritesheet(x64, 0, 5, 4, 1, 64, 64);
	public static Spritesheet fastMeleeDamageLeft = new Spritesheet(x64, 0, 6, 4, 1, 64, 64);
	public static Spritesheet fastMeleeDamageRight = new Spritesheet(x64, 0, 7, 4, 1, 64, 64);
	
	public static Spritesheet poolGangsterRightWalking = new Spritesheet(x24, 0, 12, 8, 1, 24);
	public static Spritesheet poolGangsterLeftWalking = new Spritesheet(x24, 0, 13, 8, 1, 24);
	public static Spritesheet poolGangsterRightIdle = new Spritesheet(x24, 15, 20, 1, 4, 24);
	public static Spritesheet poolGangsterLeftIdle = new Spritesheet(x24, 16, 20, 1, 4, 24);
	public static Spritesheet poolGangsterRightDamage = new Spritesheet(x24, 8, 12, 4, 1, 24);
	public static Spritesheet poolGangsterLeftDamage = new Spritesheet(x24, 8, 13, 4, 1, 24);
	public static Spritesheet poolGangsterRightDying = new Spritesheet(x24, 8, 12, 7, 1, 24);
	public static Spritesheet poolGangsterLeftDying = new Spritesheet(x24, 8, 13, 7, 1, 24);
	
	
	
//	public static Spritesheet gangMemberLeftWalking = new Spritesheet(x24, );
//	public static Spritesheet gangMemberRightWalking = new Spritesheet(x24, );
	public static Spritesheet gangMemberLeftIdle = new Spritesheet(x24, 16, 16, 1, 4, 24);
	public static Spritesheet gangMemberRightIdle = new Spritesheet(x24, 15, 16, 1, 4, 24);
//	public static Spritesheet gangMemberLeftDamage = new Spritesheet(x24,);
//	public static Spritesheet gangMemberRightDamage = new Spritesheet(x24,);
//	public static Spritesheet gangMemberLeftDying = new Spritesheet(x24, );
//	public static Spritesheet gangMemberRightDying = new Spritesheet(x24, );
	
	//	Fonts:
	public static Spritesheet font1 = new Spritesheet("/fonts/font1.png", 208, 96);
	public static Spritesheet font2 = new Spritesheet("/fonts/font2.png", 104, 48);
	public static Spritesheet font3 = new Spritesheet("/fonts/font3.png", 65, 30);
	public static Spritesheet font4 = new Spritesheet("/fonts/font12px.png", 156, 72);
	public static Spritesheet font1Characters = new Spritesheet(font1, 0, 0, 13, 6, 16);
	public static Spritesheet font2Characters = new Spritesheet(font2, 0, 0, 13, 6, 8);
	public static Spritesheet font3Characters = new Spritesheet(font3, 0, 0, 13, 6, 5);
	public static Spritesheet font4Characters = new Spritesheet(font4, 0, 0, 13, 6, 12);

	//	Projectiles:
	public static Spritesheet shotgun_pellet = new Spritesheet(bullets, 0, 1, 8, 2, 6);
	public static Spritesheet bolt = new Spritesheet(bullets, 0, 3, 8, 2, 6);
	public static Spritesheet pistol_bullet_fade = new Spritesheet(x16, 8, 7, 1, 3, 16);
	public static Spritesheet fast_bullet_anim = new Spritesheet(x24, 8, 1, 2, 1, 24);
	
	//	Particles:
	public static Spritesheet rain = new Spritesheet(bullets, 5, 0, 3, 1, 6);
	public static Spritesheet ventSteam = new Spritesheet(x32, 12, 0, 6, 1, 32);
	
	//	Weapons:
	public static Spritesheet pistolShine = new Spritesheet(x32, 9, 0, 1, 7, 32);
	public static Spritesheet shotgunShine = new Spritesheet(x32, 10, 0, 1, 7, 32);
	public static Spritesheet crossbowShine = new Spritesheet(x32, 11, 0, 1, 7, 32);
	public static Spritesheet assaultRifleShine = new Spritesheet(x32, 8, 0, 1, 7, 32);
	
	//	Tiles:
	public static Spritesheet wallHorizontalBars = new Spritesheet(x16, 5, 12, 5, 1, 16);
	public static Spritesheet factoryWindowTileBack = new Spritesheet(x16, 7, 13, 2, 3, 16);
	public static Spritesheet factoryWindowTileLit = new Spritesheet(x16, 9, 13, 2, 3, 16);
	public static Spritesheet wireFenceHorizontal = new Spritesheet(x16, 0, 6, 4, 1, 16, 48);
	public static Spritesheet poolGrateBorder = new Spritesheet(x16, 11, 19, 2, 8, 16);
	public static Spritesheet wallTopBorder = new Spritesheet(x16, 7, 19, 2, 8, 16);
	
	private Sprite[] sprites;

	public Spritesheet(Spritesheet sheet, int x, int y, int width, int height, int sprSize) { // all in sprite precision
		this(sheet, x, y, width, height, sprSize, sprSize);
	}
	
	public Spritesheet(Spritesheet sheet, int x, int y, int width, int height, int sprWidth, int sprHeight) { // all in sprite precision
		int xx = x * sprWidth;
		int yy = y * sprHeight;
		WIDTH = width * sprWidth;
		HEIGHT = height * sprHeight;
		if (width == height) SIZE = width;
		else SIZE = -1;
		pixels = new int[WIDTH * HEIGHT];
		alphas = new int[WIDTH * HEIGHT];
		for (int y0 = 0; y0 < HEIGHT; y0++) {
			int yp = yy + y0;
			for (int x0 = 0; x0 < WIDTH; x0++) {
				int xp = xx + x0;
				pixels[x0 + y0 * WIDTH] = sheet.pixels[xp + yp * sheet.WIDTH];
				alphas[x0 + y0 * WIDTH] = sheet.alphas[xp + yp * sheet.WIDTH];
			}
		}

		int frame = 0;
		sprites = new Sprite[width * height];
		for (int ya = 0; ya < height; ya++) {
			for (int xa = 0; xa < width; xa++) {
				int spritePixels[] = new int[sprWidth * sprHeight];
				for (int y0 = 0; y0 < sprHeight; y0++) {
					for (int x0 = 0; x0 < sprWidth; x0++) {
						spritePixels[x0 + y0 * sprWidth] = pixels[(x0 + xa * sprWidth) + (y0 + ya * sprHeight) * WIDTH];
					}
				}
				Sprite sprite = new Sprite(spritePixels, sprWidth, sprHeight);
				sprites[frame++] = sprite;
			}
		}
	}

	public Spritesheet(String path, int size) {
		this(path, size, size);
	}

	public Spritesheet(String path, int width, int height) {
		this.path = path;
		SIZE = -1;
		WIDTH = width;
		HEIGHT = height;
		pixels = new int[width * height];
		alphas = new int[width * height];
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
			for (int i = 0; i < pixels.length; i++) {
				alphas[i] = (pixels[i] >> 24 & 0xff);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
