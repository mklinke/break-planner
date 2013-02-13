/**
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
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.table.TableCellRenderer;

import org.joda.time.LocalTime;

import com.mklinke.breakplanner.model.Break;
import com.mklinke.breakplanner.view.ExitListener;
import com.mklinke.breakplanner.view.MainView;
import com.mklinke.breakplanner.view.NewBreakListener;
import com.mklinke.breakplanner.view.RemoveBreakListener;

/**
 * The main window for the application.
 * 
 * @author Martin Klinke
 */
public class MainWindow extends JFrame implements MainView {

  /**
   * @author Martin Klinke
   * 
   */
  private final class DeleteBreakCellRenderer implements TableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, final int row, int column) {
      if (getBreakTableModel().getBreak(row).isEditable()) {
        JButton button = new JButton("Delete");
        return button;
      }
      return null;
    }
  }

  /**
   * @author Martin Klinke
   * 
   */
  private final class DeleteBreakCellEditor extends DefaultCellEditor {

    /**
     * 
     */
    private static final long serialVersionUID = -6068340334732348163L;
    private JButton button;
    private boolean delete;

    /**
     * @param arg0
     */
    public DeleteBreakCellEditor(JCheckBox checkBox) {
      super(checkBox);

      button = new JButton();
      button.setOpaque(true);
      button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          delete = true;
          fireEditingStopped();
        }
      });
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.swing.DefaultCellEditor#getTableCellEditorComponent(javax.swing
     * .JTable, java.lang.Object, boolean, int, int)
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected, int row, int column) {
      if (isSelected) {
        button.setForeground(table.getSelectionForeground());
        button.setBackground(table.getSelectionBackground());
      } else {
        button.setForeground(table.getForeground());
        button.setBackground(table.getBackground());
      }
      return button;
    }

    public boolean stopCellEditing() {
      delete = true;
      return super.stopCellEditing();
    }

    public Object getCellEditorValue() {
      return delete;
    }
  }

  private static final long serialVersionUID = -2146496351553774171L;

  private JLabel statusLabel;

  private JTable breakTable;

  private BreakTableModel breakTableModel;

  private JTextField newBreakNameText;

  private JButton newBreakButton;

  private JScrollPane tableScrollPane;

  private JSpinner newBreakTimeSpinner;

  public MainWindow() {
    Container contentPane = getContentPane();
    Panel mainPanel = new Panel();
    mainPanel.setLayout(new BorderLayout());
    contentPane.add(mainPanel);

    Panel centerPanel = new Panel();
    centerPanel.setLayout(new BorderLayout());

    Panel insertBreakPanel = new Panel();
    insertBreakPanel.setLayout(new BorderLayout());

    centerPanel.add(getTableScrollPane(), BorderLayout.CENTER);

    centerPanel.add(insertBreakPanel, BorderLayout.SOUTH);

    Panel inputPanel = new Panel();
    inputPanel.setLayout(new GridLayout(1, 2));

    inputPanel.add(getNewBreakNameText(), 0);
    inputPanel.add(getNewBreakTimeSpinner(), 1);

    insertBreakPanel.add(inputPanel, BorderLayout.CENTER);
    insertBreakPanel.add(getNewBreakButton(), BorderLayout.EAST);

    mainPanel.add(centerPanel, BorderLayout.CENTER);
    mainPanel.add(getStatusLabel(), BorderLayout.SOUTH);

    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    pack();
  }

  private JScrollPane getTableScrollPane() {
    if (tableScrollPane == null) {
      tableScrollPane = new JScrollPane(getBreakTable());
    }
    return tableScrollPane;
  }

  private JTable getBreakTable() {
    if (breakTable == null) {
      breakTable = new JTable(getBreakTableModel());
      breakTable.removeColumn(getBreakTable().getColumn(
          BreakTableModel.COL_UNIQUE_NAME));
      breakTable.getColumn(BreakTableModel.COL_DELETE).setCellRenderer(
          new DeleteBreakCellRenderer());
      breakTable.getColumn(BreakTableModel.COL_DELETE).setCellEditor(
          new DeleteBreakCellEditor(new JCheckBox()));
      breakTable.setMinimumSize(new Dimension(100, 100));
    }
    return breakTable;
  }

  private BreakTableModel getBreakTableModel() {
    if (breakTableModel == null) {
      breakTableModel = new BreakTableModel();
    }
    return breakTableModel;
  }

  private JTextField getNewBreakNameText() {
    if (newBreakNameText == null) {
      newBreakNameText = new JTextField();
    }
    return newBreakNameText;
  }

  private JSpinner getNewBreakTimeSpinner() {
    if (newBreakTimeSpinner == null) {
      newBreakTimeSpinner = new JSpinner();
      newBreakTimeSpinner.setModel(new SpinnerDateModel(new Date(),
          new LocalTime(0, 0).toDateTimeToday().toDate(), new LocalTime(23, 59)
              .toDateTimeToday().toDate(), Calendar.HOUR_OF_DAY));
    }
    return newBreakTimeSpinner;
  }

  private JButton getNewBreakButton() {
    if (newBreakButton == null) {
      newBreakButton = new JButton();

    }
    return newBreakButton;
  }

  private JLabel getStatusLabel() {
    if (statusLabel == null) {
      statusLabel = new JLabel();
    }
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

  public void registerNewBreakListener(final NewBreakListener newBreakListener) {
    getNewBreakButton().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Object value = getNewBreakTimeSpinner().getValue();
        newBreakListener
            .newBreak(getNewBreakNameText().getText(), (Date) value);
      }
    });
  }

  public void registerRemoveBreakListener(
      RemoveBreakListener removeBreakListener) {
    getBreakTableModel().registerRemoveBreakListener(removeBreakListener);
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.mklinke.breakplanner.view.MainView#addBreak(com.mklinke.breakplanner
   * .model.Break)
   */
  public void addBreak(Break aBreak) {
    getBreakTableModel().addBreak(aBreak);
  }

  public Break removeBreak(UUID removedBreakUUID) {
    return getBreakTableModel().removeBreak(removedBreakUUID);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.mklinke.breakplanner.view.MainView#showError(java.lang.String)
   */
  public void showError(String string) {
    JOptionPane.showMessageDialog(this, "Error", string,
        JOptionPane.ERROR_MESSAGE);
  }

}
