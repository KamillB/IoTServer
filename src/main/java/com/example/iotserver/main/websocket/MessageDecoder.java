package com.example.iotserver.main.websocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import com.example.iotserver.main.models.MessageWS;
import com.google.gson.Gson;

public class MessageDecoder implements Decoder.Text<MessageWS> {
	@Override
	public MessageWS decode(String jsonMessage) throws DecodeException {
		Gson gson = new Gson();
		MessageWS messageWS = gson.fromJson(jsonMessage, MessageWS.class);
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