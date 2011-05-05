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

import com.mklinke.breakplanner.view.MainView;
import com.mklinke.breakplanner.view.ExitListener;

/**
 * The main controller for the application flow.
 * 
 * @author Martin Klinke
 */
public class MainController implements ExitListener {
  private MainView view;

  /**
   * Creates a new main controller instance with the given view.
   * @param view the view instance to use for the app
   * @throws IllegalArgumentException if view is null
   */
  public MainController(MainView view) {
    if (view == null) {
      throw new IllegalArgumentException("view must not be null");
    }
    this.view = view;
  }

  public void run() {
    view.registerExitListener(this);
    view.show();
    view.setStatus("Looking for peers...");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.mklinke.breakplanner.view.ExitListener#confirmExit()
   */
  public boolean confirmExit() {
    return view.confirmExit();
  }

}
