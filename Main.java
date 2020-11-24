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
public class Main {
    public static void main(String[] args){
        ArbolAVL arbolitoAVL = new ArbolAVL();
        //Insertando nodos
        arbolitoAVL.insertar(10);
        arbolitoAVL.insertar(5);
        arbolitoAVL.insertar(13);
        arbolitoAVL.insertar(1);
        arbolitoAVL.insertar(6);
        arbolitoAVL.insertar(17);
        
        arbolitoAVL.preOrden(arbolitoAVL.obtenerRaiz());
    }
}
