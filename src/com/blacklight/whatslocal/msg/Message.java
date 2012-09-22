package com.blacklight.whatslocal.msg;

import java.util.Date;

public class Message {
	private String id;
	private ChatSession session;
	private boolean fromMe;
	private Date date;
	private String content;
	
	public Message(ChatSession session, boolean fromMe, Date date, String content)
	{
		this.session = session;
		this.fromMe = fromMe;
		this.date = date;
		this.content = content;
	}

	public String getID()
	{
		return id;
	}
	
	public boolean getFromMe()
	{
		return fromMe;
	}
	
	public ChatSession getSession() {
		return session;
	}

	public Date getDate() {
		return date;
	}

	public String getContent() {
		return content;
	}
}
