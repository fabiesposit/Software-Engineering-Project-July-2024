package boundary;

import java.awt.AlphaComposite;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class FormMenuUtente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String username;
	private JTextField localita_tf;
	private JTextArea Data_tf;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormMenuUtente frame = new FormMenuUtente("nicog");
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
	public FormMenuUtente(String username) {
		
		
		this.username = username;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(FormMenuUtente.class.getResource("/resources/immagini/logo.jpg")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		// Creare il CardLayout
        CardLayout cardLayout = new CardLayout();
        
        //al content pane passo cardLayout
		contentPane = new JPanel(cardLayout);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel MenuUtentePanel = new JPanel();
		
		MenuUtentePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		MenuUtentePanel.setLayout(null);
		contentPane.add(MenuUtentePanel, "MenuUtente");
		
		//bottone VISUALIZZA LISTA BB
		JButton btnNewButton = new JButton("Visualizza lista Bed&Breakfast");
		btnNewButton.setForeground(new Color(184, 134, 11));
		btnNewButton.setBackground(new Color(255, 255, 255));
		MenuUtentePanel.add(btnNewButton);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//Crea nuovo frame per la visualizzazione BB
				FrameVisualizzazioneBB fvb = new FrameVisualizzazioneBB(username,null,null);
				fvb.setVisible(true);
				
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(73, 65, 280, 21);
	
		
		//pannello visualizza per Data (per inserire Data)
 		JPanel visualizzaPerData = new JPanel(null);
 		visualizzaPerData.setBackground(new Color(255, 250, 240));
 		contentPane.add(visualizzaPerData, "visualizzaPerData");
 		

 		Data_tf = new JTextArea();
 		Data_tf.addMouseListener(new MouseAdapter() {
 			@Override
 			public void mouseClicked(MouseEvent e) {
 				Data_tf.setText("");
 				Data_tf.setForeground(Color.BLACK);
 				
 			}
 		});
 		Data_tf.setBounds(113, 131, 172, 22);
 		visualizzaPerData.add(Data_tf);
 		Data_tf.setText("gg/mm/aaaa");
 		Data_tf.setForeground(Color.LIGHT_GRAY);
 		
 		JButton confermaVPD = new JButton("Conferma");
 		confermaVPD.setBackground(new Color(255, 255, 240));
 		confermaVPD.addMouseListener(new MouseAdapter() {
 			@Override
 			public void mouseClicked(MouseEvent e) {
 				
 				if(Data_tf.getText().length()==0 || Data_tf.getText().equals("gg/mm/aaaa")) {
 					Data_tf.setForeground(Color.RED);
 				}
 				else {
 				
 		        if(inputValidationData(Data_tf.getText()) ){
 				FrameVisualizzazioneBB fvb = new FrameVisualizzazioneBB(username,null,Data_tf.getText());
 				Data_tf.setText("gg/mm/aaaa");
 				Data_tf.setForeground(Color.LIGHT_GRAY);
				fvb.setVisible(true);
 		        }else {
 		        	Data_tf.setForeground(Color.RED);
 		        }
 				
 				}
 				
 		
 			}
 		});
 		confermaVPD.setBounds(208, 179, 104, 23);
 		visualizzaPerData.add(confermaVPD);
 		
 		
 		JButton annullaVPD = new JButton("Annulla");
 		annullaVPD.setBackground(new Color(255, 255, 240));
 		annullaVPD.addMouseListener(new MouseAdapter() {
 			@Override
 			public void mouseClicked(MouseEvent e) {
 				cardLayout.show(contentPane, "MenuUtente");
 				Data_tf.setText("gg/mm/aaaa");
 				Data_tf.setForeground(Color.LIGHT_GRAY);
 				
 			}
 		});
 		annullaVPD.setForeground(new Color(210, 180, 140));
 		annullaVPD.setBounds(90, 179, 108, 23);
 		visualizzaPerData.add(annullaVPD);
 		
 		JLabel lblNewLabel_1_1 = new JLabel("Cerca B&B liberi");
 		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
 		lblNewLabel_1_1.setForeground(new Color(210, 180, 140));
 		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.BOLD, 22));
 		lblNewLabel_1_1.setBounds(113, 35, 177, 22);
 		visualizzaPerData.add(lblNewLabel_1_1);
 		
 		JLabel lblInserisciLaData = new JLabel("Inserisci la data di partenza \r\n");
 		lblInserisciLaData.setForeground(new Color(184, 134, 11));
 		lblInserisciLaData.setFont(new Font("SansSerif", Font.PLAIN, 12));
 		lblInserisciLaData.setBounds(123, 67, 156, 22);
 		visualizzaPerData.add(lblInserisciLaData);
 		
 		JLabel lblEVediQuale = new JLabel("e vedi quale dei nostri B&B è disponibile!\r\n");
 		lblEVediQuale.setForeground(new Color(184, 134, 11));
 		lblEVediQuale.setFont(new Font("SansSerif", Font.PLAIN, 12));
 		lblEVediQuale.setBounds(90, 88, 238, 22);
 		visualizzaPerData.add(lblEVediQuale);
		
		
		//bottone
       JButton VisualizzaPerDate = new JButton("Visualizza Bed&Breakfast per Data");
       VisualizzaPerDate.setForeground(new Color(184, 134, 11));
       VisualizzaPerDate.setBackground(new Color(255, 255, 255));
       VisualizzaPerDate.addMouseListener(new MouseAdapter() {
       	@Override
       	public void mouseClicked(MouseEvent e) {
       		cardLayout.show(contentPane, "visualizzaPerData");
       	}
       });
       VisualizzaPerDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
       VisualizzaPerDate.setBounds(73, 97, 280, 21);
       MenuUtentePanel.add(VisualizzaPerDate);
       
       
       
     //pannello visualizza per Localita (per inserire località)
       
       	
 		JPanel visualizzaPerLocalita = new JPanel(null);
 		visualizzaPerLocalita.setBackground(new Color(255, 250, 240));
 		contentPane.add(visualizzaPerLocalita, "visualizzaPerLocalita");
 		
 	
 		
 		localita_tf = new JTextField();
 		localita_tf.addMouseListener(new MouseAdapter() {
 			@Override
 			public void mouseClicked(MouseEvent e) {
 				localita_tf.setForeground(Color.BLACK);
 				localita_tf.setBackground(Color.WHITE);
 			}
 		});
 		localita_tf.setBounds(119, 128, 160, 20);
 		visualizzaPerLocalita.add(localita_tf);
 		localita_tf.setColumns(10);
 		
 		JButton ConfermaVPL = new JButton("Conferma");
 		ConfermaVPL.setBackground(new Color(255, 255, 240));
 		localita_tf.setForeground(Color.BLACK);
		localita_tf.setBackground(Color.WHITE);
 		ConfermaVPL.addMouseListener(new MouseAdapter() {
 			@Override
 			public void mouseClicked(MouseEvent e) {
 				//creo un nuovo frame visualizzazioneBB però con costruttore che prevede anche la localita
 				
 				//INPUT VALIDATION LOCALITA
 				
 				if(inputValidationLoc(localita_tf.getText()) ) {
 				FrameVisualizzazioneBB fvb = new FrameVisualizzazioneBB(username,localita_tf.getText(),null);
 				localita_tf.setText("");
 				localita_tf.setForeground(Color.BLACK);
 				
				fvb.setVisible(true);
 				}
 				else {
 					localita_tf.setForeground(Color.RED);
 					
 				}
 			}
 		});
 		ConfermaVPL.setBounds(198, 177, 102, 23);
 		visualizzaPerLocalita.add(ConfermaVPL);
 		
 		JButton AnnullaVPL = new JButton("Annulla");
 		AnnullaVPL.setBackground(new Color(255, 255, 240));
 		AnnullaVPL.addMouseListener(new MouseAdapter() {
 			@Override
 			public void mouseClicked(MouseEvent e) {
 				localita_tf.setText("");
 				localita_tf.setForeground(Color.BLACK);
 				cardLayout.show(contentPane, "MenuUtente");
 			}
 		});
 		AnnullaVPL.setForeground(new Color(210, 180, 140));
 		AnnullaVPL.setBounds(86, 177, 102, 23);
 		visualizzaPerLocalita.add(AnnullaVPL);
 		
 		JLabel lblNewLabel_1_1_1 = new JLabel("Cerca B&B liberi");
 		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
 		lblNewLabel_1_1_1.setForeground(new Color(210, 180, 140));
 		lblNewLabel_1_1_1.setFont(new Font("SansSerif", Font.BOLD, 22));
 		lblNewLabel_1_1_1.setBounds(109, 37, 177, 22);
 		visualizzaPerLocalita.add(lblNewLabel_1_1_1);
 		
 		JLabel lblInserisciLaDestinazione = new JLabel("Inserisci la destinazione");
 		lblInserisciLaDestinazione.setForeground(new Color(184, 134, 11));
 		lblInserisciLaDestinazione.setFont(new Font("SansSerif", Font.PLAIN, 12));
 		lblInserisciLaDestinazione.setBounds(130, 69, 156, 22);
 		visualizzaPerLocalita.add(lblInserisciLaDestinazione);
 		
 		JLabel lblEVediQuale_1 = new JLabel("e vedi quale dei nostri B&B è disponibile!\r\n");
 		lblEVediQuale_1.setForeground(new Color(184, 134, 11));
 		lblEVediQuale_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
 		lblEVediQuale_1.setBounds(86, 90, 238, 22);
 		visualizzaPerLocalita.add(lblEVediQuale_1);
       
       //bottone
       JButton VisualizzaPerLocalita = new JButton("Visualizza Bed&Breakfast per Località");
       VisualizzaPerLocalita.setBackground(new Color(255, 255, 255));
       VisualizzaPerLocalita.setForeground(new Color(184, 134, 11));
       VisualizzaPerLocalita.addMouseListener(new MouseAdapter() {
       	@Override
       	public void mouseClicked(MouseEvent e) {
       		
       		cardLayout.show(contentPane, "visualizzaPerLocalita");
       	}
       });
       VisualizzaPerLocalita.setFont(new Font("Tahoma", Font.PLAIN, 14));
       VisualizzaPerLocalita.setBounds(73, 128, 278, 21);
       MenuUtentePanel.add(VisualizzaPerLocalita);
       
       //bottone
       JButton ValutaBB = new JButton("Valuta Bed&Breakfast");
       ValutaBB.setForeground(new Color(205, 133, 63));
       ValutaBB.setBackground(new Color(255, 255, 255));
       ValutaBB.addMouseListener(new MouseAdapter() {
       	@Override
       	public void mouseClicked(MouseEvent e) {
       		
       		FormValutaStruttura formvs = new FormValutaStruttura(username);
       		formvs.setVisible(true);
       	}
       });
       ValutaBB.setFont(new Font("Tahoma", Font.PLAIN, 14));
       ValutaBB.setBounds(73, 178, 280, 21);
       MenuUtentePanel.add(ValutaBB);
       
       //bottone
    
       JButton btnAnnullaPrenotazione = new JButton("Annulla Prenotazione");
       btnAnnullaPrenotazione.setForeground(new Color(210, 105, 30));
       btnAnnullaPrenotazione.setBackground(new Color(255, 255, 255));
       btnAnnullaPrenotazione.addMouseListener(new MouseAdapter() {
       	@Override
       	public void mouseClicked(MouseEvent e) {
       		FormAnnullaPrenotazione frame = new FormAnnullaPrenotazione(username);
			frame.setVisible(true);
       	}
       });
       btnAnnullaPrenotazione.setFont(new Font("Tahoma", Font.PLAIN, 14));
       btnAnnullaPrenotazione.setBounds(73, 209, 280, 21);
       MenuUtentePanel.add(btnAnnullaPrenotazione);
       
       JLabel lblNewLabel_1 = new JLabel("Menù");
       lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
       lblNewLabel_1.setForeground(new Color(205, 133, 63));
       lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 22));
       lblNewLabel_1.setBounds(151, 10, 112, 22);
       MenuUtentePanel.add(lblNewLabel_1);
       
       JLabel lblScegliLoperazioneDa = new JLabel("Scegli l'operazione da effettuare");
       lblScegliLoperazioneDa.setForeground(new Color(184, 134, 11));
       lblScegliLoperazioneDa.setFont(new Font("SansSerif", Font.PLAIN, 12));
       lblScegliLoperazioneDa.setBounds(122, 34, 206, 21);
       MenuUtentePanel.add(lblScegliLoperazioneDa);
       
       JLabel dot = new JLabel("• ");
       dot.setForeground(new Color(184, 134, 11));
       dot.setFont(new Font("Tahoma", Font.PLAIN, 15));
       dot.setHorizontalAlignment(SwingConstants.CENTER);
       dot.setBounds(50, 65, 29, 21);
       MenuUtentePanel.add(dot);
       
       JLabel dot_1 = new JLabel("• ");
       dot_1.setHorizontalAlignment(SwingConstants.CENTER);
       dot_1.setForeground(new Color(184, 134, 11));
       dot_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
       dot_1.setBounds(50, 96, 29, 21);
       MenuUtentePanel.add(dot_1);
       
       JLabel dot_1_1 = new JLabel("• ");
       dot_1_1.setHorizontalAlignment(SwingConstants.CENTER);
       dot_1_1.setForeground(new Color(184, 134, 11));
       dot_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
       dot_1_1.setBounds(50, 128, 29, 21);
       MenuUtentePanel.add(dot_1_1);
       
       JLabel dot_1_2 = new JLabel("• ");
       dot_1_2.setHorizontalAlignment(SwingConstants.CENTER);
       dot_1_2.setForeground(new Color(184, 134, 11));
       dot_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
       dot_1_2.setBounds(50, 178, 29, 21);
       MenuUtentePanel.add(dot_1_2);
       
       JLabel dot_1_3 = new JLabel("• ");
       dot_1_3.setHorizontalAlignment(SwingConstants.CENTER);
       dot_1_3.setForeground(new Color(184, 134, 11));
       dot_1_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
       dot_1_3.setBounds(50, 209, 29, 21);
       MenuUtentePanel.add(dot_1_3);
       
       URL imgUrl = getClass().getResource("/resources/immagini/fotoMU.jpg");
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
		        MenuUtentePanel.add(backgroundLabel);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
          
       	
       	
       } else {
           System.out.println("Immagine non trovata!");
       }
		

		cardLayout.show(contentPane, "MenuUtente");
	}
	
	//input validation per la data
	public boolean inputValidationData(String data) {
		
	    // SUPPONIAMO CHE IL FORMATO DELLA DATA SIA GG/MM/AAAA
	    boolean valid = true;

	    // Verifica lunghezza data
	    if (data.length() != 10) {
	        System.out.println("Lunghezza data non valida");
	        return false;
	    }

	    
	    // Tokenizza la data per ottenere giorno, mese e anno
	   
	    
	    // Crea il formatter per la data
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    LocalDate currentDate = LocalDate.now();
	    System.out.println(currentDate);
	    LocalDate dataDesiderata = null;

	    // Tenta di parsare la data inserita
	    try {
	        dataDesiderata = LocalDate.parse(data, formatter);
	    } catch (DateTimeParseException e) {
	        System.out.println("Formato data non valido");
	        return false;
	    }
	    
	    
	    StringTokenizer tokens = new StringTokenizer(data, "/");
	    int dayInserito = Integer.parseInt(tokens.nextToken());
	    int monthInserito = Integer.parseInt(tokens.nextToken());
	    int yearInserito = Integer.parseInt(tokens.nextToken());
	    // Verifica se la data è nel passato
	    if (dataDesiderata.isBefore(currentDate)) {
	        System.out.println("Data nel passato");
	        valid = false;
	    }

	    // Ottieni giorno, mese e anno dalla data desiderata
	    int day = dataDesiderata.getDayOfMonth();
	    int month = dataDesiderata.getMonthValue();
	    int year = dataDesiderata.getYear();

	    // Verifica se l'anno è bisestile
	    boolean bisestile = dataDesiderata.isLeapYear();

	    // Controllo per 29 febbraio in un anno bisestile
	    if (monthInserito == 2 && dayInserito == 29 && !bisestile) {
	        System.out.println("Data non valida: il 29 febbraio non esiste in un anno non bisestile");
	        valid = false;
	    }

	    // Verifica che il giorno inserito sia valido per il mese
	    if (dayInserito > day) {
	        System.out.println("Data non valida: il mese " + month + " non ha " + dayInserito + " giorni");
	        valid = false;
	    }

	    System.out.println(valid);

	    return valid;
		
		
	}
	
	public boolean inputValidationLoc(String localita) {
		boolean valid = true;
		//niente numeri, niente caratteri speciali, lunghezza minore di 50
		
		if( localita_tf.getText().length()==0 || localita.length()>50 || !localita.matches("[a-zA-Z\\s]+")) {
			valid = false;
		}
		
		
		
		return valid;
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
