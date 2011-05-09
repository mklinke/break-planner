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
package com.mklinke.breakplanner.model;

import java.io.IOException;
import java.util.List;

/**
 * @author Martin Klinke
 * 
 */
public interface BreakRepository {

  /**
   * Adds a new break.
   * 
   * @param newBreak
   *          the new break to add.
   * @throws IOException
   *           if an error occurs while adding the break
   */
  void addBreak(Break newBreak) throws IOException;
  void removeBreak(Break newBreak) throws IOException;
  /**
   * Gets the currently available breaks.
   * 
   * @return the currently available breaks
   */
  List<Break> getBreaks();

  /**
   * Removes breaks that were created by this instance.
   */
  void removeBreaks();

  /**
   * @param remoteBreakListener
   */
  void registerRemoteBreakListener(RemoteBreakListener remoteBreakListener);

  /**
   * Close all connections.
   * @throws IOException 
   */
  void disconnect() throws IOException;

}
