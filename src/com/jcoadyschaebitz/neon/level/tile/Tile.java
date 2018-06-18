package com.jcoadyschaebitz.neon.level.tile;

import java.util.ArrayList;
import java.util.List;

import com.jcoadyschaebitz.neon.entity.mob.Mob.Direction;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.level.Level;

public class Tile {

	public Sprite sprite;
	public static List<Tile> tiles = new ArrayList<Tile>();
	protected boolean isOutdoors = true;
	public boolean canHaveShadow;
	public int zIndex;

	private int colour;

	public static Tile dirt = new DirtTile(Sprite.dirt, 1);
	public static Tile grass = new GrassTile(Sprite.grass, 2);
	public static Tile blueGrass = new BlueGrassTile(Sprite.blueGrass, 3);
	public static Tile voidTile = new voidTile(Sprite.wall);

	static Sprite[] floorSprites = { Sprite.tarmac, Sprite.tarmac_1, Sprite.tarmac_2 };
	public static Tile tarmac = new RandomisedTile(0xff7f3300, false, floorSprites, 0);
//	public static Tile tarmac = new FloorTile(Sprite.tarmac, 0xff7f3300, false, true);
	public static Tile tarmac2 = new FloorTile(Sprite.tarmac_1, 0xff702c00, false, true);
	public static Tile tarmac3 = new FloorTile(Sprite.tarmac_2, 0xff66260F, false, true);
	public static Tile tarmac4 = new FloorTile(Sprite.tarmac_3, 0xff2F2816, false, true);
	public static Tile tarmac5 = new FloorTile(Sprite.tarmac_4, 0xff373319, false, true);
	public static Tile tarmac6 = new FloorTile(Sprite.tarmac_5, 0xff5B232F, false, true);
	public static Tile stepsArch = new WallTile(Sprite.steps_arch, 0xff3F3B00, true);
	public static Tile stepsBase = new FloorTile(Sprite.steps_base, 0xff4A4600, true, false);
	public static Tile stepsBaseRight = new FloorTile(Sprite.steps_base_right, 0xff332F00, false, false);
	public static Tile stepsBaseLeft = new FloorTile(Sprite.steps_base_left, 0xff2D2A00, false, false);
	public static Tile stairsDown = new StairTile(Sprite.steps_down, 0xff3D1800, false, false, Direction.UP);
	public static Tile stepsUp = new FloorTile(Sprite.steps_up, 0xff, false, false);
	public static Tile stepsRight = new FloorTile(Sprite.steps_right, 0xff331417, false, false);
	public static Tile stepsLeft = new FloorTile(Sprite.steps_left, 0xff3D181C, false, false);
	public static Tile outsideStairsUpLeft = new StairTile(Sprite.steps_left, 0xff6B2A00, false, true, Direction.LEFT);
	public static Tile outsideStairsUpRight = new StairTile(Sprite.steps_right, 0xff592200, false, true, Direction.RIGHT);
	public static Tile pavement = new FloorTile(Sprite.pavement, 0xff9E6E4F, false, true);
	public static Tile pavementEdge = new FloorTile(Sprite.pavementEdge, 0xff8E6247, false, true);
	public static Tile barCarpet = new FloorTile(Sprite.barCarpet, 0xffB74900, false, false);
	public static Tile barCarpetSteps1 = new FloorTile(Sprite.barCarpetSteps1, 0xffA53F46, false, false);
	public static Tile barCarpetSteps2 = new FloorTile(Sprite.barCarpetSteps2, 0xff913840, false, false);
	public static Tile barCarpetSteps3 = new FloorTile(Sprite.barCarpetSteps3, 0xff7C3038, false, false);
	public static Tile barCarpetSteps4 = new FloorTile(Sprite.barCarpetSteps4, 0xff6B2931, false, false);
	public static Tile woodPlanksSunset = new FloorTile(Sprite.woodPlanksSunset, 0xffB71F00, false, false);
	public static Tile insideTiles = new FloorTile(Sprite.insideTiles, 0xff000E60, false, false);

