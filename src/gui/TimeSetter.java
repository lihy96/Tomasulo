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
import javax.swing.JCheckBox;

public class TimeSetter extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public UserWindow parent = null;
	int space = 0;
//	int circles = 0;
	JSpinner sp_space;
	public TimeSetter(UserWindow _p) {
		parent = _p;
		setTitle("设置连续执行的参数...");
		setBounds(100, 100, 345, 159);
		getContentPane().setLayout(new BorderLayout(0, 0));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("一次执行的周期数：");
			lblNewLabel.setBounds(20, 36, 147, 15);
			lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
			contentPanel.add(lblNewLabel);
		}
		
		sp_space = new JSpinner();
		sp_space.setBounds(177, 33, 80, 22);
		sp_space.setModel(new SpinnerNumberModel(5, 1, 1000, 1));
		contentPanel.add(sp_space);
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
			space = (int) sp_space.getValue();
			System.out.println("");
			
			Clock.setStep(space);
			
			DataLoader.update_console("Steps of one run: " + Clock.getStep());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
