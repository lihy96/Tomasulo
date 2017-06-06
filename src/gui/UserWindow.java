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
import com.sun.org.apache.xpath.internal.operations.And;

import gui.DataLoader.DataType;
import jdk.nashorn.internal.ir.Flags;
import kernel.FP;
import kernel.FakeMemory;
import main.Clock;
import main.MainDriver;
import util.ConstDefinition;

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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import java.awt.Font;

public class UserWindow {

//	private boolean isClickedDel = true;
	
	public JFrame frmSimulator;
	
	public JTable table_instrqueue;
	public JTable table_state;
	public JTable table_mem;
	public JTable table_station;
	public JTable table_fu;
//	public JTable table_ru;
	
	public JLabel label_clock;
	
	public int addr_mem;
	
	JComboBox<String> cb_ins0,cb_ins1,cb_ins2,cb_ins3;
	JComboBox<Integer> cb_addr;
	public static JTextArea ta_console;
	public TimeSetter timeSetter;
	public Thread thread = null;
	public ClockRun runnable = null;
	/**
	 * Create the application.
	 */
	public UserWindow() {
		initialize();
	}
	
	public int get_circles() {
		return timeSetter.circles;
	}
	
	public long get_space() {
		return timeSetter.space;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		timeSetter = new TimeSetter(this);
		
		MyImage.init_img();
		
		frmSimulator = new JFrame();
		frmSimulator.setTitle("Tomasulo Simulator");
		frmSimulator.setBounds(0, 0, 900, 702);
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
		
		JScrollPane scrollPane_mem = new JScrollPane();
		scrollPane_mem.setBounds(42, 376, 234, 39);
		frmSimulator.getContentPane().add(scrollPane_mem);
		
		table_mem = new JTable();
		table_mem.setCellSelectionEnabled(true);
		table_mem.getTableHeader().setReorderingAllowed(false);
		scrollPane_mem.setViewportView(table_mem);
		
		JScrollPane scrollPane_station = new JScrollPane();
		scrollPane_station.setBounds(327, 305, 508, 200);
		frmSimulator.getContentPane().add(scrollPane_station);
		
		table_station = new JTable();
		table_station.setCellSelectionEnabled(true);
		table_station.getTableHeader().setReorderingAllowed(false);
		scrollPane_station.setViewportView(table_station);
		
		JScrollPane scrollPane_fu = new JScrollPane();
		scrollPane_fu.setBounds(114, 572, 721, 55);
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
		lblFloatRegistersfu.setBounds(359, 547, 242, 15);
		frmSimulator.getContentPane().add(lblFloatRegistersfu);
		
		JLabel lblRegisterNumber = new JLabel("Reg Number");
		lblRegisterNumber.setBounds(16, 573, 88, 15);
		frmSimulator.getContentPane().add(lblRegisterNumber);
		
		JLabel lblExpression = new JLabel("state");
		lblExpression.setBounds(16, 592, 65, 15);
		frmSimulator.getContentPane().add(lblExpression);
		
		JPanel panel = new JPanel();
		panel.setBounds(42, 41, 519, 46);
		frmSimulator.getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 11, 0, 0));
		
		JButton btnInputInstr = new JButton("");
		btnInputInstr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_Run();
			}
		});
		btnInputInstr.setIcon(new ImageIcon(MyImage.img_a));
		btnInputInstr.setBorder(BorderFactory.createEmptyBorder());	
		panel.add(btnInputInstr);
		
		JButton btnStop = new JButton("");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_clear();
			}
		});
		
		JButton btnSetClock = new JButton("");
		btnSetClock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_clock();
			}
		});
		btnSetClock.setIcon(new ImageIcon(MyImage.img_clock));
		btnSetClock.setBorder(BorderFactory.createEmptyBorder());
		panel.add(btnSetClock);
		btnStop.setIcon(new ImageIcon(MyImage.img_stop));
		btnStop.setBorder(BorderFactory.createEmptyBorder());	
		panel.add(btnStop);
		
		JButton btnNext = new JButton("");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_next();
			}
		});
		btnNext.setIcon(new ImageIcon(MyImage.img_next));
		btnNext.setBorder(BorderFactory.createEmptyBorder());
		panel.add(btnNext);
		
		JSeparator separator = new JSeparator();
		panel.add(separator);
		
		JButton btnExportReg = new JButton("");
		btnExportReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_export_reg();
			}
		});
		
		JButton btnSetReg = new JButton("");
		btnSetReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_set_reg();
			}
		});
		
		JButton btn_loadfile = new JButton("");
		btn_loadfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_load_from_file();
			}
		});
		btn_loadfile.setIcon(new ImageIcon(MyImage.img_load));
		panel.add(btn_loadfile);
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
		
		JSeparator separator_1 = new JSeparator();
		panel.add(separator_1);
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
		lbClock.setBounds(73, 478, 40, 40);
		lbClock.setIcon(new ImageIcon(MyImage.img_pc));
		lbClock.setBorder(BorderFactory.createEmptyBorder());	
		frmSimulator.getContentPane().add(lbClock);
		
		label_clock = new JLabel("0");
		label_clock.setBounds(178, 491, 54, 15);
		frmSimulator.getContentPane().add(label_clock);
		
		cb_addr = new JComboBox<Integer>();
		cb_addr.setEditable(true);
		cb_addr.setBounds(220, 345, 54, 21);
		for (int i = 0; i < 4096; i++)
			cb_addr.addItem(i);
		cb_addr.setSelectedIndex(0);
		cb_addr.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        update_mem_origin_addr((int)cb_addr.getSelectedItem());
		    }
		});
		frmSimulator.getContentPane().add(cb_addr);
		
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
		
		JScrollPane panel_console = new JScrollPane();
		panel_console.setBounds(598, 60, 276, 200);
		frmSimulator.getContentPane().add(panel_console);
		
		ta_console = new JTextArea();
		ta_console.setBackground(new Color(255, 255, 255));
		ta_console.setForeground(Color.WHITE);
		ta_console.setFont(new Font("Courier New", Font.PLAIN, 13));
		ta_console.setEnabled(false);
		ta_console.setTabSize(4);
		panel_console.setViewportView(ta_console);
		
		JLabel lblConsole = new JLabel("Console:");
		lblConsole.setBounds(598, 35, 88, 15);
		frmSimulator.getContentPane().add(lblConsole);
		
		JLabel lblData_1 = new JLabel("data");
		lblData_1.setBounds(16, 610, 65, 15);
		frmSimulator.getContentPane().add(lblData_1);
		
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
		    
		    model = (DefaultTableModel) table_state.getModel();
		    model.setColumnIdentifiers(Config.run_state_name);
	
		    
		    model = (DefaultTableModel) table_station.getModel();
		    model.setColumnIdentifiers(Config.reserv_sta_name);

		    
		    model = (DefaultTableModel) table_fu.getModel();
		    model.setColumnIdentifiers(Config.fu_name);
		    model.fireTableDataChanged();	
		    		    
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
	
	
	/**
	 * 单步执行下一条指令
	 */
	public void do_next() {
		Clock.run_one_step();
	}
	
	/**
	 * 导出内存中的数据
	 */
	public void do_export_mem() {
		String path = "";
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = fileChooser.showOpenDialog(frmSimulator);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    path = selectedFile.getAbsolutePath();
		    System.out.println("Load file: " + path);
		    
		    // add wanglt code here for export mem data
		}
	}
	
	/**
	 * 导出寄存器中的数据
	 */
	public void do_export_reg() {
		String path = "";
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = fileChooser.showOpenDialog(frmSimulator);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    path = selectedFile.getAbsolutePath();
		    System.out.println("Load file: " + path);
		    
		    // add wanglt code here for export reg data
		}
	}
	
	public void do_clock() {
		System.out.println("clock");
		timeSetter.setVisible(true);
	}
	
	public void do_set_mem() {
		String path = "";
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(frmSimulator);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    path = selectedFile.getAbsolutePath();
		    System.out.println("Load memory file: " + path);
		    // n行double->n个地址
		    File file=new File(path);
		    String encoding = "utf-8";
		    try {
			    if(file.isFile() && file.exists()){ //判断文件是否存在
	                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
	                BufferedReader bufferedReader = new BufferedReader(read);
	                String lineTxt = null;
	                int k = 0;
	                while((lineTxt = bufferedReader.readLine()) != null && k<ConstDefinition.MEM_NUM){
	                	Clock.mem.set(k, Double.valueOf(lineTxt));
	                	k++;
	                }
	                for (int i=0; i<k; i++)
	                	System.out.println(Clock.mem.get(i));
	                read.close();
			    }
		    }
		    catch (Exception e) {
	            System.out.println("读取内存文件内容出错");
	            e.printStackTrace();
			}
		}
	}
	
	public void do_set_reg() {
		String path = "";
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(frmSimulator);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    path = selectedFile.getAbsolutePath();
		    System.out.println("Load register file: " + path);
		    // 11行double->11个寄存器
		    File file=new File(path);
		    String encoding = "utf-8";
		    try {
			    if(file.isFile() && file.exists()){ //判断文件是否存在
	                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
	                BufferedReader bufferedReader = new BufferedReader(read);
	                String lineTxt = null;
	                int k = 0;
	                while((lineTxt = bufferedReader.readLine()) != null && k<11){
	                	Clock.fp.set(k, Double.valueOf(lineTxt));
	                	k++;
	                }
//	                for (int i=0; i<11; i++)
//	                	System.out.println(Clock.fp.get(i));
	                read.close();
			    }
		    }
		    catch (Exception e) {
	            System.out.println("读取寄存器文件内容出错");
	            e.printStackTrace();
			}
		}
	}
	
	public void do_clear() {
		System.out.println("stop");
		Clock.clear();
		cb_addr.setSelectedIndex(0);
		DataLoader.update_all(0);
	}
	
	public void do_Run() {
		System.out.println("run");
		
//		Clock.run();
//		DataLoader.update_all(0);
		
		if (thread != null) {
			thread.stop();
			thread = null;
		}
		else {
			thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Clock.run();
				}
			});
			thread.start();
		}
//		try {
//			thread.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		if (thread != null) {
//			runnable.terminate();
//			try {
//				thread.join();
//				System.out.println("Thread end.");
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			thread = null;
//		}
//		else {
////			System.out.println("lslls");
//			runnable = new ClockRun() ;
//			thread = new Thread(runnable);
////	        System.out.println("Starting thread: " + thread);
//			thread.start();
//		}
		
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
	
	public class ClockRun implements Runnable {
			
	    private volatile boolean running = true;

	    public void terminate() {
	        running = false;
	    }
		
		@Override
		public void run() {
//			System.out.println("" + running + " : " + Clock.get_clock());
			while(running && Clock.get_clock() < get_circles()) {
				Clock.run_one_step();
				try {
					Thread.sleep(get_space());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
