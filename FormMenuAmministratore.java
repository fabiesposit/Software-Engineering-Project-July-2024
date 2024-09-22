package boundary;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import dto.MyDTO_BB;
import dto.MyDTO_S;
import dto.myDTO_report;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import controller.ControllerAmministratore;


import javax.swing.JCheckBox;
//import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class FormMenuAmministratore extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String username;
	private JTextField nome_tf;
	private JTextField localita_tf;
	private JTextField postiletto_tf;
	private JTextField costogiornaliero_tf;
	private String nome;
	private String localita;
	private String costo_giornaliero;
	private String posti_letto;
	private ArrayList<MyDTO_S> lista_servizi;
	private ArrayList<Integer> selectedIndices;
	private ControllerAmministratore controller;
	private JTextField tiposervizio_tf;
	private JTextField costoservizio_tf;
	private String tipo_servizio;
	private String costo_servizio;
	private JTable table;
	private JTextField infoname_tf;
	private JTextField infolocalita_tf;
	private JTextField infocosto_tf;
	private JTextField infopostiletto_tf;
	private JTextField month;
	private myDTO_report report;
	private String mese;
	private JPanel checkboxPanel;
	private DefaultListModel<JCheckBox> listModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormMenuAmministratore frame = new FormMenuAmministratore("debug");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//INPUT VALIDATION MESE DATA PER GENERARE REPORT
private boolean inputValidationReport(String data) {  
		
	 // SUPPONIAMO CHE IL FORMATO DELLA DATA SIA GG/MM/AAAA
	boolean valid = true;
	

	// Verifica lunghezza data
	if (data.length() != 7) {
	    System.out.println("Lunghezza data non valida");
	    return false;
	}
	
	
	
	
	// Crea il formatter per la data
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
	YearMonth currentYearMonth = YearMonth.now();
	System.out.println(currentYearMonth);
	YearMonth dataDesiderata = null;
	
	// Tenta di parsare la data inserita
	try {
	    dataDesiderata = YearMonth.parse(data, formatter);
	} catch (DateTimeParseException e) {
	    System.out.println("Formato data non valido");
	    return false;
	}
	// Tokenizza la data per ottenere giorno, mese e anno
	StringTokenizer tokens = new StringTokenizer(data, "/");
	int meseInserito = Integer.parseInt(tokens.nextToken());
	int annoInserito = Integer.parseInt(tokens.nextToken());
	
	// Verifica che il mese sia compreso tra 1 e 12
		if (meseInserito < 1 || meseInserito > 12) {
		    System.out.println("Mese non valido");
		    return false;
		}
	// Verifica se la data è nel futuro
	if (dataDesiderata.isAfter(currentYearMonth)) {
	    System.out.println("Data nel futuro");
	    valid = false;
	}

	return valid;
	 }	

//INPUT VALIDATION per aggiungere/modificare BB
private int inputValidationBB(String nome,String localita, String costo_giornaliero, String posti_letto) {
    int value = 0;
    // 0 -> OK
    // -1 -> campi vuoti
    // 1 -> lunghezza campi > 50
    // 2 -> il campo località contiene numeri
    // 3 -> posti letto inferiori a 0
    // 4 -> costo inferiore a 0

    // Controlla se i campi sono vuoti
    if (nome.isEmpty() || localita.isEmpty() || costo_giornaliero.isEmpty() || posti_letto.isEmpty()) {
        return -1;
    }

    // Controlla la lunghezza dei campi
    if (nome.length() > 50 || localita.length() > 50) {
        return 1;
    }

    // Controlla che localita non contenga numeri
    if (!localita.matches("[a-zA-Z\\s]+")) {
        return 2;
    }

    try {
    // Controlla che costo non sia inferiore a 0
    if (Float.parseFloat(posti_letto) <= 0) {
        return 3;
    }
    }
    catch(Exception e) {
    	return 3;
    }
    
    try {
    // Controlla che costo non siano inferiori a 0
    if (Float.parseFloat(costo_giornaliero) <= 0) {
        return 4;
    }
    }
    catch(Exception e) {
    	
    	return 4;
    }
    

    return value;
}
private int  inputValidationServizio() {
	int value = 0;
	//0-> OK
	//-1 -> campi vuoti
	//1 -> lunghezza tipo >10
	//2 -> costo < 0
	
	if(tipo_servizio.isEmpty() || costo_servizio.isEmpty()) {
		return -1;
	}
	
	if(tipo_servizio.length()>50) {
		return 1;
	}
	
	try {
	 
	 if(Float.parseFloat(costo_servizio)<= 0.0) {
		 return 2;
	 }
	}
	catch(Exception e) {
		return 2;
	}
	
	return value;
}

