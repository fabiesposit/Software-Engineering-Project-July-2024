package boundary;

import java.awt.BorderLayout;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.ControllerCliente;
import dto.MyDTO_BB;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import java.awt.Toolkit;


public class FrameVisualizzazioneBB extends JFrame {
	private JPanel contentPane;
	private String username;
	private String localita;
	private String date; 
	private JTable table;
	private ArrayList<MyDTO_BB> lista_bb;
	
	private ControllerCliente controller;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameVisualizzazioneBB frame = new FrameVisualizzazioneBB("debug",null,null);
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
	
	public FrameVisualizzazioneBB(String username,String localita, String Date) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameVisualizzazioneBB.class.getResource("/resources/immagini/logo.jpg")));
		
		
		this.localita = localita;
		this.username = username;
		this.date = Date;
		
		
		this.controller= ControllerCliente.getInstance();
		if(localita == null && date == null) {
		this.lista_bb = controller.visualizzaListaBB();
		}
		else if (localita!=null && date == null) {
		this.lista_bb = controller.visualizzaListaBBPerLocalita(this.localita);
		}
		else if(localita==null && date!=null) {
		this.lista_bb = controller.visualizzaListaBBPerData(date);
		}
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String[] columnNames = {"Nome","Localita","Recensioni","Posti letto","Costo"};
		DefaultTableModel tableModel = new DefaultTableModel(columnNames,0) {
			
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		for(int i = 0; i<lista_bb.size();i++) {
			
			
			Object[] rowData = {lista_bb.get(i).getNome(), lista_bb.get(i).getLocalita(),lista_bb.get(i).getMedia_recensioni(),lista_bb.get(i).getPosti_letto(),lista_bb.get(i).getCosto_giornaliero()};
			
			tableModel.addRow(rowData);

		}
		
        table = new JTable(tableModel);
        table.setBackground(new Color(255, 248, 220));
        //table.setCellSelectionEnabled(true);
        table.setRowSelectionAllowed(true);
        //table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setViewportBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(160, 82, 45), new Color(222, 184, 135)));
        tableScrollPane.setBounds(21, 73, 390, 146);
        contentPane.add(tableScrollPane);
		tableScrollPane.setBackground(new Color(255, 248, 220));
		
		JLabel lblNewLabel_1 = new JLabel("I nostri B&B");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(new Color(205, 133, 63));
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 22));
		lblNewLabel_1.setBounds(115, 21, 177, 22);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblPrenotaneSubitoUno = new JLabel("Prenotane subito uno!");
		lblPrenotaneSubitoUno.setForeground(new Color(184, 134, 11));
		lblPrenotaneSubitoUno.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblPrenotaneSubitoUno.setBounds(144, 42, 130, 21);
		contentPane.add(lblPrenotaneSubitoUno);
		
		JLabel lblEnjoyYourbb = new JLabel("Enjoy YourB&B!");
		lblEnjoyYourbb.setForeground(new Color(184, 134, 11));
		lblEnjoyYourbb.setFont(new Font("SansSerif", Font.ITALIC, 12));
		lblEnjoyYourbb.setBounds(162, 229, 96, 21);
		contentPane.add(lblEnjoyYourbb);

		JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(255, 255, 240));
       
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        
        TableColumn column1 = table.getColumnModel().getColumn(0);
        TableColumn column2 = table.getColumnModel().getColumn(1);
        TableColumn column3 = table.getColumnModel().getColumn(2);
        TableColumn column4 = table.getColumnModel().getColumn(3);
        TableColumn column5 = table.getColumnModel().getColumn(4);
        
        column1.setPreferredWidth(150); // Colonna più larga
        column2.setPreferredWidth(100); 
        column3.setPreferredWidth(100); 
        column4.setPreferredWidth(100); 
        column5.setPreferredWidth(100); 
       
        
        //NEW
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	int i = table.getSelectedRow();
				
				
					
					BBFrame bbf = new BBFrame(lista_bb.get(i),username,date);
					bbf.setVisible(true);
             
            }
        });
		
        
		
	}
}
