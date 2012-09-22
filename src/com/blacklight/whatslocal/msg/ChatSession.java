package com.blacklight.whatslocal.msg;

import java.util.ArrayList;

public class ChatSession {
	private String id;
	private String remoteJid;
	private String contact;
	private ArrayList<Message> messages;

	public ChatSession(String id, String remoteJid, String contact)
	{
		this.id = id;
		this.contact = contact;
		this.remoteJid = remoteJid;
		messages = new ArrayList<Message>();
	}
	
	public String getID()
	{
		return id;
	}
	
	public String getContact()
	{
		return contact;
	}
	
	public String getRemoteJid()
	{
		return remoteJid;
	}
	
	public Message[] getMessages()
	{
		return (Message[]) messages.toArray();
	}
}