//INIZIALIZZA CHECKBOX PER PRENDERE I SERVIZI CHE GIà CI SONO QUANDO
//COSTRUISCO IL PANEL AGGIUNGIBB
private void Checkboxes(DefaultListModel<JCheckBox> boxList, ArrayList<Integer> selectedIndices) {
		    // Pulisce il pannello dei checkbox

		    checkboxPanel.removeAll();
	        checkboxPanel.revalidate();
	        checkboxPanel.repaint();
		    boxList.clear();		
		    boxList.removeAllElements();		 
		    
		 // Aggiunta dei nuovi checkbox al pannello
		    for (MyDTO_S servizio: lista_servizi) {
		    	
		        JCheckBox checkBox = new JCheckBox(servizio.getTipo() + " " + servizio.getCosto_giornaliero());

		        // Aggiungi il checkbox alla lista
		        boxList.addElement(checkBox);
		        
		        checkboxPanel.add(checkBox);
		        
		        if (selectedIndices.contains(servizio.getId())) {
		        	checkBox.setSelected(true);
		        }
		        
		        
		        checkBox.addItemListener(e -> {
		            if (checkBox.isSelected()) {
		            	
		                selectedIndices.add(servizio.getId());
		                System.out.println(selectedIndices);
		            } else {
		                selectedIndices.remove(Integer.valueOf(servizio.getId()));
		                System.out.println(selectedIndices);
		            }
		        });
		    }

		   
		}
	 
	 //COSTRUZIONE PANEL VISUALIZZA REPORT
public void PanelVisualizzaReport(JPanel VisualizzaReport, CardLayout cardLayout) {

		VisualizzaReport.setLayout(null);
		VisualizzaReport.setBorder(new EmptyBorder(5, 5, 5, 5));

	     // Titolo del report
	     JLabel lblNewLabel_7 = new JLabel("REPORT del mese: " + mese);
	     lblNewLabel_7.setBounds(53, 20, 400, 30);
	     lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 18)); // Font più grande
	     VisualizzaReport.add(lblNewLabel_7);

	     // Incassi totali
	     JLabel lblNewLabel_8 = new JLabel("Incassi totali: " + report.getIncassi_tot());
	     lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 14));
	     lblNewLabel_8.setBounds(10, 52, 140, 30);
	     VisualizzaReport.add(lblNewLabel_8);

	     // Miglior B&B
	     String bestBB = report.getBestBB();
	     if(bestBB == null) {
	    	 bestBB = "nessun B&B è stato classificato come il migliore";
	     }
	     JLabel lblNewLabel_8_1 = new JLabel("Miglior B&B: " + bestBB);
	     lblNewLabel_8_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
	     lblNewLabel_8_1.setBounds(10, 76, 400, 30);
	     VisualizzaReport.add(lblNewLabel_8_1);

	     // Tabella con recensioni
	     JScrollPane scrollPane = new JScrollPane();
	     scrollPane.setSize(406, 118);
	     scrollPane.setLocation(10, 112);
	     scrollPane.setPreferredSize(new Dimension(400, 200)); // Dimensione preferita
	     VisualizzaReport.add(scrollPane);

	     String[] columns = {"B&B", "Media valutazioni"};
	     DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
	     for (int i = 0; i < report.getBb().size(); i++) {
	         String campo1 = report.getBb().get(i).getNome(); // Prelevo il campo dal DTO
	         float campo2 = report.getBb().get(i).getMedia_recensioni();
	         Object[] rowData = {campo1, campo2}; // Creo la riga della tab
	         tableModel.addRow(rowData); // Aggiungo la riga alla tab
	     }

	     JTable table_1 = new JTable(tableModel);
	     table_1.setFillsViewportHeight(true);
	     scrollPane.setViewportView(table_1);
	     
	   //annulla
	     JButton annullaBtn = new JButton("Chiudi");
	     annullaBtn.addMouseListener(new MouseAdapter() {
	      @Override
	      public void mouseClicked(MouseEvent e) {
		       //torna al panel form amministratore
		       cardLayout.show(contentPane, "MenuAmministratore");
	      }
	     });
	     annullaBtn.setForeground(new Color(255, 0, 0));
	     annullaBtn.setBounds(320, 25, 80, 20);
	     VisualizzaReport.add(annullaBtn);
	 }

