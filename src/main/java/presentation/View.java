package presentation;

import model.Orders;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clasa View contine pagina principala de unde utilizatorul poate apasa unul din butoanele
 * Order,Client si Product. In functie de ce buton apasa, se va deschide o noua fereasta specififca
 * butonului respectiv. Ca sa realizez asta, am pus actionListener pe fiecare buton, iar in momentul in care
 * unul este apasat se face o instanta noua pentru clasa specificata.
 */
public class View {
    JFrame frame;
    JButton studentBtn, productBtn, orderBtn, closeBtn;
    JLabel name;
    JPanel panel;
    Orders orders = new Orders();

    public View(){
        frame = new JFrame("Order Management");
        frame.setSize(300,260);

        Font  f4  = new Font(Font.DIALOG_INPUT,  Font.BOLD|Font.ITALIC, 15);
        Font  f3  = new Font(Font.DIALOG,  Font.BOLD, 15);
        name = new JLabel("ORDER MANAGEMENT");
        name.setFont(f3);
        //name.setBounds(20,100,30,20);
        panel = new JPanel();
        panel.add(name);
        panel.setBackground(new Color(40,200,50));
        //panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(new Insets(30, 200, 150, 200)));
        studentBtn = new JButton("Client");
        studentBtn.setBounds(200,100,100,30);
        panel.add(studentBtn);

        productBtn = new JButton("Product");
        productBtn.setBounds(200,100,100,30);
        panel.add(productBtn);

        orderBtn = new JButton("Order");
        orderBtn.setBounds(200,100,100,30);
        panel.add(orderBtn);

        closeBtn = new JButton("Close");
        closeBtn.setBounds(200,100,100,30);
        panel.add(closeBtn);

        panel.setBackground(new Color(153, 255, 255));

        frame.add(panel);
        frame.setVisible(true);
        frame.setResizable(false);

        studentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ClientGUI s = new ClientGUI();
            }
        });

        productBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductGUI p = new ProductGUI();
            }
        });

        orderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderGUI o = new OrderGUI();
            }
        });

        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }
}
