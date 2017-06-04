package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.Window.Type;
import javax.swing.JLabel;
import java.awt.Scrollbar;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.sun.org.apache.bcel.internal.generic.SWITCH;

import gui.DataLoader.DataType;
import kernel.FP;
import kernel.FakeMemory;
import main.Clock;
import main.MainDriver;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.JComboBox;

public class UserWindow {

//	private boolean isClickedDel = true;
	
	public JFrame frmSimulator;
	
	public JTable table_instrqueue;
	public JTable table_state;
	public JTable table_loadqueue;
	public JTable table_storequeue;
	public JTable table_mem;
	public JTable table_station;
	public JTable table_fu;
//	public JTable table_ru;
	
	public JLabel label_clock;
	
	public int addr_mem;
	
	JComboBox<String> cb_ins0,cb_ins1,cb_ins2,cb_ins3;
	
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					UserWindow window = new UserWindow();
//					window.frmSimulator.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public UserWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		MyImage.init_img();
		
		frmSimulator = new JFrame();
		frmSimulator.setAlwaysOnTop(true);
		frmSimulator.setTitle("Simulator");
		frmSimulator.setBounds(0, 0, 900, 613);
		frmSimulator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSimulator.getContentPane().setLayout(null);
		
		JScrollPane scrollPane_instrqueue = new JScrollPane();
		scrollPane_instrqueue.setBounds(42, 140, 234, 121);
		frmSimulator.getContentPane().add(scrollPane_instrqueue);
		
		table_instrqueue = new JTable();
		table_instrqueue.setEnabled(false);
		scrollPane_instrqueue.setViewportView(table_instrqueue);
		table_instrqueue.setCellSelectionEnabled(true);
		table_instrqueue.getTableHeader().setReorderingAllowed(false);
		
		
		JScrollPane scrollPane_state = new JScrollPane();
		scrollPane_state.setBounds(327, 140, 234, 121);
		frmSimulator.getContentPane().add(scrollPane_state);
		
		table_state = new JTable();
		table_state.setCellSelectionEnabled(true);
		table_state.getTableHeader().setReorderingAllowed(false);
		scrollPane_state.setViewportView(table_state);
		
		
		JScrollPane scrollPane_loadqueue = new JScrollPane();
		scrollPane_loadqueue.setBounds(661, 56, 174, 67);
		frmSimulator.getContentPane().add(scrollPane_loadqueue);
		
		table_loadqueue = new JTable();
		table_loadqueue.setCellSelectionEnabled(true);
		table_loadqueue.getTableHeader().setReorderingAllowed(false);
		scrollPane_loadqueue.setViewportView(table_loadqueue);
		
		JScrollPane scrollPane_storequeue = new JScrollPane();
		scrollPane_storequeue.setBounds(661, 166, 174, 67);
		frmSimulator.getContentPane().add(scrollPane_storequeue);
		
		table_storequeue = new JTable();
		table_storequeue.setCellSelectionEnabled(true);
		table_storequeue.getTableHeader().setReorderingAllowed(false);
		scrollPane_storequeue.setViewportView(table_storequeue);
		
		JScrollPane scrollPane_mem = new JScrollPane();
		scrollPane_mem.setBounds(42, 376, 234, 39);
		frmSimulator.getContentPane().add(scrollPane_mem);
		
		table_mem = new JTable();
		table_mem.setCellSelectionEnabled(true);
		table_mem.getTableHeader().setReorderingAllowed(false);
		scrollPane_mem.setViewportView(table_mem);
		
		JScrollPane scrollPane_station = new JScrollPane();
		scrollPane_station.setBounds(327, 305, 508, 141);
		frmSimulator.getContentPane().add(scrollPane_station);
		
		table_station = new JTable();
		table_station.setCellSelectionEnabled(true);
		table_station.getTableHeader().setReorderingAllowed(false);
		scrollPane_station.setViewportView(table_station);
		
		JScrollPane scrollPane_fu = new JScrollPane();
		scrollPane_fu.setBounds(114, 490, 721, 60);
		frmSimulator.getContentPane().add(scrollPane_fu);
		