//COSTRUZIONE PANEL GENERA REPORT
public void PanelGeneraReport(JPanel GeneraReportPanel, CardLayout cardLayout) {
		
	GeneraReportPanel.revalidate();
	GeneraReportPanel.removeAll();
	GeneraReportPanel.repaint();
	
	  month = new JTextField();
	  month.setText("mm/aaaa");
	  month.setForeground(Color.LIGHT_GRAY);
	  
	  
	  
	  month.setBounds(159, 96, 96, 19);
	  month.setColumns(10);
	  GeneraReportPanel.add(month);
	  
	  JLabel lblNewLabel_6 = new JLabel("Inserisci il mese per cui desideri generare un report");
	  lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 13));
	  lblNewLabel_6.setBounds(33, 36, 363, 40);
	  GeneraReportPanel.add(lblNewLabel_6);
	  
	  //VISUALIZZA REPORT
	  JPanel VisualizzaReport = new JPanel();	  
	  contentPane.add(VisualizzaReport, "VisualizzaReport");
	  VisualizzaReport.setLayout(null);
	  
	  month.addMouseListener(new MouseAdapter() {
		  @Override 
		  public void mouseClicked(MouseEvent e) { 
			  
			   month.setText("");
			   month.setForeground(Color.BLACK);
		  }
	  });
	  //conferma
	  JButton btnNewButton = new JButton("Conferma");
	  btnNewButton.addMouseListener(new MouseAdapter() {
	   @Override
	   public void mouseClicked(MouseEvent e) {
		
		    mese=month.getText();
		    System.out.println(mese);
		    if(month.getText().length()!=0) {
		    if(inputValidationReport(mese) ) {
			     report= controller.generaReport(mese);
		         PanelVisualizzaReport(VisualizzaReport, cardLayout);		                 
			     cardLayout.show(contentPane, "VisualizzaReport");
		    }
		    else {
		    	month.setForeground(Color.RED);
		    }
		    }
		    else {
		    	month.setForeground(Color.RED);
		    }
		    
	   }
	  });
	  btnNewButton.setBounds(159, 162, 96, 21);
	  GeneraReportPanel.add(btnNewButton);
	  
	  //annulla
	  JButton annullaBtn = new JButton("Annulla");
	  annullaBtn.addMouseListener(new MouseAdapter() {
	   @Override
	   public void mouseClicked(MouseEvent e) {
		    //torna al panel form amministratore
		    cardLayout.show(contentPane, "MenuAmministratore");
	   }
	  });
	  annullaBtn.setForeground(new Color(255, 0, 0));
	  annullaBtn.setBounds(10, 211, 117, 29);
	  GeneraReportPanel.add(annullaBtn);
	   
	 }
	
