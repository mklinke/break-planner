/**
 *  Copyright 2013 Martin Klinke, http://www.martinklinke.com.
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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mklinke.breakplanner.p2p.P2PGateway;

/**
 * Unit tests for {@link BreakRepositoryP2P}.
 * 
 * @author mklinke
 */
@RunWith(MockitoJUnitRunner.class)
public class BreakRepositoryP2PTest {
  private BreakRepositoryP2P breakRepository;

  @Mock
  P2PGateway gateway;
  @Mock
  RemoteBreakListener listener;
  @Mock
  ServiceInfo serviceInfo;

  @Before
  public void setUp() throws IOException {
    breakRepository = new BreakRepositoryP2P(gateway);
  }

  @Test(expected = IllegalArgumentException.class)
  public void registerRemoteBreakListenerMustNotBeNull() {
    breakRepository.registerRemoteBreakListener(null);
  }

  @Test
  public void registerRemoteBreakListenerMustRegisterListenerOnGateway() {
    breakRepository.registerRemoteBreakListener(listener);
    verify(gateway).addServiceListener(any(ServiceListener.class));
  }

  @Test
  public void disconnectMustCallDisconnectOnGateway() throws IOException {
    breakRepository.disconnect();
    verify(gateway).disconnect();
  }

  @Test(expected = IllegalArgumentException.class)
  public void cannotAddNullBreak() throws IOException {
    breakRepository.addBreak(null);
  }

  @Test
  public void addBreakMustRegisterService() throws IOException {
    Break aBreak = new Break("Coffee Break", new LocalDateTime());
    breakRepository.addBreak(aBreak);
    verify(gateway).registerService(aBreak.getUniqueName(), aBreak.asMap());
  }

  @Test
  @SuppressWarnings("unchecked")
  public void removeBreakMustUnregisterService() throws IOException {
    Break aBreak = new Break("Coffee Break", new LocalDateTime());
    when(serviceInfo.getName()).thenReturn(aBreak.getUniqueName());
    when(gateway.registerService(anyString(), anyMap()))
        .thenReturn(serviceInfo);
    breakRepository.addBreak(aBreak);
    breakRepository.removeBreak(aBreak);
    verify(gateway).unregisterService(any(ServiceInfo.class));
  }

  @Test
  public void removeBreakDoesNotUnregisterAnythingOnUnknownBreak()
      throws IOException {
    Break aBreak = new Break("Coffee Break", new LocalDateTime());
    breakRepository.removeBreak(aBreak);
    verify(gateway, never()).unregisterService(any(ServiceInfo.class));
  }
}
