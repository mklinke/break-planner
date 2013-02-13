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
package com.mklinke.breakplanner.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

/**
 * Represents a break.
 * 
 * @author Martin Klinke
 */
public class Break {

  static final String UNIQUE_NAME_PREFIX = "BreakPlanner-";
  static final String DESCRIPTION = "description";
  static final String TIME = "time";

  /**
   * Checks whether the given name is a valid
   * 
   * @param name
   * @return
   */
  public static boolean isBreakName(String name) {
    if (name == null || !name.startsWith(UNIQUE_NAME_PREFIX)) {
      return false;
    }
    try {
      getUuidFromName(name);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  public static UUID getUuidFromName(String name) {
    return UUID.fromString(name.substring(UNIQUE_NAME_PREFIX.length()));
  }

  public static Break fromMap(String name, Map<String, String> properties) {
    Long time = Long.parseLong(properties.get(TIME));
    Break newBreak = new Break(properties.get(DESCRIPTION), new DateTime(
        time.longValue()).toLocalDateTime());
    newBreak.setUuid(Break.getUuidFromName(name));
    return newBreak;
  }

  private UUID uuid;
  private boolean editable;
  private String description;

  private LocalDateTime time;

  /**
   * Creates a new break with the given description.
   * 
   * @param description
   *          the description for the break e.g. "Coffee"
   * @throws IllegalArgumentException
   *           if the supplied description is null, empty or consists only of
   *           blanks
   */
  public Break(String description, LocalDateTime time) {
    if (description == null || "".equals(description.trim())) {
      throw new IllegalArgumentException("Invalid description: " + description);
    }
    this.uuid = UUID.randomUUID();
    this.description = description;
    setTime(time);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Break other = (Break) obj;
    if (uuid == null) {
      if (other.uuid != null) {
        return false;
      }
    } else if (!uuid.equals(other.uuid)) {
      return false;
    }
    return true;
  }

  /**
   * @param uuid
   *          the uuid to set
   */
  public void setUuid(UUID uuid) {
    if (uuid == null) {
      throw new IllegalArgumentException("uuid must not be null");
    }
    this.uuid = uuid;
  }

  /**
   * @return the uuid
   */
  public UUID getUuid() {
    return uuid;
  }

  /**
   * @return the editable
   */
  public boolean isEditable() {
    return editable;
  }

  /**
   * @param editable
   *          the editable to set
   */
  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  /**
   * @param description
   *          the description to set
   */
  public void setDescription(String description) {
    if (!isEditable()) {
      throw new IllegalStateException(
          "Break is not editable, cannot change description");
    }
    this.description = description;
  }

  /**
   * @return the unique name of the break
   */
  public String getUniqueName() {
    return UNIQUE_NAME_PREFIX + uuid;
  }

  /**
   * @return the description of the break
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param time
   *          the time for the break
   * @throws IllegalArgumentException
   *           if the time is null or in the past
   */
  public void setTime(LocalDateTime time) {
    LocalDateTime aMinuteAgo = new LocalDateTime().minusMinutes(1);
    if (time == null || time.isBefore(aMinuteAgo)) {
      throw new IllegalArgumentException("Invalid time: " + time);
    }
    this.time = time;
  }

  /**
   * @return the time for the break
   */
  public LocalDateTime getTime() {
    return time;
  }

  public Map<String, String> asMap() {
    Map<String, String> properties = new HashMap<String, String>();
    properties.put(DESCRIPTION, this.getDescription());
    properties.put(TIME,
        new Long(this.getTime().toDateTime().getMillis()).toString());
    return properties;
  }
}
