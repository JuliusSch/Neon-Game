package com.jcoadyschaebitz.neon.level.tile;

import java.util.Arrays;
import java.util.List;

import com.jcoadyschaebitz.neon.cutscene.CutScene;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class BorderRenderer implements Renderer {
	
	private int[] spriteMap, adjTileCols;
	private Level currentLevel;
	private static List<String> strPatterns = Arrays.asList(CutScene.loadTextFileArray("/textures/border_patterns.txt"));
	private List<Sprite> sprites;
	
	public BorderRenderer(Sprite[] sprites, int[] adjTileCols) {
		this.adjTileCols = adjTileCols;
		this.sprites = Arrays.asList(generateBorderSprites(sprites));
		currentLevel = Level.level_1;
	}

	@Override
	public void render(int x, int y, Screen screen, Level level, long seed) {
		if (currentLevel != level) {
			spriteMap = updateSpriteMap(level.generateBorderMap(adjTileCols), level.getWidth(), level.getHeight());
			currentLevel = level;
		}
		if (spriteMap[(x) + (y) * level.getWidth()] != -1) screen.renderTranslucentSprite(x << 4, y << 4, sprites.get(spriteMap[x + y * level.getWidth()]), true);
	}
	
	public static int[] updateSpriteMap(int[] inputMap, int width, int height) {
		int[] spriteIndices = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int[] x3 = new int[3 * 3];
				for (int j = -1; j < 2; j++) {
					for (int i = -1; i < 2; i++) {
						if ((x + i) < 0 || (x + i) >= width || (y+j) < 0 || (y+j) >= height) x3[(i + 1) + (j + 1) * 3] = 0;
						else x3[(i + 1) + (j + 1) * 3] = inputMap[(x + i) + (y + j) * width];
					}
				}
				if(x3[4] == 0) spriteIndices[x + y * width] = strPatterns.indexOf(stringify(x3));
				else spriteIndices[x + y * width] = -1;
			}
		}
		return spriteIndices;
	}
	
	private static String stringify(int[] in) {
		String out = "";
		for (int i = 0; i < in.length; i++) {
			out = out + in[i];
		}
		return out;
	}
	
	public List<Sprite> getSprites() {
		return sprites;
	}
	
	private static Sprite[] generateBorderSprites(Sprite[] baseSpr) {
		Sprite[] out = new Sprite[256];
		out[0] = Sprite.nullSprite;
		out[1] = baseSpr[11];
		out[2] = baseSpr[1];
		out[3] = baseSpr[10];
		out[4] = baseSpr[0];
		out[5] = baseSpr[2];
		out[6] = baseSpr[9];
		out[7] = baseSpr[3];
		out[8] = baseSpr[8];
		out[9] = baseSpr[1];
		out[10] = baseSpr[11].overlay(baseSpr[10], 0, 0);
		out[11] = baseSpr[0];
		out[12] = baseSpr[11].overlay(baseSpr[2], 0, 0);
		out[13] = baseSpr[11].overlay(baseSpr[9], 0, 0);
		out[14] = baseSpr[11].overlay(baseSpr[3], 0, 0);
		out[15] = baseSpr[11].overlay(baseSpr[8], 0, 0);
		out[16] = baseSpr[1];
		out[17] = baseSpr[7];
		out[18] = baseSpr[6];
		out[19] = baseSpr[1].overlay(baseSpr[9], 0, 0);
		out[20] = baseSpr[1].overlay(baseSpr[3], 0, 0);
		out[21] = baseSpr[1].overlay(baseSpr[8], 0, 0);
		out[22] = baseSpr[10].overlay(baseSpr[0], 0, 0);
		out[23] = baseSpr[2];
		out[24] = baseSpr[10].overlay(baseSpr[9], 0, 0);
		out[25] = baseSpr[10].overlay(baseSpr[3], 0, 0);
		out[26] = baseSpr[10].overlay(baseSpr[8], 0, 0);
		out[27] = baseSpr[0].overlay(baseSpr[2], 0, 0);
		out[28] = baseSpr[0];
		out[29] = baseSpr[5];
		out[30] = baseSpr[0].overlay(baseSpr[8], 0, 0);
		out[31] = baseSpr[2].overlay(baseSpr[9], 0, 0);
		out[32] = baseSpr[4];
		out[33] = baseSpr[2];
		out[34] = baseSpr[3];
		out[35] = baseSpr[8].overlay(baseSpr[9], 0, 0);
		out[36] = baseSpr[3];
		out[37] = baseSpr[1];
		out[38] = baseSpr[7];
		out[39] = baseSpr[6];
		out[40] = baseSpr[1].overlay(baseSpr[9], 0, 0);
		out[41] = baseSpr[1].overlay(baseSpr[3], 0, 0);
		out[42] = baseSpr[1].overlay(baseSpr[8], 0, 0);
		out[43] = baseSpr[0].overlay(baseSpr[10], 0, 0);
		out[44] = baseSpr[2].overlay(baseSpr[11], 0, 0);
		out[45] = (baseSpr[11].overlay(baseSpr[10], 0, 0)).overlay(baseSpr[9], 0, 0);
		out[46] = (baseSpr[11].overlay(baseSpr[10], 0, 0)).overlay(baseSpr[3], 0, 0);
		out[47] = (baseSpr[11].overlay(baseSpr[10], 0, 0)).overlay(baseSpr[8], 0, 0);
		out[48] = baseSpr[0].overlay(baseSpr[2], 0, 0);
		out[49] = baseSpr[0];
		out[50] = baseSpr[5];
		out[51] = baseSpr[0].overlay(baseSpr[8], 0, 0);
		out[52] = (baseSpr[11].overlay(baseSpr[9], 0, 0)).overlay(baseSpr[2], 0, 0);
		out[53] = baseSpr[11].overlay(baseSpr[4], 0, 0);
		out[54] = baseSpr[11].overlay(baseSpr[2], 0, 0);
		out[55] = baseSpr[11].overlay(baseSpr[3], 0, 0);
		out[56] = (baseSpr[11].overlay(baseSpr[9], 0, 0)).overlay(baseSpr[8], 0, 0);
		out[57] = baseSpr[11].overlay(baseSpr[3], 0, 0);
		out[58] = baseSpr[7];
		out[59] = baseSpr[6];
		out[60] = baseSpr[1].overlay(baseSpr[9], 0, 0);
		out[61] = baseSpr[1].overlay(baseSpr[3], 0, 0);
		out[62] = baseSpr[1].overlay(baseSpr[8], 0, 0);
		out[63] = baseSpr[14];
		out[64] = baseSpr[7];
		out[65] = baseSpr[13];
		out[66] = baseSpr[7].overlay(baseSpr[8], 0, 0);
		out[67] = baseSpr[6].overlay(baseSpr[9], 0, 0);
		out[68] = baseSpr[15];
		out[69] = baseSpr[6];
		out[70] = baseSpr[1].overlay(baseSpr[3], 0, 0);
		out[71] = (baseSpr[1].overlay(baseSpr[8], 0, 0)).overlay(baseSpr[9], 0, 0);
		out[72] = baseSpr[1].overlay(baseSpr[3], 0, 0);
		out[73] = baseSpr[0].overlay(baseSpr[2], 0, 0);
		out[74] = baseSpr[0].overlay(baseSpr[10], 0, 0);
		out[75] = baseSpr[5].overlay(baseSpr[10], 0, 0);
		out[76] = (baseSpr[0].overlay(baseSpr[10], 0, 0)).overlay(baseSpr[11], 0, 0);
		out[77] = baseSpr[2].overlay(baseSpr[9], 0, 0);
		out[78] = baseSpr[4];
		out[79] = baseSpr[2];
		out[80] = baseSpr[10].overlay(baseSpr[3], 0, 0);
		out[81] = (baseSpr[10].overlay(baseSpr[9], 0, 0)).overlay(baseSpr[8], 0, 0);
		out[82] = baseSpr[10].overlay(baseSpr[3], 0, 0);
		out[83] = baseSpr[0].overlay(baseSpr[2], 0, 0);
		out[84] = baseSpr[12];
		out[85] = baseSpr[0].overlay(baseSpr[2], 0, 0);
		out[86] = baseSpr[5];
		out[87] = baseSpr[0].overlay(baseSpr[8], 0, 0);
		out[88] = baseSpr[5];
		out[89] = baseSpr[4];
		out[90] = baseSpr[2].overlay(baseSpr[9], 0, 0);
		out[91] = baseSpr[4];
		out[92] = baseSpr[3];
		out[93] = baseSpr[7];
		out[94] = baseSpr[6];
		out[95] = baseSpr[1].overlay(baseSpr[9], 0, 0);
		out[96] = baseSpr[1].overlay(baseSpr[3], 0, 0);
		out[97] = baseSpr[1].overlay(baseSpr[8], 0, 0);
		out[98] = baseSpr[14];
		out[99] = baseSpr[7];
		out[100] = baseSpr[13];
		out[101] = baseSpr[7].overlay(baseSpr[8], 0, 0);
		out[102] = baseSpr[6].overlay(baseSpr[9], 0, 0);
		out[103] = baseSpr[15];
		out[104] = baseSpr[6];
		out[105] = baseSpr[1].overlay(baseSpr[3], 0, 0);
		out[106] = (baseSpr[1].overlay(baseSpr[8], 0, 0)).overlay(baseSpr[9], 0, 0);
		out[107] = baseSpr[1].overlay(baseSpr[3], 0, 0);
		out[108] = baseSpr[0].overlay(baseSpr[2], 0, 0);
		out[109] = baseSpr[0].overlay(baseSpr[10], 0, 0);
		out[110] = baseSpr[5].overlay(baseSpr[10], 0, 0);
		out[111] = (baseSpr[0].overlay(baseSpr[8], 0, 0)).overlay(baseSpr[10], 0, 0);
		out[112] = (baseSpr[2].overlay(baseSpr[9], 0, 0)).overlay(baseSpr[11], 0, 0);
		out[113] = baseSpr[4].overlay(baseSpr[11], 0, 0);
		out[114] = baseSpr[2].overlay(baseSpr[11], 0, 0);
		out[115] = (baseSpr[10].overlay(baseSpr[11], 0, 0)).overlay(baseSpr[3], 0, 0);
		out[116] = ((baseSpr[8].overlay(baseSpr[9], 0, 0)).overlay(baseSpr[10], 0, 0)).overlay(baseSpr[11], 0, 0);
		out[117] = (baseSpr[10].overlay(baseSpr[11], 0, 0)).overlay(baseSpr[3], 0, 0);
		out[118] = baseSpr[0].overlay(baseSpr[2], 0, 0);
		out[119] = baseSpr[12];
		out[120] = baseSpr[0].overlay(baseSpr[2], 0, 0);
		out[121] = baseSpr[5];
		out[122] = baseSpr[0].overlay(baseSpr[8], 0, 0);
		out[123] = baseSpr[5];
		out[124] = baseSpr[4].overlay(baseSpr[11], 0, 0);
		out[125] = (baseSpr[9].overlay(baseSpr[11], 0, 0)).overlay(baseSpr[2], 0, 0);
		out[126] = baseSpr[4].overlay(baseSpr[11], 0, 0);
		out[127] = baseSpr[3].overlay(baseSpr[11], 0, 0);
		out[128] = baseSpr[14];
		out[129] = baseSpr[7];
		out[130] = baseSpr[13];
		out[131] = baseSpr[7].overlay(baseSpr[8], 0, 0);
		out[132] = baseSpr[6].overlay(baseSpr[9], 0, 0);
		out[133] = baseSpr[15];
		out[134] = baseSpr[6];
		out[135] = baseSpr[1].overlay(baseSpr[3], 0, 0);
		out[136] = (baseSpr[8].overlay(baseSpr[9], 0, 0)).overlay(baseSpr[1], 0, 0);
		out[137] = baseSpr[1].overlay(baseSpr[3], 0, 0);
		out[138] = baseSpr[14];
		out[139] = Sprite.nullSprite;
		out[140] = baseSpr[14];
		out[141] = baseSpr[13];
		out[142] = baseSpr[7].overlay(baseSpr[8], 0, 0);
		out[143] = baseSpr[13];
		out[144] = baseSpr[15];
		out[145] = baseSpr[6].overlay(baseSpr[9], 0, 0);
		out[146] = baseSpr[15];
		out[147] = baseSpr[1].overlay(baseSpr[3], 0, 0);
		out[148] = baseSpr[0].overlay(baseSpr[2], 0, 0);
		out[149] = baseSpr[12];
		out[150] = baseSpr[0].overlay(baseSpr[2], 0, 0);
		out[151] = baseSpr[5].overlay(baseSpr[10], 0, 0);
		out[152] = (baseSpr[8].overlay(baseSpr[10], 0, 0)).overlay(baseSpr[0], 0, 0);
		out[153] = baseSpr[5].overlay(baseSpr[10], 0, 0);
		out[154] = baseSpr[4];
		out[155] = baseSpr[2].overlay(baseSpr[9], 0, 0);
		out[156] = baseSpr[4];
		out[157] = baseSpr[3].overlay(baseSpr[10], 0, 0);
		out[158] = baseSpr[12];
		out[159] = baseSpr[0].overlay(baseSpr[2], 0, 0);
		out[160] = baseSpr[12];
		out[161] = baseSpr[5];
		out[162] = baseSpr[4];
		out[163] = baseSpr[14];
		out[164] = baseSpr[7];
		out[165] = baseSpr[13];
		out[166] = baseSpr[7].overlay(baseSpr[8], 0, 0);
		out[167] = baseSpr[6].overlay(baseSpr[9], 0, 0);
		out[168] = baseSpr[15];
		out[169] = baseSpr[6];
		out[170] = baseSpr[1].overlay(baseSpr[3], 0, 0);
		out[171] = (baseSpr[8].overlay(baseSpr[9], 0, 0)).overlay(baseSpr[1], 0, 0);
		out[172] = baseSpr[1].overlay(baseSpr[3], 0, 0);
		out[173] = baseSpr[14];
		out[174] = Sprite.nullSprite;
		out[175] = baseSpr[14];
		out[176] = baseSpr[13];
		out[177] = baseSpr[7].overlay(baseSpr[8], 0, 0);
		out[178] = baseSpr[13];
		out[179] = baseSpr[15];
		out[180] = baseSpr[6].overlay(baseSpr[9], 0, 0);
		out[181] = baseSpr[15];
		out[182] = baseSpr[1].overlay(baseSpr[3], 0, 0);
		out[183] = baseSpr[0].overlay(baseSpr[2], 0, 0);
		out[184] = baseSpr[12];
		out[185] = baseSpr[0].overlay(baseSpr[2], 0, 0);
		out[186] = baseSpr[5].overlay(baseSpr[10], 0, 0);
		out[187] = (baseSpr[8].overlay(baseSpr[10], 0, 0)).overlay(baseSpr[0], 0, 0);
		out[188] = baseSpr[5].overlay(baseSpr[10], 0, 0);
		out[189] = baseSpr[4].overlay(baseSpr[11], 0, 0);
		out[190] = (baseSpr[9].overlay(baseSpr[11], 0, 0)).overlay(baseSpr[2], 0, 0);
		out[191] = baseSpr[4].overlay(baseSpr[11], 0, 0);
		out[192] = (baseSpr[10].overlay(baseSpr[11], 0, 0)).overlay(baseSpr[3], 0, 0);
		out[193] = baseSpr[12];
		out[194] = baseSpr[0].overlay(baseSpr[2], 0, 0);
		out[195] = baseSpr[12];
		out[196] = baseSpr[5];
		out[197] = baseSpr[4].overlay(baseSpr[11], 0, 0);
		out[198] = baseSpr[14];
		out[199] = Sprite.nullSprite;
		out[200] = baseSpr[14];
		out[201] = baseSpr[13];
		out[202] = baseSpr[7].overlay(baseSpr[8], 0, 0);
		out[203] = baseSpr[13];
		out[204] = baseSpr[15];
		out[205] = baseSpr[6].overlay(baseSpr[9], 0, 0);
		out[206] = baseSpr[15];
		out[207] = baseSpr[1].overlay(baseSpr[3], 0, 0);
		out[208] = Sprite.nullSprite;
		out[209] = baseSpr[14];
		out[210] = Sprite.nullSprite;
		out[211] = baseSpr[13];
		out[212] = baseSpr[15];
		out[213] = baseSpr[12];
		out[214] = baseSpr[0].overlay(baseSpr[2], 0, 0);
		out[215] = baseSpr[12];
		out[216] = baseSpr[5].overlay(baseSpr[10], 0, 0);
		out[217] = baseSpr[4];
		out[218] = baseSpr[12];
		out[219] = baseSpr[14];
		out[220] = Sprite.nullSprite;
		out[221] = baseSpr[14];
		out[222] = baseSpr[13];
		out[223] = baseSpr[7].overlay(baseSpr[8], 0, 0);
		out[224] = baseSpr[13];
		out[225] = baseSpr[15];
		out[226] = baseSpr[6].overlay(baseSpr[9], 0, 0);
		out[227] = baseSpr[15];
		out[228] = baseSpr[1].overlay(baseSpr[3], 0, 0);
		out[229] = Sprite.nullSprite;
		out[230] = baseSpr[14];
		out[231] = Sprite.nullSprite;
		out[232] = baseSpr[13];
		out[233] = baseSpr[15];
		out[234] = baseSpr[12];
		out[235] = baseSpr[0].overlay(baseSpr[2], 0, 0);
		out[236] = baseSpr[12];
		out[237] = baseSpr[5].overlay(baseSpr[10], 0, 0);
		out[238] = baseSpr[4].overlay(baseSpr[11], 0, 0);
		out[239] = baseSpr[12];
		out[240] = Sprite.nullSprite;
		out[241] = baseSpr[14];
		out[242] = Sprite.nullSprite;
		out[243] = baseSpr[13];
		out[244] = baseSpr[15];
		out[245] = Sprite.nullSprite;
		out[246] = baseSpr[12];
		out[247] = Sprite.nullSprite;
		out[248] = baseSpr[14];
		out[249] = Sprite.nullSprite;
		out[250] = baseSpr[13];
		out[251] = baseSpr[15];
		out[252] = Sprite.nullSprite;
		out[253] = baseSpr[12];
		out[254] = Sprite.nullSprite;
		out[255] = Sprite.nullSprite;
		return out;
	}

}