//COSTRUZIONE PANEL AGGIUNGIBB
public void PanelAggiungiBB(JPanel AggiungiBBPanel, CardLayout cardLayout, JPanel AggiungiServiziPanel) {
	AggiungiBBPanel.removeAll();
	AggiungiBBPanel.revalidate();
	AggiungiBBPanel.repaint();
	
	
	lista_servizi = controller.visualizzaListaServizi();

    AggiungiBBPanel.setLayout(null);
    AggiungiBBPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

    JLabel lblNewLabel = new JLabel("AggiungiB&B");
    lblNewLabel.setBounds(163, 6, 85, 16);
    AggiungiBBPanel.add(lblNewLabel);

    JLabel lblNewLabel_1 = new JLabel("Nome:");
    lblNewLabel_1.setBounds(6, 37, 61, 16);
    AggiungiBBPanel.add(lblNewLabel_1);

    JLabel lblNewLabel_2 = new JLabel("Località:");
    lblNewLabel_2.setBounds(6, 77, 61, 16);
    AggiungiBBPanel.add(lblNewLabel_2);

    JLabel lblNewLabel_3 = new JLabel("Posti Letto:");
    lblNewLabel_3.setBounds(6, 118, 71, 16);
    AggiungiBBPanel.add(lblNewLabel_3);

    JLabel lblNewLabel_4 = new JLabel("Costo Giornaliero:");
    lblNewLabel_4.setBounds(6, 160, 114, 16);
    AggiungiBBPanel.add(lblNewLabel_4);
    
	JLabel ERROR_LABEL = new JLabel("");
	ERROR_LABEL.setForeground(Color.RED);
	ERROR_LABEL.setBounds(16, 193, 267, 13);
	AggiungiBBPanel.add(ERROR_LABEL);

    nome_tf = new JTextField();
    nome_tf.setBounds(62, 32, 130, 26);
    AggiungiBBPanel.add(nome_tf);
    nome_tf.setColumns(10);
    nome_tf.setText(nome);
  

    localita_tf = new JTextField();
    localita_tf.setBounds(67, 73, 125, 26);
    AggiungiBBPanel.add(localita_tf);
    localita_tf.setColumns(10);
    localita_tf.setText(localita);
    


    postiletto_tf = new JTextField();
    postiletto_tf.setBounds(84, 112, 108, 26);
    AggiungiBBPanel.add(postiletto_tf);
    postiletto_tf.setColumns(10);
    postiletto_tf.setText(posti_letto);


    costogiornaliero_tf = new JTextField();
    costogiornaliero_tf.setBounds(130, 157, 62, 26);
    AggiungiBBPanel.add(costogiornaliero_tf);
    costogiornaliero_tf.setColumns(10);
    costogiornaliero_tf.setText(costo_giornaliero);


    // Lista di JCheckBox in modo da poterli resettare dopo l'aggiunta del nuovo BB
    DefaultListModel<JCheckBox> boxList = new DefaultListModel<JCheckBox>();

    // Creazione del JPanel per i checkbox
    JPanel checkboxPanel = new JPanel();
    this.checkboxPanel = checkboxPanel;
    checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
   
    
    //INIZIALIZZA CHECKBOX   
    Checkboxes(boxList, selectedIndices);
    
    // Creazione dello JScrollPane con il JPanel dei checkbox
    JScrollPane scrollPane = new JScrollPane(checkboxPanel);
    scrollPane.setBounds(232, 58, 182, 118);
    AggiungiBBPanel.add(scrollPane);
    
    // VADO AD AGGIUNGI SERVIZI
    JButton serviziBtn = new JButton("Aggiungi Servizi");
    serviziBtn.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
        	 nome = nome_tf.getText();
             localita = localita_tf.getText();
             costo_giornaliero = costogiornaliero_tf.getText();
             posti_letto = postiletto_tf.getText();
            cardLayout.show(contentPane, "AggiungiServiziPanel");
            PanelAggiungiServizi(AggiungiBBPanel, AggiungiServiziPanel, cardLayout,  boxList);
        }
    });
    serviziBtn.setBounds(137, 211, 146, 29);
    AggiungiBBPanel.add(serviziBtn);

    // Torna indietro
    JButton annullaBtn = new JButton("Annulla");
    annullaBtn.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            // Torna al panel form amministratore
        	selectedIndices.removeAll(selectedIndices);
          	 nome = "";
          	 localita = "";
          	 posti_letto= "";
          	 costo_giornaliero= "";
        	
            cardLayout.show(contentPane, "MenuAmministratore");
        }
    });
    annullaBtn.setForeground(new Color(255, 0, 0));
    annullaBtn.setBounds(10, 211, 117, 29);
    AggiungiBBPanel.add(annullaBtn);

    
    // Conferma aggiunta
    JButton confermaBtn = new JButton("Conferma");
    confermaBtn.setForeground(Color.GREEN);
    confermaBtn.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
        	nome = nome_tf.getText();
            localita = localita_tf.getText();
            costo_giornaliero = costogiornaliero_tf.getText();
            posti_letto = postiletto_tf.getText();

            
            int input_value = inputValidationBB( nome, localita,  costo_giornaliero,  posti_letto);
            System.out.println(input_value);
            // 0 -> OK
            // -1 -> campi vuoti
            // 1 -> lunghezza campi > 50
            // 2 -> il campo località contiene numeri
            // 3 -> posti letto inferiori a 0
            // 4 -> costo inferiore a 0
            if(input_value == 0) {
            	ERROR_LABEL.setText("");
	            if (controller.aggiungiBB(selectedIndices, nome, localita, Float.parseFloat(costo_giornaliero), Integer.parseInt(posti_letto))) {
	                JOptionPane.showMessageDialog(null, "B&B aggiunto correttamente");
	                cardLayout.show(contentPane, "MenuAmministratore");
	            } else {
	                JOptionPane.showMessageDialog(null, "errore inserimento B&B ");
	            }	            
            }
            else if(input_value == -1) {
            	ERROR_LABEL.setText("Campi vuoti");
            }
            else if(input_value == 1) {
            	ERROR_LABEL.setText("localita o nome troppo lungo");
            }
            else if (input_value==2) {
            	ERROR_LABEL.setText("la località non può contenere numeri");
            }
            else if (input_value==3) {
            	ERROR_LABEL.setText("Posti letto non validi");
            }else if (input_value==4) {
            	
            	ERROR_LABEL.setText("Costo B&B non valido");
            }
        }
    });
    
    confermaBtn.setBounds(293, 211, 117, 29);
    AggiungiBBPanel.add(confermaBtn);

    JLabel lblNewLabel_5 = new JLabel("Seleziona Servizi Aggiuntivi:");
    lblNewLabel_5.setBounds(240, 37, 182, 16);
    AggiungiBBPanel.add(lblNewLabel_5);
}
	
