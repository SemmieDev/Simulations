package semmieboy_yt.simulations;

public interface Simulation {
    String getName();
    String getDescription();
    String init();
    void update();
    void render();
    void stop();
}
