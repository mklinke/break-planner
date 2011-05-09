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

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.mklinke.breakplanner.model.Break;
import com.mklinke.breakplanner.view.RemoveBreakListener;

/**
 * @author Martin Klinke
 * 
 */
public class BreakTableModel extends DefaultTableModel {

  public static final String COL_UNIQUE_NAME = "UniqueName";

  public static final String COL_BREAK_NAME = "Break name";

  public static final String COL_TIME = "Time";

  public static final String COL_DELETE = "Delete";

  /**
   * 
   */
  private static final long serialVersionUID = 7899475221035469623L;

  private List<Break> breaks = new ArrayList<Break>();

  private RemoveBreakListener removeBreakListener;

  /**
   * 
   */
  public BreakTableModel() {
    addColumn(COL_UNIQUE_NAME);
    addColumn(COL_BREAK_NAME);
    addColumn(COL_TIME);
    addColumn(COL_DELETE);

  }

  public void addBreak(Break newBreak) {
    breaks.add(newBreak);
    addRow(new Object[] { newBreak.getUniqueName(), newBreak.getDescription(),
        newBreak.getTime(), false });
  }

  /**
   * @param removedBreak
   */
  public void removeBreak(Break removedBreak) {
    for (int i = 0; i < getRowCount(); i++) {
      if (removedBreak.getUniqueName().equals(getValueAt(i, 0))) {
        removeRow(i);
        breaks.remove(i);
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
   */
  @Override
  public boolean isCellEditable(int row, int column) {
    return breaks.get(row).isEditable() && column == 3;
  }

  /**
   * @param row
   * @return
   */
  public Break getBreak(int row) {
    return breaks.get(row);
  }

  /**
   * @param row
   */
  public void removeBreak(int row) {
    removeRow(row);
    breaks.remove(row);
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.table.DefaultTableModel#setValueAt(java.lang.Object, int,
   * int)
   */
  @Override
  public void setValueAt(Object aValue, int row, int column) {
    super.setValueAt(aValue, row, column);
    if (column == 3 && aValue instanceof Boolean && ((Boolean) aValue)) {
      if (removeBreakListener != null) {
        removeBreakListener.removeBreak(breaks.get(row));
      }
      removeBreak(row);
    }
  }

  /**
   * @param removeBreakListener
   */
  public void registerRemoveBreakListener(
      RemoveBreakListener removeBreakListener) {
    this.removeBreakListener = removeBreakListener;
  }
}
