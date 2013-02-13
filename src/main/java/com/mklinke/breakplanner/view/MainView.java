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
package com.mklinke.breakplanner.view;

import java.util.UUID;

import com.mklinke.breakplanner.model.Break;

/**
 * The main view interface.
 * 
 * @author Martin Klinke
 */
public interface MainView {

  /**
   * @param message
   *          the status message to set
   */
  void setStatus(String message);

  /**
   * Display the view.
   */
  void show();

  /**
   * @param exitListener
   *          the listener to notify about application exit
   */
  void registerExitListener(ExitListener exitListener);

  /**
   * @param newBreakListener
   *          the listener to notify about break creation
   */
  void registerNewBreakListener(NewBreakListener newBreakListener);

  void registerRemoveBreakListener(RemoveBreakListener removeBreakListener);

  /**
   * @return
   */
  boolean confirmExit();

  /**
   * @param aBreak
   */
  void addBreak(Break aBreak);

  /**
   * @param string
   */
  void showError(String string);

  /**
   * @param removedBreakUUID
   * @return the removed break instance
   */
  Break removeBreak(UUID removedBreakUUID);

}
