package com.gmail.stels.sbal.servlets;

import com.gmail.stels.sbal.MessageList;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetListServlet extends MyServlet {
	
	private MessageList msgList = MessageList.getInstance();

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String fromStr = req.getParameter("from");
		int from = 0;
		try {
			from = Integer.parseInt(fromStr);
			if (from < 0) from = 0;
		} catch (Exception ex) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
		}
		String toStr = req.getParameter("to");
		String json;
		if(toStr != null){
			json = msgList.toJSON(from, toStr);
		}
		else {
			json = msgList.toJSON(from);
		}
		doGetSendJson(resp, json);
	}
}
