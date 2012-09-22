package com.blacklight.whatslocal.main;

import java.io.File;

import com.blacklight.whatslocal.db.Db;

public class Main {
	public static void main(String[] args)
	{
		try
		{
			Db appDb = new Db(new File("/home/blacklight/whatsapp/msgstore.db.crypt"));
			appDb.getChatSessions();
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
