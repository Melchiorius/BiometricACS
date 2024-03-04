/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.boost.ui.panel;

/**
 *
 * @author borisost
 */
public class BorderedPanel extends javax.swing.JPanel {

    private String panelTitle = "Panel title";

    public String getPanelTitle() {
        return panelTitle;
    }

    public void setPanelTitle(String panelTitle) {
        this.panelTitle = panelTitle;
        setBorder(javax.swing.BorderFactory.createTitledBorder(getPanelTitle()));
    }
    
    /**
     * Creates new form BorderedPanel
     */
    public BorderedPanel() {
        initComponents();
    }

    private void initComponents() {

        setBorder(javax.swing.BorderFactory.createTitledBorder(getPanelTitle()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 278, Short.MAX_VALUE)
        );
    }
}
