/*
 *  DummyData.java
 *  
 *  @Authors: Niklas Adolfsson and Alexander Persson
 *  @Date: 2016-05-13
 *  
 *  A simple class to get date and formats it
 *  
 */

import java.util.Calendar;

public class DummyDate
{
  public int year;
  public int month;
  public int day;
  public int sec;
  private Calendar now;
  
  public DummyDate()
  { 
    now = Calendar.getInstance();
    year = now.get(Calendar.YEAR);
    month = now.get(Calendar.MONTH)+1;
    day = now.get(Calendar.DAY_OF_MONTH);
    sec = now.get(Calendar.SECOND);
  }
  
  public String toString()
  {
    return Integer.toString(year) + Integer.toString(month) + Integer.toString(day);
  }
  
  public int compareTo(DummyDate d) 
  {
    if ( d.year == this.year && d.month == this.month && d.day == this.day /*&& d.sec == this.sec*/)
    {
      return 1;
    }
    else
    {
      return -1;
    }
  }
  
  public void updateTime()
  {
    year = now.get(Calendar.YEAR);
    month = now.get(Calendar.MONTH)+1;
    day = now.get(Calendar.DAY_OF_MONTH);
    sec = now.get(Calendar.SECOND);
  }
  
}
