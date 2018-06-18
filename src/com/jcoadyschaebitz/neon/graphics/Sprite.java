package com.jcoadyschaebitz.neon.graphics;

import com.jcoadyschaebitz.neon.Game;

public class Sprite {

	public final int SIZE;
	private int x, y;
	private int width, height;
	public int[] pixels;
	protected Spritesheet sheet;

//	test sprites:
	public static Sprite dirt = new Sprite(16, 0, 0, Spritesheet.spritesheet1);
	public static Sprite grass = new Sprite(16, 0, 1, Spritesheet.spritesheet1);
	public static Sprite blueGrass = new Sprite(16, 0, 2, Spritesheet.spritesheet1);
	public static Sprite voidSprite = new Sprite(16, 0xff566543);
	public static Sprite nullSprite = new Sprite(16, 0xffff00ff);
	
	public static Sprite blackFade = new Sprite(Game.getWindowWidth(), Game.getWindowWidth(), 0xff000000);
	
//	floor sprites:
	public static Sprite tarmac = new Sprite(16, 6, 0, Spritesheet.spritesheet1);
	public static Sprite tarmac_1 = new Sprite(16, 6, 1, Spritesheet.spritesheet1);
	public static Sprite tarmac_2 = new Sprite(16, 6, 2, Spritesheet.spritesheet1);
	public static Sprite tarmac_3 = new Sprite(16, 6, 3, Spritesheet.spritesheet1);
	public static Sprite tarmac_4 = new Sprite(16, 6, 4, Spritesheet.spritesheet1);
	public static Sprite tarmac_5 = new Sprite(16, 6, 5, Spritesheet.spritesheet1);
	public static Sprite steps_arch = new Sprite(16, 6, 6, Spritesheet.spritesheet1);
	public static Sprite steps_base = new Sprite(16, 6, 7, Spritesheet.spritesheet1);
	public static Sprite steps_base_right = new Sprite(16, 6, 8, Spritesheet.spritesheet1);
	public static Sprite steps_base_left = new Sprite(16, 6, 9, Spritesheet.spritesheet1);
	public static Sprite steps_down = new Sprite(16, 11, 0, Spritesheet.spritesheet1);
	public static Sprite steps_right = new Sprite(16, 2, 1, Spritesheet.spritesheet1);
	public static Sprite steps_left = new Sprite(16, 2, 2, Spritesheet.spritesheet1);
	public static Sprite steps_up = new Sprite(16, 2, 3, Spritesheet.spritesheet1);
	public static Sprite pavement = new Sprite(16, 7, 4, Spritesheet.spritesheet1);
	public static Sprite pavementEdge = new Sprite(16, 7, 5, Spritesheet.spritesheet1);
	public static Sprite barCarpet = new Sprite(16, 10, 1, Spritesheet.spritesheet1);
	public static Sprite barCarpetSteps1 = new Sprite(16, 10, 0, Spritesheet.spritesheet1);
	public static Sprite barCarpetSteps2 = new Sprite(16, 12, 1, Spritesheet.spritesheet1);
	public static Sprite barCarpetSteps3 = new Sprite(16, 13, 1, Spritesheet.spritesheet1);
	public static Sprite barCarpetSteps4 = new Sprite(16, 12, 0, Spritesheet.spritesheet1);
	public static Sprite woodPlanksSunset = new Sprite(16, 12, 1, Spritesheet.spritesheet1);
	public static Sprite insideTiles = new Sprite(16, 11, 1, Spritesheet.spritesheet1);
	
//	wall sprites: 
	public static Sprite wall = new Sprite(16, 5, 0, Spritesheet.spritesheet1);
	public static Sprite wallDark = new Sprite(16, 2, 0, Spritesheet.spritesheet1);
	public static Sprite wallDarkTransitionTop = new Sprite(16, 7, 7, Spritesheet.spritesheet1);
	public static Sprite wallDarkTransitionBottom = new Sprite(16, 7, 8, Spritesheet.spritesheet1);
	public static Sprite wallDarkTransitionLeft = new Sprite(16, 5, 4, Spritesheet.spritesheet1);
	public static Sprite wallDarkTransitionRight = new Sprite(16, 7, 10, Spritesheet.spritesheet1);
	public static Sprite wallDarkTransitionTopLeftClosed = new Sprite(16, 8, 7, Spritesheet.spritesheet1);
	public static Sprite wallDarkTransitionTopRightClosed = new Sprite(16, 8, 8, Spritesheet.spritesheet1);
	public static Sprite wallDarkTransitionBottomLeftClosed = new Sprite(16, 8, 9, Spritesheet.spritesheet1);
	public static Sprite wallDarkTransitionBottomRightClosed = new Sprite(16, 8, 10, Spritesheet.spritesheet1);
	public static Sprite wallDarkTransitionTopLeftOpen = new Sprite(16, 9, 7, Spritesheet.spritesheet1);
	public static Sprite wallDarkTransitionTopRightOpen = new Sprite(16, 9, 8, Spritesheet.spritesheet1);
	public static Sprite wallDarkTransitionBottomLeftOpen = new Sprite(16, 9, 9, Spritesheet.spritesheet1);
	public static Sprite wallDarkTransitionBottomRightOpen = new Sprite(16, 9, 10, Spritesheet.spritesheet1);
	
