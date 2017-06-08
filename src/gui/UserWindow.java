package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
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
//	public Thread thread = null;
//	public ClockRun runnable = null;
	/**
	 * Create the application.
	 */
	public UserWindow() {
		initialize();
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
		table_state.setEnabled(false);
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
		table_station.setEnabled(false);
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
		btnInputInstr.setToolTipText("连续执行");
		btnInputInstr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_Run();
			}
		});
		btnInputInstr.setIcon(new ImageIcon(MyImage.img_a));
		btnInputInstr.setBorder(BorderFactory.createEmptyBorder());	
		panel.add(btnInputInstr);
		
		JButton btnStop = new JButton("");
		btnStop.setToolTipText("重置");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_clear();
			}
		});
		
		JButton btnSetClock = new JButton("");
		btnSetClock.setToolTipText("设置一次执行的周期数");
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
		btnNext.setToolTipText("单步运行");
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
		btnExportReg.setToolTipText("导出寄存器的值");
		btnExportReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_export_reg();
			}
		});
		
		JButton btnSetReg = new JButton("");
		btnSetReg.setToolTipText("导入寄存器的值");
		btnSetReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_set_reg();
			}
		});
		
		JButton btn_loadfile = new JButton("");
		btn_loadfile.setToolTipText("从文件中导入指令");
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
		btnSetMem.setToolTipText("导入内存的值");
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
		btnExportMem.setToolTipText("导出内存的值");
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
		for (int i = 0; i < 4092; i++)
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
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(-2, 0, 642, 21);
		frmSimulator.getContentPane().add(menuBar);
		
		JMenu mnNewMenu = new JMenu("文件");
		menuBar.add(mnNewMenu);
		
		JMenuItem menuItem = new JMenuItem("导入指令");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_load_from_file();
			}
		});
		mnNewMenu.add(menuItem);
		
		JMenu mnNewMenu_1 = new JMenu("设置");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem menuItem_1 = new JMenuItem("设置执行参数");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_clock();
			}
		});
		mnNewMenu_1.add(menuItem_1);
		
		JMenu mnNewMenu_2 = new JMenu("执行");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmn = new JMenuItem("执行n个周期");
		mntmn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_Run();
			}
		});
		mnNewMenu_2.add(mntmn);
		
		JMenuItem menuItem_2 = new JMenuItem("单步执行");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_next();
			}
		});
		mnNewMenu_2.add(menuItem_2);
		
		JMenu mnNewMenu_3 = new JMenu("导入");
		menuBar.add(mnNewMenu_3);
		
		JMenuItem menuItem_3 = new JMenuItem("导入寄存器");
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_set_reg();
			}
		});
		mnNewMenu_3.add(menuItem_3);
		
		JMenuItem menuItem_4 = new JMenuItem("导入内存");
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_set_mem();
			}
		});
		mnNewMenu_3.add(menuItem_4);
		
		JMenu mnNewMenu_4 = new JMenu("导出");
		menuBar.add(mnNewMenu_4);
		
		JMenuItem menuItem_5 = new JMenuItem("导出寄存器");
		menuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_export_reg();
			}
		});
		mnNewMenu_4.add(menuItem_5);
		
		JMenuItem menuItem_6 = new JMenuItem("导出内存");
		menuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_export_mem();
			}
		});
		mnNewMenu_4.add(menuItem_6);
		
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
		DataLoader.update_console("Load instructions successfully!");
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
		    System.out.println("Export memory file: " + path);
		    try {
			    File file =new File(path+"\\"+ConstDefinition.MEM_FILENAME);
				//if file doesnt exists, then create it
				if(!file.exists()) {
					file.createNewFile();
				}
				//true = append file, false = write directly
				PrintStream out = new PrintStream(file);
				for (int i=0; i<ConstDefinition.MEM_NUM; i++) {
					System.out.println(String.valueOf(Clock.mem.get(i)));
					out.println(String.valueOf(Clock.mem.get(i)));
				}
				out.close();
				DataLoader.update_console("Export successfully!");
	
			}
			catch(IOException e){
				System.out.println("导出内存数据出错！");
				DataLoader.update_console("Export error!");
				e.printStackTrace();
			}
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
		    System.out.println("Export register file: " + path);
		    try {
			    File file =new File(path+"\\"+ConstDefinition.REG_FILENAME);
				//if file doesnt exists, then create it
				if(!file.exists()) {
					file.createNewFile();
				}
				//true = append file, false = write directly
				PrintStream out = new PrintStream(file);
				for (int i=0; i<ConstDefinition.FP_NUM; i++) {
					System.out.println(String.valueOf(Clock.fp.get(i)));
					out.println(String.valueOf(Clock.fp.get(i)));
				}
				out.close();
	
				DataLoader.update_console("Export successfully!");
			}
			catch(IOException e){
				System.out.println("导出寄存器数据出错！");
				DataLoader.update_console("Export Error!");
				e.printStackTrace();
			}
		}
	}
	
	public void do_clock() {
		System.out.println("clock");
		timeSetter.setVisible(true);
		DataLoader.update_console("Set clocks of one step: " + timeSetter.space);
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
			    DataLoader.update_console("Import Memory successfully!");
		    }
		    catch (Exception e) {
	            System.out.println("读取内存文件内容出错");
	            DataLoader.update_console("read file error!!");
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
	                while((lineTxt = bufferedReader.readLine()) != null && k<ConstDefinition.FP_NUM){
	                	Clock.fp.set(k, Double.valueOf(lineTxt));
	                	k++;
	                }
//	                for (int i=0; i<ConstDefinition.FP_NUM; i++)
//	                	System.out.println(Clock.fp.get(i));
	                read.close();
			    }
			    DataLoader.update_console("Import Regs successfully!");
		    }
		    catch (Exception e) {
	            System.out.println("读取寄存器文件内容出错");
	            DataLoader.update_console("Import Regs error!");
	            e.printStackTrace();
			}
		}
		DataLoader.update_all(-1);
	}
	
	public void do_clear() {
		System.out.println("stop");
		Clock.clear();
		cb_addr.setSelectedIndex(0);
		DataLoader.update_all(0);
//		DataLoader.update_console("Clear all");
		DataLoader.clear_console();
	}
	
	public void do_Run() {
		Clock.run();
		DataLoader.update_console("Run successfully!");
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
				DataLoader.update_console("Memory update successfully!");
			} catch (Exception e) {
				e.printStackTrace();
				DataLoader.update_console("Memory update error!");
			}
			
			break;
		case RESERV_STARION:
	
			break;
		case FU:
			try {
				FP.getInstance().set(col, Double.parseDouble(newdata));
				DataLoader.update_console("Register update successfully!");
			} catch (Exception e) {
				e.printStackTrace();
				DataLoader.update_console("Register update error!");
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
//		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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
		boolean flag = instrs.add(ret);
//		if (flag)
//			DataLoader.update_console("Add Instructions successfully!");
//		else
//			DataLoader.update_console("Add Instructions error!");
		Clock.queue.load(instrs);
		
		DataLoader.update_all(addr_mem);
	}
}
