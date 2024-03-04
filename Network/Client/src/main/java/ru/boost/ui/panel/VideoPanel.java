package ru.boost.ui.panel;

import ru.boost.network.client.controller.ClientController;
import ru.boost.network.client.controller.VideoController;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class VideoPanel extends BorderedPanel {

    private JLabel image;

    public VideoPanel() {
        initComponents();
    }

    private void initComponents(){
        Dimension dimension = new Dimension(800,600);
        setMinimumSize(dimension);
        setPreferredSize(dimension);
        setLayout(new BorderLayout());
        image = new JLabel();
        image.setPreferredSize(dimension);
        add(image,BorderLayout.CENTER);
        java.util.Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateUI();
            }
        }, 0, 33);
    }

    @Override
    public void paint(Graphics g) {
        ImageIcon icon = VideoController.getFrame();
        if(icon != null) {
            image.setIcon(icon);
        }
        super.paint(g);
    }
}
