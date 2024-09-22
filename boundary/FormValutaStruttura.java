package boundary;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import controller.ControllerCliente;
import dto.MyDTO_BB;
import dto.MyDTO_S;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.MouseMotionAdapter;
import java.awt.Button;
import javax.swing.border.EtchedBorder;
import java.awt.Toolkit;

public class FormValutaStruttura extends JFrame {

	private JPanel contentPane;

	private String username;
	private DefaultTableModel table_model;
	private JTable table;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormValutaStruttura frame = new FormValutaStruttura("nicog");
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
	public FormValutaStruttura(String username) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FormValutaStruttura.class.getResource("/resources/immagini/logo.jpg")));
		
		// Creare il CardLayout
        CardLayout cardLayout = new CardLayout();
        
		this.username = username;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel(cardLayout);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	
				
		//Pannello che mostra le info sul BB da prenotare
		
		
		JPanel mainPanel = new JPanel(null);
		mainPanel.setBackground(new Color(255, 250, 205));
		
		mainPanel.setLayout(null);
		contentPane.add(mainPanel,"MainPanel");
		
		JPanel PanelBBDaValutare = new JPanel();
		PanelBBDaValutare.setBackground(new Color(255, 250, 240));
		
		
		//PER VISUALIZZARE DURANTE PROGRAMMAZIONE
		//BBDaValutare_page(PanelBBDaValutare,cardLayout,null);
		
		
		String[] columns = {"B&B","Località"};
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(160, 82, 45), new Color(222, 184, 135)));
		scrollPane.setBounds(21, 73, 379, 145);
		mainPanel.add(scrollPane);
		scrollPane.setBackground(new Color(255, 248, 220));
		// PANEL CHE MOSTRA L'INTERFACCIA PER VALUTARE IL BB
		
		
		
		table_model = new DefaultTableModel(columns,0);
		
		
		ControllerCliente controller = ControllerCliente.getInstance();
		
		// OTTENGO TUTTI 
		ArrayList<MyDTO_BB> bb_pernottati = controller.visualizzaBBPernottati(username);
		

		for(int i = 0;i<bb_pernottati.size();i++) {
			
			Object [] rowData = {bb_pernottati.get(i).getNome(),bb_pernottati.get(i).getLocalita()};
			
			table_model.addRow(rowData);
			
		}
	
		table = new JTable(table_model);
		scrollPane.setViewportView(table);
				
		table.setRowSelectionAllowed(true);
		
		
		 table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
	            @Override
	            public Component getTableCellRendererComponent(JTable table, Object value,
	                    boolean isSelected, boolean hasFocus, int row, int column) {
	                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	                c.setBackground(new Color(210,180,140));
	                c.setForeground(Color.white);
	                
	              
	                c.setFont(c.getFont().deriveFont(Font.BOLD)); // Testo in grassetto
	                
	                return c;
	            }
	        });
		 
		 	JTableHeader header = table.getTableHeader();
	        header.setBackground(new Color(255, 255, 240));
	       
	        header.setFont(header.getFont().deriveFont(Font.BOLD));
		
		table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    String nomeBB = (String) table.getValueAt(row, 0);
                    String localita = (String) table.getValueAt(row, 1);
                    
                   
                    
                    //System.out.println(nomeBB+" "+localita);
                    
                    //IMPLEMENTAZIONE VERA
                    
                    
            		
                    
                    BBDaValutare_page(PanelBBDaValutare,cardLayout,bb_pernottati.get(row));
                    
                    cardLayout.show(contentPane, "InfoBB");
                    
                }
            }
        });
		
		
		JButton btnNewButton = new JButton("Annulla");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				dispose();
			}
		});
		
		
		
		btnNewButton.setForeground(new Color(205, 133, 63));
		btnNewButton.setBounds(157, 228, 85, 21);
		mainPanel.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("B&B visitati");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(new Color(205, 133, 63));
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 22));
		lblNewLabel_1.setBounds(112, 21, 177, 22);
		mainPanel.add(lblNewLabel_1);
		
		JLabel lblLasciaciUnaValutazione = new JLabel("Lasciaci una valutazione, aiuta a migliorarci per te!");
		lblLasciaciUnaValutazione.setHorizontalAlignment(SwingConstants.CENTER);
		lblLasciaciUnaValutazione.setForeground(new Color(184, 134, 11));
		lblLasciaciUnaValutazione.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblLasciaciUnaValutazione.setBounds(55, 42, 303, 21);
		mainPanel.add(lblLasciaciUnaValutazione);
		
		cardLayout.show(contentPane, "MainPanel");
		
	}
	
	
	public void BBDaValutare_page(JPanel PanelBBDaValutare, CardLayout cardLayout,MyDTO_BB bb) {
		
		
		
		PanelBBDaValutare.removeAll();
		PanelBBDaValutare.revalidate();
		PanelBBDaValutare.repaint();
		
		ArrayList<MyDTO_S> servizi = bb.getServizi();
		
		
		
		String[] services = new String[servizi.size()];
		//conversione servizi dto in stringhe
		for(int i = 0;i<servizi.size();i++) {
			
			services[i] = new String(servizi.get(i).getTipo());
		}
		
		JLabel nomeBbLabel = new JLabel("NomeBB");
		nomeBbLabel.setForeground(new Color(205, 133, 63));
		nomeBbLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nomeBbLabel.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 20));
		nomeBbLabel.setBounds(114, 10, 179, 26);
		PanelBBDaValutare.add(nomeBbLabel);
		nomeBbLabel.setText(bb.getNome());
		
		JLabel lblLocalit = new JLabel("Località");
		lblLocalit.setForeground(new Color(205, 133, 63));
		lblLocalit.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblLocalit.setBounds(22, 55, 79, 13);
		PanelBBDaValutare.add(lblLocalit);
		 
		JLabel localitaBbLabel = new JLabel((String) bb.getLocalita());
		localitaBbLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		localitaBbLabel.setBounds(22, 70, 71, 21);
		PanelBBDaValutare.add(localitaBbLabel);
		localitaBbLabel.setText(bb.getLocalita());
		
		JLabel cgLabel = new JLabel("Costo giornaliero");
		cgLabel.setForeground(new Color(205, 133, 63));
		cgLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
		cgLabel.setBounds(22, 92, 111, 21);
		PanelBBDaValutare.add(cgLabel);
		
		JLabel cgBbLabel = new JLabel((String) null);
		cgBbLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		cgBbLabel.setBounds(22, 112, 71, 13);
		PanelBBDaValutare.add(cgBbLabel);
		cgBbLabel.setText(Float.toString(bb.getCosto_giornaliero()));
		
		
		JLabel lblServiziOfferti = new JLabel("Servizi offerti");
		lblServiziOfferti.setForeground(new Color(205, 133, 63));
		lblServiziOfferti.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblServiziOfferti.setBounds(199, 55, 124, 13);
		
		PanelBBDaValutare.add(lblServiziOfferti);
		
		JList lista_servizi = new JList(services);
		lista_servizi.setBackground(new Color(255, 250, 250));
		JScrollPane scrollPane = new JScrollPane(lista_servizi);
		scrollPane.setBounds(199, 73, 200, 47);
		PanelBBDaValutare.add(scrollPane);
		
		//VERIFICO SE LA VALUTAZIONE ESISTE GIA'
		
		contentPane.add(PanelBBDaValutare,"InfoBB");
		PanelBBDaValutare.setLayout(null);
		
		JButton CancelButton = new JButton("Annulla");
		CancelButton.setForeground(new Color(205, 133, 63));
		CancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				cardLayout.show(contentPane,"MainPanel");
			}
		});
		CancelButton.setBounds(147, 222, 85, 21);
		PanelBBDaValutare.add(CancelButton);
		
		ControllerCliente controller = ControllerCliente.getInstance();
		
		int voto = controller.strutturaValutata(username, bb.getId());
		
		gestioneStelle(voto, PanelBBDaValutare, controller, bb.getId());
		
	}
	
	public void gestioneStelle(int voto,JPanel PanelBBDaValutare, ControllerCliente controller, int idBB) {
		
		
		
		JButton star1 = new JButton("1");
		star1.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		star1.setBounds(49, 170, 42, 33);
		PanelBBDaValutare.add(star1);
		 
		JButton star2 = new JButton("2");
		star2.setFont(new Font("Tahoma", Font.BOLD, 13));
		star2.setBounds(112, 170, 42, 33);
		PanelBBDaValutare.add(star2);
		
		
		JButton star3 = new JButton("3");
		star3.setFont(new Font("Tahoma", Font.BOLD, 13));
		star3.setBounds(176, 170, 42, 33);
		PanelBBDaValutare.add(star3);
		
	
		
		JButton star4 = new JButton("4");
		star4.setFont(new Font("Tahoma", Font.BOLD, 13));
		star4.setBounds(240, 170, 42, 33);
		PanelBBDaValutare.add(star4);
		
		
		
		JButton star5 = new JButton("5");
		star5.setFont(new Font("Tahoma", Font.BOLD, 13));
		star5.setBounds(303, 170, 42, 33);
		PanelBBDaValutare.add(star5);
		
		FormValutaStruttura fvs = this;
		star1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				star1.setBackground(Color.RED);
				
				star2.setBackground(Color.getColor("240,240,240"));

				star3.setBackground(Color.getColor("240,240,240"));
				
				star4.setBackground(Color.getColor("240,240,240"));
	
				star5.setBackground(Color.getColor("240,240,240"));
				
				int nuovo_voto = 1;
				int vecchio_voto = controller.strutturaValutata(username, idBB);
				
				fvs.inserisciVoto(vecchio_voto, nuovo_voto, controller, idBB);
				
				
			
			}
			
		});
		
		star2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				star1.setBackground(Color.RED);
			
				star2.setBackground(Color.RED);
			
				star3.setBackground(Color.getColor("240,240,240"));
			
				star4.setBackground(Color.getColor("240,240,240"));
		
				star5.setBackground(Color.getColor("240,240,240"));
				
				int nuovo_voto = 2;			
				int vecchio_voto = controller.strutturaValutata(username, idBB);
				
				fvs.inserisciVoto(vecchio_voto, nuovo_voto, controller, idBB);
			}
		
		});
		
		
		star3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				star1.setBackground(Color.YELLOW);
	
				star2.setBackground(Color.YELLOW);
		
				star3.setBackground(Color.YELLOW);

				star4.setBackground(Color.getColor("240,240,240"));

				star5.setBackground(Color.getColor("240,240,240"));
				
				int nuovo_voto = 3;
				int vecchio_voto = controller.strutturaValutata(username, idBB);
				
				fvs.inserisciVoto(vecchio_voto, nuovo_voto, controller, idBB);
			}
			
			
		});
		
		star4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				star1.setBackground(Color.GREEN);
		
				star2.setBackground(Color.GREEN);
		
				star3.setBackground(Color.GREEN);
	
				star4.setBackground(Color.GREEN);
				
				star5.setBackground(Color.getColor("240,240,240"));
				
				int nuovo_voto = 4;
				int vecchio_voto = controller.strutturaValutata(username, idBB);
				
				fvs.inserisciVoto(vecchio_voto, nuovo_voto, controller, idBB);
			}
			
			
		});
		
		star5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				star1.setBackground(Color.GREEN);
				
				star2.setBackground(Color.GREEN);
				
				star3.setBackground(Color.GREEN);
				
				star4.setBackground(Color.GREEN);
			
				star5.setBackground(Color.GREEN);
				
				int nuovo_voto = 5;
				int vecchio_voto = controller.strutturaValutata(username, idBB);
				
				fvs.inserisciVoto(vecchio_voto, nuovo_voto, controller, idBB);
			}
			
		
		});
		
		
		JLabel lblLaTuaValutazione = new JLabel("VALUTAZIONE");
		lblLaTuaValutazione.setForeground(new Color(205, 133, 63));
		lblLaTuaValutazione.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLaTuaValutazione.setBounds(152, 140, 103, 13);
		PanelBBDaValutare.add(lblLaTuaValutazione);
		
		System.out.println(voto);
		
		
		switch(voto) {
		
			case 1:
			
				star1.setBackground(Color.RED);
				break;
			
			case 2:
				star1.setBackground(Color.RED);
				star2.setBackground(Color.RED);
				break;
			case 3:
				star1.setBackground(Color.YELLOW);
				star2.setBackground(Color.YELLOW);
				star3.setBackground(Color.YELLOW);
				break;
			case 4:
				star1.setBackground(Color.GREEN);
				star2.setBackground(Color.GREEN);
				star3.setBackground(Color.GREEN);
				star4.setBackground(Color.GREEN);
				break;
			case 5:
				star1.setBackground(Color.GREEN);
				star2.setBackground(Color.GREEN);
				star3.setBackground(Color.GREEN);
				star4.setBackground(Color.GREEN);
				star5.setBackground(Color.GREEN);
				break;
			
		}
		
	
		
	}
	
	public void inserisciVoto(int voto, int nuovo_voto, ControllerCliente controller, int idBB) {
		
		if(voto==-1) {
			
			controller.inserisciValutazione(username, idBB,nuovo_voto,false);
			System.out.println("Creo la nuova valutazione");
			
		}
		else if(voto!=nuovo_voto) {
			System.out.println("Aggiorno la valutazione esistente");
			controller.inserisciValutazione(username, idBB, nuovo_voto, true);
				
		}
	}
}