	public static Sprite chineseNeonSign = new Sprite(16, 9, 7, Spritesheet.spritesheet1);
	public static Sprite wallGrate = new Sprite(16, 5, 1, Spritesheet.spritesheet1);
	public static Sprite pipes = new Sprite(16, 5, 2, Spritesheet.spritesheet1);
	public static Sprite wall3 = new Sprite(16, 5, 3, Spritesheet.spritesheet1);
	public static Sprite window1 = new Sprite(16, 0, 0, Spritesheet.spritesheet1);
	public static Sprite window2 = new Sprite(16, 1, 0, Spritesheet.spritesheet1);
	public static Sprite window3 = new Sprite(16, 0, 1, Spritesheet.spritesheet1);
	public static Sprite window4 = new Sprite(16, 1, 1, Spritesheet.spritesheet1);
	public static Sprite factoryWindow1 = new Sprite(16, 0, 2, Spritesheet.spritesheet1);
	public static Sprite factoryWindow2 = new Sprite(16, 1, 2, Spritesheet.spritesheet1);
	public static Sprite factoryWindow3 = new Sprite(16, 0, 3, Spritesheet.spritesheet1);
	public static Sprite factoryWindow4 = new Sprite(16, 1, 3, Spritesheet.spritesheet1);
	public static Sprite factoryWindow5 = new Sprite(16, 0, 4, Spritesheet.spritesheet1);
	public static Sprite factoryWindow6 = new Sprite(16, 1, 4, Spritesheet.spritesheet1);
	public static Sprite factoryWindowLit1 = new Sprite(16, 12, 5, Spritesheet.spritesheet1);
	public static Sprite factoryWindowLit2 = new Sprite(16, 13, 5, Spritesheet.spritesheet1);
	public static Sprite factoryWindowLit3 = new Sprite(16, 12, 6, Spritesheet.spritesheet1);
	public static Sprite factoryWindowLit4 = new Sprite(16, 13, 6, Spritesheet.spritesheet1);
	public static Sprite factoryWindowLit5 = new Sprite(16, 12, 7, Spritesheet.spritesheet1);
	public static Sprite factoryWindowLit6 = new Sprite(16, 13, 7, Spritesheet.spritesheet1);
	public static Sprite scaffold_left = new Sprite(16, 8, 5, Spritesheet.spritesheet1);
	public static Sprite scaffold_right = new Sprite(16, 9, 5, Spritesheet.spritesheet1);
	public static Sprite scaffold_right1 = new Sprite(16, 10, 5, Spritesheet.spritesheet1);
	public static Sprite scaffold_right2 = new Sprite(16, 11, 5, Spritesheet.spritesheet1);
	public static Sprite scaffold_right3 = new Sprite(16, 10, 6, Spritesheet.spritesheet1);
	public static Sprite scaffold_left_base = new Sprite(16, 8, 6, Spritesheet.spritesheet1);
	public static Sprite scaffold_right_base = new Sprite(16, 9, 6, Spritesheet.spritesheet1);
	public static Sprite wallPipesRightEnd = new Sprite(16, 5, 5, Spritesheet.spritesheet1);
	public static Sprite wallPipesLeftEnd = new Sprite(16, 5, 7, Spritesheet.spritesheet1);
	public static Sprite scaffold_top = new Sprite(16, 5, 6, Spritesheet.spritesheet1);
	public static Sprite wallPanel1 = new Sprite(16, 14, 5, Spritesheet.spritesheet1);
	public static Sprite wallPanel2 = new Sprite(16, 15, 5, Spritesheet.spritesheet1);
	public static Sprite wallPanel3 = new Sprite(16, 14, 6, Spritesheet.spritesheet1);
	public static Sprite wallPanel4 = new Sprite(16, 15, 6, Spritesheet.spritesheet1);
	public static Sprite green_neon_sign_1 = new Sprite(16, 11, 6, Spritesheet.spritesheet1);
	public static Sprite green_neon_sign_2 = new Sprite(16, 11, 7, Spritesheet.spritesheet1);
	public static Sprite green_neon_sign_3 = new Sprite(16, 11, 8, Spritesheet.spritesheet1);
	public static Sprite red_neon_sign_1 = new Sprite(16, 12, 8, Spritesheet.spritesheet1);
	public static Sprite red_neon_sign_2 = new Sprite(16, 12, 9, Spritesheet.spritesheet1);
	public static Sprite red_neon_sign_3 = new Sprite(16, 12, 10, Spritesheet.spritesheet1);
	public static Sprite orange_neon_sign_1 = new Sprite(16, 10, 7, Spritesheet.spritesheet1);
	public static Sprite orange_neon_sign_2 = new Sprite(16, 10, 8, Spritesheet.spritesheet1);
	public static Sprite air_con_unit_1 = new Sprite(16, 10, 9, Spritesheet.spritesheet1);
	public static Sprite air_con_unit_2 = new Sprite(16, 11, 9, Spritesheet.spritesheet1);
	public static Sprite air_con_unit_3 = new Sprite(16, 10, 10, Spritesheet.spritesheet1);
	public static Sprite air_con_unit_4 = new Sprite(16, 11, 10, Spritesheet.spritesheet1);
	public static Sprite wallPipes1 = new Sprite(16, 5, 8, Spritesheet.spritesheet1);
	public static Sprite wallPipes2 = new Sprite(16, 5, 9, Spritesheet.spritesheet1);
	public static Sprite bigVent1 = new Sprite(16, 14, 7, Spritesheet.spritesheet1);
	public static Sprite bigVent2 = new Sprite(16, 15, 7, Spritesheet.spritesheet1);
	public static Sprite bigVent3 = new Sprite(16, 14, 8, Spritesheet.spritesheet1);
	public static Sprite bigVent4 = new Sprite(16, 15, 8, Spritesheet.spritesheet1);
	public static Sprite biggerVent = new Sprite(48, 7, 0, Spritesheet.x24);
	
