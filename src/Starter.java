/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Ismail
 */
public class Starter {
    
    public static void main(String args[]) {
        FirstFrame frame = new FirstFrame();
        frame.setVisible(true);

        boolean response = frame.getResponse();
        frame.dispose();

        if (response) {
            new LoginPage().setVisible(true);
        } else {
            new RegisterPage().setVisible(true);
        }
    }


}