//COSTRUZIONE PANEL AGGIUNGI SERVIZI
private void PanelAggiungiServizi(JPanel AggiungiBBPanel, JPanel AggiungiServiziPanel, CardLayout cardLayout,  DefaultListModel<JCheckBox> boxList) {
	
	AggiungiServiziPanel.removeAll();
	AggiungiServiziPanel.revalidate();
	AggiungiServiziPanel.repaint();
	
	AggiungiServiziPanel.setLayout(null);
    AggiungiServiziPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

    JLabel lblNewLabel_8 = new JLabel("Aggiungi Servizio");
    lblNewLabel_8.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
    lblNewLabel_8.setBounds(139, 6, 148, 20);
    AggiungiServiziPanel.add(lblNewLabel_8);

    JLabel lblNewLabel_6 = new JLabel("Tipo Servizio:");
    lblNewLabel_6.setBounds(6, 50, 85, 16);
    AggiungiServiziPanel.add(lblNewLabel_6);

    JLabel lblNewLabel_7 = new JLabel("Costo Giornaliero:");
    lblNewLabel_7.setBounds(6, 88, 122, 16);
    AggiungiServiziPanel.add(lblNewLabel_7);

    tiposervizio_tf = new JTextField();
    tiposervizio_tf.setBounds(101, 46, 130, 26);
    AggiungiServiziPanel.add(tiposervizio_tf);
    tiposervizio_tf.setColumns(10);

    costoservizio_tf = new JTextField();
    costoservizio_tf.setBounds(131, 86, 130, 26);
    AggiungiServiziPanel.add(costoservizio_tf);
    costoservizio_tf.setColumns(10);
    
    JLabel ERROR_LABEL = new JLabel("");
	ERROR_LABEL.setForeground(Color.RED);
	ERROR_LABEL.setBounds(16, 193, 267, 13);
	AggiungiServiziPanel.add(ERROR_LABEL);
	
    JButton annullaBtn = new JButton("Indietro");
    annullaBtn.setForeground(new Color(255, 2, 0));
    annullaBtn.setBounds(6, 227, 117, 29);
    AggiungiServiziPanel.add(annullaBtn);

    JButton confermaBtn = new JButton("Conferma");
    confermaBtn.setForeground(new Color(2, 233, 0));
    confermaBtn.setBounds(317, 227, 117, 29);
    AggiungiServiziPanel.add(confermaBtn);

    confermaBtn.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            tipo_servizio = tiposervizio_tf.getText();
            costo_servizio = costoservizio_tf.getText();
            
            int input_value = inputValidationServizio();
            //0-> OK
        	//-1 -> campi vuoti
        	//1 -> lunghezza tipo >10
        	//2 -> costo < 0
            if(input_value ==0) {
            if (controller.aggiungiServizio(tipo_servizio, Float.parseFloat(costo_servizio))) {
            	ERROR_LABEL.setText("");
                JOptionPane.showMessageDialog(null, "Nuovo servizio aggiunto correttamente");
                nome_tf.setText(nome);
                System.out.println(nome);
                PanelAggiungiBB(AggiungiBBPanel, cardLayout, AggiungiServiziPanel);
                System.out.println(selectedIndices);
   
            } else {
                JOptionPane.showMessageDialog(null, "Inserimento nuovo servizio fallito");
            }
            //PanelAggiungiBB( AggiungiBBPanel,  cardLayout,  AggiungiServiziPanel);
           Checkboxes(boxList, selectedIndices);
           
            cardLayout.show(contentPane, "AggiungiBBPanel");           
        
            }else if(input_value == -1) {
        	ERROR_LABEL.setText("campi vuoti");
        }
        else if(input_value==1) {
        	ERROR_LABEL.setText("tipo del servizio troppo lungo");
        }       else if(input_value==2) {
        	ERROR_LABEL.setText("Costo del servizio non valido");
        }
        
        
    }
    });

    annullaBtn.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            // Torna al panel di aggiunta BB
            cardLayout.show(contentPane, "AggiungiBBPanel");
        }
    });
}

