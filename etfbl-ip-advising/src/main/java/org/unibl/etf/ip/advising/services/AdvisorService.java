package org.unibl.etf.ip.advising.services;


import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.IOException;
import javax.mail.*;
import javax.mail.internet.*;
import org.unibl.etf.ip.advising.beans.AdvisorBean;
import org.unibl.etf.ip.advising.entities.Advisor;
import org.unibl.etf.ip.advising.utils.ConnectionPool;
import org.unibl.etf.ip.advising.utils.ServiceUtil;


public class AdvisorService {

	public AdvisorService() {
		// TODO Auto-generated constructor stub
	}

	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static String SQL_SELECT_BY_CREDENTIALS = "SELECT * FROM  fitness_online_db.advisor_account where username = ? AND password = ? AND is_admin = false;";
	
	public AdvisorBean login(String username, String password) {
		String passwordHash = hashPassword(password);
		return loginAdvisor(username, passwordHash);
	}
	
	private AdvisorBean loginAdvisor(String username, String password) {
		
		Advisor advisor = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { username, password };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection,
					SQL_SELECT_BY_CREDENTIALS, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				advisor = new Advisor(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("username"), rs.getString("password"), rs.getBoolean("is_admin"));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return mapAdvisorToAdvisorBean(advisor);
	}
	
	private String hashPassword(String password) {
		return password;
	}
	
	private AdvisorBean mapAdvisorToAdvisorBean(Advisor advisor) {
		if(advisor == null) {
			return null;
		}
		
		AdvisorBean advisorBean = new AdvisorBean();
		advisorBean.setFirstName(advisor.getFirstName());
		advisorBean.setLastName(advisor.getLastName());
		advisorBean.setUsername(advisor.getUsername());
		advisorBean.setPassword(advisor.getPassword());
		advisorBean.setLoggedIn(true);
		return advisorBean;
	}
	
	public static void sendMessage(String emailTo, String content) {
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "ivanadodikid@gmail.com";
        String password =  "kymo nddp cqic wrgl";
        
        String fromEmail = "ivanadodikid@gmail.com";
        String toEmail = emailTo;

        String subject = "IP Fitness Online Advisor Response";
        String body = content;

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText(body);
            Thread sendEmailThread = new Thread("Mailing thread") {
                public void run() {
                	try {
						Transport.send(message);
						System.out.println("Email message sent.");
					} catch (MessagingException e) {
						System.err.println("Error sending messsage.");
						e.printStackTrace();
					}
                }
             };

             sendEmailThread.start();       
        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
	
	 public static void sendEmailWithAttachment(String emailTo, String content, File attachedFile, String attachedFileDescription)
	            throws AddressException, MessagingException {
		 String host = "smtp.gmail.com";
	        String port = "587";
	        String username = "ivanadodikid@gmail.com";
	        String password =  "kymo nddp cqic wrgl";
	        
	        String fromEmail = "ivanadodikid@gmail.com";
	        String toEmail = emailTo;

	        String subject = "IP Fitness Online Advisor Response";
	        String body = content;

	        Properties props = new Properties();
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.port", port);
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");

	        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
	                return new javax.mail.PasswordAuthentication(username, password);
	            }
	        });
	        

	        // creates a new e-mail message
	        Message msg = new MimeMessage(session);

	        msg.setFrom(new InternetAddress(username));
	        InternetAddress[] toAddresses = { new InternetAddress(emailTo) };
	        msg.setRecipients(Message.RecipientType.TO, toAddresses);
	        msg.setSubject(subject);
	        msg.setSentDate(new Date());

	        // creates message part
	        MimeBodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setContent(content, "text/html");

	        // creates multi-part
	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(messageBodyPart);

	        // adds attachment
	        if (attachedFile != null && attachedFile.exists()) {
	            MimeBodyPart attachPart = new MimeBodyPart();

	            try {
	                attachPart.attachFile(attachedFile);
	                if (attachedFileDescription != null && !attachedFileDescription.isEmpty()) {
	                    attachPart.setFileName(attachedFileDescription);
	                } else {
	                    attachPart.setFileName(attachedFile.getName());
	                }
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }

	            multipart.addBodyPart(attachPart);
	        }

	        // sets the multi-part as e-mail's content
	        msg.setContent(multipart);
	        
	        try {
	            Transport.send(msg);
	            System.out.println("Email message sent.");
	        } catch (MessagingException e) {
	            // Log the error
	            System.err.println("Error sending email message: " + e.getMessage());
	            // Throw an exception to propagate the error
	            throw new MessagingException("Error sending email message", e);
	        }
	   
	 }
	
	 
	

}
