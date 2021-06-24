package semmieboy_yt.simulations;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class Scenes {
    private static void prepFrame() {
        Main.container.removeAll();
    }

    public static void mainMenu() {
        prepFrame();
        Main.jFrame.setTitle("Simulations");
        Main.setSize(512, 512);
        Main.container.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        Dimension buttonSize = new Dimension(120, 30);
        for (int i = 0; i < Main.simulations.length; i++) {
            JButton jButton = new JButton(Main.simulations[i].getName());
            jButton.setToolTipText(Main.simulations[i].getDescription());
            jButton.setFont(jButton.getFont().deriveFont(Font.PLAIN));
            jButton.setPreferredSize(buttonSize);
            int finalI = i;
            jButton.addActionListener(event -> {
                Main.setSimulation(finalI);
                while (Main.currentSimulation != finalI) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
                simulation();
            });
            Main.container.add(jButton);
        }
    }

    public static void simulation() {
        prepFrame();

        Main.jFrame.setTitle("Simulations - "+Main.simulations[Main.currentSimulation].getName());
        Main.setSize(712, 612);
        Main.container.setLayout(null);
        Main.container.add(Main.simulationRender);

        JButton exit = new JButton("Exit");
        exit.addActionListener(event -> {
            Main.setSimulation(-1);
            mainMenu();
        });
        exit.setFont(exit.getFont().deriveFont(Font.PLAIN));
        exit.setBounds(10, 10, 60, 20);
        Main.container.add(exit);

        JLabel description = new JLabel(Main.simulations[Main.currentSimulation].getDescription());
        description.setFont(description.getFont().deriveFont(Font.BOLD, 20));
        description.setBounds(0, 10, 712, 30);
        description.setHorizontalAlignment(SwingConstants.CENTER);
        description.setVerticalAlignment(SwingConstants.CENTER);
        Main.container.add(description);

        JLabel iterationsSecLabel = new JLabel(Main.iterationsSec+" iterations/sec");
        iterationsSecLabel.setBounds(525, 120, 150, 20);
        iterationsSecLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Main.container.add(iterationsSecLabel);

        JSlider iterationsSecSlider = new JSlider(1, 200, Main.iterationsSec);
        iterationsSecSlider.setBounds(525, 140, 150, 40);
        iterationsSecSlider.setPaintLabels(true);
        Hashtable<Integer, JComponent> labels = new Hashtable<>();
        labels.put(1, new JLabel("1"));
        labels.put(50, new JLabel("50"));
        labels.put(100, new JLabel("100"));
        labels.put(150, new JLabel("150"));
        labels.put(200, new JLabel("200"));
        iterationsSecSlider.setLabelTable(labels);
        iterationsSecSlider.addChangeListener(changeEvent -> {
            Main.iterationsSec = (short)((JSlider)changeEvent.getSource()).getValue();
            iterationsSecLabel.setText(Main.iterationsSec+" iterations/sec");
        });
        Main.container.add(iterationsSecSlider);

        JButton pauseResume = new JButton("Resume");
        pauseResume.addActionListener(actionEvent -> {
            if (Main.running) {
                Main.running = false;
                pauseResume.setText("Resume");
            } else {
                Main.running = true;
                pauseResume.setText("Pause");
            }
        });
        pauseResume.setFont(exit.getFont().deriveFont(Font.PLAIN));
        pauseResume.setBounds(525, 200, 100, 20);
        Main.container.add(pauseResume);

        JButton step = new JButton("Step");
        step.addActionListener(actionEvent -> Main.doStep = true);
        step.setFont(step.getFont().deriveFont(Font.PLAIN));
        step.setBounds(625, 200, 70, 20);
        Main.container.add(step);
    }
}
