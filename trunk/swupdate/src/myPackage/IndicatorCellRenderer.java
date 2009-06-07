package myPackage;
import java.awt.Color;
import java.awt.Component;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.sun.jmx.snmp.Enumerated;



public class IndicatorCellRenderer extends JProgressBar implements TableCellRenderer {
  private Hashtable limitColors;
  private int[] limitValues;
  private JTable myTable = null;
  
  public IndicatorCellRenderer() {
    super(JProgressBar.HORIZONTAL);
    setBorderPainted(true);
  }

  void setTable(JTable thetable)
  {
	  myTable = thetable;
  }
  
  public IndicatorCellRenderer(int min, int max) {
    super(JProgressBar.HORIZONTAL, min, max);
    setBorderPainted(true);
  }
  public boolean isDisplayable() {
	// This does the trick. It makes sure animation is always performed
	return true;
	} 

  public Component getTableCellRendererComponent(JTable table, Object value,
                   boolean isSelected, boolean hasFocus, int row, int column) {
    int n = 0;
    if (! (value instanceof Number)) {
      String str;
      if (value instanceof String) {
        str = (String)value;
      } else {
    	  try{
        str = value.toString();
    	  }
    	  catch(Exception e)
    	  {
    		str = "0";  
    	  }
      }
      try {
        n = Integer.valueOf(str).intValue();
      } catch (NumberFormatException ex) {
      }
    } else {
      n = ((Number)value).intValue();
    }
    Color color = getColor(n);
    if (color != null) {
      setForeground(color);
    }
    setValue(n);
    
    myTable.repaint();
    return this;
  }

  
  public void setLimits(Hashtable limitColors) {
    this.limitColors = limitColors;
    int i=0;
    int n = limitColors.size();
    limitValues = new int[n];
    Enumeration<Integer> enume = limitColors.keys();
    while (enume.hasMoreElements()) {
      limitValues[i++] = ((Integer)enume.nextElement()).intValue();
    }
    sort(limitValues);
  }

  private Color getColor(int value) {
    Color color = null;
    if (limitValues != null) {
      int i;
      for (i=0;i<limitValues.length;i++) {
        if (limitValues[i] < value) {
          color = (Color)limitColors.get(new Integer(limitValues[i]));
        }
      }
    }
    return color;
  }

  private void sort(int[] a) {
    int n = a.length;
    for (int i=0; i<n-1; i++) {
      int k = i;
      for (int j=i+1; j<n; j++) {
        if (a[j] < a[k]) {
          k = j;
        }
      }
      int tmp = a[i];
      a[i] = a[k];
      a[k] = tmp;
    }
  }
}
