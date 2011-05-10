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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.mklinke.breakplanner.controller.MainController;
import com.mklinke.breakplanner.model.BreakRepository;
import com.mklinke.breakplanner.view.MainView;

/**
 * TODO: Description missing
 * @author mklinke
 */
public class BreakPlannerTest
{
   @Test
   public void runMustCallControllerRun()
   {
      final MainController controller = mock(MainController.class);
      BreakPlanner breakPlanner = new BreakPlanner()
      {
         @Override
         protected MainView getView()
         {
            return mock(MainView.class);
         };

         @Override
         protected BreakRepository getModel()
            throws java.io.IOException
         {
            return mock(BreakRepository.class);
         };

         @Override
         protected MainController getController(
            MainView view,
            BreakRepository model)
         {
            return controller;
         };
      };
      breakPlanner.run();
      verify(controller).run();
   }
}
