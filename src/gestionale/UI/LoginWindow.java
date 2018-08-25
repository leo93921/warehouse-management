package gestionale.UI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import gestionale.Connection;
import gestionale.User;
import gestionale.User.Type;

import javax.swing.JPasswordField;
import javax.swing.JButton;

public class LoginWindow {

	private JFrame frame;
	private JTextField txtUsername;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 318, 137);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(10, 11, 62, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 36, 62, 14);
		frame.getContentPane().add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.setText("ProjectLeader0");
		txtUsername.setBounds(82, 8, 210, 20);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(82, 33, 210, 20);
		frame.getContentPane().add(txtPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(203, 64, 89, 23);
		frame.getContentPane().add(btnLogin);
		
		
		ActionListener loginButtonListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String username = txtUsername.getText();
				char[] passArray = txtPassword.getPassword();
				String password = new String(passArray);
				Arrays.fill(passArray, '0');
				User user = Connection.findUser(username, password);
				password = "";
				if(user == null){
					JOptionPane.showMessageDialog(frame, "There is not any user with these credentials, try again.");
					return;
				}
				
				Type type = user.getType();
				
				frame.dispose();
				if (type.equals(User.Type.EMPLOYEE)){
					new EmployeeWindow(user);
				}else if(type.equals(User.Type.PROJECTLEADER)){
					new ProjectLeaderWindow(user);
				}else{
					new WarehousemanMainWindow(user);
				}
				
			}
			
		};
		
		btnLogin.addActionListener(loginButtonListener);
	}
}
