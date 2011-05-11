/* 
 ******************************************************************************
 * Copyright Xavo AG 2011
 * All rights reserved
 * Author: Xavo AG, mklinke
 * 
 * $HeadURL$
 * 
 * $Revision$
 * $LastChangedBy$
 * $LastChangedDate$
 ******************************************************************************
 */
package com.mklinke.breakplanner;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mklinke.breakplanner.controller.MainController;
import com.mklinke.breakplanner.model.BreakRepository;
import com.mklinke.breakplanner.view.MainView;

/**
 * TODO: Description missing
 * 
 * @author mklinke
 */
@RunWith(MockitoJUnitRunner.class)
public class BreakPlannerTest {
  @Mock
  MainController controller;
  @Mock
  MainView view;
  @Mock
  BreakRepository model;

  @Test
  public void runMustCallControllerRun() {
    BreakPlanner breakPlanner = new BreakPlanner() {
      @Override
      protected MainView getView() {
        return view;
      };

      @Override
      protected BreakRepository getModel() throws java.io.IOException {
        return model;
      };

      @Override
      protected MainController getController(MainView view,
          BreakRepository model) {
        return controller;
      };
    };
    breakPlanner.run();
    verify(controller).run();
  }
}