//COSTRUZIONE PANEL MODIFICABB 
public void PanelModificaBB(JPanel ModificaBBPanel, CardLayout cardLayout, JPanel InfoBBPanel) {
	ModificaBBPanel.removeAll();
	ModificaBBPanel.revalidate();
	ModificaBBPanel.repaint();
	
    ModificaBBPanel.setLayout(null);
    
    JLabel lblNewLabel_8 = new JLabel("Seleziona B&B da modificare");
    lblNewLabel_8.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
    lblNewLabel_8.setBounds(100, 6, 250, 16);
    ModificaBBPanel.add(lblNewLabel_8);

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBounds(10, 30, 416, 192);
    ModificaBBPanel.add(scrollPane);

    String[] columns = {"B&B", "Posti Letto", "Località", "Costo Giornaliero", "Id"};
    DefaultTableModel table_model = new DefaultTableModel(columns, 0);
    ArrayList<MyDTO_BB> bb_prelevati = controller.visualizzaListaBB();

    for (int i = 0; i < bb_prelevati.size(); i++) {
        Object[] rowData = {bb_prelevati.get(i).getNome(), bb_prelevati.get(i).getPosti_letto(), bb_prelevati.get(i).getLocalita(), bb_prelevati.get(i).getCosto_giornaliero(), bb_prelevati.get(i).getId()};
        table_model.addRow(rowData);
    }

    table = new JTable(table_model);
    table.setBounds(6, 55, 265, 134);
    ModificaBBPanel.add(table);

    scrollPane.setViewportView(table);
    table.setRowSelectionAllowed(true);

    JButton btnNewButton = new JButton("Indietro");
    btnNewButton.setForeground(new Color(255, 4, 0));
    btnNewButton.setBounds(0, 227, 117, 29);
    ModificaBBPanel.add(btnNewButton);

    btnNewButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(contentPane, "MenuAmministratore");
        }
    });

    table.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            MyDTO_BB bb_selezionato = null;
            int row = table.getSelectedRow();
            if (row != -1) {
                int id_selezionato = (int) table.getValueAt(row, 4);

                for (int i = 0; i < bb_prelevati.size(); i++) {
                    if (id_selezionato == bb_prelevati.get(i).getId()) {
                        bb_selezionato = bb_prelevati.get(i);
                        break;
                    }
                }
            }
            PanelInfoBB(InfoBBPanel, cardLayout, bb_selezionato, ModificaBBPanel);
            cardLayout.show(contentPane, "InfoBBPanel");
        }
    });
}

