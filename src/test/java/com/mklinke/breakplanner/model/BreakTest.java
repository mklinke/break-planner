/**
 * Copyright 2011 Martin Klinke
 */
package com.mklinke.breakplanner.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Martin Klinke
 *
 */
public class BreakTest {
  @Test 
  public void NewBreakWithName()
  {
    String name = "Coffee break";
    Break aBreak = new Break(name);
    assertEquals(name, aBreak.getName());
  }
  
  @Test(expected=IllegalArgumentException.class) 
  public void NewBreakNameMustNotBeNull()
  {
    new Break(null);
  }
  
  @Test(expected=IllegalArgumentException.class) 
  public void NewBreakNameMustNotBeEmpty()
  {
    new Break("");
  }
  
  @Test(expected=IllegalArgumentException.class) 
  public void NewBreakNameMustNotBeBlank()
  {
    new Break(" ");
  }
}
