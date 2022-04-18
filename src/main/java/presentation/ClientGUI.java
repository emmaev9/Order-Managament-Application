package presentation;

import bll.ClientBLL;
import model.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa ClientGUI contine un JTable care contine toti clientii prezenti in baza de date, field-uri
 * speficice pentru atributele clientului(id, nume, adresa, email, varsta) si nu in ultimul rand butoane pentru
 * stergerea, adaugarea sau modificarea unui client. Dupa fiecare apasare a unui buton se apeleaza functia
 * refreshTable care sterge toate datele din tabel si le insereaza iar pentru a vedea modificarile in timp real.
 */
public class ClientGUI extends Frame {
    JFrame frame;

    JLabel lableId, lableAddress, lableName, lableAge, lableEmail;
    JTextField textId, textAddress, textEmail, textAge, textName;
    JButton addClientBtn, deleteClientBtn, updateClientBtn, showClientBtn, backBtn, refreshBtn;
    JPanel panel1, panel2;
    JTable clientsTable;
    DefaultTableModel clientsTableModel;
    ClientBLL clientBLL;
    List<Client> listOfClients = new ArrayList<Client>();


    public ClientGUI() {
        clientBLL = new ClientBLL();
        frame = new JFrame("CLIENT");
        frame.setVisible(true);
        frame.setSize(1000, 400);
        frame.setLayout(new GridLayout());

        panel1 = new JPanel();
        //panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setBorder(new EmptyBorder(new Insets(30, 200, 150, 200)));
        frame.add(panel1);
        panel1.setBounds(10, 20, 100, 300);

        lableId = new JLabel("ID");
        panel1.add(lableId);
        textId = new JTextField(15);
        panel1.add(textId);

        lableName = new JLabel("Name");
        panel1.add(lableName);
        textName = new JTextField(15);
        panel1.add(textName);

        lableAddress = new JLabel("Address");
        panel1.add(lableAddress);
        textAddress = new JTextField(15);
        panel1.add(textAddress);

        lableAge = new JLabel("Age");
        panel1.add(lableAge);
        textAge = new JTextField(15);
        panel1.add(textAge);

        lableEmail = new JLabel("Email");
        panel1.add(lableEmail);
        textEmail = new JTextField(15);
        panel1.add(textEmail);

        //frame.setBackground(new Color(30,200,50));

        panel2 = new JPanel();
        //panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel2.setBorder(new EmptyBorder(new Insets(75, 200, 150, 200)));
        frame.add(panel2);

        addClientBtn = new JButton("Add new client");
        panel2.add(addClientBtn);

        deleteClientBtn = new JButton("Delete client");
        panel2.add(deleteClientBtn);

        updateClientBtn = new JButton("Update client");
        panel2.add(updateClientBtn);

        backBtn = new JButton("Close");
        backBtn.setBounds(10, 20, 10, 100);
        panel2.add(backBtn);
        panel1.setBackground(new Color(153, 255, 255));
        panel2.setBackground(new Color(153, 255, 255));

        listOfClients = clientBLL.findAllClients();
        clientsTableModel = new DefaultTableModel();
        Properties<Client> properties = new Properties<Client>();
        clientsTableModel = properties.retrieveProperties(listOfClients, clientsTableModel);
        clientsTable = new JTable(clientsTableModel);
        clientsTable.setBounds(30, 40, 400, 100);
        clientsTable.setBackground(new Color(220, 255, 255));
        JScrollPane sp = new JScrollPane(clientsTable);
        sp.setPreferredSize(new Dimension(600,300));
        frame.add(sp);
        frame.add(panel2);
        frame.setBackground(new Color(153, 255, 255));
        clientsTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = clientsTable.getSelectedRow();
                System.out.println(i);
                TableModel model = clientsTable.getModel();
                textId.setText(model.getValueAt(i, 0).toString());
                textName.setText(model.getValueAt(i, 1).toString());
                textAddress.setText(model.getValueAt(i, 2).toString());
                textEmail.setText(model.getValueAt(i, 3).toString());
                textAge.setText(model.getValueAt(i, 4).toString());
                System.out.println(model.getValueAt(i, 1).toString());
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        addClientBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clientID = textId.getText().toString();
                String clientName = textName.getText().toString();
                String clientAddress = textAddress.getText().toString();
                String clientEmail = textEmail.getText().toString();
                String clientAge = textAge.getText().toString();
                System.out.println(clientID + " " + textAge.getText());
                Client client = new Client(Integer.parseInt(clientID), clientName, clientAddress, Integer.parseInt(clientAge), clientEmail);
                clientBLL.insertClient(client);
                refreshTable();
            }
        });

        deleteClientBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clientId = textId.getText().toString();
                Client s = clientBLL.findClientById(Integer.parseInt(clientId));
                clientBLL.deleteClient(s);
                clientsTable.addNotify();
                refreshTable();

            }
        });

        updateClientBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clientID = textId.getText().toString();
                String clientName = textName.getText().toString();
                String clientAddress = textAddress.getText().toString();
                String clientEmail = textEmail.getText().toString();
                String clientAge = textAge.getText().toString();
                System.out.println(clientID + " " + textAge.getText());
                Client client = new Client(Integer.parseInt(clientID), clientName, clientAddress, Integer.parseInt(clientAge), clientEmail);
                clientBLL.updateClient(client);
                clientsTable.addNotify();
                refreshTable();
            }
        });

    }

    public void refreshTable() {

        clientsTableModel.setRowCount(0);
        clientsTableModel.setColumnCount(0);
        listOfClients = clientBLL.findAllClients();

        Properties<Client> properties = new Properties<Client>();
        clientsTableModel = properties.retrieveProperties(listOfClients, clientsTableModel);
    }


    /*public void addActionListenerAddBtn(ActionListener actionListener){
        addStudentBtn.addActionListener(actionListener);
    }
    public void addActionListenerDeleteBtn(ActionListener actionListener){
        deleteStudentBtn.addActionListener(actionListener);
    }
    public void addActionListenerUpdateBtn(ActionListener actionListener){
        updateStudentBtn.addActionListener(actionListener);
    }
    public void setAddActionListenerRefreshBtn(ActionListener actionListener){
        refreshBtn.addActionListener(actionListener);
    } */

    public String getId() {
        return textId.getText();
    }

    public String getName() {
        return textName.getText();
    }

    public String getAddress() {
        return textAddress.getText();
    }

    public String getAge() {
        return textAge.getText();
    }

    public String getEmail() {
        return textEmail.getText();
    }
}