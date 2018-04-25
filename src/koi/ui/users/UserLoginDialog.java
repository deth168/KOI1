package koi.ui.users;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import koi.core.Staff;
import koi.dao.DrinkDAO;
import koi.dao.OrderDAO;
import koi.dao.StaffDAO;
import koi.uii.BillingApp;


@SuppressWarnings("serial")
public class UserLoginDialog extends JDialog {

	
	private StaffDAO staffDAO;
	private DrinkDAO drinkDAO;
	private OrderDAO orderDAO;
	
	private final JPanel contentPanel = new JPanel();
	private JLabel lblWelcome;
	private JPanel credentialPanel;
	private JTextField emailTextField;
	private JPasswordField passwordField;
	
	public UserLoginDialog(StaffDAO staffDAO, DrinkDAO drinkDAO, OrderDAO orderDAO) {
		
		this();
		
		this.staffDAO = staffDAO;
		this.drinkDAO = drinkDAO;
		this.orderDAO = orderDAO;
		

		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent we) {
				
				int promtResult = JOptionPane.showConfirmDialog(null, "Exit Application ?",
						"Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
				
				if(promtResult == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		});
	}
	
	public UserLoginDialog() {
		
		setTitle("KOI The - Log In");
		setBounds(100, 100, 350, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel lblpanel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) lblpanel.getLayout();
			flowLayout.setVgap(10);
			contentPanel.add(lblpanel, BorderLayout.NORTH);
			{
				lblWelcome = new JLabel("KOI The IFL");
				lblWelcome.setFont(new Font("Sylfaen", Font.BOLD, 16));
				lblpanel.add(lblWelcome);
			}
		}
		{
			credentialPanel = new JPanel();
			contentPanel.add(credentialPanel, BorderLayout.CENTER);
			credentialPanel.setLayout(null);
			
			JLabel lblEmail = new JLabel("E-mail");
			lblEmail.setFont(new Font("Sylfaen", Font.PLAIN, 15));
			lblEmail.setBounds(21, 19, 46, 14);
			credentialPanel.add(lblEmail);
			
			emailTextField = new JTextField();
			emailTextField.setBounds(86, 16, 180, 20);
			credentialPanel.add(emailTextField);
			emailTextField.setColumns(30);		
			
			JLabel lblPassword = new JLabel("Password");
			lblPassword.setFont(new Font("Sylfaen", Font.PLAIN, 15));
			lblPassword.setBounds(10, 50, 70, 14);
			credentialPanel.add(lblPassword);
			
			
			//Login Button
			JButton btnLogIn = new JButton("Log in");
			btnLogIn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
		
					performLogin();
				}
			});
			btnLogIn.setBounds(177, 78, 89, 23);
			credentialPanel.add(btnLogIn);
			
			JLabel lblNewUser = new JLabel("New user ?");
			lblNewUser.setFont(new Font("Sylfaen", Font.PLAIN, 15));
			lblNewUser.setBounds(34, 121, 80, 14);
			credentialPanel.add(lblNewUser);
			
			//button signup
			JButton btnSignUp = new JButton("Sign up");
			btnSignUp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//goto from signup
					UserSignUpDialog dialog = new UserSignUpDialog(UserLoginDialog.this, staffDAO);
					//UserLoginDialog dialog = new UserLoginDialog();
					dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
					
					//dissolve current dialog and create new dialog
					dispose();
					//setVisible(false);    can use this also but dispose() is preferred to release memory
					dialog.setVisible(true);
					
				}
			});
			btnSignUp.setBounds(110, 117, 89, 23);
			credentialPanel.add(btnSignUp);
			
			passwordField = new JPasswordField();
			passwordField.setBounds(86, 47, 180, 20);
			credentialPanel.add(passwordField);
		
		}
	}
	
	//Login
	private void performLogin(){
		String email = emailTextField.getText();
		String plainTextPassword = new String(passwordField.getPassword());
		
		try {
			Staff staff = staffDAO.searchStaff(email);	//if not NULL, customer records found in  database
			if(staff == null){
				JOptionPane.showMessageDialog(UserLoginDialog.this, "Staff not found", "OOPS!",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			//Authentication check
			boolean check = staffDAO.authenticate(plainTextPassword, staff);
			if(check){
				System.out.println("Staff authenticated");
				emailTextField.setText("");
				passwordField.setText("");
				BillingApp frame = new BillingApp(UserLoginDialog.this, orderDAO, drinkDAO, staff);
				frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);;
				dispose();
				frame.setVisible(true);
			}
			else{
				JOptionPane.showMessageDialog(UserLoginDialog.this, "Invalid password!", "Invalid login",
						JOptionPane.ERROR_MESSAGE);
				return;
			}			
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(UserLoginDialog.this, "Error logging in: "
					+ e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
