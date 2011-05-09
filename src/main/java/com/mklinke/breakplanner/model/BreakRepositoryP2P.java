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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

import org.joda.time.LocalTime;

import com.mklinke.breakplanner.p2p.P2PGateway;

/**
 * @author Martin Klinke
 * 
 */
public class BreakRepositoryP2P implements BreakRepository, BreakListener {

  static final String DESCRIPTION = "description";
  static final String TIME = "time";

  private P2PGateway gateway;
  private List<Break> myBreaks = new ArrayList<Break>();
  private List<ServiceInfo> hostedBreaks = new ArrayList<ServiceInfo>();

  public BreakRepositoryP2P() throws IOException {
    gateway = new P2PGateway();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.mklinke.breakplanner.model.BreakRepository#registerRemoteBreakListener
   * (com.mklinke.breakplanner.model.RemoteBreakListener)
   */
  public void registerRemoteBreakListener(
      final RemoteBreakListener remoteBreakListener) {
    gateway.addServiceListener(new ServiceListener() {

      public void serviceResolved(ServiceEvent event) {
        Break newBreak = convert(event.getInfo());
        if (newBreak != null && !myBreaks.contains(newBreak)) {
          remoteBreakListener.remoteBreakAdded(newBreak);
        }
      }

      public void serviceRemoved(ServiceEvent event) {
        Break newBreak = convert(event.getInfo());
        if (newBreak != null) {
          remoteBreakListener.remoteBreakRemoved(newBreak);
        }
      }

      public void serviceAdded(ServiceEvent event) {
        // we don't know enough about the service here yet -> serviceResolved
      }
    });
  }

  /* (non-Javadoc)
   * @see com.mklinke.breakplanner.model.BreakRepository#disconnect()
   */
  public void disconnect() throws IOException {
    gateway.disconnect(); 
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see com.mklinke.breakplanner.model.BreakRepository#addBreak(com.mklinke.
   * breakplanner.model.Break)
   */
  public void addBreak(Break newBreak) throws IOException {
    myBreaks.add(newBreak);
    Map<String, String> properties = new HashMap<String, String>();
    properties.put(DESCRIPTION, newBreak.getDescription());
    properties.put(TIME, new Long(newBreak.getTime().toDateTimeToday()
        .getMillis()).toString());
    hostedBreaks.add(gateway.registerService(newBreak.getUniqueName(),
        properties));
  }

  /* (non-Javadoc)
   * @see com.mklinke.breakplanner.model.BreakRepository#removeBreak(com.mklinke.breakplanner.model.Break)
   */
  public void removeBreak(Break aBreak) throws IOException {
    for (ServiceInfo serviceInfo : hostedBreaks) {
      if(serviceInfo.getName().equals(aBreak.getUniqueName())){
        gateway.unregisterService(serviceInfo);
        hostedBreaks.remove(serviceInfo);
        break;
      }
    }
    myBreaks.remove(aBreak);
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see com.mklinke.breakplanner.model.BreakRepository#getBreaks()
   */
  public List<Break> getBreaks() {
    List<ServiceInfo> serviceInfos = gateway.getServices();
    List<Break> breaks = new ArrayList<Break>();
    for (ServiceInfo serviceInfo : serviceInfos) {
      if (!Break.isBreakName(serviceInfo.getName())) {
        continue;
      }
      serviceInfo = gateway.resolve(serviceInfo);
      Break newBreak = convert(serviceInfo);
      if (newBreak != null) {
        breaks.add(newBreak);
      }
    }
    return breaks;
  }

  private Break convert(ServiceInfo serviceInfo) {
    Break newBreak = null;
    if (Break.isBreakName(serviceInfo.getName())) {
      Long time = Long.parseLong(serviceInfo.getPropertyString(TIME));
      newBreak = new Break(serviceInfo.getPropertyString(DESCRIPTION),
          new LocalTime(time));
      newBreak.setUuid(Break.getUuidFromName(serviceInfo.getName()));
    }
    return newBreak;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.mklinke.breakplanner.model.BreakRepository#removeBreaks()
   */
  public void removeBreaks() {
    for (ServiceInfo serviceInfo : hostedBreaks) {
      gateway.unregisterService(serviceInfo);
    }
  }
}
