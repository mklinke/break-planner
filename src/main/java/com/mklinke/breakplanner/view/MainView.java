/**
 * Copyright 2011 Martin Klinke
 */
package com.mklinke.breakplanner.view;

/**
 * @author Martin Klinke
 * 
 */
public interface MainView {

  /**
   * @param message
   *          the status message to set
   */
  void setStatus(String message);

  /**
   * Display the view.
   */
  void show();

  /**
   * @param exitListener the listener to notify about application exit
   */
  void registerExitListener(ExitListener exitListener);

  /**
   * @return
   */
  boolean confirmExit();

}