	public static Sprite wireFence = new Sprite(16, 48, 0, 2, Spritesheet.spritesheet1);
	
	public static Sprite tileBottomShadow = new Sprite(16, 32, 14, 0, Spritesheet.spritesheet1);
	public static Sprite tileLeftShadowTop = new Sprite(16, 13, 1, Spritesheet.spritesheet1);
	public static Sprite tileLeftShadowBottom = new Sprite(16, 12, 0, Spritesheet.spritesheet1);
	public static Sprite tileRightShadowTop = Sprite.flipSprite(tileLeftShadowTop);
	public static Sprite tileRightShadowBottom = Sprite.flipSprite(tileLeftShadowBottom);
	public static Sprite shadow1 = new Sprite(16, 5, 11, Spritesheet.spritesheet1);
	
	public static Sprite down_facing_bin = new Sprite (48, 32, 5, 0, Spritesheet.x24);
	public static Sprite down_facing_bin_shadow = new Sprite(48, 24, 5, 2, Spritesheet.x24);
	public static Sprite left_facing_bin = new Sprite(24, 48, 12, 0, Spritesheet.x24);
	public static Sprite left_facing_bin_shadow = new Sprite(24, 48, 13, 0, Spritesheet.x24);
	public static Sprite right_facing_bin = Sprite.flipSprite(left_facing_bin);
	
