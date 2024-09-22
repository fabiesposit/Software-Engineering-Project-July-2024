package boundary;
import java.awt.AlphaComposite;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import controller.ControllerCliente;
import dto.MyDTO_BB;
import dto.MyDTO_S;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class BBFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtNumeroDiCarta;
	private JTextField txtCvv;
	private JLabel lblNewLabel_1;
	private JLabel ct_tf;
	private MyDTO_BB dtobb;
	private String username;
	private Float costo_totale;
	private String foto_casuale;
	private JTextField DataField;
	
	//NEW
	private ControllerCliente controller;

	
	public Float getCosto_totale() {
		return costo_totale;
	}
	public void setCosto_totale(Float costo_totale) {
		this.costo_totale = costo_totale;
	}


	//panel per il pagamento
	private void pagamento_page(JPanel Pagamento_Panel,CardLayout cardLayout, JPanel mainPanel, String Date, String [] services) {
		Pagamento_Panel.removeAll();
		Pagamento_Panel.revalidate();
		Pagamento_Panel.repaint();
		
		JLabel labelheader = new JLabel("Stai per partire!");
		labelheader.setHorizontalAlignment(SwingConstants.CENTER);
		labelheader.setForeground(new Color(51, 102, 153));
		labelheader.setFont(new Font("SansSerif", Font.BOLD, 22));
		labelheader.setBounds(106, 20, 202, 29);
		Pagamento_Panel.add(labelheader);
		
		JLabel lblInserisciITuoi = new JLabel("Inserisci i tuoi dati di pagamento ");
		lblInserisciITuoi.setHorizontalAlignment(SwingConstants.CENTER);
		lblInserisciITuoi.setForeground(new Color(70, 130, 180));
		lblInserisciITuoi.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblInserisciITuoi.setBounds(100, 48, 210, 22);
		Pagamento_Panel.add(lblInserisciITuoi);
		
		JLabel lblilCostoTotale = new JLabel("*Il costo totale prevede anche i servizi offerti dalla struttura");
		lblilCostoTotale.setHorizontalAlignment(SwingConstants.CENTER);
		lblilCostoTotale.setForeground(new Color(70, 130, 180));
		lblilCostoTotale.setFont(new Font("SansSerif", Font.PLAIN, 10));
		lblilCostoTotale.setBounds(10, 210, 374, 22);
		Pagamento_Panel.add(lblilCostoTotale);
	
		
		
Pagamento_Panel.setLayout(null);
		
		//numero carta
		txtNumeroDiCarta = new JTextField();
		txtNumeroDiCarta.setForeground(Color.LIGHT_GRAY);
		txtNumeroDiCarta.setText("Numero di carta");
		txtNumeroDiCarta.setBounds(80, 99, 234, 27);
		txtNumeroDiCarta.setColumns(10);
		Pagamento_Panel.add(txtNumeroDiCarta);
		

		txtNumeroDiCarta.addFocusListener(new FocusAdapter() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (txtNumeroDiCarta.getText().equals("Numero di carta")) {
		            txtNumeroDiCarta.setText("");
		            txtNumeroDiCarta.setForeground(Color.BLACK);
		        }
		    }

		    @Override
		    public void focusLost(FocusEvent e) {
		        if (txtNumeroDiCarta.getText().isEmpty()) {
		            txtNumeroDiCarta.setForeground(Color.LIGHT_GRAY);
		            txtNumeroDiCarta.setText("Numero di carta");
		        }
		    }
		});
		
		
		
		
		//cvv carta		
		txtCvv = new JTextField();
		txtCvv.setForeground(Color.LIGHT_GRAY);
		txtCvv.setText("cvv");
		txtCvv.setColumns(10);
		txtCvv.setBounds(80, 138, 234, 27);
		Pagamento_Panel.add(txtCvv);

		
		txtCvv.addFocusListener(new FocusAdapter() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (txtCvv.getText().equals("cvv")) {
		            txtCvv.setText("");
		            txtCvv.setForeground(Color.BLACK);
		        }
		    }

		    @Override
		    public void focusLost(FocusEvent e) {
		        if (txtCvv.getText().isEmpty()) {
		            txtCvv.setForeground(Color.LIGHT_GRAY);
		            txtCvv.setText("cvv");
		        }
		    }
		});
		
		
		//costo totale (calcola il costo del BB + i vari servizi che offre)
		lblNewLabel_1 = new JLabel("*Costo totale:");
		lblNewLabel_1.setForeground(new Color(0, 102, 153));
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLabel_1.setBounds(80, 70, 97, 27);
		Pagamento_Panel.add(lblNewLabel_1);
		
		ct_tf = new JLabel("");
		ct_tf.setForeground(new Color(0, 0, 0));
		ct_tf.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		ct_tf.setBounds(175, 72, 92, 23);
		Pagamento_Panel.add(ct_tf);
		ct_tf.setText(Float.toString(this.costo_totale));
		
		
		//button che serve nella  MessageDialog
		JButton confirmButton = new JButton("OK");
		confirmButton.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				dispose();
				
				MainFrame mf = new MainFrame();
				mf.setVisible(true);
			}
			
		});
		
		//bottone prenota
		JButton btnNewButton = new JButton("Prenota");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//NEW
				//ControllerCliente controller = ControllerCliente.getInstance();
				//verifica metodo di pagamento 
				if(inputValidationPagamento(txtNumeroDiCarta.getText(),txtCvv.getText())) {
				if(controller.verificaMetodoDiPagamento(txtNumeroDiCarta.getText(),Integer.valueOf(txtCvv.getText()))) {
					
					//conferma della prenotazione se il metodo di pagamento è valido
					boolean risposta = controller.confermaPrenotazione(username,dtobb.getId(),costo_totale,DataField.getText());
					System.out.println(risposta);
					
					
					
					if(risposta) {
							
						txtCvv.setForeground(Color.GREEN);
						txtNumeroDiCarta.setForeground(Color.GREEN);
						
							txtNumeroDiCarta.setEditable(false);
							txtCvv.setEditable(false);
							
							//mostra finestra di diaologo di conferma prenotazione
							JOptionPane.showMessageDialog(confirmButton, "Prenotazione Confermata");
							
							dispose();
					}
					else {
						txtCvv.setForeground(Color.RED);
						txtNumeroDiCarta.setForeground(Color.RED);
							//mostra finestra di diaologo di errore prenotazione
							JOptionPane.showMessageDialog(confirmButton, "Errore durante la prenotazione: riprova");
					}
						
						
				}
				
				else {
					//se il metodo di pagamento non è valido
					txtCvv.setForeground(Color.RED);
					txtNumeroDiCarta.setForeground(Color.RED);
				}
				}
				//ELSE DELL INPUT VALIDATION
				else {
					txtCvv.setForeground(Color.RED);
					txtNumeroDiCarta.setForeground(Color.RED);
				}
						
				
			}
		});
		
		btnNewButton.setBounds(205, 180, 109, 20);
		Pagamento_Panel.add(btnNewButton);
		
		//bottone annulla , ritorna alla pagina del BB
	 	JButton returnButton = new JButton("Annulla");
	 	returnButton.setForeground(new Color(0, 102, 153));
	 	returnButton.addMouseListener(new MouseAdapter() {
	 		@Override
	 		public void mouseClicked(MouseEvent e) {
	 			
	 			//ricostruisce il panel main
	 			//NEW
	 			PanelMain(mainPanel, cardLayout, Date, Pagamento_Panel, services);
	 			cardLayout.show(contentPane, "MainPanel");
	 		}
	 	});

       	returnButton.setBounds(80, 180, 109, 20);
       	Pagamento_Panel.add(returnButton);
		
	}
	

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//BBFrame frame = new BBFrame();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BBFrame(MyDTO_BB dtob, String username, String Date) {
		
		
	
		ArrayList<String> foto = new ArrayList<String>();
		foto.add("fotoBBF.jpg");
		foto.add("fotoBBF2.jpg");
		
		foto_casuale = foto.get(new Random().nextInt(2));
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(BBFrame.class.getResource("/resources/immagini/logo.jpg")));
		
		this.dtobb = dtob;
		this.username = username;
		this.costo_totale = dtob.getCosto_giornaliero();
		//istanza di controller
		this.controller = ControllerCliente.getInstance();
		
		//i servizi del BB saranno in formato dto
		ArrayList<MyDTO_S> servizi = dtobb.getServizi();
		
		String[] services = new String[servizi.size()];
		//conversione servizi dto in stringhe
		for(int i = 0;i<servizi.size();i++) {
			
			services[i] = new String(servizi.get(i).getTipo());
			
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		// Creare il CardLayout
        CardLayout cardLayout = new CardLayout();
        
        //al content pane passo cardLayout
		contentPane = new JPanel(cardLayout);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// Creare i pannelli

		//pannello principale 
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
	    contentPane.add(mainPanel, "MainPanel");
	   
	   //pannello di pagamento
	    JPanel Pagamento_Panel = new JPanel();
	    Pagamento_Panel.setBackground(new Color(240, 248, 255));
	    Pagamento_Panel.setForeground(new Color(240, 255, 255));
       	contentPane.add(Pagamento_Panel, "Pagamento");

		
       	//NEW
		PanelMain(mainPanel, cardLayout, Date, Pagamento_Panel, services);
		

		
	}
	
	//NEW
	public void PanelMain(JPanel mainPanel, CardLayout cardLayout, String Date, JPanel Pagamento_Panel, String [] services) {
		mainPanel.removeAll();
		mainPanel.revalidate();
		mainPanel.repaint();
		
		JLabel nome_label = new JLabel("");
		nome_label.setHorizontalAlignment(SwingConstants.CENTER);
		nome_label.setForeground(new Color(0, 102, 153));
		nome_label.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 20));
		nome_label.setBounds(47, 10, 304, 29);
		mainPanel.add(nome_label);
		
		nome_label.setText(dtobb.getNome());
		
		
		JLabel lblLocalit = new JLabel("Località");
		lblLocalit.setForeground(new Color(0, 102, 153));
		lblLocalit.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblLocalit.setBounds(32, 82, 62, 13);
		mainPanel.add(lblLocalit);
		
		JLabel località_label = new JLabel("");
		località_label.setForeground(new Color(0, 0, 0));
		località_label.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		località_label.setBounds(32, 97, 162, 13);
		mainPanel.add(località_label);
		
		località_label.setText(dtobb.getLocalita());
		
		
		JLabel lblCostoGiornaliero = new JLabel("Costo Giornaliero");
		lblCostoGiornaliero.setForeground(new Color(0, 102, 153));
		lblCostoGiornaliero.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblCostoGiornaliero.setBounds(32, 117, 162, 13);
		mainPanel.add(lblCostoGiornaliero);
		
		JLabel cg_label = new JLabel("");
		cg_label.setForeground(Color.BLACK);
		cg_label.setBackground(new Color(0, 102, 153));
		cg_label.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		cg_label.setBounds(32, 132, 29, 13);
		mainPanel.add(cg_label);
		
		
		cg_label.setText(Float.toString(dtobb.getCosto_giornaliero()));
		
		JLabel lblPostiLetto = new JLabel("Posti letto");
		lblPostiLetto.setForeground(new Color(0, 102, 153));
		lblPostiLetto.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblPostiLetto.setBounds(32, 51, 91, 13);
		mainPanel.add(lblPostiLetto); 
		
		JLabel pl_label = new JLabel("");
		pl_label.setForeground(new Color(0, 0, 0));
		pl_label.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		pl_label.setBounds(32, 66, 79, 13);
		mainPanel.add(pl_label);
		
		pl_label.setText(Integer.toString(dtobb.getPosti_letto()));
		
		JLabel lblMediaRecensioni = new JLabel("Media Recensioni");
		lblMediaRecensioni.setForeground(new Color(0, 102, 153));
		lblMediaRecensioni.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblMediaRecensioni.setBounds(226, 51, 141, 13);
		mainPanel.add(lblMediaRecensioni);
		
		JLabel mr_label = new JLabel("");
		mr_label.setForeground(Color.BLACK);
		mr_label.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		mr_label.setBounds(226, 66, 125, 13);
		mainPanel.add(mr_label);
		
		float media = dtobb.getMedia_recensioni();
		
		
		if(media == 0) {
		mr_label.setText("Nessuna recensione");}
		else {
			mr_label.setText(Float.toString(dtobb.getMedia_recensioni()));
		
			if(media<=2) {
				
				mr_label.setForeground(Color.RED);
			}
			else if(media>2&&media<=3) {
				mr_label.setForeground(Color.YELLOW);
			}
			else if(media>3){
				mr_label.setForeground(new Color(0,153,51));
			}
		}
		
		JLabel lblDataPernottamento = new JLabel("Data Pernottamento");
		lblDataPernottamento.setForeground(new Color(0, 102, 153));
		lblDataPernottamento.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblDataPernottamento.setBounds(32, 193, 162, 13);
		mainPanel.add(lblDataPernottamento);
		
		
		JLabel lblCostoTotale = new JLabel("Costo Totale");
		lblCostoTotale.setForeground(new Color(0, 102, 153));
		lblCostoTotale.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblCostoTotale.setBounds(32, 152, 124, 13);
		mainPanel.add(lblCostoTotale);
		
		JLabel ct_label = new JLabel("0.0");
		ct_label.setForeground(Color.BLACK);
		ct_label.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		ct_label.setBounds(32, 167, 79, 13);
		mainPanel.add(ct_label);
		//utilizzo controller per calcolare costo totale
		this.costo_totale = controller.calcolaCosto(dtobb.getId());
		ct_label.setText(Float.toString(this.costo_totale));;
		

		JLabel ErroreData = new JLabel("");
		ErroreData.setForeground(new Color(255, 0, 0));
		ErroreData.setBounds(20, 226, 162, 16);
		mainPanel.add(ErroreData);
		
		//TextField per inserimento data
		DataField = new JTextField();
		DataField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			DataField.setBackground(Color.WHITE);
			}
		});
		DataField.setBounds(226, 192, 130, 19);
		DataField.setForeground(Color.LIGHT_GRAY);
		DataField.setText("gg/mm/aaaa");
		
		DataField.addFocusListener(new FocusAdapter() {
		    @Override
		    public void focusGained(FocusEvent e) {
		    	
		        if (DataField.getText().equals("gg/mm/aaaa")) {
		        	DataField.setText("");
		        	DataField.setForeground(Color.BLACK);
		        }
		    }

		    @Override
		    public void focusLost(FocusEvent e) {
		        if (DataField.getText().isEmpty()) {
		        	DataField.setForeground(Color.LIGHT_GRAY);
		        	DataField.setText("gg/mm/aaaa");
		        }
		    }
		});
		
		if(Date!=null) {
			DataField.setText(Date);
			DataField.setEditable(false);
			
		}
		
		mainPanel.add(DataField);
		
	
		
		//bottone prenota
		JButton btnNewButton = new JButton("PRENOTA");
		btnNewButton.setForeground(new Color(0, 102, 153));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//verifica formato data
				
				
				if(DataField.getText().length()==0 || DataField.getText().equals("gg/mm/aaaa")) {
					DataField.setForeground(Color.RED);
 				}
				else {
					boolean inputValido = inputValidation(DataField.getText());
				if(!inputValido) {
					//formato data errato
					ErroreData.setText("Data inserita non valida!");
				}
				
				else {
					//Se formato data corretto
					
					
					//System.out.println("FRAME Sto verificando data");
					//verifica disponibilità data
					
					if(controller.verificaDate(DataField.getText(),dtobb.getId())) {
						
						//System.out.println(costo_totale);
						 //viene creata qui la pagina di pagamento poichè il costo totale viene settato dopo 
						pagamento_page(Pagamento_Panel, cardLayout,  mainPanel,  Date,  services);
						cardLayout.show(contentPane, "Pagamento");
						
						
					}
					else {
						//mostra finestra di dialogo 
						JOptionPane.showMessageDialog(null, "Data non disponibile");
					}
					
				}
				}
			}
		});
		btnNewButton.setBounds(131, 221, 130, 21);
		mainPanel.add(btnNewButton);
		
		//per mostrare nel main panel i servizi offerti
		JLabel lblServiziOfferti = new JLabel("Servizi offerti");
		lblServiziOfferti.setForeground(new Color(0, 102, 153));
		lblServiziOfferti.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblServiziOfferti.setBounds(226, 86, 124, 13);
		mainPanel.add(lblServiziOfferti);
		
		JList lista_servizi = new JList(services);
		JScrollPane scrollPane = new JScrollPane(lista_servizi);
		scrollPane.setBounds(226, 112, 162, 45);
		mainPanel.add(scrollPane);
		
		lista_servizi.setBackground(new Color(240, 248, 255));
		
		   URL imgUrl = getClass().getResource("/resources/immagini/"+foto_casuale);
	       if (imgUrl != null) {
	       	
	       	
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
		

		
		cardLayout.show(contentPane, "MainPanel");
	}
	
