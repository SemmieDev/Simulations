package semmieboy_yt.simulations.instances;

import semmieboy_yt.simulations.Main;
import semmieboy_yt.simulations.Simulation;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class TestSimulation extends Simulation {
    private int brushSize = 10;
    private long time = 0;

    @Override
    public String getName() {
        return "Test Simulation";
    }

    @Override
    public String getDescription() {
        return "This simulation is meant for testing only";
    }

    @Override
    public String init() {
        return null;
    }

    @Override
    public void update() {
        long currentTime = System.currentTimeMillis();
        System.out.println(currentTime - time);
        time = currentTime;
    }

    @Override
    public void repaint(Graphics graphics) {
        graphics.fillRect(0, 0, 512, 512);
    }

    @Override
    public void stop() {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        Main.simulationRender.getGraphics().fillOval(mouseEvent.getX() - brushSize / 2, mouseEvent.getY() - brushSize / 2, brushSize, brushSize);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        brushSize += mouseWheelEvent.getY() / 50;
    }
}
