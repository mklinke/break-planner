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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Martin Klinke
 * 
 */
public class P2PGatewayTest {

  private JmDNS jmDNS;
  private P2PGateway gateway;

  /*
   * mock tests
   */

  @Before
  public void setUp() throws IOException {
    this.jmDNS = mock(JmDNS.class);
    this.gateway = new P2PGateway(jmDNS);
  }

  @Test
  public void addServiceListener() {
    ServiceListener serviceListener = mock(ServiceListener.class);
    gateway.addServiceListener(serviceListener);
    verify(jmDNS).addServiceListener(P2PGateway.SERVICE_TYPE, serviceListener);
  }

  @Test
  public void registerService() throws IOException {
    Map<String, String> properties = new HashMap<String, String>();
    gateway.registerService("service", properties);
    verify(jmDNS).registerService(any(ServiceInfo.class));
  }

  @Test
  public void getServices() throws IOException {
    ServiceInfo serviceInfo = mock(ServiceInfo.class);
    when(jmDNS.list(anyString())).thenReturn(new ServiceInfo[] { serviceInfo });
    List<ServiceInfo> services = gateway.getServices();
    assertTrue(services.contains(serviceInfo));
    verify(jmDNS).list(P2PGateway.SERVICE_TYPE);
  }

  @Test
  public void resolve() {
    ServiceInfo serviceInfo = mock(ServiceInfo.class);
    gateway.resolve(serviceInfo);
    verify(jmDNS).getServiceInfo(eq(P2PGateway.SERVICE_TYPE), anyString());
    verify(serviceInfo).getName();
  }

  @Test
  public void unregisterService() throws IOException {
    ServiceInfo serviceInfo = mock(ServiceInfo.class);
    gateway.unregisterService(serviceInfo);
    verify(jmDNS).unregisterService(serviceInfo);
  }

  @Test
  public void disconnect() throws IOException {
    gateway.disconnect();
    verify(jmDNS).close();
  }

  // TODO add negative tests

  /*
   * integration tests (real network is used -> tests are ignored by default)
   */
  @Test
  @Ignore
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
  @Ignore
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
