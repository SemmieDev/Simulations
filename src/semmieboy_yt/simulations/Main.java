package semmieboy_yt.simulations;

import semmieboy_yt.simulations.instances.TestSimulation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static short iterationsSec = 1;
    public static boolean running = true;

    public static void main(String[] args) {
        Simulation[] simulations = new Simulation[] {
                new TestSimulation()
        };

        Frame frame = new Frame("Simulations");
        frame.setResizable(false);
        frame.setSize(512, 512);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                frame.dispose();
            }
        });
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width / 2 - 256, screenSize.height / 2 - 256);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));

        Dimension buttonSize = new Dimension(120, 30);
        for (Simulation simulation : simulations) {
            Button button = new Button(simulation.getName());
            button.setPreferredSize(buttonSize);
            button.addActionListener(event -> setSimulation(simulation));
            frame.add(button);
        }

        frame.setVisible(true);

        Thread thread = new Thread(() -> {
            long time = System.currentTimeMillis();
            short step = 0;
            while (true) {
                if (running) {
                    while (step >= 1000) {
                        long now = System.currentTimeMillis();
                        System.out.println(now - time);
                        time = now;
                        step -= 1000;
                    }
                    step += iterationsSec;
                }
                try {
                    // TODO: 6/20/2021 Use something else then Thread#sleep, because Thread#sleep is broken
                    Thread.sleep(1);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }, "Game Loop");
        thread.setDaemon(true);
        thread.start();
    }

    public static void setSimulation(Simulation simulation) {

    }
}
