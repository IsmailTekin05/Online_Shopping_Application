/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package errors;

import javax.swing.JOptionPane;

/**
 *
 * @author Ismail
 */
public class InvalidLoginInputError {
    
    public void message(String message) {
        JOptionPane.showMessageDialog(null, message, "Login Error", JOptionPane.ERROR_MESSAGE);
    }
    
}