	public static Tile wall = new WallTile(Sprite.wall, 0xff00ff21, true);
	public static Tile variableWall = new VariableWallTile(Sprite.wallDark, 0xff00D819, true);
	public static Tile wallDarkTransitionTop = new WallTile(Sprite.wallDarkTransitionTop, 0xff, true);
	public static Tile wallDarkTransitionBottom = new WallTile(Sprite.wallDarkTransitionBottom, 0xff, true);
	public static Tile wallDarkTransitionLeft = new WallTile(Sprite.wallDarkTransitionLeft, 0xff4CFF5E, true);
	public static Tile wallDarkTransitionRight = new WallTile(Sprite.wallDarkTransitionRight, 0xff26FF3F, true);
	public static Tile wallDarkTransitionTopLeftClosed = new WallTile(Sprite.wallDarkTransitionTopLeftClosed, 0xff, true);
	public static Tile wallDarkTransitionTopRightClosed = new WallTile(Sprite.wallDarkTransitionTopRightClosed, 0xff, true);
	public static Tile wallDarkTransitionBottomLeftClosed = new WallTile(Sprite.wallDarkTransitionBottomLeftClosed, 0xff, true);
	public static Tile wallDarkTransitionBottomRightClosed = new WallTile(Sprite.wallDarkTransitionBottomRightClosed, 0xff, true);
	public static Tile wallDarkTransitionTopLeftOpen = new WallTile(Sprite.wallDarkTransitionTopLeftOpen, 0xff, true);
	public static Tile wallDarkTransitionTopRightOpen = new WallTile(Sprite.wallDarkTransitionTopRightOpen, 0xff, true);
	public static Tile wallDarkTransitionBottomLeftOpen = new WallTile(Sprite.wallDarkTransitionBottomLeftOpen, 0xff, true);
	public static Tile wallDarkTransitionBottomRightOpen = new WallTile(Sprite.wallDarkTransitionBottomRightOpen, 0xff, true);
	
	public static Tile chineseNeonSign = new WallTile(Sprite.chineseNeonSign, 0xff004170, true);
	public static Tile pipes = new WallTile(Sprite.pipes, 0xff0026ff, true);
	public static Tile wall3 = new WallTile(Sprite.wall3, 0xff265D00, true);
	public static Tile wallGrate = new WallTile(Sprite.wallGrate, 0xff267f00, true);
	public static Tile pipeRight = new WallTile(Sprite.wallPipesRightEnd, 0xff298900, true);
	public static Tile pipeLeft = new WallTile(Sprite.wallPipesLeftEnd, 0xff3B871B, true);
	public static Tile wireFence = new WallTile(Sprite.wireFence, 0xffB6FF0f, false);
	public static Tile wallPanel1 = new WallTile(Sprite.wallPanel1, 0xff00FF4A, true);
	public static Tile wallPanel2 = new WallTile(Sprite.wallPanel2, 0xff00FF5D, true);
	public static Tile wallPanel3 = new WallTile(Sprite.wallPanel3, 0xff00FF6D, true);
	public static Tile wallPanel4 = new WallTile(Sprite.wallPanel4, 0xff00FF7C, true);
	public static Tile verticalPipes = new WallTile(Sprite.wallPipes1, 0xff, true);
	public static Tile verticalPipesBottom = new WallTile(Sprite.wallPipes2, 0xff, true);
	
	public static Tile window1 = new WallTile(Sprite.window1, 0xff9B883B, true);
	public static Tile window2 = new WallTile(Sprite.window2, 0xff917F37, true);
	public static Tile window3 = new WallTile(Sprite.window3, 0xffA5913E, true);
	public static Tile window4 = new WallTile(Sprite.window4, 0xff877633, true);
	
