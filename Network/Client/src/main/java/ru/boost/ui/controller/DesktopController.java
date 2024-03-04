package ru.boost.ui.controller;

import ru.boost.network.client.controller.ClientController;
import ru.boost.network.common.businesslogic.entity.Person;
import ru.boost.tools.file.ImageFileReader;
import ru.boost.ui.panel.AccessPanel;
import ru.boost.ui.panel.FacePanel;
import ru.boost.ui.panel.VideoPanel;

import javax.swing.*;
import java.awt.*;

public class DesktopController {

    private static DesktopController instance;
    private static String TITLE = "Big Brother";
    private static String logoPath = "ru/boost/ui/icon/logo.jpg";
    private static ImageIcon logo = ImageFileReader.loadImage(logoPath);


    public static DesktopController getInstance() {
        if (instance == null) {
            instance = new DesktopController();
        }
        return instance;
    }

    private DesktopController() {
        initComponents();
    }

    private void initComponents() {
        JFrame frame = new JFrame(TITLE);
        if (logo != null){
            frame.setIconImage(logo.getImage());
        }
        VideoPanel videoPanel = new VideoPanel();
        AccessPanel accessPanel = new AccessPanel();
        FacePanel facePanel = new FacePanel();

        accessPanel.addRowSelectionListener(() -> {
            Person person = accessPanel.getSelectedPerson();
            facePanel.setPerson(person);
        });

        facePanel.addPersonDataUpdater((id,name,access) -> {
            accessPanel.updateUI();
            ClientController.updateData(id,name,access);
        });

        videoPanel.setPanelTitle("Video");
        facePanel.setPanelTitle("Person");
        accessPanel.setPanelTitle("Data");


        accessPanel.setMaximumSize(new Dimension(facePanel.getPreferredSize().width, accessPanel.getMaximumSize().height));
        Dimension dim = new Dimension(facePanel.getPreferredSize().width, accessPanel.getPreferredSize().height);
        accessPanel.setPreferredSize(dim);
        accessPanel.setMinimumSize(dim);


        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        accessPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        facePanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        infoPanel.add(accessPanel);
        infoPanel.add(facePanel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        videoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        mainPanel.add(videoPanel);
        mainPanel.add(infoPanel);



        frame.getContentPane().add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
        frame.setMinimumSize(frame.getPreferredSize());
    }


    public static void main(String[] args) {
        getInstance();
    }

}
