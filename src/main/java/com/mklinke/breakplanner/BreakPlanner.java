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
package com.mklinke.breakplanner;

import java.io.IOException;

import com.mklinke.breakplanner.controller.MainController;
import com.mklinke.breakplanner.model.BreakRepository;
import com.mklinke.breakplanner.model.BreakRepositoryP2P;
import com.mklinke.breakplanner.ui.MainWindow;
import com.mklinke.breakplanner.view.MainView;

/**
 * The main class for starting the application.
 * 
 * @author Martin Klinke
 */
public class BreakPlanner {
  public static void main(String[] args) {
    new BreakPlanner().run();
  }

  /**
   * Executes the application.
   */
  public void run() {
    MainView view = getView();
    try {
      BreakRepository model = getModel();
      MainController controller = getController(view, model);
      controller.run();
    } catch (IOException e) {
      view.showError("Error initialization: " + e.getMessage());
    }
  }

  protected MainController getController(MainView view, BreakRepository model) {
    return new MainController(view, model);
  }

  protected BreakRepository getModel() throws IOException {
    return new BreakRepositoryP2P();
  }

  protected MainView getView() {
    return new MainWindow();
  }
}
