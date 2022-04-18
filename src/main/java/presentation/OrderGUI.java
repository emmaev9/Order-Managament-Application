package presentation;

import bll.BillUtility;
import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
//import com.sun.org.apache.xpath.internal.operations.Or;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Client;
import model.Orders;
import model.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrderGUI {
    /**
     * Clasa OrderGUI contine 3 JTable-uri. Primul reprezinta tabelul cu client, al doilea reprezinta lista
     * cu comenzile, iar al treilea reprezinta lista cu produsele. Cu ajutorul unui mouseListener se va selecta un
     * rand din tabelul clientilor si un rand din tabelul produselor, iar dupa ce se va introduce o valoare in
     * JTextField-uri pentru cantitate, se va apasa butonul make order si de va face automat o comanda noua
     * care va fi inserata in tabel
     */
    JFrame frame, ordersFrame;
    JLabel labelID, labelProductID, labelClientID, labelQuantity, labelError;
    JTextField textQuantity;

    JButton createOrderBtn;
    JPanel panel1, panel2, mainPanel, panel3;
    JTable clientsTable, productsTable, ordersTable;
    DefaultTableModel clientTableModel, productsTableModel, ordersTableModel;
    OrderDAO orderDAO;
    OrderBLL orderBLL;
    ClientBLL clientBLL;
    ProductBLL productBLL;


    public OrderGUI() {

        clientBLL = new ClientBLL();
        productBLL = new ProductBLL();
        orderBLL = new OrderBLL();

        frame = new JFrame("ORDER");
        frame.setVisible(true);
        frame.setSize(900, 500);
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        mainPanel = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel2.setLayout(new FlowLayout());
        panel3.setLayout(new FlowLayout());


        clientTableModel = new DefaultTableModel();
        productsTableModel = new DefaultTableModel();
        ordersTableModel = new DefaultTableModel();

        Properties<Client> clientProp = new Properties<Client>();
        clientProp.retrieveProperties(clientBLL.findAllClients(), clientTableModel);
        Properties<Product> productProp = new Properties<Product>();
        productProp.retrieveProperties(productBLL.findAllProducts(), productsTableModel);
        Properties<Orders> orderProp = new Properties<Orders>();
        orderProp.retrieveProperties(orderBLL.findAllOrders(), ordersTableModel);

        clientsTable = new JTable(clientTableModel);
        productsTable = new JTable(productsTableModel);
        ordersTable = new JTable(ordersTableModel);
        //clientsTable.setPreferredSize(new Dimension(300,200));
        clientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

       // clientsTable.setBounds(30, 40, 400, 100);
        clientsTable.setBackground(new Color(220, 255, 255));
        JScrollPane clientTableScrollPane = new JScrollPane(clientsTable);
        clientTableScrollPane.setPreferredSize(new Dimension(380,300));
        panel1.add(clientTableScrollPane);

        productsTable.setBackground(new Color(220, 255, 255));
        JScrollPane productTableScrollPane = new JScrollPane(productsTable);
        productTableScrollPane.setPreferredSize(new Dimension(250,300));
        panel2.add(productTableScrollPane);

        ordersTable.setBackground(new Color(220, 255, 255));
        JScrollPane orderTableScrollPane = new JScrollPane(ordersTable);
        orderTableScrollPane.setPreferredSize(new Dimension(210,300));
        panel3.add(orderTableScrollPane);

        panel1.setBackground(new Color(153, 255, 255));
        panel2.setBackground(new Color(153, 255, 255));
        panel3.setBackground(new Color(153, 255, 255));
        mainPanel.setBackground(new Color(153, 255, 255));
        frame.setBackground(new Color(153, 255, 255));

        clientsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

       // mainPanel.setBorder(new EmptyBorder(new Insets(30, 200, 150, 200)));

        createOrderBtn = new JButton("Make Order");
        createOrderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idClient = Integer.parseInt(clientTableModel.getValueAt(clientsTable.getSelectedRow(), 0).toString());
                int idProdus = Integer.parseInt(productsTableModel.getValueAt(productsTable.getSelectedRow(), 0).toString());

                int quantity = Integer.parseInt(textQuantity.getText());
                Product selectedProduct = productBLL.findProductById(idProdus);
                if(selectedProduct.getQuantity() < quantity){
                    labelError.setText("Not enough items in stock");
                }
                else{
                    Orders orders = new Orders(0, idClient, idProdus, quantity);
                    int returnedKey = orderBLL.insertOrder(orders);
                    orders.setOrderId(returnedKey);
                    BillUtility.createBill(orders);

                    refreshTables();
                }
            }
        });
        //GroupLayout layout = new GroupLayout(mainPanel);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        labelQuantity = new JLabel("Quantity");

        mainPanel.add(labelQuantity);

        textQuantity = new JTextField(10);
        textQuantity.setPreferredSize(new Dimension(10,20));
        textQuantity.setEditable(true);
      //  textQuantity.setBorder(BorderFactory.createEmptyBorder(200,300,10,10));
        mainPanel.add(textQuantity);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,400,50,300));
        mainPanel.add(createOrderBtn);

        labelError = new JLabel("");

        mainPanel.add(labelError);

        frame.add(mainPanel, BorderLayout.PAGE_END);
        frame.add(panel1, BorderLayout.WEST);
        frame.add(panel2, BorderLayout.EAST);
        frame.add(panel3, BorderLayout.CENTER);

    }

    public void refreshTables() {

        ordersTableModel.setRowCount(0);
        ordersTableModel.setColumnCount(0);
        ArrayList<Orders> orders = (ArrayList<Orders>)orderBLL.findAllOrders();
        Properties<Orders> ordersProp = new Properties<Orders>();
        ordersProp.retrieveProperties(orders, ordersTableModel);

        productsTableModel.setRowCount(0);
        productsTableModel.setColumnCount(0);
        ArrayList<Product> products = (ArrayList<Product>)productBLL.findAllProducts();
        Properties<Product> productsProp = new Properties<Product>();
        productsProp.retrieveProperties(products, productsTableModel);

        labelError.setText("");

    }

}
