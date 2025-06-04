/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author D E L L
 */
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(int id) {
        super("Order with ID " + id + " not found.");
    }
}


