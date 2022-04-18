package presentation;

import bll.ProductBLL;
import model.Product;
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

public class ProductGUI extends JFrame {
    JFrame frame;
    JLabel lableId, lablePrice, lableName, lableQuantity;
    JTextField textId, textPrice, textName, textQuantity;
    JButton addProductBtn, deleteProductBtn, updateProductBtn, showProductsBtn, backBtn, refreshBtn;
    JPanel panel1, panel2;
    JTable productsTable;
    DefaultTableModel productsTableModel;
    ProductBLL productBLL;

    /**
     * Clasa ProductGUI contine un JTable care contine toate pprodusele prezente in baza de date, fielduri speficice pentru atributele produsului(id, nume,pret, cantitate) si nu in ultimul rand butoane pentru
     * stergerea, adaugarea sau modificarea unui produs. Dupa fiecare apasare a unui buton se apeleaza functia
     * refreshTable care sterge toate datele din tabel si le insereaza iar pentru a vedea modificarile in timp real
     */
    public  ProductGUI(){

        productBLL = new ProductBLL();

        frame = new JFrame("PRODUCT");
        frame.setVisible(true);
        frame.setSize(700,300);

        panel1 = new JPanel();
       panel1.setBorder(new EmptyBorder(new Insets(30, 200, 150, 200)));
       // panel1.setLayout(new FlowLayout());


        lableId = new JLabel("ID");
        panel1.add(lableId);
        textId = new JTextField(10);
        panel1.add(textId);

        lableName = new JLabel("Name");
        panel1.add(lableName);
        textName = new JTextField(10);
        panel1.add(textName);

        lablePrice = new JLabel("Price");
        panel1.add(lablePrice);
        textPrice = new JTextField(10);
        panel1.add(textPrice);

        lableQuantity = new JLabel("Quantity");
        panel1.add(lableQuantity);
        textQuantity = new JTextField(10);
        panel1.add(textQuantity);


        panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());
       // panel2.setBorder(new EmptyBorder(new Insets(30, 200, 150, 200)));

        addProductBtn = new JButton("Add new product");
        panel2.add(addProductBtn);

        deleteProductBtn = new JButton("Delete product");
        panel2.add(deleteProductBtn);

        updateProductBtn = new JButton("Update product");
        panel2.add(updateProductBtn);



        panel2.setBorder(new EmptyBorder(new Insets(75, 200, 150, 200)));

        backBtn = new JButton("Close");
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        panel2.add(backBtn);
        panel1.setBackground(new Color(153, 255, 255));
        panel2.setBackground(new Color(153, 255, 255));
        ArrayList<Product> products = (ArrayList<Product>) productBLL.findAllProducts();
        Properties<Product> properties = new Properties<Product>();
        productsTableModel = new DefaultTableModel();
        productsTableModel = properties.retrieveProperties(products,productsTableModel);

        productsTable = new JTable(productsTableModel);
        productsTable.setBounds(30,40,400,100);
        productsTable.setBackground(new Color(220, 255, 255));
        JScrollPane sp = new JScrollPane(productsTable);
        frame.add(panel1);
        frame.add(sp);
        frame.add(panel2);
        frame.setLayout(new GridLayout());

        addProductBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productID = textId.getText().toString();
                String productName = textName.getText().toString();
                String productPrice = textPrice.getText().toString();
                String productQuantity = textQuantity.getText().toString();
                Product produs = new Product(Integer.parseInt(productID), productName, Integer.parseInt(productQuantity), Integer.parseInt(productPrice));
                productBLL.insertProduct(produs);
                refreshTable();
            }
        });

        deleteProductBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = productsTable.getSelectedRow();
                String productID = productsTableModel.getValueAt(i,0).toString();
                Product p = productBLL.findProductById(Integer.parseInt(productID));
                productBLL.deleteProduct(p);
                refreshTable();

            }
        });


        updateProductBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productID = textId.getText().toString();
                String productName = textName.getText().toString();
                String productPrice = textPrice.getText().toString();
                String productQuantity = textQuantity.getText().toString();
                Product produs = new Product(Integer.parseInt(productID), productName, Integer.parseInt(productQuantity), Integer.parseInt(productPrice));
                productBLL.updateProduct(produs);
                refreshTable();
            }
        });

        productsTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = productsTable.getSelectedRow();
                System.out.println(i);
                TableModel model = productsTable.getModel();
                textId.setText(model.getValueAt(i, 0).toString());
                textName.setText(model.getValueAt(i, 1).toString());
                textPrice.setText(model.getValueAt(i, 3).toString());
                textQuantity.setText(model.getValueAt(i,2).toString());

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
    }

    public void refreshTable() {
        productsTableModel.setRowCount(0);
        productsTableModel.setColumnCount(0);
        ArrayList<Product> products = (ArrayList<Product>) productBLL.findAllProducts();
        Properties<Product> properties = new Properties<Product>();
        productsTableModel = properties.retrieveProperties(products,productsTableModel);
    }
    public void closeWindow(){
        frame.dispose();
    }
}
