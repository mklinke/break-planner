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
import java.util.Date;
import java.util.List;

import org.joda.time.LocalTime;

import com.mklinke.breakplanner.model.Break;
import com.mklinke.breakplanner.model.BreakRepository;
import com.mklinke.breakplanner.model.RemoteBreakListener;
import com.mklinke.breakplanner.view.ExceptionHandler;
import com.mklinke.breakplanner.view.ExitListener;
import com.mklinke.breakplanner.view.MainView;
import com.mklinke.breakplanner.view.NewBreakListener;
import com.mklinke.breakplanner.view.RemoveBreakListener;

/**
 * The main controller for the application flow.
 * 
 * @author Martin Klinke
 */
public class MainController implements NewBreakListener, RemoveBreakListener,
    RemoteBreakListener, ExitListener, ExceptionHandler {
  private MainView view;
  private BreakRepository model;

  /**
   * Creates a new main controller instance with the given view.
   * 
   * @param view
   *          the view instance to use for the app
   * @param model
   *          the model to use for data access
   * @throws IllegalArgumentException
   *           if view is null
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
    view.registerNewBreakListener(this);
    view.registerRemoveBreakListener(this);
    view.show();
    model.registerRemoteBreakListener(this);
    view.setStatus("Looking for breaks...");
    List<Break> breaks = model.getBreaks();
    view.setStatus("Found " + breaks.size() + " breaks.");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.mklinke.breakplanner.view.ExitListener#confirmExit()
   */
  public boolean confirmExit() {
    if (view.confirmExit()) {
      model.removeBreaks();
      try {
        model.disconnect();
      } catch (IOException e) {
        handleException("Error while disconnecting.", e);
      }
      return true;
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.mklinke.breakplanner.view.NewBreakListener#newBreak(java.lang.String)
   */
  public void newBreak(String description, Date time) {
    try {
      Break newBreak = new Break(description, new LocalTime(time.getTime()));
      newBreak.setEditable(true);
      model.addBreak(newBreak);
      view.addBreak(newBreak);
      view.setStatus("Created new break.");
    } catch (IOException e) {
      handleException("Error while adding break", e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.mklinke.breakplanner.view.RemoveBreakListener#removeBreak(com.mklinke
   * .breakplanner.model.Break)
   */
  public void removeBreak(Break aBreak) {
    try {
      model.removeBreak(aBreak);
      view.setStatus("Break removed.");
    } catch (IOException e) {
      handleException("Error while removing break", e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.mklinke.breakplanner.model.RemoteBreakListener#remoteBreakAdded(com
   * .mklinke.breakplanner.model.Break)
   */
  public void remoteBreakAdded(Break newBreak) {
    view.addBreak(newBreak);
    view.setStatus("Received new break: " + newBreak.getDescription());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.mklinke.breakplanner.model.RemoteBreakListener#remoteBreakRemoved(com
   * .mklinke.breakplanner.model.Break)
   */
  public void remoteBreakRemoved(Break removedBreak) {
    view.removeBreak(removedBreak);
    view.setStatus("Break was removed: " + removedBreak.getDescription());
  }

  /**
   * Handles an exception.
   * 
   * @param msg
   * @param e
   */
  public void handleException(String msg, Exception e) {
    view.showError(msg + "\n" + e.getMessage());
  }

}