		table_fu = new JTable();
		table_fu.setCellSelectionEnabled(true);
		table_fu.getTableHeader().setReorderingAllowed(false);
		scrollPane_fu.setViewportView(table_fu);
		
//		JScrollPane scrollPane_ru = new JScrollPane();
//		scrollPane_ru.setBounds(114, 591, 727, 41);
//		frmSimulator.getContentPane().add(scrollPane_ru);
		
//		table_ru = new JTable();
//		table_ru.setCellSelectionEnabled(true);
//		table_ru.getTableHeader().setReorderingAllowed(false);
//		scrollPane_ru.setViewportView(table_ru);
		
		JLabel lblRunningState = new JLabel("Running State");
		lblRunningState.setBounds(398, 115, 122, 15);
		frmSimulator.getContentPane().add(lblRunningState);
		
		JLabel lblLoadQueue = new JLabel("Load Queue");
		lblLoadQueue.setBounds(713, 31, 122, 15);
		frmSimulator.getContentPane().add(lblLoadQueue);
		
		JLabel lblStoreQueue = new JLabel("Store Queue");
		lblStoreQueue.setBounds(713, 141, 122, 15);
		frmSimulator.getContentPane().add(lblStoreQueue);
		
		JLabel lblMemory = new JLabel("Memory");
		lblMemory.setBounds(114, 351, 122, 15);
		frmSimulator.getContentPane().add(lblMemory);
		
		JLabel lblInstructionQueue = new JLabel("Instruction Queue");
		lblInstructionQueue.setBounds(103, 115, 116, 15);
		frmSimulator.getContentPane().add(lblInstructionQueue);
		
		JLabel lblReservation = new JLabel("Reservation Stations");
		lblReservation.setBounds(518, 280, 219, 15);
		frmSimulator.getContentPane().add(lblReservation);
		
		JLabel lblFloatRegistersfu = new JLabel("Float Registers(FU)");
		lblFloatRegistersfu.setBounds(374, 467, 242, 15);
		frmSimulator.getContentPane().add(lblFloatRegistersfu);
		
		JLabel lblRegisterNumber = new JLabel("Reg Number");
		lblRegisterNumber.setBounds(16, 491, 88, 15);
		frmSimulator.getContentPane().add(lblRegisterNumber);
		
		JLabel lblExpression = new JLabel("Expression");
		lblExpression.setBounds(16, 516, 65, 15);
		frmSimulator.getContentPane().add(lblExpression);
		
		JLabel lblData = new JLabel("data");
		lblData.setBounds(16, 535, 65, 15);
		frmSimulator.getContentPane().add(lblData);
		
