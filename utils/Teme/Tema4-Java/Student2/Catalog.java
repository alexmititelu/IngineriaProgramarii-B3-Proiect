package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Exception;

public class Catalog {
    private ArrayList<Item> stuff;

    public void add(Item ... o ){
        Collections.addAll(stuff, o);
    }

        public void list()
        {
            for(Item i: stuff)
                System.out.println(i.getName());
        }

    public Catalog() {
        this.stuff = new ArrayList<Item>();

    }
    //serializare
    public void save(){
        
            ArrayList<Object> data = new ArrayList<>(stuff);
            
        try{
            FileOutputStream fileOut = new FileOutputStream("catalog.txt");
            ObjectOutputStream obj = new ObjectOutputStream(fileOut);
            obj.writeObject(data);
            obj.close();
            fileOut.close(); 
            System.out.println("Serialization complete");
        }
        
        catch(IOException i){
            System.out.println("Serialization failure");
            i.printStackTrace();
        }
    
    }
   //deserializare
    public void load(){
        ArrayList<Object> deserialized = new ArrayList<Object>();
        try{
                FileInputStream fileIn = new FileInputStream("catalog.txt");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                deserialized = (ArrayList<Object>)in.readObject();
                in.close();
                fileIn.close();
                System.out.println("Deserialization complete, the object are:");
                
           
        }catch (IOException i) {
         System.out.println("I/O Error");
         i.printStackTrace();
         return;}
        
        catch (ClassNotFoundException c) {
         System.out.println("No items found");
         c.printStackTrace();
         return;}
        
       for(Object o : deserialized){
           System.out.println(o);
       }
        
    }


}