	public static Sprite car_down = new Sprite(48, 72, 9, 0, Spritesheet.x24);
	public static Sprite car_down_shadow = new Sprite(48, 72, 9, 1, Spritesheet.x24);
	public static Sprite car_left = new Sprite(72, 48, 7, 0, Spritesheet.x24);
	public static Sprite car_left_shadow = new Sprite(72, 48, 7, 1, Spritesheet.x24);
	public static Sprite stall1_down = new Sprite(72, 48, 8, 0, Spritesheet.x24);
	public static Sprite stall1_down_shadow = new Sprite(72, 24, 8, 2, Spritesheet.x24);
	public static Sprite canopy_right = new Sprite(24, 72, 28, 0, Spritesheet.x24);
	public static Sprite door_glow_left = new Sprite (48, 15, 0, Spritesheet.x24);
	public static Sprite barCounter = new Sprite(144, 72, 0, 4, Spritesheet.x24);
	public static Sprite barWall = new Sprite(144, 48, 0, 8, Spritesheet.x24);
	public static Sprite barStool = new Sprite(24, 0, 15, Spritesheet.x24);
	public static Sprite barScreenSolid = new Sprite(72, 48, 0, 9, Spritesheet.x24);
	public static Sprite barScreenTranslucent = new Sprite(72, 48, 0, 10, Spritesheet.x24);
	
//	player sprites:
	public static Sprite playerR = new Sprite(24, 0, 0, Spritesheet.x24);
	public static Sprite shadow = new Sprite(24, 27, 28, Spritesheet.x24);
	public static Sprite player_dead_right = new Sprite(24, 11, 7, Spritesheet.x24);
	public static Sprite player_dead_left = new Sprite(24, 11, 9, Spritesheet.x24);
	
	public static Sprite helpfulMan = new Sprite(24, 6, 1, Spritesheet.x24);
	public static Sprite cart = new Sprite(72, 48, 3, 0, Spritesheet.x24);
	public static Sprite cartShadow = new Sprite(72, 24, 3, 2, Spritesheet.x24);
	
//	projectile sprites:
	public static Sprite pistol_bullet = new Sprite(16, 8, 7, Spritesheet.spritesheet1);
	public static Sprite pistol_bullet_glow = new Sprite(32, 12, 7, Spritesheet.x32);
	public static Sprite pistolFlash = new Sprite(32, 12, 1, Spritesheet.x32);
	public static Sprite pistolFlashGlow = new Sprite(32, 12, 2, Spritesheet.x32);
	public static Sprite enemy_bullet_1 = new Sprite(16, 3, 3, Spritesheet.spritesheet1);
	public static Sprite enemy_bullet_2 = new Sprite(16, 3, 4, Spritesheet.spritesheet1);
	public static Sprite enemy_bullet_glow_1 = new Sprite(32, 12, 4, Spritesheet.x32);
	public static Sprite enemy_bullet_glow_2 = new Sprite(32, 12, 6, Spritesheet.x32);
	public static Sprite bolt = new Sprite(16, 3, 1, Spritesheet.spritesheet1);
	public static Sprite shotgunPellet = new Sprite(16, 3, 2, Spritesheet.spritesheet1);
	public static Sprite shotgunPellet_glow = new Sprite(32, 12, 3, Spritesheet.x32);
	public static Sprite slowEnemyBullet = new Sprite(16, 2, 4, Spritesheet.spritesheet1);
	public static Sprite slowEnemyBulletGlow = new Sprite(32, 12, 5, Spritesheet.x32);
	public static Sprite fastBulletBlue = new Sprite(24, 8, 1, Spritesheet.x24);
	public static Sprite fastBulletBlueGlow = new Sprite(24, 8, 2, Spritesheet.x24);
	
//	particle sprites:
	public static Sprite particle_blue = new Sprite(2, 0xff00ffff);
	public static Sprite particle_orange = new Sprite(2, 0xffFF8C00);
	public static Sprite particle_yellow = new Sprite(2, 0xffB6FF00);
	public static Sprite small_rain_particle = new Sprite(1, 0xff9BAFFF);
	public static Sprite rain_particle = new Sprite(6, 4, 0, Spritesheet.bullets);
	public static Sprite rain_splash_particle = new Sprite(6, 5, 0, Spritesheet.bullets);
	
//	enemy sprites: 
	public static Sprite two_leg_robot = new Sprite(32, 6, 0, Spritesheet.spritesheet1);
	public static Sprite soldier_dead_right = new Sprite(24, 14, 6, Spritesheet.x24);
	public static Sprite soldier_dead_left = new Sprite(24, 14, 7, Spritesheet.x24);
	public static Sprite swordGuy_dead_right = new Sprite(24, 14, 8, Spritesheet.x24);
	public static Sprite swordGuy_dead_left = new Sprite(24, 14, 9, Spritesheet.x24);
	public static Sprite slowGuy_dead_right = new Sprite(24, 14, 10, Spritesheet.x24);
	public static Sprite slowGuy_dead_left = new Sprite(24, 14, 11, Spritesheet.x24);
	public static Sprite heavy_dead_right = new Sprite(24, 4, 3, Spritesheet.x24);
	public static Sprite heavy_dead_left = new Sprite(24, 5, 3, Spritesheet.x24);
	
//	item sprites:	
	public static Sprite shotgun_ammo = new Sprite(16, 4, 0, Spritesheet.spritesheet1);
	public static Sprite shotgun = new Sprite(32, 0, 0, Spritesheet.x32);
	public static Sprite pistol = new Sprite(32, 1, 0, Spritesheet.x32);
	public static Sprite enemyGun = new Sprite(32, 2, 0, Spritesheet.x32);
	public static Sprite slowEnemyGun = new Sprite(32, 7, 0, Spritesheet.x32);
	public static Sprite crossbow = new Sprite(32, 3, 0, Spritesheet.x32);
	public static Sprite smg = new Sprite(32, 4, 0, Spritesheet.x32);
	public static Sprite miniGun = new Sprite(32, 6, 0, Spritesheet.x32);
	public static Sprite laserSword = new Sprite(48, 8, 2, Spritesheet.x24);
	public static Sprite laserSwordGlow = new Sprite(48, 8, 3, Spritesheet.x24);
	public static Sprite laserSwordSlash = new Sprite(48, 8, 4, Spritesheet.x24);
	public static Sprite swordSlash2 = new Sprite(64, 0, 4, Spritesheet.x32);
	public static Sprite skillGlow = new Sprite(32, 2, 4, Spritesheet.spritesheet1);
	public static Sprite actionSkillFlash = new Sprite(32, 2, 7, Spritesheet.spritesheet1);
	public static Sprite spotlight = new Sprite(16, 4, 7, Spritesheet.spritesheet1);
	public static Sprite xp = new Sprite(16, 0, 5, Spritesheet.spritesheet1);
	public static Sprite xpShadow = new Sprite(16, 7, 7, Spritesheet.spritesheet1);
	public static Sprite xpGlow = new Sprite(16, 1, 5, Spritesheet.spritesheet1);
	public static Sprite xpTube = new Sprite(16, 7, 6, Spritesheet.spritesheet1);
	public static Sprite xpTubeShadow = new Sprite(16, 3, 8, Spritesheet.spritesheet1);
	public static Sprite healthKit = new Sprite(16, 4, 8, Spritesheet.spritesheet1);
	public static Sprite healthKitShadow = new Sprite(16, 3, 9, Spritesheet.spritesheet1);
	