	public static Tile factoryWindow1 = new WallTile(Sprite.factoryWindow1, 0xff071915, true);
	public static Tile factoryWindow2 = new WallTile(Sprite.factoryWindow2, 0xff09211C, true);
	public static Tile factoryWindow3 = new WallTile(Sprite.factoryWindow3, 0xff0A2620, true);
	public static Tile factoryWindow4 = new WallTile(Sprite.factoryWindow4, 0xff0C2D26, true);
	public static Tile factoryWindow5 = new WallTile(Sprite.factoryWindow5, 0xff0E352D, true);
	public static Tile factoryWindow6 = new WallTile(Sprite.factoryWindow6, 0xff113D33, true);
	
	public static Tile factoryWindowLit1 = new WallTile(Sprite.factoryWindowLit1, 0xff144930, true);
	public static Tile factoryWindowLit2 = new WallTile(Sprite.factoryWindowLit2, 0xff144931, true);
	public static Tile factoryWindowLit3 = new WallTile(Sprite.factoryWindowLit3, 0xff144932, true);
	public static Tile factoryWindowLit4 = new WallTile(Sprite.factoryWindowLit4, 0xff144933, true);
	public static Tile factoryWindowLit5 = new WallTile(Sprite.factoryWindowLit5, 0xff144934, true);
	public static Tile factoryWindowLit6 = new WallTile(Sprite.factoryWindowLit6, 0xff144935, true);
	
	public static Tile shopFrontA1 = new WallTile(Spritesheet.shopFrontA.getSprites()[0], 0xff0FFFF4, true);
	public static Tile shopFrontA2 = new WallTile(Spritesheet.shopFrontA.getSprites()[1], 0xff0FFFF5, true);
	public static Tile shopFrontA3 = new WallTile(Spritesheet.shopFrontA.getSprites()[2], 0xff0FFFF6, true);
	public static Tile shopFrontA4 = new WallTile(Spritesheet.shopFrontA.getSprites()[3], 0xff0FFFF7, true);
	public static Tile shopFrontA5 = new WallTile(Spritesheet.shopFrontA.getSprites()[4], 0xff0FFFF8, true);
	public static Tile shopFrontA6 = new WallTile(Spritesheet.shopFrontA.getSprites()[5], 0xff0FFFF9, true);
	public static Tile shopFrontA7 = new WallTile(Spritesheet.shopFrontA.getSprites()[6], 0xff0FFFFa, true);
	public static Tile shopFrontA8 = new WallTile(Spritesheet.shopFrontA.getSprites()[7], 0xff0FFFFb, true);
	public static Tile shopFrontA9 = new WallTile(Spritesheet.shopFrontA.getSprites()[8], 0xff0FFFFc, true);
	public static Tile shopFrontA10 = new WallTile(Spritesheet.shopFrontA.getSprites()[9], 0xff0FFFFd, true);
	public static Tile shopFrontA11 = new WallTile(Spritesheet.shopFrontA.getSprites()[10], 0xff0FFFFe, true);
	public static Tile shopFrontA12 = new WallTile(Spritesheet.shopFrontA.getSprites()[11], 0xff0FFFFf, true);
	
	public static Tile shopFrontB1 = new WallTile(Spritesheet.shopFrontB.getSprites()[0], 0xffB6FF00, true);
	public static Tile shopFrontB2 = new WallTile(Spritesheet.shopFrontB.getSprites()[1], 0xffB6FF01, true);
	public static Tile shopFrontB3 = new WallTile(Spritesheet.shopFrontB.getSprites()[2], 0xffB6FF02, true);
	public static Tile shopFrontB4 = new WallTile(Spritesheet.shopFrontB.getSprites()[3], 0xffB6FF03, true);
	public static Tile shopFrontB5 = new WallTile(Spritesheet.shopFrontB.getSprites()[4], 0xffB6FF04, true);
	public static Tile shopFrontB6 = new WallTile(Spritesheet.shopFrontB.getSprites()[5], 0xffB6FF05, true);
	public static Tile shopFrontB7 = new WallTile(Spritesheet.shopFrontB.getSprites()[6], 0xffB6FF06, true);
	public static Tile shopFrontB8 = new WallTile(Spritesheet.shopFrontB.getSprites()[7], 0xffB6FF07, true);
	public static Tile shopFrontB9 = new WallTile(Spritesheet.shopFrontB.getSprites()[8], 0xffB6FF08, true);
	public static Tile shopFrontB10 = new WallTile(Spritesheet.shopFrontB.getSprites()[9], 0xffB6FF09, true);
	public static Tile shopFrontB11 = new WallTile(Spritesheet.shopFrontB.getSprites()[10], 0xffB6FF0a, true);
	public static Tile shopFrontB12 = new WallTile(Spritesheet.shopFrontB.getSprites()[11], 0xffB6FF0b, true);
	
