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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

/**
 * @author Martin Klinke
 * 
 */
public class P2PGateway {

  static final String SERVICE_TYPE = "_http._tcp.local.";
  static final int SERVICE_PORT = 4001;

  private JmDNS jmDNS;

  public P2PGateway() throws IOException {
    this(JmDNS.create());
  }

  public P2PGateway(JmDNS jmDNS) throws IOException {
    this.jmDNS = jmDNS;
  }

  public void addServiceListener(ServiceListener serviceListener) {
    jmDNS.addServiceListener(SERVICE_TYPE, serviceListener);
  }

  public ServiceInfo registerService(String name, Map<String, ?> props)
      throws IOException {
    ServiceInfo serviceInfo = ServiceInfo.create(SERVICE_TYPE, name,
        SERVICE_PORT, 0, 0, props);
    jmDNS.registerService(serviceInfo);
    return serviceInfo;
  }

  public List<ServiceInfo> getServices() {
    return Arrays.asList(jmDNS.list(SERVICE_TYPE));
  }

  public ServiceInfo resolve(ServiceInfo serviceInfo) {
    return jmDNS.getServiceInfo(SERVICE_TYPE, serviceInfo.getName());
  }

  /**
   * @param serviceInfo
   */
  public void unregisterService(ServiceInfo serviceInfo) {
    jmDNS.unregisterService(serviceInfo);
  }

  /**
   * @throws IOException
   * 
   */
  public void disconnect() throws IOException {
    jmDNS.close();
  }
}