//NEW
//COSTRUZIONE PANEL INFO BB 
public void PanelInfoBB(JPanel InfoBBPanel, CardLayout cardLayout, MyDTO_BB bb_selezionato, JPanel ModificaBBPanel) {
	InfoBBPanel.removeAll();
	InfoBBPanel.revalidate();
	InfoBBPanel.repaint();
	
    InfoBBPanel.setLayout(null);
    InfoBBPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

    JLabel nomeBB = new JLabel(bb_selezionato.getNome());
    nomeBB.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
    nomeBB.setBounds(150, 6, 97, 20);
    InfoBBPanel.add(nomeBB);

    JLabel lblNewLabel_9 = new JLabel("Nome:");
    lblNewLabel_9.setBounds(6, 37, 49, 16);
    InfoBBPanel.add(lblNewLabel_9);

    JLabel lblNewLabel_10 = new JLabel("Località:");
    lblNewLabel_10.setBounds(6, 70, 61, 16);
    InfoBBPanel.add(lblNewLabel_10);

    JLabel lblNewLabel_11 = new JLabel("Costo Giornaliero:");
    lblNewLabel_11.setBounds(6, 103, 125, 16);
    InfoBBPanel.add(lblNewLabel_11);

    JLabel lblNewLabel_12 = new JLabel("Posti Letto:");
    lblNewLabel_12.setBounds(6, 137, 81, 16);
    InfoBBPanel.add(lblNewLabel_12);

    JLabel lblNewLabel_13 = new JLabel("Servizi Posseduti:");
    lblNewLabel_13.setBounds(271, 37, 120, 16);
    InfoBBPanel.add(lblNewLabel_13);

    infoname_tf = new JTextField();
    infoname_tf.setBounds(58, 32, 147, 26);
    InfoBBPanel.add(infoname_tf);
    infoname_tf.setColumns(10);

    infolocalita_tf = new JTextField();
    infolocalita_tf.setBounds(68, 65, 138, 26);
    InfoBBPanel.add(infolocalita_tf);
    infolocalita_tf.setColumns(10);

    infocosto_tf = new JTextField();
    infocosto_tf.setBounds(132, 98, 73, 26);
    InfoBBPanel.add(infocosto_tf);
    infocosto_tf.setColumns(10);

    infopostiletto_tf = new JTextField();
    infopostiletto_tf.setBounds(81, 132, 124, 26);
    InfoBBPanel.add(infopostiletto_tf);
    infopostiletto_tf.setColumns(10);

    infoname_tf.setText(bb_selezionato.getNome());
    infolocalita_tf.setText(bb_selezionato.getLocalita());
    infocosto_tf.setText(String.valueOf(bb_selezionato.getCosto_giornaliero()));
    infopostiletto_tf.setText(String.valueOf(bb_selezionato.getPosti_letto()));

    lista_servizi = controller.visualizzaListaServizi();
    
    ArrayList<MyDTO_S> serviziSelezionati = new ArrayList<>();
  
    ArrayList<MyDTO_S> listaServiziPosseduti = bb_selezionato.getServizi();
    

    JPanel checklistPanel = new JPanel();
    checklistPanel.setLayout(new BoxLayout(checklistPanel, BoxLayout.Y_AXIS));
    

    for (MyDTO_S  servizio :lista_servizi) {
        JCheckBox checkBox = new JCheckBox(servizio.getTipo());
        for (MyDTO_S posseduto : listaServiziPosseduti) {

            if (servizio.getTipo().equals(posseduto.getTipo()) && servizio.getCosto_giornaliero() == posseduto.getCosto_giornaliero()) {
                checkBox.setSelected(true);
                serviziSelezionati.add(servizio);
                break;
            }
        }

     // Aggiungi un ItemListener alla checkbox
        checkBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    serviziSelezionati.add(servizio); // Aggiungi il servizio alla lista quando selezionato
                                   
                } else {
                    serviziSelezionati.remove(servizio); // Rimuovi il servizio dalla lista quando deselezionato
                }
            }
        });
        
        checklistPanel.add(checkBox);
        
    }
    

    JScrollPane scrollPane = new JScrollPane(checklistPanel);
    scrollPane.setBounds(231, 65, 192, 88);
    InfoBBPanel.add(scrollPane);
  
    //TORNA INDIETRO
    JButton infoindietroBtn = new JButton("Indietro");
    infoindietroBtn.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            cardLayout.show(contentPane, "ModificaBBPanel");
        }
    });
    infoindietroBtn.setForeground(new Color(255, 19, 0));
    infoindietroBtn.setBounds(6, 227, 117, 29);
    InfoBBPanel.add(infoindietroBtn);
	JLabel ERROR_LABEL = new JLabel("");
	ERROR_LABEL.setForeground(Color.RED);
	ERROR_LABEL.setBounds(16, 193, 267, 13);
	InfoBBPanel.add(ERROR_LABEL);

   
    //CONFERMA MODIFICA
    JButton infoconfermaBtn = new JButton("Conferma");
    infoconfermaBtn.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
        	int input_value = inputValidationBB(infoname_tf.getText(),infolocalita_tf.getText(),infocosto_tf.getText(), infopostiletto_tf.getText());
        	if(input_value==0) {
        	
        	
        	
            // Logica per confermare le modifiche
        	bb_selezionato.setNome(infoname_tf.getText());
            bb_selezionato.setLocalita(infolocalita_tf.getText());
            bb_selezionato.setCosto_giornaliero(Float.parseFloat(infocosto_tf.getText()));
            bb_selezionato.setPosti_letto(Integer.parseInt(infopostiletto_tf.getText()));            
            bb_selezionato.setServizi(serviziSelezionati);
            
            
            
	            if(controller.modificaBB(bb_selezionato)) {
	            	 JOptionPane.showMessageDialog(null, "B&B modificato correttamente");
	            }else {
	            	 JOptionPane.showMessageDialog(null, "Modifica B&B fallita");
	            }
	
	           cardLayout.show(contentPane, "ModificaBBPanel");
	           PanelModificaBB( ModificaBBPanel,  cardLayout,  InfoBBPanel);
        	
        }            else if(input_value == -1) {
        	ERROR_LABEL.setText("Campi vuoti");
        }
        else if(input_value == 1) {
        	ERROR_LABEL.setText("localita o nome troppo lungo");
        }
        else if (input_value==2) {
        	ERROR_LABEL.setText("la località non può contenere numeri");
        }
        else if (input_value==3) {
        	ERROR_LABEL.setText("Posti letto non validi");
        }else if (input_value==4) {
        	
        	ERROR_LABEL.setText("Costo B&B non valido");
        }
        }
 
    });
    infoconfermaBtn.setForeground(new Color(18, 234, 0));
    infoconfermaBtn.setBounds(317, 227, 117, 29);
    InfoBBPanel.add(infoconfermaBtn);

    //RIMUOVI 
    JButton btnNewButton_2 = new JButton("Rimuovi B&B");
    btnNewButton_2.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            // Logica per rimuovere il B&B
        	
        	 if (controller.rimuoviBB(bb_selezionato.getId())) {
                 JOptionPane.showMessageDialog(null, "B&B rimosso correttamente");
                 

             } else {
                 JOptionPane.showMessageDialog(null, "errore rimozione B&B ");
             }
        	 
        	 PanelModificaBB(ModificaBBPanel, cardLayout, InfoBBPanel);
             cardLayout.show(contentPane, "ModificaBBPanel");

        	
        	
        }
    });
    btnNewButton_2.setBounds(166, 227, 117, 29);
    InfoBBPanel.add(btnNewButton_2);
}

	

	
	/**
	 * Create the frame.
	 */
	public FormMenuAmministratore(String username) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FormMenuAmministratore.class.getResource("/resources/immagini/logo.jpg")));
		this.username = username;
		this.controller = ControllerAmministratore.getInstance();
		this.selectedIndices = new ArrayList<>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		// Creare il CardLayout
        CardLayout cardLayout = new CardLayout();
        
        //al content pane passo cardLayout
		contentPane = new JPanel(cardLayout);
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		//PANNELLO AMMINISTRATORE SCELTA 
		JPanel MenuAmministratorePanel = new JPanel();
		MenuAmministratorePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		MenuAmministratorePanel.setLayout(null);
		contentPane.add(MenuAmministratorePanel, "MenuAmministratore");
		
		JLabel LabelUI = new JLabel("");
		LabelUI.setFont(new Font("Tahoma", Font.BOLD, 10));
		LabelUI.setBounds(10, 0, 112, 25);
		MenuAmministratorePanel.add(LabelUI);
		LabelUI.setText("ADMIN " + username);
		
		
		//CREO PANEL AGGIUNGI SERVIZI
		JPanel AggiungiServiziPanel = new JPanel();
		contentPane.add(AggiungiServiziPanel, "AggiungiServiziPanel");
		AggiungiServiziPanel.setLayout(null);
						
						
		//VADO AD AGGIUNGIBB
		JPanel AggiungiBBPanel = new JPanel();
		contentPane.add(AggiungiBBPanel, "AggiungiBBPanel");
		AggiungiBBPanel.setLayout(null);

		JButton addButton = new JButton("AggiungiB&B");
		addButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//CREO IL PANEL AGGIUNGIBB E MI SPOSTO SU QUELLO PASSANDOGLI AGGIUNGI SERVIZI A CUI POSSO SALTARE DOPO
				PanelAggiungiBB(AggiungiBBPanel,cardLayout, AggiungiServiziPanel);	
				cardLayout.show(contentPane, "AggiungiBBPanel");
				
			}
		});
		addButton.setBounds(65, 52, 320, 30);
		MenuAmministratorePanel.add(addButton);
		
		
		
		//CREO PANEL INFOBB	
		JPanel InfoBBPanel = new JPanel();
		contentPane.add(InfoBBPanel, "InfoBBPanel");
		InfoBBPanel.setLayout(null);
		
		
		//VADO A MODIFICABB
		JPanel ModificaBBPanel = new JPanel();
		contentPane.add(ModificaBBPanel, "ModificaBBPanel");
		ModificaBBPanel.setLayout(null);
		
		JButton editButton = new JButton("ModificaB&B");
		editButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				PanelModificaBB(ModificaBBPanel, cardLayout, InfoBBPanel);
				cardLayout.show(contentPane, "ModificaBBPanel");
				
				
			}
		});
		
		//VADO A GENERAREPORT
		  JPanel GeneraReportPanel = new JPanel();
		  contentPane.add(GeneraReportPanel, "GeneraReportPanel");
		  GeneraReportPanel.setLayout(null);
		  
		  JButton generateButton = new JButton("GeneraReport");
		  generateButton.addMouseListener(new MouseAdapter() {
		   @Override
		   public void mouseClicked(MouseEvent e) {
			PanelGeneraReport(GeneraReportPanel, cardLayout);
		    cardLayout.show(contentPane, "GeneraReportPanel");
		   }
		  });
		  generateButton.setBounds(65, 194, 320, 30);
		  MenuAmministratorePanel.add(generateButton);

		//SHOW DEL PANEL PRINCIPALE menu amministratore
		JLabel lblNewLabel = new JLabel("Seleziona cosa desideri fare");
		lblNewLabel.setBounds(131, 6, 179, 16);
		MenuAmministratorePanel.add(lblNewLabel);
		editButton.setBounds(65, 103, 320, 30);
		MenuAmministratorePanel.add(editButton);

		//DAL CONTENT PANE VISUALIZZO IL PANEL PRINCIPALE DEL MENU
		cardLayout.show(contentPane, "MenuAmministratore");	
		
	}
}
