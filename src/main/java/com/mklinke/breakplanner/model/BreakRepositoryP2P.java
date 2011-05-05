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

import java.io.IOException;

import javax.jmdns.ServiceInfo;

import com.mklinke.breakplanner.p2p.P2PGateway;

/**
 * @author Martin Klinke
 * 
 */
public class BreakRepositoryP2P implements BreakRepository {

  private P2PGateway gateway;

  public BreakRepositoryP2P() throws IOException {
    gateway = new P2PGateway();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.mklinke.breakplanner.model.BreakRepository#connect()
   */
  public void connect() throws IOException {
    ServiceInfo[] serviceInfos = gateway.listServices();
    boolean masterDetected = false;
    for (ServiceInfo serviceInfo : serviceInfos) {
      if ("BreakPlanner".equals(serviceInfo.getName())) {
        masterDetected = true;
      }
    }
    if(!masterDetected)
    {
      gateway.registerService();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.mklinke.breakplanner.model.BreakRepository#getBreaks()
   */
  public Break[] getBreaks() {
    // TODO Auto-generated method stub
    return null;
  }

}
