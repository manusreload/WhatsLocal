package com.blacklight.whatslocal.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.blacklight.whatslocal.msg.ChatSession;

public class Db {
	private File dbFile;
	private ArrayList<ChatSession> sessions;
	
	public Db(File encryptedDbFile) throws SQLException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException
	{
		String dbPath = encryptedDbFile.getAbsolutePath();
		int slashIndex = dbPath.lastIndexOf("/");
		dbPath = dbPath.substring(0, slashIndex);
		dbFile = new File(dbPath + "/msgstore.db");
		FileOutputStream outStream = new FileOutputStream(dbFile);
		outStream.write(new DbDecrypt(encryptedDbFile).getDbContent());
		outStream.close();
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e)  {}
		
		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dbFile.getAbsolutePath());
		Statement stat = conn.createStatement();
		ResultSet res = stat.executeQuery("SELECT * FROM chat_list");
		sessions = new ArrayList<ChatSession>();
		
		while (res.next())
		{
			String id = res.getString("_id");
			String remoteJid = res.getString("key_remote_jid");
			String from = remoteJid.substring(0, remoteJid.indexOf("@")-1);
			
//			String lastMessage = res.getString("message_table_id");
//			PreparedStatement st = conn.prepareStatement("SELECT timestamp FROM messages WHERE _id=?");
//			st.setInt(1, Integer.parseInt(lastMessage));
//			ResultSet lastTimestampRes = st.executeQuery();
//			
//			if (lastTimestampRes.next())
//			{
//				Calendar cal = Calendar.getInstance();
//				cal.setTimeInMillis(Long.parseLong(lastTimestampRes.getString("timestamp")));
//				Date lastMsgDate = cal.getTime();
//				System.out.println(from + ": " + lastMsgDate);
//				lastTimestampRes.close();
//				
//				sessions.add(new ChatSession(id, from, lastMsgDate));
//			}
			
			ChatSession session = new ChatSession(id, remoteJid, from);
			PreparedStatement st = conn.prepareStatement("SELECT * FROM messages WHERE key_remote_jid=? ORDER BY _id ASC");
			st.setString(1, remoteJid);
			ResultSet messageRes = st.executeQuery();
			
			while (messageRes.next())
			{
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(Long.parseLong(messageRes.getString("timestamp")));
				Date msgDate = cal.getTime();
				
				String msgID = messageRes.getString("_id");
				boolean fromMe = messageRes.getInt("remote_from_me") == 1 ? true : false;
			}
			
			sessions.add(session);
		}
		
		res.close();
		conn.close();
	}
	
	public ChatSession[] getChatSessions()
	{
		return (ChatSession[]) sessions.toArray();
	}
}
