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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.joda.time.LocalTime;
import org.junit.Test;

/**
 * @author Martin Klinke
 * 
 */
public class BreakTest {

  @Test
  public void validBreakName() {
    assertTrue(Break.isBreakName(Break.UNIQUE_NAME_PREFIX + UUID.randomUUID()));
  }

  @Test
  public void completelyInValidBreakName() {
    assertFalse(Break.isBreakName("hrxlgrmph"));
  }

  @Test
  public void inValidBreakNameUUID() {
    assertFalse(Break.isBreakName(Break.UNIQUE_NAME_PREFIX + "hrxlgrmph"));
  }

  @Test
  public void inValidBreakNamePrefix() {
    assertFalse(Break.isBreakName("hrxlgrmph-" + UUID.randomUUID()));
  }

  @Test
  public void newBreakMustHaveUuid() {
    Break aBreak = new Break("Coffee", new LocalTime());
    assertNotNull(aBreak.getUuid());
  }

  @Test
  public void newBreakMustHaveUniqueName() {
    Break aBreak = new Break("Coffee", new LocalTime());
    assertNotNull(aBreak.getUniqueName());
  }

  @Test
  public void newBreakWithDescription() {
    String description = "Coffee";
    Break aBreak = new Break(description, new LocalTime());
    assertEquals(description, aBreak.getDescription());
  }

  @Test(expected = IllegalArgumentException.class)
  public void newBreakDescriptionMustNotBeNull() {
    new Break(null, new LocalTime());
  }

  @Test(expected = IllegalArgumentException.class)
  public void newBreakDescriptionMustNotBeEmpty() {
    new Break("", new LocalTime());
  }

  @Test(expected = IllegalArgumentException.class)
  public void newBreakDescriptionMustNotBeBlank() {
    new Break(" ", new LocalTime());
  }

  @Test
  public void newBreakMustNotBeEditable() {
    assertFalse(new Break("Coffee", new LocalTime()).isEditable());
  }

  @Test(expected = IllegalStateException.class)
  public void cannotChangeReadonlyBreak() {
    new Break("Coffee", new LocalTime()).setDescription("new description");
  }

  @Test
  public void breakSetUuid() {
    Break aBreak = new Break("Coffee", new LocalTime());
    UUID uuid = UUID.randomUUID();
    aBreak.setUuid(uuid);
    assertEquals(uuid, aBreak.getUuid());
  }

  @Test
  public void breakSetTime() {
    Break aBreak = new Break("Coffee", new LocalTime());
    aBreak.setEditable(true);
    LocalTime anHourFromNow = new LocalTime(
        System.currentTimeMillis() + 60 * 60 * 1000);
    aBreak.setTime(anHourFromNow);
    assertEquals(anHourFromNow, aBreak.getTime());
  }

  @Test(expected = IllegalArgumentException.class)
  public void breakTimeMustNotBeNull() {
    Break aBreak = new Break("Coffee", new LocalTime());
    aBreak.setEditable(true);
    aBreak.setTime(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void breakTimeMustNotBeInThePast() {
    Break aBreak = new Break("Coffee", new LocalTime());
    aBreak.setEditable(true);
    LocalTime anHourAgo = new LocalTime(
        System.currentTimeMillis() - 60 * 60 * 1000);
    aBreak.setTime(anHourAgo);
  }
}
