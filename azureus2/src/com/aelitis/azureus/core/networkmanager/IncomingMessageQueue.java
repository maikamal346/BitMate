/*
 * Created on Oct 17, 2004
 * Created by Alon Rohter
 * Copyright (C) 2004, 2005, 2006 Aelitis, All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * 
 * AELITIS, SAS au capital de 46,603.30 euros
 * 8 Allee Lenotre, La Grille Royale, 78600 Le Mesnil le Roi, France.
 *
 */

package com.aelitis.azureus.core.networkmanager;

import java.io.IOException;

import com.aelitis.azureus.core.peermanager.messaging.*;



/**
 * Inbound peer message queue.
 */
public interface IncomingMessageQueue {
  

 
  /**
   * Set the message stream decoder that will be used to decode incoming messages.
   * @param new_stream_decoder to use
   */
  public void setDecoder( MessageStreamDecoder new_stream_decoder );

  
  public MessageStreamDecoder
  getDecoder();
  
  /**
   * Get the percentage of the current message that has already been received.
   * @return percentage complete (0-99), or -1 if no message is currently being received
   */
  public int getPercentDoneOfCurrentMessage();
  
  /**
   * Receive (read) message(s) data from the underlying transport.
   * @param max_bytes to read
   * @return number of bytes received
   * @throws IOException on receive error
   */
  public int receiveFromTransport( int max_bytes ) throws IOException;
 
  
  /**
   * Notifty the queue (and its listeners) of a message received externally on the queue's behalf.
   * @param message received externally
   */
  public void notifyOfExternallyReceivedMessage( Message message );

  
  /**
   * Manually resume processing (reading) incoming messages.
   * NOTE: Allows us to resume docoding externally, in case it was auto-paused internally.
   */
  public void resumeQueueProcessing();
 

  
  /**
   * Add a listener to be notified of queue events.
   * @param listener
   */
  public void registerQueueListener( MessageQueueListener listener );
 
  
  
  /**
   * Cancel queue event notification listener.
   * @param listener
   */
  public void cancelQueueListener( MessageQueueListener listener );

  
  
  
  /**
   * Destroy this queue.
   */
  public void destroy();
  
  /**
   * For notification of queue events.
   */
  public interface MessageQueueListener {
    /**
     * A message has been read from the connection.
     * @param message recevied
     * @return true if this message was accepted, false if not handled
     */
    public boolean messageReceived( Message message );
    
    /**
     * The given number of protocol (overhead) bytes read from the connection.
     * @param byte_count number of protocol bytes
     */
    public void protocolBytesReceived( int byte_count );
    
    /**
     * The given number of (piece) data bytes read from the connection.
     * @param byte_count number of data bytes
     */
    public void dataBytesReceived( int byte_count );
    
    public boolean
    isPriority();
  }
  
  
}
