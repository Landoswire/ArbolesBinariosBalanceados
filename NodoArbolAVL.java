/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete;

/**
 *
 * @author Issei
 */
public class NodoArbolAVL {
    int dato, fe; //factor de equilibrio
    NodoArbolAVL hijoIzquierdo,hijoDerecho;
            
    public NodoArbolAVL(int d){//constructor va a recibir el dato
        this.dato=d;
        this.fe=0;
        this.hijoDerecho=null;
        this.hijoDerecho=null;
    }
}
