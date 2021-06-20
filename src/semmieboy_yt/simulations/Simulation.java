package semmieboy_yt.simulations;

import java.awt.*;

public interface Simulation {
    String getName();
    String getDescription();
    String init(Graphics graphics);
    void update();
    void render();
    void stop();
}
