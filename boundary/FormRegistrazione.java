package boundary;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.ControllerAutenticazione;
import controller.ControllerCliente;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.Toolkit;

public class FormRegistrazione extends JFrame {

	private JPanel contentPane;
	private JTextField name_tf;
	private JTextField surname_tf;
	private JTextField username_tf;
	private JTextField cf_tf;
	private JPasswordField password_tf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormRegistrazione frame = new FormRegistrazione();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	
	private static boolean isValidPassword(String password) {
        // Verifica se la password contiene almeno una lettera maiuscola e un numero
		String regex = "^(?=.*[A-Z])(?=.*\\d).+$";
        return password.matches(regex);
    }
	
	public boolean input_validation(JLabel lblErrorMsg) {
		
		boolean input_validated = false;
		
		String cf = new String(cf_tf.getText());
		String username = new String(username_tf.getText());
		String pw = new String(password_tf.getText());
		String name = new String(name_tf.getText());
		String surname = new String(surname_tf.getText());
		
		
		
		// Controllo campi non siano vuoti
		if(cf.length()== 0 ||username.length()== 0 || pw.length()== 0 || name.length() == 0 || surname.length() == 0) {
			
			lblErrorMsg.setText("Inserire tutti i campi richiesti");
			lblErrorMsg.setForeground(Color.RED);
			
		}
		
		else {
			if(name.length()>50 || !name.matches("^[a-zA-Z]+$")) {
				name_tf.setText(name);
				name_tf.setForeground(Color.RED);
				lblErrorMsg.setText("formato nome non valido!");
				lblErrorMsg.setForeground(Color.RED);
				
			
				
			} else if( surname.length()>50 || !surname.matches("^[a-zA-Z]+$")) {
				surname_tf.setText(surname);
				surname_tf.setForeground(Color.RED);
				lblErrorMsg.setText("formato cognome non valido!");
				lblErrorMsg.setForeground(Color.RED);
				
			}
			// il codice fiscale deve essere uguale a 16 caratteri
			else if(cf.length()!=16 || !cf.matches("[a-zA-Z0-9]+")) {
				

				cf_tf.setText(cf);
				cf_tf.setForeground(Color.RED);
				lblErrorMsg.setText("formato codice fiscale errato!");
				lblErrorMsg.setForeground(Color.RED);
				
			}
		
			else if (username.contains("admin") || username.contains("Admin")){
				//  username non può contenere "admin" 

				username_tf.setText(username);
				username_tf.setForeground(Color.RED);
				lblErrorMsg.setText("non puoi registrarti come amministratore");
				lblErrorMsg.setForeground(Color.RED);
			
			}
			
			// la password deve essere almeno 8 caratteri,deve contenere una maiuscola, un numero, max 20 caratteri
			else if (pw.length()<8 || pw.length()>20 || !isValidPassword(pw)){
				

				password_tf.setText(pw);
				password_tf.setForeground(Color.RED);
				lblErrorMsg.setText("formato password non corretto");
				lblErrorMsg.setForeground(Color.RED);
					
				
			}
			
			else {
				input_validated = true;
			}
			
		
		}
		return input_validated;
		
	}