	//UI sprites:
	public static Sprite item_slot = new Sprite(16, 4, 1, Spritesheet.spritesheet1);
	public static Sprite item_slot_outline = new Sprite(16, 4, 2, Spritesheet.spritesheet1);
	public static Sprite item_slot_glow = new Sprite(16, 6, 10, Spritesheet.spritesheet1);
	public static Sprite healthIcon = new Sprite(16, 4, 3, Spritesheet.spritesheet1);
	public static Sprite pistolSlotSprite = new Sprite(16, 4, 4, Spritesheet.spritesheet1);
	public static Sprite shotgunSlotSprite = new Sprite(16, 4, 5, Spritesheet.spritesheet1);
	public static Sprite crossbowSlotSprite = new Sprite(16, 4, 6, Spritesheet.spritesheet1);
	public static Sprite pistolIcon = new Sprite(16, 4, 11, Spritesheet.spritesheet1);
	public static Sprite shotgunIcon = new Sprite(16, 4, 12, Spritesheet.spritesheet1);
	public static Sprite crossbowIcon = new Sprite(16, 4, 13, Spritesheet.spritesheet1);
	public static Sprite assaultRifleIcon = new Sprite(16, 4, 14, Spritesheet.spritesheet1);
	public static Sprite skillOutline = new Sprite(16, 4, 9, Spritesheet.spritesheet1);
	public static Sprite skillOutlineSelected = new Sprite(16, 4, 10, Spritesheet.spritesheet1);
	public static Sprite skillOutlineHover = new Sprite(24, 8, 0, Spritesheet.x24);
	public static Sprite buttonOutline = new Sprite(64, 0, 1, Spritesheet.x64);
	public static Sprite buttonHighlighted = new Sprite(64, 1, 1, Spritesheet.x64);
	public static Sprite pauseMenuBackground = new Sprite(Spritesheet.pauseMenuBackground, 400, 250);
	public static Sprite menuOutline = new Sprite(Spritesheet.menuOutline, 400, 225);
	public static Sprite leftButton = new Sprite(16, 3, 10, Spritesheet.spritesheet1);
	public static Sprite rightButton = new Sprite(16, 3, 11, Spritesheet.spritesheet1);
	public static Sprite leftButtonHighlighted = new Sprite(16, 3, 12, Spritesheet.spritesheet1);
	public static Sprite rightButtonHighlighted = new Sprite(16, 3, 13, Spritesheet.spritesheet1);
	public static Sprite SkillTreeOutline = new Sprite(128, 0, 1, Spritesheet.x64);
	public static Sprite healthBarEnd = new Sprite(16, 3, 14, Spritesheet.spritesheet1);
	public static Sprite healthBarEnd2 = new Sprite(16, 3, 15, Spritesheet.spritesheet1);
	public static Sprite healthBarEnd3 = new Sprite(16, 4, 15, Spritesheet.spritesheet1);
	
