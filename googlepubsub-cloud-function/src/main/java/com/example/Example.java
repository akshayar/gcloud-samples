package com.example;

import com.example.Example.PubSubMessage;
import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Logger;

public class Example implements BackgroundFunction<PubSubMessage> {
  private static final Logger logger = Logger.getLogger(Example.class.getName());
  public String initTime= getCurrentTime();

  public void BackgroundFunction(){
    logger.info("Initializing" + initTime);
  }

  private String getCurrentTime(){
    return System.currentTimeMillis()+"";
  }

  @Override
  public void accept(PubSubMessage message, Context context) {
    String data = message.data != null
      ? new String(Base64.getDecoder().decode(message.data))
      : "Hello, World";
    logger.info(data+", initTime="+initTime);
  }

  public static class PubSubMessage {
    String data;
    Map<String, String> attributes;
    String messageId;
    String publishTime;
  }
}
