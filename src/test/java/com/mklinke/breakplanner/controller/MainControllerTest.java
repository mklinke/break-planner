/**
 * Copyright 2011 Martin Klinke
 */
package com.mklinke.breakplanner.controller;

import org.junit.Test;

import com.mklinke.breakplanner.view.MainView;

import static org.mockito.Mockito.*;

/**
 * @author Martin Klinke
 * 
 */
public class MainControllerTest {

  @Test
  public void RunShouldRegisterExitListener() {
    MainView view = mock(MainView.class);
    MainController controller = new MainController(view);
    controller.run();
    verify(view).registerExitListener(controller);
  }

}
