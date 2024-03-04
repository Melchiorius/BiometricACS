package ru.boost.ui.panel;


import ru.boost.network.common.businesslogic.entity.Person;
import ru.boost.tools.file.ImageFileReader;
import ru.boost.ui.utils.PersonUpdaterListener;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class FacePanel extends BorderedPanel {

    private String defaultImagePath = "ru/boost/ui/icon/Unknown.jpg";
    private ImageIcon defaultImage = ImageFileReader.loadImage(defaultImagePath);
    private List<PersonUpdaterListener> personDataUpdaters;
    private Person person;

    public FacePanel() {
        initComponents();
        initListeners();
        setPerson(null);
    }

    private void initListeners() {
        PropertyChangeListener personNameListener = evt -> {
            if (getPerson() != null && !getPerson().getName().equals(getPersonName())) {
                getPerson().setName(getPersonName());
            }

        };
        personNameLabel.addPropertyChangeListener("text", personNameListener);
        PropertyChangeListener accessButtonListener = evt -> {
            if (getPerson() != null && !getPerson().isAccess() == getPersonAccess()) {
                getPerson().setAccess(getPersonAccess());
            }
        };
        accessButton.addPropertyChangeListener("selected", accessButtonListener);
    }

    private Person getPerson() {
        return person;
    }


    private void setPersonImage(ImageIcon image) {
        if(image != null) {
            Image scaledImage = image.getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        }
        else{
            setPersonImage(defaultImage);
        }
    }

    private void setPersonName(String name) {
        personNameLabel.setText(name);
    }

    private void setPersonAccess(boolean access) {
        accessButton.setSelected(access);
    }

    private String getPersonName() {
        return personNameLabel.getText();
    }

    private boolean getPersonAccess() {
        return accessButton.isSelected();
    }

    public void setPerson(Person person) {
        this.person = person;
        if (person == null) {
            setDefault();
        } else {
            setPersonName(person.getName());
            setPersonAccess(person.isAccess());
            setPersonImage(person.getImage());
            accessButton.setEnabled(true);
        }
        repaint();
    }

    private void setDefault() {
        personNameLabel.setText("Unknown Person");
        setPersonImage(defaultImage);
        accessButton.setSelected(false);
        //accessButton.setEnabled(false);
    }

    private void initComponents() {

        imageLabel = new javax.swing.JLabel();
        infoPanel = new javax.swing.JPanel();

        personNameLabel = new javax.swing.JLabel();
        accessButton = new javax.swing.JRadioButton();
        accessButton.setText("Доступ ограничен");
        accessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Person person = getPerson();
                if (person != null) {
                    person.setAccess(accessButton.isSelected());
                    updatePersonDataView(person.getId(), person.getName(), person.isAccess());
                }

                if (accessButton.isSelected()) {
                    accessButton.setText("Доступ разрешен");
                } else {
                    accessButton.setText("Доступ ограничен");
                }


            }
        });

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));

        Dimension imageDimension = new java.awt.Dimension(128, 128);
        Dimension infoDimension = new java.awt.Dimension(240, 128);

        imageLabel.setMaximumSize(imageDimension);
        imageLabel.setMinimumSize(imageDimension);
        imageLabel.setPreferredSize(imageDimension);

        infoPanel.setMaximumSize(infoDimension);
        infoPanel.setMinimumSize(infoDimension);
        infoPanel.setPreferredSize(infoDimension);


        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setBorder(new LineBorder(Color.BLACK));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 0);


        infoPanel.add(personNameLabel, gbc);
        gbc.gridy++;
        infoPanel.add(accessButton, gbc);

        add(Box.createHorizontalStrut(10));
        add(imageLabel);
        add(Box.createHorizontalStrut(10));
        add(infoPanel);

        System.out.println(getPreferredSize().width + "/" + getPreferredSize().height);
    }

    private void updatePersonDataView(long id, String name, boolean access) {
        for (PersonUpdaterListener personDataUpdater : personDataUpdaters) {
            personDataUpdater.update(id, name, access);
        }
    }

    public void addPersonDataUpdater(PersonUpdaterListener updater) {
        if (personDataUpdaters == null) {
            personDataUpdaters = new ArrayList<>();
        }
        personDataUpdaters.add(updater);
    }

    public void removePersonDataUpdater(Runnable updater) {
        personDataUpdaters.remove(updater);
    }


    private javax.swing.JRadioButton accessButton;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel personNameLabel;

}