		JPanel panel = new JPanel();
		panel.setBounds(42, 41, 519, 46);
		frmSimulator.getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 11, 0, 0));
		
		JButton btnInputInstr = new JButton("");
		btnInputInstr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_A();
			}
		});
		btnInputInstr.setIcon(new ImageIcon(MyImage.img_a));
		btnInputInstr.setBorder(BorderFactory.createEmptyBorder());	
		panel.add(btnInputInstr);
		
		JSeparator separator = new JSeparator();
		panel.add(separator);
		
		JButton btnExportReg = new JButton("");
		btnExportReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_export_reg();
			}
		});
		btnExportReg.setIcon(new ImageIcon(MyImage.img_export_reg));
		btnExportReg.setBorder(BorderFactory.createEmptyBorder());	
		panel.add(btnExportReg);
		
		JButton btnExportMem = new JButton("");
		btnExportMem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_export_mem();
			}
		});
		btnExportMem.setIcon(new ImageIcon(MyImage.img_export_mem));
		btnExportMem.setBorder(BorderFactory.createEmptyBorder());	
		panel.add(btnExportMem);
		
		JButton btnStop = new JButton("");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_stop();
			}
		});
		btnStop.setIcon(new ImageIcon(MyImage.img_stop));
		btnStop.setBorder(BorderFactory.createEmptyBorder());	
		panel.add(btnStop);
		
		JSeparator separator_1 = new JSeparator();
		panel.add(separator_1);
		
		JButton btnSetReg = new JButton("");
		btnSetReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_set_reg();
			}
		});
		btnSetReg.setIcon(new ImageIcon(MyImage.img_reg));
		btnSetReg.setBorder(BorderFactory.createEmptyBorder());
		panel.add(btnSetReg);
		
		JButton btnSetMem = new JButton("");
		btnSetMem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_set_mem();
			}
		});
		btnSetMem.setIcon(new ImageIcon(MyImage.img_mem));
		btnSetMem.setBorder(BorderFactory.createEmptyBorder());
		panel.add(btnSetMem);
		
		JSeparator separator_2 = new JSeparator();
		panel.add(separator_2);
		
		JButton btnSetClock = new JButton("");
		btnSetClock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_clock();
			}
		});
		btnSetClock.setIcon(new ImageIcon(MyImage.img_clock));
		btnSetClock.setBorder(BorderFactory.createEmptyBorder());
		panel.add(btnSetClock);
		
		JButton btnNext = new JButton("");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_next();
			}
		});
		btnNext.setIcon(new ImageIcon(MyImage.img_next));
		btnNext.setBorder(BorderFactory.createEmptyBorder());
		panel.add(btnNext);
		
		JButton btnAddInstr = new JButton("");
		btnAddInstr.setBounds(286, 272, 25, 25);
		btnAddInstr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_add_instr();
			}
		});
		btnAddInstr.setIcon(new ImageIcon(MyImage.img_add));
		btnAddInstr.setBorder(BorderFactory.createEmptyBorder());
		frmSimulator.getContentPane().add(btnAddInstr);
		
		JLabel lbClock = new JLabel("Clock");
		lbClock.setBounds(74, 425, 40, 40);
		lbClock.setIcon(new ImageIcon(MyImage.img_pc));
		lbClock.setBorder(BorderFactory.createEmptyBorder());	
		frmSimulator.getContentPane().add(lbClock);
		
		label_clock = new JLabel("0");
		label_clock.setBounds(180, 440, 54, 15);
		frmSimulator.getContentPane().add(label_clock);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 884, 21);
		frmSimulator.getContentPane().add(menuBar);
		
		JMenu mnNewMenu = new JMenu("File[E]");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("从文件导入");
		mntmNewMenuItem.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent event)
		    {
		    	do_load_from_file();
		    }});
		mnNewMenu.add(mntmNewMenuItem);

		JMenu mnInput = new JMenu("Input[I]");
		menuBar.add(mnInput);
		
		JMenu mnAssigns = new JMenu("Assign[S]");
		menuBar.add(mnAssigns);
		
		JMenu mnRun = new JMenu("Run[R]");
		menuBar.add(mnRun);
		
		JMenuItem mnItem_run = new JMenuItem("运行");
		mnItem_run.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent event)
		    {
		    	do_run();
		    }});
		mnRun.add(mnItem_run);
		
		JMenuItem mnItem_RunOne = new JMenuItem("单步运行");
		mnItem_RunOne.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent event)
		    {
		    	do_next();
		    }});
		mnRun.add(mnItem_RunOne);
		
		
		JMenu mnMode = new JMenu("Mode[C]");
		menuBar.add(mnMode);
		
		JMenu mnHelp = new JMenu("Help[H]");
		menuBar.add(mnHelp);
		
		JLabel lbLoad1 = new JLabel("Load1");
		lbLoad1.setBounds(607, 77, 88, 15);
		frmSimulator.getContentPane().add(lbLoad1);
		
		JLabel lbLoad2 = new JLabel("Load2");
		lbLoad2.setBounds(607, 90, 88, 15);
		frmSimulator.getContentPane().add(lbLoad2);
		
		JLabel lbLoad3 = new JLabel("Load3");
		lbLoad3.setBounds(607, 108, 88, 15);
		frmSimulator.getContentPane().add(lbLoad3);
		
		JLabel lbStore1 = new JLabel("Store1");
		lbStore1.setBounds(607, 185, 88, 15);
		frmSimulator.getContentPane().add(lbStore1);
		
		JLabel lbStore2 = new JLabel("Store2");
		lbStore2.setBounds(607, 203, 88, 15);
		frmSimulator.getContentPane().add(lbStore2);
		
		JLabel lbStore3 = new JLabel("Store3");
		lbStore3.setBounds(607, 218, 88, 15);
		frmSimulator.getContentPane().add(lbStore3);
		
		JComboBox<Integer> comboBox = new JComboBox<Integer>();
		comboBox.setEditable(true);
		comboBox.setBounds(220, 345, 54, 21);
		for (int i = 0; i < 4096; i++)
			comboBox.addItem(i);
		comboBox.setSelectedIndex(0);
		comboBox.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        update_mem_origin_addr((int)comboBox.getSelectedItem());
		    }
		});
		frmSimulator.getContentPane().add(comboBox);
		
		JPanel panel_addIns = new JPanel();
		panel_addIns.setBounds(42, 275, 234, 20);
		frmSimulator.getContentPane().add(panel_addIns);
		panel_addIns.setLayout(new GridLayout(1, 4, 0, 0));
		
		cb_ins0 = new JComboBox<String>();
		panel_addIns.add(cb_ins0);
		
		cb_ins0.addItem("");
		for (String s: Config.cmds) {
			cb_ins0.addItem(s);
		}
		
		cb_ins1 = new JComboBox<String>();
		cb_ins1.setEditable(true);
		panel_addIns.add(cb_ins1);
		
		
		cb_ins2 = new JComboBox<String>();
		cb_ins2.setEditable(true);
		panel_addIns.add(cb_ins2);
		
		cb_ins3 = new JComboBox<String>();
		cb_ins3.setEditable(true);
		panel_addIns.add(cb_ins3);
		
		cb_ins1.addItem("");
		cb_ins2.addItem("");
		cb_ins3.addItem("");
		for (int i = 0; i <= 10; i++){
			cb_ins1.addItem("F"+i);
			cb_ins2.addItem("F"+i);
			cb_ins3.addItem("F"+i);
		}
		for (int i = 0; i < 4096; i++){
			cb_ins1.addItem(""+i);
			cb_ins2.addItem(""+i);
			cb_ins3.addItem(""+i);
		}

		

		
		update_name();
		frmSimulator.addComponentListener(new ComponentAdapter() {
	        @Override
	        public void componentResized(ComponentEvent e) {
	            resizeColumns(table_state);
	        }
	    });
		add_table_listener();
	}
	
	public void update_name() {
		try {
			DefaultTableModel model = null;
		    model = (DefaultTableModel) table_instrqueue.getModel();
		    model.setColumnIdentifiers(Config.instr_queue_name);
		    model.fireTableDataChanged();
		    
		    model = (DefaultTableModel) table_state.getModel();
		    model.setColumnIdentifiers(Config.run_state_name);
		    model.fireTableDataChanged();
		    
		    model = (DefaultTableModel) table_loadqueue.getModel();
		    model.setColumnIdentifiers(Config.load_queue_name);
		    model.fireTableDataChanged();	
		    
		    model = (DefaultTableModel) table_storequeue.getModel();
		    model.setColumnIdentifiers(Config.store_queue_name);
		    model.fireTableDataChanged();	
		    
		    model = (DefaultTableModel) table_station.getModel();
		    model.setColumnIdentifiers(Config.reserv_sta_name);
		    model.fireTableDataChanged();	
		    
		    model = (DefaultTableModel) table_fu.getModel();
		    model.setColumnIdentifiers(Config.fu_name);
		    model.fireTableDataChanged();	
		    
//		    model = (DefaultTableModel) table_ru.getModel();
//		    model.setColumnIdentifiers(Config.ru_name);
//		    model.fireTableDataChanged();	
		    
		    
		} catch (Exception e) {
			
		}
	}
	
	public void add_table_listener() {
		table_instrqueue.getModel().addTableModelListener(new TableModelListener() {
		      public void tableChanged(TableModelEvent e) {
		         int row = e.getFirstRow();
		         int row2 = e.getLastRow();
		         
		         int col = e.getColumn();
		         if (col >= 0) { // user change data in table
		        	 update_table_data(DataLoader.DataType.INSTR_QUEUE, e.getFirstRow(), e.getColumn(), get_table_data(table_instrqueue, row, col), false);
		         } 
		         else { // add row 
		        	 
		         }
		      }
	    });
		
		
		table_mem.getModel().addTableModelListener(new TableModelListener() {
		      public void tableChanged(TableModelEvent e) {
		         int row = 0;
		         int col = e.getColumn();
		         if (col >= 0) { // user change data in table
		        	 update_table_data(DataLoader.DataType.MEM, e.getFirstRow(), e.getColumn(), get_table_data(table_mem, row, col), false);
		         } 		   
		         else { // nothing
		        	 
		         }
		      }
	    });
		
		
		table_fu.getModel().addTableModelListener(new TableModelListener() {
		      public void tableChanged(TableModelEvent e) {
		    	 int row = e.getFirstRow();
		         int col = e.getColumn();
		         if (col >= 0) { // user change data in table
		        	 update_table_data(DataLoader.DataType.FU, row, col, get_table_data(table_fu, row, col), false);
		         } 		   
		         else { // nothing
		        	 
		         }
		      }
	    });
	}
	
	private void resizeColumns(JTable jTable1) {
	    int tW = jTable1.getWidth();
	    System.out.println("wieth " + tW);
	    TableColumn column;
	    TableColumnModel jTableColumnModel = jTable1.getColumnModel();
	    int cantCols = jTableColumnModel.getColumnCount();
	    for (int i = 0; i < cantCols; i++) {
	        column = jTableColumnModel.getColumn(i);
	        int pWidth = Math.round(Config.columnWidthPercentage[i] * tW);
	        column.setPreferredWidth(pWidth);
	    }
	}
	
	private String get_table_data(JTable table, int row, int col) {
		DefaultTableModel model = ((DefaultTableModel) table.getModel());
		return (String) model.getValueAt(row, col);
	}
		
	public void do_load_from_file(){
		String path = "";
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(frmSimulator);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    path = selectedFile.getAbsolutePath();
		    System.out.println("Load file: " + path);
		    Clock.queue.load(path);
		    
		    DataLoader.update_table_instr();
		    DataLoader.update_clock();
		}
	}
	
	public void do_run() {
		System.out.println("run");
//		Clock.run();
	}
	
	/**
	 * 单步执行下一条指令
	 */
	public void do_next() {
		Clock.run_one_step();
		DataLoader.update_all(addr_mem);
	}
	
	/**
	 * 导出内存中的数据
	 */
	public void do_export_mem() {
		System.out.println("export mem");
	}
	
	/**
	 * 导出寄存器中的数据
	 */
	public void do_export_reg() {
		System.out.println("export reg");
	}
	
	public void do_clock() {
		System.out.println("clock");
	}
	
	public void do_set_mem() {
		System.out.println("mem set");
	}
	
	public void do_set_reg(){
		System.out.println("reg set");
	}
	
	public void do_stop() {
		System.out.println("stop");
		
	}
	
	public void do_A() {
		System.out.println("A");
	}
	
	
	public void update_table_data(DataLoader.DataType type, int row, int col, String newdata, Boolean isdel) {
		switch (type) {
		case INSTR_QUEUE:
			System.out.printf("ins updata data %d %d: %s\n", row, col, newdata);
			if (isdel) {
				System.out.println("del row");
			}
			break;
		case RUNNING_STATE:
		
			break;
		case LOAD_QUEUE:
		
			break;
		case STORE_QUEUE:
		
			break;
		case MEM:
			try {
				Clock.mem.set(addr_mem + col,Double.parseDouble(newdata));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		case RESERV_STARION:
	
			break;
		case FU:
			try {
				FP.getInstance().set(col, Double.parseDouble(newdata));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case RU:
		
			break;
		default:
			break;
		
		}
	}
	
	public void update_mem_origin_addr(int addr) {
		if (addr >= 4096) addr = 4096;
		if (addr < 0) addr = 0;
		addr_mem = addr;
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		DataLoader.update_all(addr);
	}
	
	public void do_add_instr() {
		String ret = "";
		ret = (String) cb_ins0.getSelectedItem();
		if (ret.equals(""))
			return;
		
		String op1 = (String) cb_ins1.getSelectedItem();
		String op2 = (String) cb_ins2.getSelectedItem();
		String op3 = (String) cb_ins3.getSelectedItem();
		
		ret += op1.equals("") ? "" : " " + op1;
		ret += op2.equals("") ? "" : "," + op2;
		ret += op3.equals("") ? "" : "," + op3;
		
		
		ArrayList<String> instrs = new ArrayList<String>();
		instrs.add(ret);
		
		Clock.queue.load(instrs);
		
		DataLoader.update_all(addr_mem);
	}
}
