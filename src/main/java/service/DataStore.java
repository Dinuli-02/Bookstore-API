/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// --- service/DataStore.java ---
package service;

import model.Book;
import model.Author;
import model.Customer;
import model.CartItem;
import model.Order;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DataStore {
    public static Map<Integer, Book> books = new HashMap<>();
    public static AtomicInteger bookIdCounter = new AtomicInteger(1);
    
    public static Map<Integer, Author> authors = new HashMap<>();
    public static AtomicInteger authorIdCounter = new AtomicInteger(1);
    
    public static Map<Integer, Customer> customers = new HashMap<>();
    public static AtomicInteger customerIdCounter = new AtomicInteger(1);


    public static Map<Integer, List<CartItem>> cartItems = new HashMap<>();
    public static AtomicInteger cartItemIdCounter = new AtomicInteger(1);
    
    public static Map<Integer, Order> orders = new HashMap<>();
    public static AtomicInteger orderIdCounter = new AtomicInteger(1);
   
}

