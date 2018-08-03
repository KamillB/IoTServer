package com.example.iotserver.main.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.example.iotserver.main.models.wsModels.WsMessage;
import com.google.gson.Gson;

public class MessageEncoder implements Encoder.Text<WsMessage> {
	@Override
	public String encode(WsMessage messageWS) throws EncodeException {
		Gson gson = new Gson();
		return gson.toJson(messageWS);
	}
	
	@Override
	public void init(EndpointConfig ec) {
	
	}
	
	@Override
	public void destroy() {
	
	}
}
