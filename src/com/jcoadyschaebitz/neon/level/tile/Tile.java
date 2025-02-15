package com.jcoadyschaebitz.neon.level.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.level.Level;
import com.jcoadyschaebitz.neon.level.tile.DiagonalTile.DiagDirection;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class Tile {

	public Sprite sprite;
	public static List<Tile> tiles = new ArrayList<Tile>();
	protected List<Renderer> additionalRenderers;
	public boolean castsShadow, isOutdoors = true, blocksProjectiles = true;
	public int overlayCol;
	protected int zIndex, colour;
	protected boolean border = false;
	protected Sprite[] dirtBaseSprites, borderSprites;

	// Renderers
	public static BorderRenderer edgeShadows = new BorderRenderer(Spritesheet.floorShadows.getSprites(), new int[] { 0xff6A654D, 0xff353535, 0xff00FF21, 0xff00AA19, 0xffFFBE00, 0xffFF00FF, 0xff3B871B,
			0xff0026FF, 0xff298900, 0xff164C00, 0xff206B00, 0xff0FFFFC, 0xff0FFFFD, 0xff0FFFFE, 0xff0FFFFF, 0xffB6FF08, 0xffB6FF09, 0xffB6FF0A, 0xffB6FF0B, 0xff008E0E, 0xff0C151E, 0xff00D819 });
	
	public static Tile voidTile = new voidTile(Sprite.wall);
	
	private static Sprite[] wallJoinSprites = { Sprite.wallPlain, Sprite.wallJoin1, Sprite.wallJoin2, Sprite.wallJoin3, Sprite.wallJoin4 };
	private static Sprite[] wallJoinSpritesFlipped = { Sprite.wallPlain, Sprite.mirror(Sprite.wallJoin1), Sprite.mirror(Sprite.wallJoin2), Sprite.mirror(Sprite.wallJoin3), Sprite.mirror(Sprite.wallJoin4) };
	private static Sprite[] corrIronHorizSprites = { Sprite.corrugatedIronHoriz1, Sprite.corrugatedIronHoriz2, Sprite.corrugatedIronHoriz3, Sprite.corrugatedIronHoriz4 };

	public static Tile tarmac = new RandomisedTileDecorator(new FloorTile(Sprite.nullSprite, 0xff7f3300, null), Spritesheet.tarmac.getSprites(), null);
	public static Tile wallJoinLeft = new RandomisedTileDecorator(new WallTile(Sprite.nullSprite, 0xff008E0E, null), wallJoinSprites, null);
	public static Tile wallJoinRight = new RandomisedTileDecorator(new WallTile(Sprite.nullSprite, 0xff00720B, null), wallJoinSpritesFlipped, null);
	public static Tile wallJoinLeft1 = new WallTile(Sprite.wallFrontLeftEdge, 0xff24D636, false, null);
	public static Tile wallJoinRight1 = new WallTile(Sprite.wallFrontRightEdge, 0xff3ED64D, false, null);
	public static Tile wallHoriz = new RandomisedTileDecorator(new WallTile(Sprite.nullSprite, 0xff21211A, null), Spritesheet.wallHorizontalBars.getSprites(), null);
	public static Tile wallHorizEdge = new WallTile(Sprite.wallHorizEdge, 0xff171912, false, null);
	public static Tile solidTarmac = new RandomisedTileDecorator(new FloorTile(Sprite.nullSprite, 0xff7F6A00, null), Spritesheet.tarmac.getSprites(), null);//new FloorTile(Sprite.tarmac, 0xff7F6A00, null);
	public static Tile tarmac2 = new FloorTile(Sprite.tarmac_1, 0xff702c00, null);
	public static Tile tarmac3 = new FloorTile(Sprite.tarmac_2, 0xff66260F, null);
	public static Tile tarmac4 = new FloorTile(Sprite.tarmac_3, 0xff2F2816, null);
	public static Tile tarmac5 = new FloorTile(Sprite.tarmac_4, 0xff373319, null);
	public static Tile tarmac6 = new FloorTile(Sprite.tarmac_5, 0xff5B232F, null);
	public static Tile concrete = new RepeatingTileDecorator(new FloorTile(Sprite.nullSprite, 0xff352F2F, null), new Vec2i(3, 3), Spritesheet.concrete.getSprites(), null, true);
	
	// Floor tiles:
	
	public static Tile stepsArch = new WallTile(Sprite.steps_arch, 0xff3F3B00, false , null);
	public static Tile stepsBase = new FloorTile(Sprite.steps_base, 0xff4A4600, null);
	public static Tile stepsBaseRight = new FloorTile(Sprite.steps_base_right, 0xff332F00, null);
	public static Tile stepsBaseLeft = new FloorTile(Sprite.steps_base_left, 0xff2D2A00, null);
	
	public static Tile stairsDown = new StairTile(Sprite.stepsDown, 0xff3D1800, StairTile.DOWN, null);
	public static Tile stairsInsideDown = new StairTile(Sprite.insideStepsDown, 0xff000C51, StairTile.DOWN, null);
	public static Tile stairsInsideUp = new StairTile(Sprite.insideStepsUp, 0xff001487, StairTile.UP, null);
	
	public static Tile stepsUp = new StairTile(Sprite.stepsUp, 0xff, StairTile.UP, null);
	public static Tile stepsRight = new FloorTile(Sprite.stepsRight, 0xff331417, null);
	public static Tile stepsLeft = new FloorTile(Sprite.stepsLeft, 0xff3D181C, null);
	
	public static Tile outsideStairsDownRight = new StairTile(Sprite.stepsRight, 0xff6B2A00, StairTile.RIGHT, null);
	public static Tile outsideStairsUpRight = new StairTile(Sprite.stepsLeft, 0xff592200, StairTile.LEFT, null);
	public static Tile outsideStairsDownRightTop = new StairTile(Sprite.stepsRightTop, 0xff4C1D00, StairTile.RIGHT, null);
	public static Tile outsideStairsDownRightBottom = new StairTile(Sprite.stepsRightBottom, 0xff491C00, StairTile.RIGHT, null);
	public static Tile outsideStairsUpRightTop = new StairTile(Sprite.stepsLeftTop, 0xff4C1100, StairTile.LEFT, null);
	public static Tile outsideStairsUpRightBottom = new StairTile(Sprite.stepsLeftBottom, 0xff380D00, StairTile.LEFT, null);
	public static Tile insideStairsDownRight = new StairTile(Sprite.insideStepsRight, 0xff001177, StairTile.RIGHT, null);
	public static Tile insideStairsUpRight = new StairTile(Sprite.insideStepsLeft, 0xff132482, StairTile.LEFT, null);
	public static Tile insideStairsDownRightTop = new StairTile(Sprite.insideStepsRTPanel_1, 0xff001382, StairTile.RIGHT, null);
	public static Tile insideStairsDownRightBottom = new StairTile(Sprite.insideStepsRightBottom, 0xff001489, StairTile.RIGHT, null);
	public static Tile insideStairsUpRightTop = new StairTile(Sprite.insideStepsLeftTop, 0xff192EA8, StairTile.LEFT, null);
	public static Tile insideStairsUpRightBottom = new StairTile(Sprite.insideStepsLeftBottom, 0xff1D35C1, StairTile.LEFT, null);
	
	public static Tile insideDownRightPanel = new WallTile(Sprite.insideStepsRTPanel_2, 0xff009769, null);
	
//	public static Tile poolStairs1 = new StairTile(Sprite)
	
	public static Tile pavement = new FloorTile(Sprite.pavement, 0xff9E6E4F, null);
	public static Tile pavementEdge = new FloorTile(Sprite.pavementEdge, 0xff8E6247, null);
	public static Tile barCarpet = new FloorTile(Sprite.barCarpet, 0xffB74900, null);
	public static Tile barCarpetSteps1 = new FloorTile(Sprite.barCarpetStepsTop, 0xffA53F46, null);
	public static Tile barCarpetSteps2 = new FloorTile(Sprite.barCarpetStepsTopRight, 0xff913840, null);
	public static Tile barCarpetSteps3 = new FloorTile(Sprite.barCarpetStepsRight, 0xff7C3038, null);
	public static Tile barCarpetSteps4 = new FloorTile(Sprite.barCarpetStepsTopRight2, 0xff6B2931, null);
	public static Tile barCarpetSteps5 = new FloorTile(Sprite.mirror(Sprite.barCarpetStepsRight), 0xff7A353D, null);
	public static Tile barCarpetSteps6 = new FloorTile(Sprite.mirror(Sprite.barCarpetStepsTopRight), 0xff8E3E46, null);
	public static Tile barCarpetSteps7 = new FloorTile(Sprite.mirror(Sprite.barCarpetStepsTopRight2), 0xff682E35, null);
	public static Tile carpetPlankSteps = new StairTile(Sprite.carpetPlankSteps, 0xff91371B, StairTile.DOWN, null);
	public static Tile woodPlanksSunset = new FloorTile(Sprite.woodPlanksSunset, 0xffB71F00, null);
	public static Tile insideTiles = new FloorTile(Sprite.insideTiles, 0xff000E60, null);
	public static Tile floatingRailFloor = new FloorTile(Sprite.floatingRailFloor, 0xff35444F, null);
	
	public static Tile blackStoneTile = new FloorTile(Sprite.blackStoneTile, 0xff232323, null);
	public static Tile whiteStoneTile = new FloorTile(Sprite.whiteStoneTile, 0xffDCDCDC, null);
	
	public static Tile poshFloorBoards = new RepeatingTileDecorator(new FloorTile(Sprite.nullSprite, 0xff3D1805, null), new Vec2i(3, 3), Spritesheet.poshBoards.getSprites(), null, false);
	public static Tile old_concrete = new FloorTile(Sprite.old_concrete, 0xff9DA7C6, null);
	public static Tile largeKitchenTile = new FloorTile(Sprite.large_kitchen_tile, 0xffC9D1FF, null);
	
	// Wall tiles:
	
	public static Tile wall = new WallTile(Sprite.wall, 0xff00ff21, true, null);
	public static Tile wallFrontBricks = new WallTile(Sprite.wallFrontBricks, 0xff00D819, false, null);
//	public static Tile wallPlain = new WallTile(Sprite.wallPlain, 0xff00AA19, true);
	public static Tile randomWallPlain = new RandomisedTileDecorator(new WallTile(Sprite.nullSprite, 0xff00AA19, null), Spritesheet.PlainWall.getSprites(), null);
	public static Tile indoorPillar = new WallTile(Sprite.indoorPillar, 0xffFF006E, false, null);
	public static Tile indoorPillarBase = new WallTile(Sprite.indoorPillarBase, 0xffEA0065, false, null);
	public static Tile corrugatedIronHoriz = new RandomisedTileDecorator(new WallTile(Sprite.nullSprite, 0xff004408, null), corrIronHorizSprites, null);
//	public static Tile corrugatedIronHoriz = new WallTile(Sprite.corrugatedIronHoriz1, 0xff004408, true);
	public static Tile corrugatedIronVert = new WallTile(Sprite.corrugatedIronVert, 0xff00443A, false, null);
	public static Tile barBottleShelf = new WallTile(Sprite.barBottleShelf, 0xffDB005F, false, null);
	public static Tile interiorPanelling = new WallTile(Sprite.interiorPanelling, 0xff932200, false, null);
	public static Tile paperPanelling = new WallTile(Sprite.paperPanelling, 0xffFFBE00, false, null);
//	public static Tile speakerPanelTop = new WallTile(Sprite.speakerPanelTop, 0xff610800, false, null);
//	public static Tile speakerPanelBottom = new WallTile(Sprite.speakerPanelBottom, 0xff340800, false, null);
	public static Tile slateTiles = new WallTile(Sprite.slateTiles, 0xff353535, false, null);
	public static Tile slateTilesLight = new WallTile(Sprite.slateTilesLight, 0xff6A654D, false, null);

	public static Tile japaneseNeonSign = new WallTile(Sprite.japaneseNeonSign, 0xff004170, false, null);
	public static Tile pipes = new WallTile(Sprite.pipes, 0xff0026ff, false, null);
	public static Tile wall3 = new WallTile(Sprite.wall3, 0xff265D00, false, null);
	public static Tile wallGrate = new WallTile(Sprite.wallGrate, 0xff267f00, false, null);
	public static Tile pipeRight = new WallTile(Sprite.wallPipesRightEnd, 0xff298900, false, null);
	public static Tile pipeLeft = new WallTile(Sprite.wallPipesLeftEnd, 0xff3B871B, false, null);
//	public static Tile wireFence = new WallTile(Sprite.wireFenceHorizontal, 0xffB6FF0f, false);
	public static Tile wallPanel1 = new WallTile(Sprite.wallPanel1, 0xff00FF4A, false, null);
	public static Tile wallPanel2 = new WallTile(Sprite.wallPanel2, 0xff00FF5D, false, null);
	public static Tile wallPanel3 = new WallTile(Sprite.wallPanel3, 0xff00FF6D, false, null);
	public static Tile wallPanel4 = new WallTile(Sprite.wallPanel4, 0xff00FF7C, false, null);
	public static Tile verticalPipes = new WallTile(Sprite.wallPipes1, 0xff, false, null);
	public static Tile verticalPipesBottom = new WallTile(Sprite.wallPipes2, 0xff, false, null);

	public static Tile window1 = new WallTile(Sprite.window1, 0xff9B883B, false, null);
	public static Tile window2 = new WallTile(Sprite.window2, 0xff917F37, false, null);
	public static Tile window3 = new WallTile(Sprite.window3, 0xffA5913E, false, null);
	public static Tile window4 = new WallTile(Sprite.window4, 0xff877633, false, null);

	public static Tile factoryWindow1 = new WallTile(Sprite.factoryWindow1, 0xff103B27, false, null);
	public static Tile factoryWindow2 = new WallTile(Sprite.factoryWindow2, 0xff103B28, false, null);
	public static Tile factoryWindow3 = new WallTile(Sprite.factoryWindow3, 0xff103B29, false, null);
	public static Tile factoryWindow4 = new WallTile(Sprite.factoryWindow4, 0xff103B2A, false, null);
	public static Tile factoryWindow5 = new WallTile(Sprite.factoryWindow5, 0xff103B2B, false, null);
	public static Tile factoryWindow6 = new WallTile(Sprite.factoryWindow6, 0xff103B2C, false, null);

	public static Tile factoryWindowTileB1 = new WallTile(Spritesheet.factoryWindowTileBack.getSprites()[0], 0xff071915, false, null);
	public static Tile factoryWindowTileB2 = new WallTile(Spritesheet.factoryWindowTileBack.getSprites()[1], 0xff09211C, false, null);
	public static Tile factoryWindowTileB3 = new WallTile(Spritesheet.factoryWindowTileBack.getSprites()[2], 0xff0A2620, false, null);
	public static Tile factoryWindowTileB4 = new WallTile(Spritesheet.factoryWindowTileBack.getSprites()[3], 0xff0C2D26, false, null);
	public static Tile factoryWindowTileB5 = new WallTile(Spritesheet.factoryWindowTileBack.getSprites()[4], 0xff0E352D, false, null);
	public static Tile factoryWindowTileB6 = new WallTile(Spritesheet.factoryWindowTileBack.getSprites()[5], 0xff113D33, false, null);

	public static Tile factoryWindowTileLit1 = new WallTile(Spritesheet.factoryWindowTileLit.getSprites()[0], 0xff, false, null);
	public static Tile factoryWindowTileLit2 = new WallTile(Spritesheet.factoryWindowTileLit.getSprites()[1], 0xff, false, null);
	public static Tile factoryWindowTileLit3 = new WallTile(Spritesheet.factoryWindowTileLit.getSprites()[2], 0xff, false, null);
	public static Tile factoryWindowTileLit4 = new WallTile(Spritesheet.factoryWindowTileLit.getSprites()[3], 0xff, false, null);
	public static Tile factoryWindowTileLit5 = new WallTile(Spritesheet.factoryWindowTileLit.getSprites()[4], 0xff, false, null);
	public static Tile factoryWindowTileLit6 = new WallTile(Spritesheet.factoryWindowTileLit.getSprites()[5], 0xff, false, null);

	public static Tile factoryWindowLit1 = new WallTile(Sprite.factoryWindowLit1, 0xff144930, false, null);
	public static Tile factoryWindowLit2 = new WallTile(Sprite.factoryWindowLit2, 0xff144931, false, null);
	public static Tile factoryWindowLit3 = new WallTile(Sprite.factoryWindowLit3, 0xff144932, false, null);
	public static Tile factoryWindowLit4 = new WallTile(Sprite.factoryWindowLit4, 0xff144933, false, null);
	public static Tile factoryWindowLit5 = new WallTile(Sprite.factoryWindowLit5, 0xff144934, false, null);
	public static Tile factoryWindowLit6 = new WallTile(Sprite.factoryWindowLit6, 0xff144935, false, null);

	public static Tile shopFrontA1 = new WallTile(Spritesheet.shopFrontA.getSprites()[0], 0xff0FFFF4, false, null);
	public static Tile shopFrontA2 = new WallTile(Spritesheet.shopFrontA.getSprites()[1], 0xff0FFFF5, false, null);
	public static Tile shopFrontA3 = new WallTile(Spritesheet.shopFrontA.getSprites()[2], 0xff0FFFF6, false, null);
	public static Tile shopFrontA4 = new WallTile(Spritesheet.shopFrontA.getSprites()[3], 0xff0FFFF7, false, null);
	public static Tile shopFrontA5 = new WallTile(Spritesheet.shopFrontA.getSprites()[4], 0xff0FFFF8, false, null);
	public static Tile shopFrontA6 = new WallTile(Spritesheet.shopFrontA.getSprites()[5], 0xff0FFFF9, false, null);
	public static Tile shopFrontA7 = new WallTile(Spritesheet.shopFrontA.getSprites()[6], 0xff0FFFFa, false, null);
	public static Tile shopFrontA8 = new WallTile(Spritesheet.shopFrontA.getSprites()[7], 0xff0FFFFb, false, null);
	public static Tile shopFrontA9 = new WallTile(Spritesheet.shopFrontA.getSprites()[8], 0xff0FFFFc, false, null);
	public static Tile shopFrontA10 = new WallTile(Spritesheet.shopFrontA.getSprites()[9], 0xff0FFFFd, false, null);
	public static Tile shopFrontA11 = new WallTile(Spritesheet.shopFrontA.getSprites()[10], 0xff0FFFFe, false, null);
	public static Tile shopFrontA12 = new WallTile(Spritesheet.shopFrontA.getSprites()[11], 0xff0FFFFf, false, null);

	public static Tile shopFrontB1 = new WallTile(Spritesheet.shopFrontB.getSprites()[0], 0xffB6FF00, false, null);
	public static Tile shopFrontB2 = new WallTile(Spritesheet.shopFrontB.getSprites()[1], 0xffB6FF01, false, null);
	public static Tile shopFrontB3 = new WallTile(Spritesheet.shopFrontB.getSprites()[2], 0xffB6FF02, false, null);
	public static Tile shopFrontB4 = new WallTile(Spritesheet.shopFrontB.getSprites()[3], 0xffB6FF03, false, null);
	public static Tile shopFrontB5 = new WallTile(Spritesheet.shopFrontB.getSprites()[4], 0xffB6FF04, false, null);
	public static Tile shopFrontB6 = new WallTile(Spritesheet.shopFrontB.getSprites()[5], 0xffB6FF05, false, null);
	public static Tile shopFrontB7 = new WallTile(Spritesheet.shopFrontB.getSprites()[6], 0xffB6FF06, false, null);
	public static Tile shopFrontB8 = new WallTile(Spritesheet.shopFrontB.getSprites()[7], 0xffB6FF07, false, null);
	public static Tile shopFrontB9 = new WallTile(Spritesheet.shopFrontB.getSprites()[8], 0xffB6FF08, false, null);
	public static Tile shopFrontB10 = new WallTile(Spritesheet.shopFrontB.getSprites()[9], 0xffB6FF09, false, null);
	public static Tile shopFrontB11 = new WallTile(Spritesheet.shopFrontB.getSprites()[10], 0xffB6FF0a, false, null);
	public static Tile shopFrontB12 = new WallTile(Spritesheet.shopFrontB.getSprites()[11], 0xffB6FF0b, false, null);

	public static Tile scaffoldLeft = new WallTile(Sprite.scaffold_left, 0xff1A5600, false, null);
	public static Tile scaffoldRight = new WallTile(Sprite.scaffold_right, 0xff1D6000, false, null);
	public static Tile scaffoldLeftBase = new WallTile(Sprite.scaffold_left_base, 0xff164C00, false, null);
	public static Tile scaffoldRightBase = new WallTile(Sprite.scaffold_right_base, 0xff206B00, false, null);
	public static Tile scaffoldRight1 = new WallTile(Sprite.scaffold_right1, 0xff103800, false, null);
	public static Tile scaffoldRight2 = new WallTile(Sprite.scaffold_right2, 0xff134200, false, null);
	public static Tile scaffoldRight3 = new WallTile(Sprite.scaffold_right3, 0xff103500, false, null);
	public static Tile scaffoldTop = new WallTile(Sprite.scaffold_top, 0xff0D2B00, false, null);

	public static Tile greenNeonSign1 = new WallTile(Sprite.green_neon_sign_1, 0xff8CFF00, false, null);
	public static Tile greenNeonSign2 = new WallTile(Sprite.green_neon_sign_2, 0xff8CFF01, false, null);
	public static Tile greenNeonSign3 = new WallTile(Sprite.green_neon_sign_3, 0xff8CFF02, false, null);

	public static Tile redNeonSign1 = new WallTile(Sprite.red_neon_sign_1, 0xffFF0014, false, null);
	public static Tile redNeonSign2 = new WallTile(Sprite.red_neon_sign_2, 0xffFF0013, false, null);
	public static Tile redNeonSign3 = new WallTile(Sprite.red_neon_sign_3, 0xffFF0012, false, null);

	public static Tile orangeNeonSign1 = new WallTile(Sprite.orange_neon_sign_1, 0xffFF2312, false, null);
	public static Tile orangeNeonSign2 = new WallTile(Sprite.orange_neon_sign_2, 0xffFF2313, false, null);

	public static Tile airConUnit1 = new WallTile(Sprite.air_con_unit_1, 0xff0D7740, false, null);
	public static Tile airConUnit2 = new WallTile(Sprite.air_con_unit_2, 0xff0D7741, false, null);
	public static Tile airConUnit3 = new WallTile(Sprite.air_con_unit_3, 0xff0D7742, false, null);
	public static Tile airConUnit4 = new WallTile(Sprite.air_con_unit_4, 0xff0D7743, false, null);

	public static Tile bigVent1 = new WallTile(Sprite.bigVent1, 0xff030C0A, false, null);
	public static Tile bigVent2 = new WallTile(Sprite.bigVent2, 0xff030C0B, false, null);
	public static Tile bigVent3 = new WallTile(Sprite.bigVent3, 0xff030C0C, false, null);
	public static Tile bigVent4 = new WallTile(Sprite.bigVent4, 0xff030C0D, false, null);

	public static Tile wallDiagTopLeft = new DiagonalTile(Sprite.wallDiagTopLeft, 0xff001591, DiagDirection.TOPLEFT, null);
	public static Tile wallDiagTopRight = new DiagonalTile(Sprite.wallDiagTopRight, 0xff00157C, DiagDirection.TOPRIGHT, null);
	public static Tile wallDiagBottomLeft = new DiagonalTile(Sprite.wallDiagBottomLeft, 0xff001555, DiagDirection.BOTTOMLEFT, null);
	public static Tile wallDiagBottomRight = new DiagonalTile(Sprite.wallDiagBottomRight, 0xff001569, DiagDirection.BOTTOMRIGHT, null);
	
	public static Tile buildingSide1 = new LowerWallTile(Sprite.buildingSide1, 0xff25547C, false, null);
	public static Tile buildingSide2 = new LowerWallTile(Sprite.buildingSide2, 0xff224D72, false, null);
	public static Tile buildingSide3 = new LowerWallTile(Sprite.buildingSide3, 0xff1F4668, false, null);
	public static Tile buildingSide4 = new LowerWallTile(Sprite.buildingSide4, 0xff1B3E5B, false, null);
	public static Tile buildingSide5 = new LowerWallTile(Sprite.buildingSide5, 0xff193A54, false, null);
	public static Tile lowerWall = new LowerWallTile(Sprite.wall, 0xff17364F, false, null);
	
	// wall floor tiles:
	public static List<Tile> hiddenTiles = Tile.initHiddenTiles();
	public static BorderRenderer wallEdges = new BorderRenderer(Spritesheet.wallTopBorder.getSprites(), new int[] {});

	// Water tiles:
	public static Tile poolWater = new WaterTile(0xff2B8F9E, Sprite.poolWaterTop, Sprite.whitePoolTiles, WaterTile.FULL, null);
	public static Tile poolEdge = new WaterTile(0xff2B8FCD, Sprite.poolWater, Sprite.smallPoolFloorTiles, WaterTile.TOP_EDGE, null);
	public static Tile poolFloorTilesB = new FloorTile(Sprite.poolFloorTile, 0xffB2BDFF, new ArrayList<Renderer>(Arrays.<Renderer>asList(new BorderRenderer(Spritesheet.poolGrateBorder.getSprites(), new int[] { 0xff2B8F9E, 0xff2B8FCD }), new ReflectionRenderer(true, 0.1))));
	public static Tile whiteTiles = new FloorTile(Sprite.whitePoolTiles, 0xff001BB7, new ArrayList<Renderer>(Arrays.<Renderer>asList(new ReflectionRenderer(true, 0.1))));
	
	
	public Tile(Sprite sprite, int colour, int zIndex, List<Renderer> renderers) {
		this.sprite = sprite;
		this.colour = colour;
		tiles.add(this);
		this.zIndex = zIndex;
		this.additionalRenderers = renderers;
	}

	public Tile(Sprite sprite, int zIndex, List<Renderer> renderers) {
		this(sprite, 0, zIndex, renderers);
	}

	public void renderDirt(int x, int y, Screen screen, Level level, long seed) {
	}

	public boolean border() {
		return border;
	}

	public void render(int x, int y, Screen screen, Level level, long seed) {
		screen.renderTile(x << 4, y << 4, this);
		if (additionalRenderers != null) for (Renderer r : additionalRenderers) {
			r.render(x, y, screen, level, seed);
		}
	}

	public int getZ() {
		return zIndex;
	}
	
	public boolean isDiagonal() {
		return false;
	}

	public int isStair() {
		return StairTile.NOT_A_STAIR;
	}

	public int getColour() {
		return colour;
	}

	public Sprite getSprite() {
		return sprite;
	}
	
	public Sprite getSprite(int x, int y, Level level) {
		return sprite;
	}
	
	public void renderOverlay(int x, int y, Screen screen, Level level, int spriteCol, Sprite borderSprite) {
		overlayCol = spriteCol;		
		renderOverlay(x, y, screen, level, Level.getTile(spriteCol).getSprite().overlay(borderSprite, 0, 0));
	}
	
	public void renderOverlay(int x, int y, Screen screen, Level level, Sprite sprite) {
//		if (borderSprite != Sprite.nullSprite) sprite = sprite.overlay(borderSprite, 0, 0);					// Modified: translucency in horizontal block below player position
		screen.renderTranslucentSprite(x << 4, y << 4, sprite, true);
	}

	public Sprite getShadowSprite(Level level, int x, int y) {
		return Sprite.tileBottomShadow1;
	}
	
	public static Tile getTile(int colour) {
		for (int i = 0; i < tiles.size(); i++) {
			if (tiles.get(i).colour == colour) return tiles.get(i);
		}
		return Tile.voidTile;
	}
	
	private static List<Tile> initHiddenTiles() {
		List<Tile> hTiles = new ArrayList<Tile>();
		List<Integer> tileCols = new ArrayList<Integer>();
		for (Tile t : tiles) tileCols.add(t.getColour());
		for (int col : tileCols) tiles.add(new TranslucentWallTile((col - 0xff000000) + 0xaa000000, true, null));
		return hTiles;
	}
}
