package presentation;

import model.Client;

import javax.swing.table.DefaultTableModel;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;


/**
 * Clasa Properties care are o singura metoda, si anume retriveProperties(List objects,
 * DefautTableModel tableModel) care returneaza un defaultTableModel care va avea ca header-uri la
 * coloane numele field-urilor specific clasei T si va contine pe linii obiectele de tipul T trimise ca
 * argumente.
 */
public class Properties<T> {

    @SuppressWarnings("unchecked")
    public Properties() {

    }

    public DefaultTableModel retrieveProperties(List<T> objects, DefaultTableModel tableModel) {
        int numberOfFields = 0;
        Object oneObject = objects.get(0);
        for (Field field : oneObject.getClass().getDeclaredFields()) {
            tableModel.addColumn(field.getName());
            numberOfFields++;
        }
       try {
            String data[][] = new String[objects.size()][numberOfFields];
            for (int i = 0; i < objects.size(); i++) {
                Object ob = objects.get(i);
                int j = 0;
                for (Field field : oneObject.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    Object value = field.get(ob);
                    data[i][j] = value.toString();
                    j++;
                }
                tableModel.addRow(data[i]);
            }
        } catch (Exception e) {
                System.out.println(e.getMessage());
        }
        return tableModel;
    }
}
