/***
 *  Copyright 2011 Martin Klinke, http://www.martinklinke.com.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.mklinke.breakplanner.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.mklinke.breakplanner.model.Break;
import com.mklinke.breakplanner.view.ExitListener;
import com.mklinke.breakplanner.view.MainView;

/**
 * The main window for the application.
 * 
 * @author Martin Klinke
 */
public class MainWindow extends JFrame implements
    MainView {
  
  private static final long serialVersionUID = -2146496351553774171L;
  
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

  /* (non-Javadoc)
   * @see com.mklinke.breakplanner.view.MainView#setBreaks(com.mklinke.breakplanner.model.Break[])
   */
  public void setBreaks(Break[] breaks) {
    // TODO Auto-generated method stub
    
  }

  /* (non-Javadoc)
   * @see com.mklinke.breakplanner.view.MainView#run(java.lang.Runnable, java.lang.Runnable)
   */
  public void run(Runnable async, Runnable sync) {
    //TODO use SwingWorker?
  }
}
