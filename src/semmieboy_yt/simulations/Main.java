package semmieboy_yt.simulations;

import semmieboy_yt.simulations.instances.GameOfLife;
import semmieboy_yt.simulations.instances.PixelsFighting;
import semmieboy_yt.simulations.instances.TestSimulation;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static short iterationsSec = 1;
    public static boolean running;
    public static final Simulation[] simulations = new Simulation[] {
            new TestSimulation(),
            new GameOfLife(),
            new PixelsFighting()
    };
    public static final JFrame jFrame = new JFrame();
    public static final Container container = jFrame.getContentPane();
    public volatile static int currentSimulation = -1, switchSimulation = -2;
    public static SimulationRender simulationRender = new SimulationRender();
    public volatile static boolean render, handleEvents, doStep, fastMode;

    private static short step = 0;
    private static Thread fastGameLoop;

    public static void main(String[] args) {
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);

        Scenes.mainMenu();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setLocation(screenSize.width / 2 - jFrame.getWidth() / 2, screenSize.height / 2 - jFrame.getHeight() / 2);

        try {
            Executors.newSingleThreadScheduledExecutor(runnable -> {
                Thread thread = new Thread(runnable, "Game Loop Thread");
                thread.setDaemon(true);
                return thread;
            }).scheduleAtFixedRate(() -> {
                if (iterationsSec == 201) {
                    if (!fastMode) {
                        fastMode = true;
                        fastGameLoop = new Thread(() -> {
                            while (fastMode) {
                                update();
                                if (fastMode) fastMode = iterationsSec == 201;
                            }
                            System.out.println("hey");
                        }, "Fast Game Loop Thread");
                        fastGameLoop.setDaemon(true);
                        fastGameLoop.start();
                    }
                } else {
                    Main.update();
                }
            }, 0, 1, TimeUnit.MILLISECONDS).get();
        } catch (InterruptedException | ExecutionException exception) {
            exception.printStackTrace();
        }
    }

    public static void setSimulation(int id) {
        while (switchSimulation != -2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
        switchSimulation = id;
    }

    public static void setSize(int width, int height) {
        Insets insets = jFrame.getInsets();
        jFrame.setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
    }

    public static synchronized void update() {
        final int switchSimulation = Main.switchSimulation;
        Main.switchSimulation = -2;
        if (switchSimulation != -2) {
            handleEvents = render = false;
            if (currentSimulation != -1) {
                if (fastMode) {
                    fastMode = false;
                    try {
                        fastGameLoop.join();
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
                simulations[currentSimulation].stop();
                running = false;
                iterationsSec = 1;
            }
            if (switchSimulation != -1) {
                String error;
                if ((error = simulations[switchSimulation].init()) != null) {
                    SwingUtilities.invokeLater(() -> {
                        jFrame.setVisible(false);
                        JOptionPane.showMessageDialog(jFrame, error, "Failed to initialize "+simulations[switchSimulation].getName(), JOptionPane.ERROR_MESSAGE);
                        jFrame.dispose();
                    });
                }
                handleEvents = render = true;
            }
            currentSimulation = switchSimulation;
        } else if (running) {
            // TODO: 6/25/2021 Clean up this mess
            doStep = false;
            if (iterationsSec == 201) {
                if (currentSimulation != -1) simulations[currentSimulation].update();
            } else {
                while (step >= 1000) {
                    if (currentSimulation != -1) simulations[currentSimulation].update();
                    step -= 1000;
                }
                step += iterationsSec;
            }
        } else if (doStep) {
            doStep = false;
            if (currentSimulation != -1) simulations[currentSimulation].update();
        }
    }

    private static void asapMode() {

    }
}