	protected Sprite(Spritesheet sheet, int width, int height) {
		if (width == height) SIZE = width;
		else SIZE = -1;
		this.sheet = sheet;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		load();
	}
	
	public Sprite(int size, int x, int y, Spritesheet sheet) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int width, int height, int x, int y, Spritesheet sheet) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		this.x = x * width;
		this.y = y * height;
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int width, int height, int colour) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColour(colour);
	}

	public Sprite(int size, int colour) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColour(colour);
	}

	public Sprite(int[] spritePixels, int sprWidth, int sprHeight) {
		if (sprWidth == sprHeight) SIZE = sprWidth;
		else SIZE = -1;
		width = sprWidth;
		height = sprHeight;
		pixels = spritePixels;
	}

	public static Sprite flipSprite(Sprite sprite) {
		int[] tempPixels = new int[sprite.width * sprite.height];
 		for (int y = 0; y < sprite.height; y++) {
			for (int x = 0; x < sprite.width; x++) {
				tempPixels[x + y * sprite.width] = sprite.pixels[(sprite.width - 1) - x + y * sprite.width];
			}	
		} return new Sprite(tempPixels, sprite.width, sprite.height);
	}
	
	public static Sprite flipSpriteVert(Sprite sprite) {
		int[] tempPixels = new int[sprite.width * sprite.height];
 		for (int y = 0; y < sprite.height; y++) {
			for (int x = 0; x < sprite.width; x++) {
				tempPixels[x + y * sprite.width] = sprite.pixels[x + (sprite.height - y - 1) * sprite.width];
			}	
		} return new Sprite(tempPixels, sprite.width, sprite.height);
	}
	
	public static Sprite rotateSprite(Sprite sprite, double angle, int width, int height) {
		int result[] = new int[width * height];
		Sprite rotatedSprite;
		
		double nx_x = rotX(-angle, 1.0, 0.0);
		double nx_y = rotY(-angle, 1.0, 0.0);
		double ny_x = rotX(-angle, 0.0, 1.0);
		double ny_y = rotY(-angle, 0.0, 1.0);
		
		double x0 = rotX(-angle, -width / 2, -height / 2) + width / 2;
		double y0 = rotY(-angle, -width / 2, -height / 2) + height / 2;
		
		for (int y = 0; y < height; y++) {
			double x1 = x0;
			double y1 = y0;
			for (int x = 0; x < width; x++) {
				int xx = (int) x1;
				int yy = (int) y1;
				int col = 0;
				if (xx < 0 || xx >= width || yy < 0 || yy >= height) col = 0xffff00ff;
				else col = sprite.pixels[xx + yy * width];
				result[x + y * width] = col;
				x1 += nx_x;
				y1 += nx_y;
			}
			x0 += ny_x;
			y0 += ny_y;
			
		}
		rotatedSprite = new Sprite(result, width, height);
		return rotatedSprite;
	}

	private static double rotX(double angle, double x, double y) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		return x * cos + y * -sin;
	}

	private static double rotY(double angle, double x, double y) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		return x * sin + y * cos;
	}

	
	private void setColour(int colour) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = colour;
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	private void load() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sheet.pixels[(this.x + x) + (this.y + y) * sheet.WIDTH];
			}
		}
	}
}
