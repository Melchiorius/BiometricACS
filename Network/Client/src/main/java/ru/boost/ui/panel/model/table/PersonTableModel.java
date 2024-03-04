/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.boost.ui.panel.model.table;

import ru.boost.network.client.controller.ClientController;
import ru.boost.network.common.businesslogic.entity.Person;
import ru.boost.network.common.businesslogic.setttings.Settings;

import javax.swing.table.AbstractTableModel;

public class PersonTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Name", "Access"};
    private final Class<?>[] columnTypes = {String.class, Boolean.class};
    private Person[] data;

    public PersonTableModel() {
        this(ClientController.getData());
    }
    
    public PersonTableModel(Person[] data) {
        this.data = data;
    }

    public void setData(Person[] data){
        this.data = data;
    }

    public Person getPerson(int id){
        if(id < 0 || id >= data.length){
            return null;
        }
        return data[id];
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Person person = data[rowIndex];
        switch (columnIndex) {
            case 0:
                return person.getName();
            case 1:
                return person.isAccess();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnTypes[columnIndex];
    }
}