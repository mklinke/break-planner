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

import java.io.IOException;

import com.mklinke.breakplanner.model.BreakRepository;
import com.mklinke.breakplanner.view.ExitListener;
import com.mklinke.breakplanner.view.MainView;

/**
 * The main controller for the application flow.
 * 
 * @author Martin Klinke
 */
public class MainController implements ExitListener {
  private MainView view;
  private BreakRepository model;

  /**
   * Creates a new main controller instance with the given view.
   * @param view the view instance to use for the app
   * @param model the model to use for data access
   * @throws IllegalArgumentException if view is null
   */
  public MainController(MainView view, BreakRepository model) {
    if (view == null) {
      throw new IllegalArgumentException("view must not be null");
    }
    if (model == null) {
      throw new IllegalArgumentException("model must not be null");
    }
    this.view = view;
    this.model = model;
  }

  public void run() {
    view.registerExitListener(this);
    view.show();
    view.setStatus("Looking for breaks...");
    Runnable connector = new Runnable() {
      
      public void run() {
        try {
          model.connect();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }   
      }
    };
    Runnable displayBreaks = new Runnable(){
      public void run()
      {
        view.setBreaks(model.getBreaks());
      }
    };
    view.run(connector, displayBreaks);
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
