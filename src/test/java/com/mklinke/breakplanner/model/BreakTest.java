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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.UUID;

import org.joda.time.LocalDateTime;
import org.junit.Test;

/**
 * @author Martin Klinke
 * 
 */
public class BreakTest {

  /*
   * static tests
   */

  @Test
  public void validBreakName() {
    assertTrue(Break.isBreakName(Break.UNIQUE_NAME_PREFIX + UUID.randomUUID()));
  }

  @Test
  public void nullBreakName() {
    assertFalse(Break.isBreakName(null));
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
  public void breakFromMap() {
    String name = Break.UNIQUE_NAME_PREFIX + UUID.randomUUID();
    HashMap<String, String> properties = new HashMap<String, String>();
    String description = "Coffee Break";
    properties.put(Break.DESCRIPTION, description);
    LocalDateTime time = new LocalDateTime();
    properties.put(Break.TIME, new Long(time.toDateTime().getMillis()).toString());
    Break aBreak = Break.fromMap(name,
        properties);
    assertNotNull(aBreak);
    assertEquals(name, aBreak.getUniqueName());
    assertEquals(description, aBreak.getDescription());
    assertEquals(time, aBreak.getTime());
  }

  /*
   * instance tests
   */

  @Test
  public void newBreakMustHaveUuid() {
    Break aBreak = new Break("Coffee", new LocalDateTime());
    assertNotNull(aBreak.getUuid());
  }

  @Test
  public void newBreakMustHaveUniqueName() {
    Break aBreak = new Break("Coffee", new LocalDateTime());
    assertNotNull(aBreak.getUniqueName());
  }

  @Test
  public void newBreakWithDescription() {
    String description = "Coffee";
    Break aBreak = new Break(description, new LocalDateTime());
    assertEquals(description, aBreak.getDescription());
  }

  @Test(expected = IllegalArgumentException.class)
  public void newBreakDescriptionMustNotBeNull() {
    new Break(null, new LocalDateTime());
  }

  @Test(expected = IllegalArgumentException.class)
  public void newBreakDescriptionMustNotBeEmpty() {
    new Break("", new LocalDateTime());
  }

  @Test(expected = IllegalArgumentException.class)
  public void newBreakDescriptionMustNotBeBlank() {
    new Break(" ", new LocalDateTime());
  }

  @Test
  public void newBreakMustNotBeEditable() {
    assertFalse(new Break("Coffee", new LocalDateTime()).isEditable());
  }

  @Test(expected = IllegalStateException.class)
  public void cannotChangeReadonlyBreak() {
    new Break("Coffee", new LocalDateTime()).setDescription("new description");
  }

  @Test
  public void breakMustAllowChangeOfUuid() {
    Break aBreak = new Break("Coffee", new LocalDateTime());
    aBreak.setEditable(true);
    aBreak.setDescription("new description");
  }

  @Test
  public void editableBreakMustAllowChangeOfDescription() {
    Break aBreak = new Break("Coffee", new LocalDateTime());
    aBreak.setEditable(true);
    aBreak.setDescription("new description");
  }

  @Test
  public void breakSetUuid() {
    Break aBreak = new Break("Coffee", new LocalDateTime());
    UUID uuid = UUID.randomUUID();
    aBreak.setUuid(uuid);
    assertEquals(uuid, aBreak.getUuid());
  }

  @Test(expected = IllegalArgumentException.class)
  public void breakSetNullUuid() {
    Break aBreak = new Break("Coffee", new LocalDateTime());
    aBreak.setUuid(null);
  }

  @Test
  public void breakSetTime() {
    Break aBreak = new Break("Coffee", new LocalDateTime());
    aBreak.setEditable(true);
    LocalDateTime anHourFromNow = new LocalDateTime().plusHours(1);
    aBreak.setTime(anHourFromNow);
    assertEquals(anHourFromNow, aBreak.getTime());
  }

  @Test(expected = IllegalArgumentException.class)
  public void breakTimeMustNotBeNull() {
    Break aBreak = new Break("Coffee", new LocalDateTime());
    aBreak.setEditable(true);
    aBreak.setTime(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void breakTimeMustNotBeInThePast() {
    Break aBreak = new Break("Coffee", new LocalDateTime());
    aBreak.setEditable(true);
    LocalDateTime anHourAgo = new LocalDateTime().minusMinutes(2);
    aBreak.setTime(anHourAgo);
  }
}