	/**
	 * Create the frame.
	 */
	public FormRegistrazione() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FormRegistrazione.class.getResource("/resources/immagini/logo.jpg")));
		setBackground(new Color(255, 250, 250));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(245, 255, 250));
		contentPane.setBackground(new Color(245, 255, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Inserisci dati utente");
		lblNewLabel.setForeground(new Color(102, 205, 170));
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblNewLabel.setBounds(150, 49, 110, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Registrazione");
		lblNewLabel_1.setForeground(new Color(95, 158, 160));
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblNewLabel_1.setBounds(144, 22, 180, 24);
		contentPane.add(lblNewLabel_1);
		
		name_tf = new JTextField();
		name_tf.setBounds(42, 91, 136, 19);
		contentPane.add(name_tf);
		name_tf.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Nome*");
		lblNewLabel_2.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(42, 75, 41, 11);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Cognome*");
		lblNewLabel_2_1.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 12));
		lblNewLabel_2_1.setBounds(42, 112, 60, 24);
		contentPane.add(lblNewLabel_2_1);
		
		surname_tf = new JTextField();
		surname_tf.setColumns(10);
		surname_tf.setBounds(42, 134, 136, 19);
		contentPane.add(surname_tf);
		
		JLabel lblNewLabel_2_2 = new JLabel("Username*");
		lblNewLabel_2_2.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		lblNewLabel_2_2.setBounds(248, 72, 76, 24);
		contentPane.add(lblNewLabel_2_2);
		
		username_tf = new JTextField();
		username_tf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				username_tf.setText("");
				username_tf.setForeground(Color.BLACK);
			}
		});
		username_tf.setColumns(10);
		username_tf.setBounds(248, 91, 136, 19);
		contentPane.add(username_tf);
		
		cf_tf = new JTextField();
		cf_tf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				cf_tf.setText("");
				cf_tf.setForeground(Color.BLACK);
			}
		});
		cf_tf.setColumns(10);
		cf_tf.setBounds(42, 178, 136, 19);
		contentPane.add(cf_tf);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Codice Fiscale*");
		lblNewLabel_2_1_1.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		lblNewLabel_2_1_1.setBounds(42, 158, 136, 24);
		contentPane.add(lblNewLabel_2_1_1);
		
		JLabel lblNewLabel_2_1_2 = new JLabel("Password*");
		lblNewLabel_2_1_2.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		lblNewLabel_2_1_2.setBounds(248, 116, 66, 24);
		contentPane.add(lblNewLabel_2_1_2);
		
		JLabel lblErrorMsg = new JLabel("");
		lblErrorMsg.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorMsg.setBounds(23, 202, 392, 16);
		contentPane.add(lblErrorMsg);
		
		JButton ConfirmButton = new JButton("Registrati");
		ConfirmButton.setBackground(new Color(240, 255, 240));
		ConfirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				boolean value = input_validation(lblErrorMsg);
				
				if(value) {
					
					
					ControllerAutenticazione controller =ControllerAutenticazione.getInstance();
					
					boolean result = controller.registraUtente(name_tf.getText(), surname_tf.getText(), cf_tf.getText(), username_tf.getText(), password_tf.getText());
					
					System.out.println(result);
					
					if(!result){
						
						username_tf.setText(username_tf.getText());
						username_tf.setForeground(Color.RED);
						
						
						lblErrorMsg.setText("username già utilizzato");
						lblErrorMsg.setForeground(Color.RED);

						
						
						
					}
					else {
						
						username_tf.setForeground(Color.BLACK);

						lblErrorMsg.setText("");
						ConfirmButton.setText("Registrato");
						ConfirmButton.setForeground(Color.GREEN);
						ConfirmButton.setBackground(Color.WHITE);
						name_tf.setForeground(Color.BLACK);
						surname_tf.setForeground(Color.BLACK);
						cf_tf.setForeground(Color.BLACK);
						password_tf.setForeground(Color.BLACK);
						username_tf.setForeground(Color.BLACK);
		                JOptionPane.showMessageDialog(null, "Registrazione avvenuta correttamente");
		                dispose();
		                

					}
					
				
				}
				
				
			}
		});
		ConfirmButton.setForeground(Color.BLACK);
		ConfirmButton.setBounds(227, 228, 96, 21);
		contentPane.add(ConfirmButton);
		
		JButton btnAnnulla = new JButton("Annulla");
		btnAnnulla.setBackground(new Color(240, 255, 240));
		btnAnnulla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				dispose();
				
				
				
			}
		});
		btnAnnulla.setForeground(new Color(95, 158, 160));
		btnAnnulla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnAnnulla.setBounds(110, 228, 85, 21);
		contentPane.add(btnAnnulla);
		
		
		password_tf = new JPasswordField();
		password_tf.setBounds(248, 134, 136, 19);
		contentPane.add(password_tf);
		
		JLabel lblCampiObbligatori = new JLabel("*obbligatorio");
		lblCampiObbligatori.setForeground(new Color(192, 192, 192));
		lblCampiObbligatori.setFont(new Font("SansSerif", Font.PLAIN, 10));
		lblCampiObbligatori.setBounds(42, 232, 85, 13);
		contentPane.add(lblCampiObbligatori);
		password_tf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				password_tf.setText("");
				password_tf.setForeground(Color.BLACK);
			}
		});
		
		
		
		
	}
}