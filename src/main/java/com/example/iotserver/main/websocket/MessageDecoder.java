package com.example.iotserver.main.websocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.example.iotserver.main.models.wsModels.WsMessage;
import com.google.gson.Gson;

public class MessageDecoder implements Decoder.Text<WsMessage> {
	@Override
	public WsMessage decode(String jsonMessage) throws DecodeException {
		Gson gson = new Gson();
		WsMessage messageWS = gson.fromJson(jsonMessage, WsMessage.class);
		return messageWS;
	}
	
	@Override
	public boolean willDecode(String jsonMessage) {
		try {
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	@Override
	public void init(EndpointConfig ec) {}

	@Override
	public void destroy() {}
}