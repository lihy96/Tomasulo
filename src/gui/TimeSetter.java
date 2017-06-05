package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Clock;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Font;

public class TimeSetter extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public UserWindow parent = null;
	long space = 0;
	int circles = 0;
	JSpinner sp_circle,sp_space;
	public TimeSetter(UserWindow _p) {
		parent = _p;
		setTitle("设置连续执行的参数...");
		setBounds(100, 100, 444, 241);
		getContentPane().setLayout(new BorderLayout(0, 0));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("连续执行的时间间隔：");
			lblNewLabel.setBounds(86, 77, 147, 15);
			lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel label = new JLabel("连续执行的周期数：");
			label.setBounds(86, 117, 147, 15);
			label.setFont(new Font("微软雅黑", Font.PLAIN, 12));
			contentPanel.add(label);
		}
		
		sp_space = new JSpinner();
		sp_space.setBounds(267, 74, 80, 22);
		sp_space.setModel(new SpinnerNumberModel(new Long(1000), new Long(0), new Long(20000), new Long(1)));
		contentPanel.add(sp_space);
		
		sp_circle = new JSpinner();
		sp_circle.setBounds(267, 114, 80, 22);
		sp_circle.setModel(new SpinnerNumberModel(new Integer(100), new Integer(0), null, new Integer(1)));
		contentPanel.add(sp_circle);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						do_ok();
					}
				});
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
					}
				});
				buttonPane.add(cancelButton);
			}
		}
		setVisible(false);
	}
	
	private void do_ok () {
		this.setVisible(false);
		try {
			circles = (int) sp_circle.getValue();
			space = (long) sp_space.getValue();
			System.out.println("");
			
			Clock.setMaxCycle(circles);
			Clock.setTimeOut(space);
			
			DataLoader.update_console("Timeout: " + Clock.get_timeout() + "ms");
			DataLoader.update_console("Cycles: " + Clock.get_clock_max());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
