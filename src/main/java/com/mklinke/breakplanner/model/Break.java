/**
 * Copyright 2011 Martin Klinke
 */
package com.mklinke.breakplanner.model;

/**
 * Represents a break.
 * 
 * @author Martin Klinke
 */
public class Break {

  private String name;

  /**
   * Creates a new break with the given name.
   * @param name the name for the break e.g. "Coffee"
   * @throws IllegalArgumentException if the supplied name is null, empty or consists only of blanks
   */
  public Break(String name) {
    if (name == null || "".equals(name.trim()))
    {
      throw new IllegalArgumentException("Invalid name: "+name);
    }
    this.name = name;
  }

  /**
   * @return the name of the break
   */
  public String getName() {
    return name;
  }
  
}
