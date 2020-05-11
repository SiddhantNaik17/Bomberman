package game.bomberman.gfx;

import java.awt.image.BufferedImage;

public class Asset {

    private static final int width = 16, height = 16;

    public static BufferedImage[] playerRight, playerLeft, playerUp, playerDown, playerDeath, bomb, bombDetonation,
            brickBlast, enemyLeft, enemyRight, enemyDeath;

    public static BufferedImage background, rock, door;

    public static void init() {
        SpriteSheet spriteSheet = new SpriteSheet((ImageLoader.loadImage("/textures/sprite_sheet.png")));

        playerRight = new BufferedImage[3];
        playerRight[0] = spriteSheet.crop(0, 15, width, height);
        playerRight[1] = spriteSheet.crop(width, 15, width, height);
        playerRight[2] = spriteSheet.crop(width * 2, 15, width, height);

        playerLeft = new BufferedImage[3];
        playerLeft[0] = spriteSheet.crop(0, 0, width, height);
        playerLeft[1] = spriteSheet.crop(width, 0, width, height);
        playerLeft[2] = spriteSheet.crop(width * 2, 0, width, height);

        playerUp = new BufferedImage[3];
        playerUp[0] = spriteSheet.crop(width * 3, 16, width, height);
        playerUp[1] = spriteSheet.crop(width * 4, 16, width, height);
        playerUp[2] = spriteSheet.crop(width * 5, 16, width, height);

        playerDown = new BufferedImage[3];
        playerDown[0] = spriteSheet.crop(width * 3, 0, width, height);
        playerDown[1] = spriteSheet.crop(width * 4, 0, width, height);
        playerDown[2] = spriteSheet.crop(width * 5, 0, width, height);

        playerDeath = new BufferedImage[7];
        playerDeath[0] = spriteSheet.crop(0, 32, width, height);
        playerDeath[1] = spriteSheet.crop(width, 32, width, height);
        playerDeath[2] = spriteSheet.crop(width * 2, 32, width, height);
        playerDeath[3] = spriteSheet.crop(width * 3, 32, width, height);
        playerDeath[4] = spriteSheet.crop(width * 4, 32, width, height);
        playerDeath[5] = spriteSheet.crop(width * 5, 32, width, height);
        playerDeath[6] = spriteSheet.crop(width * 6, 32, width, height);

        background = spriteSheet.crop(0, 68, width, height);
        rock = spriteSheet.crop(48, 48, width, height);
        door = spriteSheet.crop(176, 48, width, height);

        brickBlast = new BufferedImage[7];
        brickBlast[0] = spriteSheet.crop(width * 4, 48, width, height);
        brickBlast[1] = spriteSheet.crop(width * 5, 48, width, height);
        brickBlast[2] = spriteSheet.crop(width * 6, 48, width, height);
        brickBlast[3] = spriteSheet.crop(width * 7, 48, width, height);
        brickBlast[4] = spriteSheet.crop(width * 8, 48, width, height);
        brickBlast[5] = spriteSheet.crop(width * 9, 48, width, height);
        brickBlast[6] = spriteSheet.crop(width * 10, 48, width, height);

        enemyLeft = new BufferedImage[3];
        enemyLeft[0] = spriteSheet.crop(0, 240, width, height);
        enemyLeft[1] = spriteSheet.crop(width, 240, width, height);
        enemyLeft[2] = spriteSheet.crop(width * 2, 240, width, height);

        enemyRight = new BufferedImage[3];
        enemyRight[0] = spriteSheet.crop(width * 3, 240, width, height);
        enemyRight[1] = spriteSheet.crop(width * 4, 240, width, height);
        enemyRight[2] = spriteSheet.crop(width * 5, 240, width, height);

        enemyDeath = new BufferedImage[5];
        enemyDeath[0] = spriteSheet.crop(width * 6, 240, width, height);
        enemyDeath[1] = spriteSheet.crop(width * 7, 240, width, height);
        enemyDeath[2] = spriteSheet.crop(width * 8, 240, width, height);
        enemyDeath[3] = spriteSheet.crop(width * 9, 240, width, height);
        enemyDeath[4] = spriteSheet.crop(width * 10, 240, width, height);

        bomb =  new BufferedImage[3];
        bomb[0] = spriteSheet.crop(0, 48, width, height);
        bomb[1] = spriteSheet.crop(width, 48, width, height);
        bomb[2] = spriteSheet.crop(width * 2, 48, width, height);

        bombDetonation =  new BufferedImage[4];
        bombDetonation[0] = spriteSheet.crop(3, 67, 74, 74);
        bombDetonation[1] = spriteSheet.crop(83, 67, 74, 74);
        bombDetonation[2] = spriteSheet.crop(0, 144, 80, 80);
        bombDetonation[3] = spriteSheet.crop(80, 144, 80, 80);
    }
}
