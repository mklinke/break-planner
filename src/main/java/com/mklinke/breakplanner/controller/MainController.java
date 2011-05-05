/**
 * Copyright 2011 Martin Klinke
 */
package com.mklinke.breakplanner.controller;

import com.mklinke.breakplanner.view.MainView;
import com.mklinke.breakplanner.view.ExitListener;

/**
 * @author Martin Klinke
 * 
 */
public class MainController implements ExitListener{
  private MainView view;

  public MainController(MainView view) {
    this.view = view;
  }
  
  public void run()
  {
    view.registerExitListener(this);
    view.show();
    view.setStatus("Looking for peers...");
  }

  /* (non-Javadoc)
   * @see com.mklinke.breakplanner.view.ExitListener#confirmExit()
   */
  public boolean confirmExit() {
    return view.confirmExit();
  }

}
