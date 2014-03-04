import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class Tabel2 extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel thePanel;
	public static JTable theTable;
	public static LocalTableModel theTableModel;
	private AbstractAction theRemoveRowAction;

	private static int actief, cvar;
	private JToolBar toolBar;
	public static ArrayList<Reis> theRows = ReisBeheer.reizen;
	private static ArrayList<Reis> sRows = new ArrayList<Reis>();

	private class Row {
		private int ID;
		private String bestemming;
		private String vertrek;
		private String tijd;
		private int pers;

		public Row(int aID, String aBestemming, String aVertrek, String aTijd,
				int aPers) {
			ID = aID;
			bestemming = aBestemming;
			vertrek = aVertrek;
			tijd = aTijd;
			pers = aPers;
		}

		public int getID() {
			return ID;
		}

		public String getBest() {
			return bestemming;
		}

		public String getVertrek() {
			return vertrek;
		}

		public String getTijd() {
			return tijd;
		}

		public int getPers() {
			return pers;
		}
	}

	public class LocalTableModel extends AbstractTableModel {
		public String getColumnName(int column) {
			if (column == 0) {
				return "Status";
			}
			if (column == 1) {
				return "Vertrek Punt";
			}
			if (column == 2) {
				return "Bestemming";
			}
			if (column == 3) {
				return "Vertrek Tijd";
			} 
			if (column == 4){
				return "Personen";
			}
			if (column == 5){
				return "Wachttijd";
			}else{
				return "Reistijd";
			}
		}

		public Class getColumnClass(int columnIndex) {
			if (columnIndex == 0) {
				return Integer.class;
			}
			if (columnIndex == 1) {
				return String.class;
			}
			if (columnIndex == 2) {
				return String.class;
			}
			if (columnIndex == 3) {
				return String.class;
			}
			if (columnIndex == 3) {
				return Integer.class;
			}
			if (columnIndex == 3) {
				return Integer.class;
			}else {
				return Integer.class;
			}
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

		public int getRowCount() {
			return sRows.size();
		}

		public int getColumnCount() {
			return 7;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			Reis row = sRows.get(rowIndex);
			if (columnIndex == 0) {
				int st = row.getStatus();
				if (st == 0) {
					return ("Gereserveerd");
				} else if (st == 1) {
					return ("Wachtend");
				} else if (st == 2) {
					return ("Reizend");
				} else if (st == 3) {
					return ("Beëindigd");
				}
			}
			if (columnIndex == 1) {
				return ("Station " + (row.getStart().id));
			}
			if (columnIndex == 2) {
				return ("Station " + (row.getBestemming().id));
			}
			if (columnIndex == 3) {
				if (row.getStartTijd().equals("99:99")){
					return ("Direct Vertrekken");
				}else{
					return ("" + row.getStartTijd());
				}
			}
			if (columnIndex == 4) {
				return ("" + row.getAantal());
			}
			if (columnIndex == 5) {
				return ("" + row.getWachtTijd());
			}else {
				return ("" + row.getReistijd());
			}
		}
	}

	public Tabel2() {
		setLayout(null);
		actief = 0;
		thePanel = new JPanel(new BorderLayout());
		// thePanel.setLayout(null);

		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.add(new AbstractAction("Alle reizen") {
			public void actionPerformed(ActionEvent e) {
				loadAll();
			}
		});
		theRemoveRowAction = new AbstractAction("Actieve Reizen") {
			public void actionPerformed(ActionEvent e) {
				loadAct();
			}
		};
		toolBar.add(theRemoveRowAction);
		toolBar.add(new AbstractAction("Afgehandelde reizen") {
			public void actionPerformed(ActionEvent e) {
				loadAf();
			}
		});
		thePanel.add(toolBar, BorderLayout.NORTH);

		theTableModel = new LocalTableModel();
		theTable = new JTable(theTableModel) {
			public void refresh() {
				repaint();
			}
		};
		theTable.setAutoCreateRowSorter(true);

		thePanel.add(new JScrollPane(theTable), BorderLayout.CENTER);
		// validateSelection();
		// loadAll();
		reload();
		thePanel.setBounds(0, 0, 900, 230);
		add(thePanel);
	}

	public void removeRow() {
		int index = theTable.getSelectedRow();
		if (index == -1)
			return;
		ReisBeheer.treizen.remove(index);
		theTableModel.fireTableRowsDeleted(index, index);
		index = (ReisBeheer.treizen.size() - 1);
		if (index >= 0) {
			theTable.getSelectionModel().setSelectionInterval(index, index);
		}
	}

	// Herlaad de lijst met reizen die moeten worden weergegeven
	public static void reload() {
		sRows.clear();
		if (actief == 1) {// Inactieve reizen
			for (Reis reis : theRows) {
				if (reis.getStatus() == 3)
					sRows.add(reis);
			}
		} else if (actief == 2) {// Actieve reizen
			for (Reis reis : theRows) {
				if (reis.getStatus() < 3)
					sRows.add(reis);
			}
		} else {// Alle reizen
			for (Reis reis : theRows) {
				sRows.add(reis);
			}
		}

		refresh();
	}

	public void loadAll() {
		actief = 0;
		reload();
	}

	public void loadAf() {
		actief = 1;
		reload();
	}

	public void loadAct() {
		actief = 2;
		reload();
	}

	// Vernieuw de tabel
	public static void refresh() {
		System.out.println("Refresh||Rows: " + theTableModel.getRowCount() + "|| LSize" + sRows.size());
		if (theTableModel.getRowCount() > 0) {
			cvar = 1;
			theTableModel.fireTableRowsInserted(0,
					theTableModel.getRowCount() - 1);
		} else {
			if (cvar == 1)
				theTableModel.fireTableRowsDeleted(0, 0);
			cvar = 0;
		}
	}

}
