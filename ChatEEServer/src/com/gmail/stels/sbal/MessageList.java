package com.gmail.stels.sbal;

import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageList implements Jsonable{
	private static final MessageList msgList = new MessageList();

    private final Gson gson;
	private final List<Message> list = new LinkedList<>();

	public static MessageList getInstance() {
		return msgList;
	}

  	private MessageList() {
		gson = new GsonBuilder().create();
	}

	private synchronized List<Message> getListByReceiver(String receiver){
		List<Message> list = new LinkedList<>();
		for(Message message: this.list){
			if(receiver.equals(message.getTo())){
				list.add(message);
			}
		}
		return list;
	}

	private synchronized List<Message> getListGlobal(){
		List<Message> list = new LinkedList<>();
		for(Message message: this.list){
			if(message.getTo() == null){
				list.add(message);
			}
		}
		return list;
	}

	public synchronized void add(Message m) {
		list.add(m);
	}

	public synchronized String toJSON(int n) {
		List<Message> list = getListGlobal();
		if (n >= list.size()) return null;
		return gson.toJson(new JsonMessages(list, n));
	}

	public synchronized String toJSON(int n, String receiver) {
		List<Message> list = getListByReceiver(receiver);
		if (n >= list.size()) return null;
		return gson.toJson(new JsonMessages(list, n));
	}

	@Override
	public synchronized String toJSON() {
		return this.toJSON(0);
	}
}
