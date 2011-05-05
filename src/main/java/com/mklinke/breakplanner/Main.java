/**
 * Copyright 2011 Martin Klinke
 */
package com.mklinke.breakplanner;

import com.mklinke.breakplanner.controller.MainController;
import com.mklinke.breakplanner.ui.MainWindow;
import com.mklinke.breakplanner.view.MainView;

/**
 * @author Martin Klinke
 * 
 */
public class Main {
  public static void main(String[] args) {
    MainView view = new MainWindow(); 
    MainController controller = new MainController(view);
    controller.run();
  }
}
