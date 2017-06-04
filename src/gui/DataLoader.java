package gui;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DataLoader {
	
	public static UserWindow parent;
	public static DataLoader dataLoader;
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
		case LOAD_QUEUE:
			update_table(parent.table_loadqueue, data);
			break;
		case STORE_QUEUE:
			update_table(parent.table_storequeue, data);
			break;
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
			update_table(parent.table_ru, data);
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
			
		}
	}
	
	public static Object[][] change(ArrayList<ArrayList<String>> list) {
		if (list == null)
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		int row = list.size();
		int col = row > 0 ? list.get(0).size() : 0;
		Object[][] ret = new Object[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0;j < col; j++){
				ret[i][j] = list.get(i).get(j);
			}
		}
		
		return ret;
	}
	
	public static void start_window() {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserWindow window = new UserWindow();
					window.frmSimulator.setVisible(true);
					DataLoader dataLoader = new DataLoader(window);
					
//					new Thread(){
//						public void run() {
//							int cnt = 0;
//							while(true)
//							try {
//								sleep(1000);
//								dataLoader.update_by_data(DataLoader.create_tmp_data(cnt++));
//								System.out.println("cnt "+cnt);
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//
//						}
//					}.start();
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
//	public static void main(String[] args) {
////		start_window();
//		UserWindow window = new UserWindow();
//		window.frmSimulator.setVisible(true);
//		DataLoader dataLoader = new DataLoader(window);
//	}
	
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
	
}
