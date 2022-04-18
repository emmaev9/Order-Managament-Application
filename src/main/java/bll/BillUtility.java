package bll;

import model.Client;
import model.Orders;
import model.Product;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BillUtility {
    /**
     * Salveaza intr-un fisier datele de facturare.
     */

    public static void createBill(Orders order){

        ProductBLL productBLL = new ProductBLL();
        ClientBLL clientBLL = new ClientBLL();

        Product p = productBLL.findProductById(order.getProductId());
        Client c = clientBLL.findClientById(order.getClientId());

        String numeFisier = "factura" + order.getOrderId() + ".txt";

        File file = new File(numeFisier);

        try{
            FileWriter billWriter = new FileWriter(file, false);

            billWriter.write("Factura\n\n");
            billWriter.write("Client: " + c.getName()+"\n");
            billWriter.write("Email: " + c.getEmail() + "\n");
            billWriter.write("Adresa de livrare: " + c.getAddress() + "\n\n");
            billWriter.write("Produs: " + p.getName() + "   Pret: " + p.getPrice() + "\n");
            billWriter.write("Cantitate: " + order.getQuantity() + "\n");
            billWriter.write("Pret total: " + order.getQuantity()* p.getPrice()+ "\n");

            billWriter.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }


    }

}
