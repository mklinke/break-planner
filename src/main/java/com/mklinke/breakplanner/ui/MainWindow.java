/**
 * Copyright 2011 Martin Klinke
 */
package com.mklinke.breakplanner.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.mklinke.breakplanner.view.MainView;
import com.mklinke.breakplanner.view.ExitListener;

/**
 * @author Martin Klinke
 * 
 */
public class MainWindow extends JFrame implements
    MainView {

  private JLabel statusLabel;

  public MainWindow() {
    Container contentPane = getContentPane();
    Panel panel = new Panel();
    panel.setLayout(new BorderLayout());
    contentPane.add(panel);

    panel.add(getStatusLabel(), BorderLayout.SOUTH);

    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
  }

  private JLabel getStatusLabel() {
    if (statusLabel == null)
      statusLabel = new JLabel();
    return statusLabel;
  }

  public void registerExitListener(final ExitListener exitListener) {
    this.addWindowListener(new WindowAdapter() {

      public void windowClosing(WindowEvent e) {
        if (exitListener.confirmExit()) {
          dispose();
        }
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.mklinke.breakplanner.view.BreakPlannerMainView#confirmExit()
   */
  public boolean confirmExit() {
    return JOptionPane.showConfirmDialog(this, "Exit?", "Break Planner",
        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.mklinke.breakplanner.ui.BreakPlannerMainView#setStatus(java.lang.String
   * )
   */
  public void setStatus(String message) {

    getStatusLabel().setText(message);
  }
}
