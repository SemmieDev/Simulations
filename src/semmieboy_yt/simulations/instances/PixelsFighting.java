package semmieboy_yt.simulations.instances;

import semmieboy_yt.simulations.Main;
import semmieboy_yt.simulations.Simulation;

import java.awt.*;
import java.util.Random;

public class PixelsFighting extends Simulation {
    //Team 2 is true
    private boolean[][] pixels, pixelsPrev;
    private Random random;
    private boolean firstPaint;
    private final Color team1 = new Color(1, 76, 60), team2 = new Color(2, 40, 55);
    private int team1Size = 131072, team2Size = 131072;

    @Override
    public String getName() {
        return "Pixels Fighting";
    }

    @Override
    public String getDescription() {
        return "A bunch of pixels fighting to cover the arena";
    }

    @Override
    public String init() {
        pixels = new boolean[512][512];
        for (short x = 256; x < 512; x++) for (short y = 0; y < 512; y++) pixels[x][y] = true;
        pixelsPrev = new boolean[512][512];
        random = new Random();
        firstPaint = true;
        return null;
    }

    @Override
    public void update() {
        for (short x = 0; x < 512; x++) System.arraycopy(pixels[x], 0, pixelsPrev[x], 0, 512);
        Graphics graphics = Main.simulationRender.getGraphics();
        //0 is disabled, 1 is up, 2 is right, 3 is down, 4 is left
        byte[] sides = new byte[4];
        byte sidesAmount, willing;
        short tempX, tempY;
        int tempTeam1Size = 0, tempTeam2Size = 0;
        for (short x = 0; x < 512; x++) {
            for (short y = 0; y < 512; y++) {
                if (pixelsPrev[x][y]) {
                    tempTeam2Size++;
                } else {
                    tempTeam1Size++;
                }

                sides[0] = sides[1] = sides[2] = sides[3] = 0;
                sidesAmount = 0;
                willing = 0;

                if (inBounds(x, (tempY = (short)(y - 1)))) {
                    if (pixelsPrev[x][tempY] == pixelsPrev[x][y]) {
                        willing++;
                    } else {
                        sides[sidesAmount++] = 1;
                    }
                }
                if (inBounds((tempX = (short)(x + 1)), y)) {
                    if (pixelsPrev[tempX][y] == pixelsPrev[x][y]) {
                        willing++;
                    } else {
                        sides[sidesAmount++] = 2;
                    }
                }
                if (inBounds(x, (tempY = (short)(y + 1)))) {
                    if (pixelsPrev[x][tempY] == pixelsPrev[x][y]) {
                        willing++;
                    } else {
                        sides[sidesAmount++] = 3;
                    }
                }
                if (inBounds((tempX = (short)(x - 1)), y)) {
                    if (pixelsPrev[tempX][y] == pixelsPrev[x][y]) {
                        willing++;
                    } else {
                        sides[sidesAmount++] = 4;
                    }
                }

                if (sidesAmount > 0) {
                    if (random.nextInt(6) > willing) {
                        if (sidesAmount == 1) {
                            takeOver(x, y, sides[0], graphics);
                        } else {
                            takeOver(x, y, sides[random.nextInt(sidesAmount)], graphics);
                        }
                    }
                }
            }
        }
        team1Size = tempTeam1Size;
        team2Size = tempTeam2Size;
    }

    @Override
    public void repaint(Graphics graphics) {
        if (firstPaint) {
            graphics.setColor(team1);
            graphics.fillRect(0, 0, 256, 512);
            graphics.setColor(team2);
            graphics.fillRect(256, 0, 256, 512);
            firstPaint = false;
            return;
        }
        for (short x = 0; x < 512; x++) {
            for (short y = 0; y < 512; y++) {
                if (pixels[x][y]) {
                    graphics.setColor(team2);
                } else {
                    graphics.setColor(team1);
                }
                graphics.fillRect(x, y, 1, 1);
            }
        }
    }

    @Override
    public void stop() {
        pixels = null;
        random = null;
    }

    private boolean inBounds(short x, short y) {
        return x >= 0 && x <= 511 && y >= 0 && y <= 511;
    }

    private void takeOver(short x, short y, byte dir, Graphics graphics) {
        if (pixelsPrev[x][y]) {
            graphics.setColor(team2);
        } else {
            graphics.setColor(team1);
        }
        switch (dir) {
            case 1 -> {
                pixels[x][y - 1] = pixelsPrev[x][y];
                graphics.fillRect(x, y - 1, 1, 1);
            }
            case 2 -> {
                pixels[x + 1][y] = pixelsPrev[x][y];
                graphics.fillRect(x + 1, y, 1, 1);
            }
            case 3 -> {
                pixels[x][y + 1] = pixelsPrev[x][y];
                graphics.fillRect(x, y + 1, 1, 1);
            }
            case 4 -> {
                pixels[x - 1][y] = pixelsPrev[x][y];
                graphics.fillRect(x - 1, y, 1, 1);
            }
        }
    }
}
