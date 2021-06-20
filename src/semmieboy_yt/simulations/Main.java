package semmieboy_yt.simulations;

import semmieboy_yt.simulations.instances.TestSimulation;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        Simulation[] simulations = new Simulation[] {
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
                new TestSimulation(),
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
        frame.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        Dimension buttonSize = new Dimension(110, 30);
        System.out.println((512 - (4 + 1) * 10) / 4);
        for (Simulation simulation : simulations) {
            Button button = new Button(simulation.getName());
            button.setPreferredSize(buttonSize);
            frame.add(button);
        }

        frame.setVisible(true);

        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            for (Component component : frame.getComponents()) {
                if (component instanceof Button) System.out.println(component.getLocation());
            }
        }).start();
    }
}
