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
package com.mklinke.breakplanner.p2p;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.jmdns.ServiceInfo;

import org.junit.Test;

/**
 * @author Martin Klinke
 * 
 */
public class P2PGatewayTest {

  @Test
  public void registerAndGetService() throws IOException {
    P2PGateway server = new P2PGateway();
    String name = "BreakPlanner-" + UUID.randomUUID().toString();
    Map<String, String> properties = new HashMap<String, String>();
    server.registerService(name, properties);
    P2PGateway client = new P2PGateway();
    List<ServiceInfo> serviceInfos = client.getServices();
    assertNotNull(serviceInfos);
    assertTrue(serviceInfos.size() > 0);
    boolean serviceDetected = false;
    for (ServiceInfo serviceInfo : serviceInfos) {
      if (name.equals(serviceInfo.getName())) {
        serviceDetected = true;
        break;
      }
    }
    assertTrue(serviceDetected);
  }

  @Test
  public void getServiceDescription() throws IOException {
    P2PGateway server = new P2PGateway();
    String name = "BreakPlanner-" + UUID.randomUUID().toString();
    String description = "Coffee Break";
    Map<String, String> properties = new HashMap<String, String>();
    String descriptionKey = "description";
    properties.put(descriptionKey, description);
    server.registerService(name, properties);
    P2PGateway client = new P2PGateway();
    List<ServiceInfo> serviceInfos = client.getServices();
    boolean descriptionFound = false;
    for (ServiceInfo serviceInfo : serviceInfos) {
      if (name.equals(serviceInfo.getName())) {
        String propertyString = serviceInfo.getPropertyString(descriptionKey);
        if (description.equals(propertyString)) {
          descriptionFound = true;
          break;
        }
      }
    }
    assertTrue(descriptionFound);
  }
}
