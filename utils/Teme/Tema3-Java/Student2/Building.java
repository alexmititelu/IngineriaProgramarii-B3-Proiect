/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company;

/**
 *
 * @author Eduard
 */
public class Building extends Item implements Asset {
    int area;

    public Building(String name, int area, int price) {
        this.name=name;
        this.area=area;
        this.price=price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    public int computeProfit(){
        return this.area/this.price;
    }
    @Override
    public String toString(){
        return name;
    }
    
    
}
