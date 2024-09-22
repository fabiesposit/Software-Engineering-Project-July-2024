package boundary;




import java.awt.AlphaComposite;

//ordine visualizzazione
	//menu -> login -> menuUtente/menuAmministratore
	//menu -> registrazione-> apre nuovo frame registrazione


import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.ControllerAmministratore;
import controller.ControllerAutenticazione;
import controller.ControllerCliente;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Toolkit;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField Username_tf;
	private JPasswordField password_tf;
	private String username;
	
	//variabile booleana che mi permette di capire se ad accedere è un admin o un cliente (default false = cliente)
	private boolean AccessAdmin = false;

	 
	public boolean isAccessAdmin() {
		return AccessAdmin;
	}


	public void setAccessAdmin(boolean accessAdmin) {
		AccessAdmin = accessAdmin;
	}
	
	 

	//metodo che passo il controllo al Controller per verificare accesso
	private boolean Controller() {
		
		boolean result = false;
		
		ControllerAutenticazione controller = ControllerAutenticazione.getInstance();
		
		result = controller.loginUtente(Username_tf.getText(),password_tf.getText());
	
		String username = Username_tf.getText();
		//discrimino se è admin 
		if(username.contains("admin") || username.contains("Admin")) {
		
		//stampa debug
		System.out.println("ADMIN_LOGIN:"+ result);
		
		//setto variabile booleana che mi permette di capire se ad accedere è un admin o un cliente
		this.setAccessAdmin(true);
		
		}//caso utente
		else {

			//stampa debug
			System.out.println("USER_LOGIN:"+ result);
			
			this.setAccessAdmin(false);
			
		}
		
		
		return result;
	}
	
	
	//metodo che va a settare il panel di login  
	private void login_page(JPanel login_panel,CardLayout cardLayout) {
		
		//con setLayout(null) vado a settare manualmente i bordi
		login_panel.setLayout(null);
		login_panel.setBackground(new Color(240, 248, 255));

		login_panel.setBorder(new EmptyBorder(5, 5, 5, 5));

		
		
		//textfield per l'username (attributo di classe)
		Username_tf = new JTextField();
		Username_tf.setBounds(150, 95, 112, 19);
		Username_tf.setColumns(10);
		login_panel.add(Username_tf);
		
		JLabel Username_label = new JLabel("Username");
		Username_label.setHorizontalAlignment(SwingConstants.CENTER);
		Username_label.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 14));
		Username_label.setBounds(150, 76, 112, 19);
		login_panel.add(Username_label);
		
 
		//textfield per la password (attributo di classe)
		
		JLabel Password_label = new JLabel("Password");
		Password_label.setHorizontalAlignment(SwingConstants.CENTER);
		Password_label.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 14));
		Password_label.setBounds(150, 124, 112, 22);
		login_panel.add(Password_label);
		
		password_tf = new JPasswordField();
		password_tf.setBounds(150, 146, 112, 19);
		login_panel.add(password_tf);
		
		//label : accesso negato
			JLabel AccessDeniedLabel = new JLabel("");
			AccessDeniedLabel.setHorizontalAlignment(SwingConstants.CENTER);
	       AccessDeniedLabel.setBounds(104, 175, 204, 14);
	       login_panel.add(AccessDeniedLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Accesso");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 22));
		lblNewLabel_1.setBounds(150, 27, 112, 22);
		lblNewLabel_1.setForeground(new Color(70, 130, 180));
		login_panel.add(lblNewLabel_1);
		
		//bottone ANNULLA
		JButton btnAnnulla = new JButton("Annulla");
		btnAnnulla.setBackground(new Color(240, 255, 255));
		btnAnnulla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//mostra pannello precedente ( pannello principale con bottoni)
				cardLayout.show(contentPane, "MainPanel");
				
			}
		});
		btnAnnulla.setForeground(new Color(0, 0, 153));
		btnAnnulla.setBounds(104, 199, 96, 21);
		login_panel.add(btnAnnulla);
		
		//bottone CONFERMA
		JButton ConfirmButton = new JButton("Accedi");
		ConfirmButton.setBackground(new Color(240, 255, 255));
		ConfirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				
				
				
					password_tf.setForeground(Color.BLACK);
					Username_tf.setForeground(Color.BLACK);
					
					//richiamo il controller che verifica i dati 
					boolean result = Controller();
					
					if(!result) {
						//Caso in cui utente non è presente o la password è errata
						
						AccessDeniedLabel.setText("Username o Password Errati");
						AccessDeniedLabel.setForeground(Color.RED);
						
						
						}
						else {
						
						//TODO
						ConfirmButton.setForeground(Color.WHITE);
						ConfirmButton.setBackground(Color.GREEN);
						username = Username_tf.getText();
						
						//discrimino se ad accedere è un admin o un cliente
						if (AccessAdmin) {
							
							//pass parametri
							
							//mostro frame MenuAmministratore
							FormMenuAmministratore frame = new FormMenuAmministratore(username);
							frame.setVisible(true);
						}
						else {
							//pass parametri
							
							//mostro frame MenuUtente
							FormMenuUtente frame = new FormMenuUtente(username);
							frame.setVisible(true);
						
						}
						dispose();
						
						}

					
				
			
				
				
			}
		});
		ConfirmButton.setForeground(Color.BLACK);
		ConfirmButton.setBounds(212, 199, 96, 21);
		login_panel.add(ConfirmButton);
	
		
		
	}
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/resources/immagini/logo.jpg")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		// Creare il CardLayout
        CardLayout cardLayout = new CardLayout();
        
        //al content pane passo cardLayout
		contentPane = new JPanel(cardLayout);
		contentPane.setLocation(0, 0);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// Creare i pannelli
     
		
	   //pannello principale che contiene tasti "accedi e prenota"
	   JPanel mainPanel = new JPanel(null);
	   contentPane.add(mainPanel, "MainPanel");
	   
	   //pannello di login
       JPanel LoginPanel = new JPanel();
       login_page(LoginPanel, cardLayout);
           
       // Aggiungere i pannelli al pannello contentPane
       
       contentPane.add(LoginPanel, "Accedi");
       
       JLabel lblInserisciLeCredenziali = new JLabel("Inserisci le tue credenziali");
       lblInserisciLeCredenziali.setForeground(new Color(70, 130, 180));
       lblInserisciLeCredenziali.setFont(new Font("SansSerif", Font.PLAIN, 12));
       lblInserisciLeCredenziali.setBounds(137, 53, 158, 13);
       LoginPanel.add(lblInserisciLeCredenziali);
      
       

      
		//bottone per la registrazione
		JButton RegistrationButton = new JButton("Registrati");
		RegistrationButton.setForeground(new Color(95, 158, 160));
		
		RegistrationButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//crea una nuova form per la registrazione
				FormRegistrazione fm = new FormRegistrazione();
				fm.setVisible(true);
			}
		});
		RegistrationButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		RegistrationButton.setBounds(140, 160, 116, 21);
		mainPanel.add(RegistrationButton);
		
		//bottone per login
		JButton LoginButton = new JButton("Accedi");
		LoginButton.setForeground(new Color(70, 130, 180));
		
		LoginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//mostra pannello di login
				cardLayout.show(contentPane, "Accedi");
				
				
				
			}
		});
		LoginButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		LoginButton.setBounds(140, 126, 116, 21);
		mainPanel.add(LoginButton);
		
		JLabel lblNewLabel = new JLabel("YourB&B");
		lblNewLabel.setForeground(new Color(70, 130, 180));
		lblNewLabel.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 24));
		lblNewLabel.setBounds(145, 51, 111, 34);
		mainPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Where you can feel like home");
		lblNewLabel_2.setForeground(new Color(205, 133, 63));
		lblNewLabel_2.setFont(new Font("Segoe UI Light", Font.BOLD, 15));
		lblNewLabel_2.setBounds(104, 86, 205, 21);
		mainPanel.add(lblNewLabel_2);
		
		
		
		URL imgUrl = getClass().getResource("/resources/immagini/fotoMF.jpg");
        if (imgUrl != null) {
        	
        	/*
            ImageIcon icon = new ImageIcon(imgUrl);
            // Creare la JLabel e impostare l'icona
           
            immagine.setIcon(icon);
            immagine.setBounds(10, 11, 404, 229);
            mainPanel.add(immagine);
        	*/
        	
        	BufferedImage originalImage;
			try {
				originalImage = ImageIO.read(imgUrl);
				BufferedImage resizedImage = resizeImage(originalImage, 450, 300);
		        BufferedImage imageWithOverlay = addWhiteOverlay(resizedImage);

		        JLabel backgroundLabel = new JLabel(new ImageIcon(imageWithOverlay));
		        backgroundLabel.setBounds(0, 0, 450, 300);
		        mainPanel.add(backgroundLabel);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
           
        	
        	
        } else {
            System.out.println("Immagine non trovata!");
        }
		
		
		
		//mostra pannello principale
		cardLayout.show(contentPane, "MainPanel");
		
		
		
	}

	private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(resultingImage, 0, 0, null);
        g2d.dispose();
        return outputImage;
    }

    private BufferedImage addWhiteOverlay(BufferedImage image) {
        Graphics2D g2d = image.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.dispose();
        return image;
    }
}


