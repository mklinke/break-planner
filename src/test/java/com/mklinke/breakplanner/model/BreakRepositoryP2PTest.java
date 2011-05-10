/* 
 ******************************************************************************
 * Copyright Xavo AG 2011
 * All rights reserved
 * Author: Xavo AG, mklinke
 * 
 * $HeadURL$
 * 
 * $Revision$
 * $LastChangedBy$
 * $LastChangedDate$
 ******************************************************************************
 */
package com.mklinke.breakplanner.model;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

import com.mklinke.breakplanner.p2p.P2PGateway;

/**
 * TODO: Description missing
 * @author mklinke
 */
public class BreakRepositoryP2PTest
{
   private BreakRepositoryP2P breakRepository;
   private P2PGateway gateway;

   @Before
   public void setUp()
      throws IOException
   {
      gateway = mock(P2PGateway.class);
      breakRepository = new BreakRepositoryP2P(gateway);
   }

   @Test(expected = IllegalArgumentException.class)
   public void registerRemoteBreakListenerMustNotBeNull()
   {
      breakRepository.registerRemoteBreakListener(null);
   }

   @Test
   public void registerRemoteBreakListenerMustRegisterListenerOnGateway()
   {
      RemoteBreakListener listener = mock(RemoteBreakListener.class);
      breakRepository.registerRemoteBreakListener(listener);
      verify(gateway).addServiceListener(any(ServiceListener.class));
   }

   @Test
   public void disconnectMustCallDisconnectOnGateway()
      throws IOException
   {
      breakRepository.disconnect();
      verify(gateway).disconnect();
   }

   @Test(expected = IllegalArgumentException.class)
   public void cannotAddNullBreak()
      throws IOException
   {
      breakRepository.addBreak(null);
   }

   @Test
   public void addBreakMustRegisterService()
      throws IOException
   {
      Break aBreak = new Break("Coffee Break", new LocalTime());
      breakRepository.addBreak(aBreak);
      verify(gateway).registerService(aBreak.getUniqueName(), aBreak.asMap());
   }

   @Test
   public void removeBreakMustUnregisterService()
      throws IOException
   {
      Break aBreak = new Break("Coffee Break", new LocalTime());
      ServiceInfo serviceInfo = mock(ServiceInfo.class);
      when(serviceInfo.getName()).thenReturn(aBreak.getUniqueName());
      when(gateway.registerService(anyString(), anyMap())).thenReturn(
         serviceInfo);
      breakRepository.addBreak(aBreak);
      breakRepository.removeBreak(aBreak);
      verify(gateway).unregisterService(any(ServiceInfo.class));
   }

   @Test
   public void removeBreakDoesNotUnregisterAnythingOnUnknownBreak()
      throws IOException
   {
      Break aBreak = new Break("Coffee Break", new LocalTime());
      breakRepository.removeBreak(aBreak);
      verify(gateway, never()).unregisterService(any(ServiceInfo.class));
   }
}
