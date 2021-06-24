package semmieboy_yt.simulations.instances;

import semmieboy_yt.simulations.Main;
import semmieboy_yt.simulations.Simulation;

import java.awt.*;
import java.awt.event.MouseEvent;

public class GameOfLife extends Simulation {
    private boolean[][] cells;
    private boolean[][] cellsPrev;

    @Override
    public String getName() {
        return "Game of Life";
    }

    @Override
    public String getDescription() {
        return "Its Conway's Game of Life!";
    }

    @Override
    public String init() {
        cells = new boolean[32][32];
        cellsPrev = new boolean[32][32];
        return null;
    }

    @Override
    public void update() {
        for (int x = 0; x < 32; x++) {
            System.arraycopy(cells[x], 0, cellsPrev[x], 0, 32);
        }
        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 32; y++) {
                int neighbors = getNeighbors(x, y, true);
                Graphics graphics = Main.simulationRender.getGraphics();
                if (cellsPrev[x][y]) {
                    if (neighbors <= 1 || neighbors >= 4) {
                        cells[x][y] = false;
                        graphics.setColor(Color.WHITE);
                        graphics.fillRect(x * 16 + 1, y * 16 + 1, 14, 14);
                    }
                } else {
                    if (neighbors == 3) {
                        cells[x][y] = true;
                        graphics.setColor(Color.BLACK);
                        graphics.fillRect(x * 16 + 1, y * 16 + 1, 14, 14);
                    }
                }
            }
        }
    }

    @Override
    public void repaint(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 512, 512);
        graphics.setColor(Color.BLACK);
        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 32; y++) {
                if (cells[x][y]) graphics.fillRect(x * 16 + 1, y * 16 + 1, 14, 14);
            }
        }
        graphics.setColor(Color.LIGHT_GRAY);
        for (int x = 0; x < 33; x++) {
            graphics.fillRect(x * 16 - 1, 0, 2, 512);
        }
        for (int y = 0; y < 33; y++) {
            graphics.fillRect(0, y * 16 - 1, 512, 2);
        }
    }

    @Override
    public void stop() {
        cells = null;
        cellsPrev = null;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        int x = mouseEvent.getX() / 16;
        int y = mouseEvent.getY() / 16;
        Graphics graphics = Main.simulationRender.getGraphics();
        if (cells[x][y]) {
            cells[x][y] = false;
            graphics.setColor(Color.WHITE);
            graphics.fillRect(x * 16 + 1, y * 16 + 1, 14, 14);
        } else {
            cells[x][y] = true;
            graphics.setColor(Color.BLACK);
            graphics.fillRect(x * 16 + 1, y * 16 + 1, 14, 14);
        }
    }

    private int getNeighbors(int x, int y, boolean prev) {
        int amount = 0;
        if (getChecked(x - 1, y - 1, prev)) amount++;
        if (getChecked(x, y - 1, prev)) amount++;
        if (getChecked(x + 1, y - 1, prev)) amount++;
        if (getChecked(x - 1, y, prev)) amount++;
        if (getChecked(x + 1, y, prev)) amount++;
        if (getChecked(x - 1, y + 1, prev)) amount++;
        if (getChecked(x, y + 1, prev)) amount++;
        if (getChecked(x + 1, y + 1, prev)) amount++;
        return amount;
    }

    private boolean getChecked(int x, int y, boolean prev) {
        if (prev) {
            if (x >= 0 && x <= 31 && y >= 0 && y <= 31) return cellsPrev[x][y];
            return false;
        }
        if (x >= 0 && x <= 31 && y >= 0 && y <= 31) return cells[x][y];
        return false;
    }
}