	public static Tile scaffoldLeft = new WallTile(Sprite.scaffold_left, 0xff1A5600, true);
	public static Tile scaffoldRight = new WallTile(Sprite.scaffold_right, 0xff1D6000, true);
	public static Tile scaffoldLeftBase = new WallTile(Sprite.scaffold_left_base, 0xff164C00, true);
	public static Tile scaffoldRightBase = new WallTile(Sprite.scaffold_right_base, 0xff206B00, true);
	public static Tile scaffoldRight1 = new WallTile(Sprite.scaffold_right1, 0xff103800, true);
	public static Tile scaffoldRight2 = new WallTile(Sprite.scaffold_right2, 0xff134200, true);
	public static Tile scaffoldRight3 = new WallTile(Sprite.scaffold_right3, 0xff103500, true);
	public static Tile scaffoldTop = new WallTile(Sprite.scaffold_top, 0xff0D2B00, true);
	
	public static Tile greenNeonSign1 = new WallTile(Sprite.green_neon_sign_1, 0xff8CFF00, true);
	public static Tile greenNeonSign2 = new WallTile(Sprite.green_neon_sign_2, 0xff8CFF01, true);
	public static Tile greenNeonSign3 = new WallTile(Sprite.green_neon_sign_3, 0xff8CFF02, true);
	
	public static Tile redNeonSign1 = new WallTile(Sprite.red_neon_sign_1, 0xffFF0014, true);
	public static Tile redNeonSign2 = new WallTile(Sprite.red_neon_sign_2, 0xffFF0013, true);
	public static Tile redNeonSign3 = new WallTile(Sprite.red_neon_sign_3, 0xffFF0012, true);
	
	public static Tile orangeNeonSign1 = new WallTile(Sprite.orange_neon_sign_1, 0xffFF2312, true);
	public static Tile orangeNeonSign2 = new WallTile(Sprite.orange_neon_sign_2, 0xffFF2313, true);
	
	public static Tile airConUnit1 = new WallTile(Sprite.air_con_unit_1, 0xff0D7740, true);
	public static Tile airConUnit2 = new WallTile(Sprite.air_con_unit_2, 0xff0D7741, true);
	public static Tile airConUnit3 = new WallTile(Sprite.air_con_unit_3, 0xff0D7742, true);
	public static Tile airConUnit4 = new WallTile(Sprite.air_con_unit_4, 0xff0D7743, true);
	
	public static Tile bigVent1 = new WallTile(Sprite.bigVent1, 0xff030C0A, true);
	public static Tile bigVent2 = new WallTile(Sprite.bigVent2, 0xff030C0B, true);
	public static Tile bigVent3 = new WallTile(Sprite.bigVent3, 0xff030C0C, true);
	public static Tile bigVent4 = new WallTile(Sprite.bigVent4, 0xff030C0D, true);

	public Tile(Sprite sprite, int colour, boolean canHaveShadow, int zIndex) {
		this.sprite = sprite;
		this.colour = colour;
		tiles.add(this);
		this.canHaveShadow = canHaveShadow;
		this.zIndex = zIndex;
	}

	public Tile(Sprite sprite, boolean canHaveShadow, int zIndex) {
		this(sprite, 0, canHaveShadow, zIndex);
	}

	public void render(int x, int y, Screen screen, Level level, long seed) {
	}

	public boolean isSolid() {
		return false;
	}

	public int getColour() {
		return colour;
	}

	public boolean isOutdoors() {
		return isOutdoors;
	}

}
