package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MainWindow extends JFrame implements ActionListener {
	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;
	private static MainWindow mainWindow;

	JPanel tablePanel;
//	JTable table_instr_queue, table_run, table_load, table_store, table_mem, table_resver, table_fu, table_ru;
//	String[][] data_instr_queue, data_run, data_load, data_store, data_mem, data_resver, data_fu, data_ru;

	public static MainWindow getMainWindow() {
		if (mainWindow == null) {
			mainWindow = new MainWindow();
		}
		return mainWindow;
	}

	public MainWindow() {
		super("Simulator");
		init();
	}

	public void init() {
		setSize(WIDTH, HEIGHT);
		setLayout(new FlowLayout());
		setLocationRelativeTo(null); // locate at center of window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tablePanel = new JPanel();

		add(tablePanel);
		
		init_menu();
		init_table();
		setResizable(false);
		setVisible(true);
	}

	public void init_menu() {
		/** menu bar */
		JMenuBar menu_bar = new JMenuBar();

		JMenu file = new JMenu("文件");
		JMenuItem file_open = new JMenuItem("打开文件");
		file_open.addActionListener(this);
		file_open.setActionCommand(Config.FILE);
		file.add(file_open);
		menu_bar.add(file);

		JMenu input_instr = new JMenu("输入指令");
		JMenuItem input_instr_item = new JMenuItem("输入指令");
		input_instr_item.addActionListener(this);
		input_instr_item.setActionCommand(Config.INPUT);
		input_instr.add(input_instr_item);
		menu_bar.add(input_instr);

		JMenu assign = new JMenu("赋值");
		JMenuItem assign_item = new JMenuItem("赋值");
		assign_item.addActionListener(this);
		assign_item.setActionCommand(Config.ASSIGN);
		assign.add(assign_item);
		menu_bar.add(assign);

		JMenu run = new JMenu("运行");
		JMenuItem run_item = new JMenuItem("运行");
		run_item.addActionListener(this);
		run_item.setActionCommand(Config.RUN);
		run.add(run_item);
		menu_bar.add(run);

		JMenu mode = new JMenu("模式");
		JMenuItem mode_item = new JMenuItem("模式");
		mode_item.addActionListener(this);
		mode_item.setActionCommand(Config.MODE);
		mode.add(mode_item);
		menu_bar.add(mode);

		JMenu help = new JMenu("帮助");
		JMenuItem help_item = new JMenuItem("帮助");
		help_item.addActionListener(this);
		help_item.setActionCommand(Config.HELP);
		help.add(help_item);
		menu_bar.add(help);

		getContentPane().add(menu_bar);
		setJMenuBar(menu_bar);
	}

	public void init_table() {
		init_instr_queue_table();
	}
	
	public static JTable create_table(String[][] data, int row, String[] cols) {
		int maxn = Math.max(data.length, row);
		String[][] tmp = new String[maxn][cols.length];
		for (int i = 0; i < maxn; i++){
			if (i < data.length)
				tmp[i] = data[i];
			else
				tmp[i] = new String[cols.length];
		}
		JTable table = new JTable(tmp, cols); 
		return table;
	}
	
	public static JPanel create_panel_by_table(JTable t, String title) {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title,
				TitledBorder.CENTER, TitledBorder.TOP));
		panel.add(new JScrollPane(t));
		return panel;
	}

	public void init_instr_queue_table() {
		String[][] data = new String[][] {{ "", "", "", "" }};
		String cols[] = {"Name","Dsti","Srcj","Srck"};		
		tablePanel.add(create_panel_by_table(create_table(data, 5, cols), "指令队列"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("actionPerformed");
		String cmd = e.getActionCommand();
		if (cmd.equals(Config.FILE)) {
			System.out.println("click file");
		} else if (cmd.equals(Config.INPUT)) {
			System.out.println("click INPUT");
		} else if (cmd.equals(Config.ASSIGN)) {
			System.out.println("click assign");
		} else if (cmd.equals(Config.RUN)) {
			System.out.println("click run");
		} else if (cmd.equals(Config.MODE)) {
			System.out.println("click mode");
		} else if (cmd.equals(Config.HELP)) {
			System.out.println("click help");
		} else {
			System.out.println("!!!!");
		}
	}

	public static void main(String[] args) {
		MainWindow.getMainWindow();
	}

}

// class SampleMenuListener implements MenuListener {
// @Override
// public void menuCanceled(MenuEvent arg0) {
// // TODO Auto-generated method stub
// System.out.println("menuCanceled");
// }
//
// @Override
// public void menuSelected(MenuEvent arg0) {
// // TODO Auto-generated method stub
// System.out.println("menuSelected");
// }
//
// @Override
// public void menuDeselected(MenuEvent e) {
// // TODO Auto-generated method stub
// System.out.println("menuDeselected");
//
// }
// }
