/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author ndnha
 */
public class DataValidator {
    public static void validateEmpty (JTextField field, StringBuilder sb, String errorMessage) {
        if (field.getText().equals("")) {
            sb.append(errorMessage).append("\n");
            field.setBackground(Color.red);
            field.requestFocus();
        }
        else {
            field.setBackground(Color.white);
        }
    }
    
    public static void validateEmpty (JPasswordField field, StringBuilder sb, String errorMessage) {
        String password = new String(field.getPassword());
        if (password.equals("")) {
            sb.append(errorMessage).append("\n");
            field.setBackground(Color.red);
            field.requestFocus();
        }
        else {
            field.setBackground(Color.white);
        }
    }
    
    public static void validateEmpty (JComboBox check, StringBuilder sb, String errorMessage) {
        if (check.getSelectedIndex() == 0) {
            sb.append(errorMessage).append("\n");
            check.setBackground(Color.red);
            check.requestFocus();
        }
        else {
            check.setBackground(Color.white);
        }
    }
}
