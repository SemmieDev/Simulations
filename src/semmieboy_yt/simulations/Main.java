package semmieboy_yt.simulations;

import semmieboy_yt.simulations.instances.GameOfLife;
import semmieboy_yt.simulations.instances.TestSimulation;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static short iterationsSec = 1;
    public static boolean running;
    public static final Simulation[] simulations = new Simulation[] {
            new TestSimulation(),
            new GameOfLife()
    };
    public static final JFrame jFrame = new JFrame();
    public static final Container container = jFrame.getContentPane();
    public volatile static int currentSimulation = -1, switchSimulation = -2;
    public static SimulationRender simulationRender = new SimulationRender();
    public volatile static boolean render, handleEvents, doStep;
    private static final short[] step = {0};

    public static void main(String[] args) {
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);

        Scenes.mainMenu();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setLocation(screenSize.width / 2 - jFrame.getWidth() / 2, screenSize.height / 2 - jFrame.getHeight() / 2);

        Executors.newSingleThreadScheduledExecutor(runnable -> {
            Thread thread = new Thread(runnable, "Game Loop Thread");
            thread.setDaemon(true);
            return thread;
        }).scheduleAtFixedRate(Main::update, 0, 1, TimeUnit.MILLISECONDS);
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
        if (switchSimulation != -2) {
            handleEvents = render = false;
            if (currentSimulation != -1) simulations[currentSimulation].stop();
            if (switchSimulation == -1) {
                running = false;
            } else {
                String error;
                if ((error = simulations[switchSimulation].init()) != null) {
                    final int simulation = switchSimulation;
                    SwingUtilities.invokeLater(() -> {
                        jFrame.setVisible(false);
                        JOptionPane.showMessageDialog(jFrame, error, "Failed to initialize "+simulations[simulation].getName(), JOptionPane.ERROR_MESSAGE);
                        jFrame.dispose();
                    });
                }
                handleEvents = render = true;
            }
            currentSimulation = switchSimulation;
            switchSimulation = -2;
        } else if (running) {
            doStep = false;
            while (step[0] >= 1000) {
                if (currentSimulation != -1) simulations[currentSimulation].update();
                step[0] -= 1000;
            }
            step[0] += iterationsSec;
        } else if (doStep) {
            doStep = false;
            if (currentSimulation != -1) simulations[currentSimulation].update();
        }
    }
}
