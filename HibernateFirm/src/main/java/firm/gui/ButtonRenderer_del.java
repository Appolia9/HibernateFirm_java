package firm.gui;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import java.awt.*;

public class ButtonRenderer_del extends JButton implements TableCellRenderer{
	
	public ButtonRenderer_del() {
		//setText("Delete");
		setOpaque(true);
		setBorderPainted(false);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		setText((value == null) ? "Delete": value.toString());
		return this;
	}
}
