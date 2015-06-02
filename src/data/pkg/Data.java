/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.pkg;

import java.util.ArrayList;

/**
 *
 * @author Master
 */
public class Data {
    
    private  ArrayList<Number> forca = new ArrayList();
    private  ArrayList<Number> deslocamento = new ArrayList();
    
    public Data(ArrayList f, ArrayList d){
        forca = f;
        deslocamento = d;
    }
    
    public Data(){
        
    }
    
    public void addForca(Number f){
        forca.add(f);
    }
    public void addDeslocamento(Number d){
        deslocamento.add(d);
    }
    public double getForca(int pos){
        return forca.get(pos).doubleValue();
    }
    public double getDeslocamento(int pos){
        return deslocamento.get(pos).doubleValue();
    }
    public void resetAll(){
        forca.clear();
        deslocamento.clear();  
    }    
    public int getLength(){
        return forca.size();
    }
    public int getForcaSize(){
        return forca.size();
    }
    
    
}
