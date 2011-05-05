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

import static org.junit.Assert.assertEquals;

import org.joda.time.LocalTime;
import org.junit.Test;

/**
 * @author Martin Klinke
 * 
 */
public class BreakTest {
  @Test
  public void NewBreakWithName() {
    String name = "Coffee";
    Break aBreak = new Break(name);
    assertEquals(name, aBreak.getName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void NewBreakNameMustNotBeNull() {
    new Break(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void NewBreakNameMustNotBeEmpty() {
    new Break("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void NewBreakNameMustNotBeBlank() {
    new Break(" ");
  }

  @Test
  public void BreakSetTime() {
    Break aBreak = new Break("Coffee");
    LocalTime anHourFromNow = new LocalTime(System.currentTimeMillis() + 60 * 60 * 1000);
    aBreak.setTime(anHourFromNow);
    assertEquals(anHourFromNow, aBreak.getTime());
  }

  @Test(expected=IllegalArgumentException.class)
  public void BreakTimeMustNotBeNull() {
    Break aBreak = new Break("Coffee");
    aBreak.setTime(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void BreakTimeMustNotBeInThePast() {
    Break aBreak = new Break("Coffee");
    LocalTime anHourAgo = new LocalTime(System.currentTimeMillis() - 60 * 60 * 1000);
    aBreak.setTime(anHourAgo);
  }
}
