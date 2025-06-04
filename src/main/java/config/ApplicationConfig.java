/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 
package config;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;
import java.util.*;

import resource.BookResource;
import exception.mapper.BookNotFoundMapper;

import resource.AuthorResource;
import exception.mapper.AuthorNotFoundMapper;

import resource.CustomerResource;
import exception.mapper.CustomerNotFoundMapper;

import resource.CartResource;
import exception.mapper.CartNotFoundMapper;

import resource.OrderResource;
//import exception.mapper.OrderNotFoundMapper;

@ApplicationPath("/api")
public class ApplicationConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(BookResource.class);
        classes.add(BookNotFoundMapper.class);
        classes.add(AuthorResource.class);
        classes.add(AuthorNotFoundMapper.class);
        classes.add(CustomerResource.class);
        classes.add(CustomerNotFoundMapper.class);
        classes.add(CartResource.class);
        classes.add(CartNotFoundMapper.class);
        classes.add(OrderResource.class);
        return classes;
    }
}

