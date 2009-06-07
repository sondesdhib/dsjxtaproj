package myPackage;

import java.awt.Component;

import javax.swing.BoundedRangeModel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

class ProgressBarRenderer extends JProgressBar implements TableCellRenderer
{
	public ProgressBarRenderer()
	{
		super();
	}

	public ProgressBarRenderer(BoundedRangeModel newModel)
	{
		super(newModel);
	}

	public ProgressBarRenderer(int orient)
	{
		super(orient);
	}

	public ProgressBarRenderer(int min, int max)
	{
		super(min, max);
	}

	public ProgressBarRenderer(int orient, int min, int max)
	{
		super(orient, min, max);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column)
	{
		setValue((int) ((Float) value).floatValue());
		return this;
	}
}