package semmieboy_yt.simulations;

import java.awt.*;
import java.awt.event.*;

public abstract class Simulation implements MouseListener, MouseMotionListener, MouseWheelListener {
    public abstract String getName();
    public abstract String getDescription();
    public abstract String init();
    public abstract void update();
    public abstract void repaint(Graphics graphics);
    public abstract void stop();

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {}
    @Override
    public void mousePressed(MouseEvent mouseEvent) {}
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}
    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {}
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {}
    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {}
}
