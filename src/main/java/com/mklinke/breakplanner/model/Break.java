/**
 * Copyright 2011 Martin Klinke
 */
package com.mklinke.breakplanner.model;

import org.joda.time.LocalTime;

/**
 * Represents a break.
 * 
 * @author Martin Klinke
 */
public class Break {

  private String name;

  private LocalTime time;

  /**
   * Creates a new break with the given name.
   * 
   * @param name
   *          the name for the break e.g. "Coffee"
   * @throws IllegalArgumentException
   *           if the supplied name is null, empty or consists only of blanks
   */
  public Break(String name) {
    if (name == null || "".equals(name.trim())) {
      throw new IllegalArgumentException("Invalid name: " + name);
    }
    this.name = name;
  }

  /**
   * @return the name of the break
   */
  public String getName() {
    return name;
  }

  /**
   * @param time
   *          the time for the break
   * @throws IllegalArgumentException
   *           if the time is null or in the past
   */
  public void setTime(LocalTime time) {
    if (time == null || time.isBefore(new LocalTime())) {
      throw new IllegalArgumentException("Invalid time: " + time);
    }
    this.time = time;
  }

  /**
   * @return the time for the break
   */
  public LocalTime getTime() {
    return time;
  }

}
