package gui;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gui.DataLoader.DataType;
import kernel.FakeMemory;
import main.Clock;
import main.MainDriver;

public class DataLoader {
	
	public static UserWindow parent;
//	public static DataLoader dataLoader;
	public static Data data;
	
	public enum DataType {
		INSTR_QUEUE,
		RUNNING_STATE,
		LOAD_QUEUE,
		STORE_QUEUE,
		RESERV_STARION,
		MEM,
		FU,
		RU,
		CLOCK
	}
	
	public static Data get_data() {
		if (data == null)
			data = new Data();
		return data;
	}
	
	public DataLoader(UserWindow _parent) {
		parent = _parent;
		
	}
	
	public void update_by_data(DataType type, ArrayList<ArrayList<String>> data) {
		switch (type) {
		case INSTR_QUEUE:
			update_table(parent.table_instrqueue, data);
			break;
		case RUNNING_STATE:
			update_table(parent.table_state, data);
			break;
//		case LOAD_QUEUE:
//			update_table(parent.table_loadqueue, data);
//			break;
//		case STORE_QUEUE:
//			update_table(parent.table_storequeue, data);
//			break;
		case MEM:
			update_table(parent.table_mem, data);
			break;
		case RESERV_STARION:
			update_table(parent.table_station, data);
			break;
		case FU:
			update_table(parent.table_fu, data);
			break;
		case RU:
//			update_table(parent.table_ru, data);
			break;
		default:
			break;
		}
		
	}
	
	public void update_by_data(DataType type, int data) {
		if (type != DataType.CLOCK){
			System.out.println("!!!!!!!!!!!!!!!!!!!!bug");
		}
		parent.label_clock.setText(""+data);
		
	}
	
	public void update_table(JTable jTable, ArrayList<ArrayList<String>> info) {
		try {
			Object[][] table_data = change(info);
		    DefaultTableModel model = (DefaultTableModel) jTable.getModel();
		    
		    if (table_data == null)
		    	System.out.println("!@#!@#!@#!@#!@#!@#");
		    
		    int row = table_data.length;
		    int col = table_data.length > 0 ? table_data[0].length : 0;
		    int old_row = model.getRowCount();
		    for (int i = 0; i < old_row; i++)
		    	model.removeRow(0);
		    
		    if (jTable == parent.table_mem) {
		    	Object[] mem_name = new Object[col];
		    	for (int i = 0; i < col; i++) 
		    		mem_name[i] = info.get(0).get(i);
			    model.setColumnIdentifiers(mem_name);
			    for (int i = 1; i < row; i++) 
			    	model.addRow(table_data[i]);
		    } else {
			    for (int i = 0; i < row; i++) 
			    	model.addRow(table_data[i]);
		    }
		    
		    model.fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object[][] change(ArrayList<ArrayList<String>> list) {
		if (list == null)
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		int row = list.size();
//		int col = 30;
		
		Object[][] ret = new Object[row][];
		for (int i = 0; i < list.size(); ++i) {
			ArrayList<String> instr = list.get(i);
			ret[i] = new String[instr.size()];
			for (int j = 0; j < instr.size(); ++j) {
				ret[i][j] = instr.get(j);
			}
		}
		
		return ret;
	}
	
	
	public static Data create_tmp_data(int k) {
		Data data = new Data();
		for (int i = 0; i < 30; i++) {
			ArrayList<String> tmp = new ArrayList<String>();
			for (int j = 0; j < 4; j++) {
				tmp.add(k + ": "+i+j);
			}
			data.instr_queue.add(tmp);
		}
		
		
		for (int i = 0; i < 10; i++) {
			ArrayList<String> tmp = new ArrayList<String>();
			for (int j = 0; j < 3; j++) {
				tmp.add(k + ": "+i+j);
			}
			data.run_state.add(tmp);
		}
		
		
		{
			ArrayList<String> name = new ArrayList<String>();
			for (int i = k; i<k+5;i++)
				name.add(""+i);
			data.mem.add(name);
			ArrayList<String> mem_data = new ArrayList<String>();
			for (int i = k; i<k+5;i++)
				mem_data.add(""+i+1);
			data.mem.add(mem_data);
		}
		
		data.clock = k;
			
		return data;
	}
	
	public static void update_table_instr() {
		MainDriver.dataLoader.update_by_data(DataType.INSTR_QUEUE, Clock.get_instr_queue());
	}
	
	public static void update_table_fu() {
		MainDriver.dataLoader.update_by_data(DataType.FU, Clock.get_fp());
	}
	
	public static void update_table_reserv() {
		MainDriver.dataLoader.update_by_data(DataType.RESERV_STARION, Clock.get_reserve_station());
	}
	
	public static void update_clock() {
	
		MainDriver.dataLoader.update_by_data(DataType.CLOCK, Clock.get_clock());
	}
	
	public static void update_table_mem(int begin) {
		MainDriver.dataLoader.update_by_data(DataType.MEM, Clock.get_fake_memory(begin));
	}

	public static void update_table_state() {
		MainDriver.dataLoader.update_by_data(DataType.RUNNING_STATE, Clock.get_running_state());
	}
	
	public static void update_all(int begin) {
		if (begin < 0) {
			begin = MainDriver.window.addr_mem;
		}
		update_table_instr();
		
		update_table_reserv();
		update_table_fu();
		update_table_mem(begin);
		update_table_state();
		update_clock();
	}
	
	public static void update_console(String str) {
		UserWindow.ta_console.append(str+"\n");
	}
	public static void clear_console() {
		UserWindow.ta_console.setText("");
	}
	
}
