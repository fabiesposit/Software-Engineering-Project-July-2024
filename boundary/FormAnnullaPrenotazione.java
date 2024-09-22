package boundary;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import controller.ControllerCliente;
import dto.MyDTO_BB;
import dto.MyDTO_Prenotazione;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Toolkit;

public class FormAnnullaPrenotazione extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String username;
	private int id_prenotazione;
	private ArrayList<MyDTO_Prenotazione> lista_p;
	private ControllerCliente controller ;
	private String[] lista ;
	private JList list;
	private DefaultListModel<String> listModel;
	

	
	public String[] getListaPrenotazioni() {
		 
		
		 
		//restituisce lista prenotazioni(dto)
		lista_p = controller.visualizzaPrenotazioni(username);
		
		String[] listaStringheP = new String[lista_p.size()];
		
		for(int i=0;i<lista_p.size();i++) {
			 
			//System.out.println(lista_p.get(i).getNome());
			MyDTO_BB bb = controller.VisualizzaBB(lista_p.get(i).getIdBB());
			
			
			//costruisco array di stringhe da far visualizzare
			listaStringheP[i] = lista_p.get(i).getId_prenotazione()+ "- B&B:"+bb.getNome()+ " "+lista_p.get(i).getData_prenotazione()+ " "+ lista_p.get(i).getCosto_totale();
			
	
		}
		
		
		
		return listaStringheP;
	}
	//metodo per avere lista aggiornata
    public void aggiornaListaPrenotazioni() {
        listModel.clear();
        lista = getListaPrenotazioni();
        for (String prenotazione : lista) {
            listModel.addElement(prenotazione);
        }
    }
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//FormAnnullaPrenotazione frame = new FormAnnullaPrenotazione("user");
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
	public FormAnnullaPrenotazione(String Username) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FormAnnullaPrenotazione.class.getResource("/resources/immagini/logo.jpg")));
		
		this.controller = ControllerCliente.getInstance();
		
		
		this.username = Username;
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		listModel = new DefaultListModel<>();
		
		aggiornaListaPrenotazioni();
		list = new JList(listModel);
		list.setForeground(new Color(205, 133, 63));
		list.setBackground(new Color(255, 255, 240));
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
				int i = list.getSelectedIndex();
				
				
				if(!e.getValueIsAdjusting()) {
					
		            if (i != -1) {
		                // Continua solo se è stato effettivamente selezionato un elemento
		                System.out.println("indice elemento selezionato " + i);
		                StringTokenizer str_tok = new StringTokenizer(lista[i], "-");
		                id_prenotazione = Integer.valueOf(str_tok.nextToken()).intValue();
		                System.out.println(id_prenotazione);
		            }
					
				}
			}
				
			
		});
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(21, 67, 383, 150);
		contentPane.add(scrollPane);
		
		JButton ReturnButton = new JButton("Annulla");
		ReturnButton.setForeground(new Color(205, 133, 63));
		ReturnButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		ReturnButton.setBounds(84, 227, 89, 23);
		contentPane.add(ReturnButton);
		
		JButton ConfirmButton = new JButton("Conferma Annullamento");
		ConfirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//verifica date 
				if(controller.annullaPrenotazione(Username, id_prenotazione)) {
					JOptionPane.showMessageDialog(null, "Annullamento è stato effettuato entro il limite delle 48h, il costo totale verrà addebitato lo stesso");
					JOptionPane.showMessageDialog(null, "Addebito Effettuato sulla carta ...TODO");
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Annullamento è stato effettuato correttamente senza addebito");
					aggiornaListaPrenotazioni();
				}
			}
		});
		ConfirmButton.setBounds(183, 227, 177, 23);
		contentPane.add(ConfirmButton);
		
		JLabel lblNewLabel_1 = new JLabel("Le tue prenotazioni");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(new Color(205, 133, 63));
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 22));
		lblNewLabel_1.setBounds(84, 15, 261, 22);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblGliAnnullamentiEffettuati = new JLabel("Gli annullamenti effettuati prima di 48h dal soggiorno sono gratutiti!");
		lblGliAnnullamentiEffettuati.setForeground(new Color(205, 133, 63));
		lblGliAnnullamentiEffettuati.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblGliAnnullamentiEffettuati.setBounds(32, 36, 383, 21);
		contentPane.add(lblGliAnnullamentiEffettuati);
		
		
		
		
		
		
	}

}
