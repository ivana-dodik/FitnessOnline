package org.unibl.etf.ip.advising.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import jakarta.servlet.http.Part;
import org.unibl.etf.ip.advising.services.MessageService;
import org.unibl.etf.ip.advising.entities.Message;
import org.unibl.etf.ip.advising.services.UserService;
import org.unibl.etf.ip.advising.services.AdvisorService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet("/SubmitEmailServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class SubmitEmailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession();
        Message message = (Message) session.getAttribute("message");
        
        if (message == null) {
        	System.out.print("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        }
		
        //String submit = request.getParameter("submit");
        String messageContent = request.getParameter("message-content");
        Part filePart = request.getPart("file");
        String fileDescription = request.getParameter("file-description");
        File f = saveUploadedFile(request);
        // Spremanje privitka na privremenu lokaciju
        /*String fileName = null;
        String filePath = "/etfbl-ip-advising/src/main/webapp/WEB-INF/temporary/";
        if (filePart != null && filePart.getSize() > 0) {
            fileName = getFileName(filePart);
            String applicationPath = getServletContext().getRealPath("");
            String uploadFilePath = applicationPath + File.separator + "uploads";
            File fileSaveDir = new File(uploadFilePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdirs();
            }
            filePath = uploadFilePath + File.separator + fileName;
            filePart.write(filePath);
        } else {
        	System.out.print("NEMA PARTAAAAAAAAAA");
        }*/

        if (messageContent != null) {
            int userId = message.getUserId();
            String email = UserService.getEmailById(userId);
            try {
				AdvisorService.sendEmailWithAttachment(email, messageContent, f, fileDescription);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        // Preusmjeravanje nakon obrade zahtjeva
        response.sendRedirect("message.jsp?id=" + message.getId());
    	
    }
    
    private File saveUploadedFile(HttpServletRequest request)
            throws IllegalStateException, IOException, ServletException {
        File saveFile = null;
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        Collection<Part> multiparts = request.getParts();
        if (multiparts.size() > 0) {
            // Assuming there's only one part, get the first one
            Part part = request.getPart("file"); // "file" is the name of the input file field
            // creates a file to be saved
            String fileName = getFileName(part);
            if (fileName != null && !fileName.equals("")) {
                saveFile = new File(fileName);
                System.out.println("saveFile: " + saveFile.getAbsolutePath());
                FileOutputStream outputStream = new FileOutputStream(saveFile);
                 
                // saves uploaded file
                InputStream inputStream = part.getInputStream();
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();
            }
        }
        return saveFile;
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
}
