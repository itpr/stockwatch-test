
package com.stock.server;

import java.util.logging.Logger;

import javax.servlet.ServletContext;

public class Message {

  private static final Logger log = Logger.getLogger(Message.class.getName());

  private final ServletContext context;

  String recipient;

  String message;

  public Message(ServletContext context) {
    this.context = context;
  }

  public String getRecipient() {
    return recipient;
  }

  public String getMessage() {
    return message;
  }

  public String send() {
    log.info("send " + this);
    try {
      return SendMessage.sendMessage(context, recipient, message);
    } catch (Exception e) {
      return "Failure: Got exception in send: " + e.getMessage();
    }
  }

  public void setRecipient(String recipient) {
    this.recipient = recipient;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "Message [recipient=" + recipient + ", message=" + message + "]";
  }
}
