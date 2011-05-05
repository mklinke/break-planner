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
package com.mklinke.breakplanner.controller;

import org.junit.Test;

import com.mklinke.breakplanner.view.MainView;

import static org.mockito.Mockito.*;

/**
 * @author Martin Klinke
 * 
 */
public class MainControllerTest {

  @Test(expected=IllegalArgumentException.class)
  public void NewMainControllerViewMustNotBeNull() {
    new MainController(null);
  }
  
  @Test
  public void RunShouldRegisterExitListener() {
    MainView view = mock(MainView.class);
    MainController controller = new MainController(view);
    controller.run();
    verify(view).registerExitListener(controller);
  }

}
