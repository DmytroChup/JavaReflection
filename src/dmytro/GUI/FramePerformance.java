package dmytro.GUI;

import dmytro.Controller;

import javax.swing.*;
import java.awt.*;

public class FramePerformance extends JFrame {
    private static JPanel panel;
    public FramePerformance() {
        super("GUI");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        createGUI();

        Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(dm.height / 2 - 50, dm.height / 2 - 300, 800, 600);
        this.setVisible(true);
        this.setResizable(true);
    }

    public static void main(String[] args) {
        new FramePerformance();
    }

    private void createGUI() {
        JTextField enter = new JTextField(65);
        TextArea textArea = new TextArea();
        panel = new JPanel();

        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 30, 5));

        JButton buttonStart = new JButton("Start");
        JButton buttonClean = new JButton("Clean");
        JButton buttonExit = new JButton("Exit");

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(0, 3, 5,3));
        buttons.add(buttonStart);
        buttons.add(buttonClean);
        buttons.add(buttonExit);

        buttonStart.setBackground(Color.GREEN);
        buttonClean.setBackground(Color.YELLOW);
        buttonExit.setBackground(Color.RED);

        panel.add(new JLabel("Name of class with full path:"));
        panel.add(enter);
        panel.add(buttons);

        this.add(panel, BorderLayout.NORTH);

        this.add(textArea);

        buttonStart.addActionListener(e -> {
            String className = enter.getText();
            if (!className.isEmpty()) {
                textArea.setForeground(Color.BLACK);
                textArea.setText(Controller.task1(className));
            } else {
                textArea.setForeground(Color.RED);
                textArea.setText("Can't find this class name");
            }
        });

        buttonClean.addActionListener(e -> {
            textArea.setText("");
            enter.setText("");
        });

        buttonExit.addActionListener(e -> {
            dispose();
            System.exit(0);
        });

    }
}
