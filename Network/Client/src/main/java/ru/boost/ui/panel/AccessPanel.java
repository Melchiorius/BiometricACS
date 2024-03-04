package ru.boost.ui.panel;

import ru.boost.network.common.businesslogic.entity.Person;
import ru.boost.ui.panel.model.table.PersonTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.EventListener;

public class AccessPanel extends BorderedPanel {

    private PersonTableModel model;

    public AccessPanel() {
        initComponents();
    }

    public PersonTableModel getModel() {
        if(model == null){
            model = new PersonTableModel();
        }
        return model;
    }

    public void setModel(PersonTableModel model) {
        this.model = model;
    }

    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        setLayout(new java.awt.BorderLayout());

        table.setModel(getModel());
        table.getColumnModel().getColumn(1).setMaxWidth(50);
        table.getColumnModel().getColumn(1).setMinWidth(50);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.setToolTipText("");
        scrollPane.setViewportView(table);

        add(scrollPane, java.awt.BorderLayout.CENTER);
    }

    public EventListener addRowSelectionListener(Runnable method){
        ListSelectionListener selectionListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                // Проверяем, что событие не является анкорным (чтобы избежать обработки двойного события)
                if (!event.getValueIsAdjusting()) {
                    // Получаем индекс выделенной строки
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        method.run();
                    }
                }
            }
        };
        table.getSelectionModel().addListSelectionListener(selectionListener);
        return selectionListener;
    }

    public void removeRowSelectionListener(EventListener listener){
        if(listener instanceof ListSelectionListener) {
            table.getSelectionModel().removeListSelectionListener((ListSelectionListener)listener);
        }
    }

    public Person getSelectedPerson(){
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            return model.getPerson(selectedRow);
        }
        return null;
    }

    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;

}
