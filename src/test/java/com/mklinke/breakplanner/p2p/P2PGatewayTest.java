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

import static org.junit.Assert.*;

import java.io.IOException;

import javax.jmdns.ServiceInfo;

import org.junit.Test;

/**
 * @author Martin Klinke
 * 
 */
public class P2PGatewayTest {
  @Test
  public void registerAndList() throws IOException {
    P2PGateway server = new P2PGateway();
    server.registerService();
    P2PGateway client = new P2PGateway();
    ServiceInfo[] serviceInfos = client.listServices();
    assertTrue(serviceInfos.length > 0);
    boolean serviceAvailable = false;
    for (int i = 0; i < serviceInfos.length; i++) {
      ServiceInfo serviceInfo = serviceInfos[i];
      if ("BreakPlanner".equals(serviceInfo.getName())) {
        serviceAvailable = true;
        break;
      }
    }
    assertTrue(serviceAvailable);
  }
}