public boolean inputValidationPagamento(String numeroCarta, String cvv) {
	boolean valid = true;
	
    // Verifica che numeroCarta sia lunga 16 caratteri e contenga solo numeri
    if (numeroCarta.length() != 16 || !numeroCarta.matches("\\d{16}")) {
        valid = false;
        System.out.println("Numero di carta non valido");
    }

    // Verifica che cvv sia lungo 3 caratteri e contenga solo numeri
    if (cvv.length() != 3 || !cvv.matches("\\d{3}")) {
        valid = false;
        System.out.println("CVV non valido");
    }
	
	
	return valid;
}
	
	//input validation per formato data
public boolean inputValidation(String data) {

		
		
	    // SUPPONIAMO CHE IL FORMATO DELLA DATA SIA GG/MM/AAAA
	    boolean valid = true;

	    // Verifica lunghezza data
	    if (data.length() != 10) {
	        System.out.println("Lunghezza data non valida");
	        return false;
	    }

	    // Tokenizza la data per ottenere giorno, mese e anno
	    StringTokenizer tokens = new StringTokenizer(data, "/");
	    int dayInserito = Integer.parseInt(tokens.nextToken());
	    int monthInserito = Integer.parseInt(tokens.nextToken());
	    int yearInserito = Integer.parseInt(tokens.nextToken());

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
    
    if(foto_casuale.equals("fotoBBF")) {
    	g2d.setColor(new Color(210,180,140));
    }
    else {
    	g2d.setColor(new Color(240,248,255));
    }
    
    g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
    g2d.dispose();
    return image;
}
}
