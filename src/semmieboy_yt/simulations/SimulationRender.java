package semmieboy_yt.simulations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class SimulationRender extends JComponent {
    public SimulationRender() {
        setBounds(0, 100, 512, 512);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (Main.handleEvents) Main.simulations[Main.currentSimulation].mouseClicked(mouseEvent);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (Main.handleEvents) Main.simulations[Main.currentSimulation].mousePressed(mouseEvent);
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                if (Main.handleEvents) Main.simulations[Main.currentSimulation].mouseReleased(mouseEvent);
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                if (Main.handleEvents) Main.simulations[Main.currentSimulation].mouseEntered(mouseEvent);
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                if (Main.handleEvents) Main.simulations[Main.currentSimulation].mouseExited(mouseEvent);
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                if (Main.handleEvents) Main.simulations[Main.currentSimulation].mouseDragged(mouseEvent);
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                if (Main.handleEvents) Main.simulations[Main.currentSimulation].mouseMoved(mouseEvent);
            }
        });
        addMouseWheelListener(mouseWheelEvent -> {
            if (Main.handleEvents) Main.simulations[Main.currentSimulation].mouseWheelMoved(mouseWheelEvent);
        });
    }

    @Override
    public void paint(Graphics graphics) {
        boolean running = false;
        if (Main.running) {
            running = true;
            Main.running = false;
        }
        if (Main.render) Main.simulations[Main.currentSimulation].repaint(graphics);
        if (running) Main.running = true;
    }
}
