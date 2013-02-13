/**
 *  Copyright 2013 Martin Klinke, http://www.martinklinke.com.
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
