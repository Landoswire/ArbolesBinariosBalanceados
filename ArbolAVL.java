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
public class ArbolAVL {
    private NodoArbolAVL raiz;
    public ArbolAVL(){
        raiz=null;
    }
    public NodoArbolAVL obtenerRaiz(){
        return raiz;
    }
    
    //Buscar un nodo en el arbol
    public NodoArbolAVL buscar(int d, NodoArbolAVL r){//necesitamos el dato y la raiz
        if(raiz==null){//si esta vacio
            return null;// retornamos nada
        }else if(r.dato==d){//si es igual a d quiere decir que ya lo encontro 
            return r;//y retornamos r
        }else if(r.dato<d){//si es menor al dato buscado vamos a buscar por la derecha
            return buscar(d,r.hijoDerecho);
        }else{//si no es menor es mayor
            return buscar(d,r.hijoIzquierdo);
        }
    }
    
    //Obtener el factor de equilibrio
    public int obtenerFE(NodoArbolAVL x){
        if(x==null){
            return -1;
        }else{
            return x.fe;
        }
    }
    
    //Rotacion simple izquierda
    public NodoArbolAVL rotacionIzquierda(NodoArbolAVL c){
        NodoArbolAVL auxiliar=c.hijoIzquierdo;
        c.hijoIzquierdo=auxiliar.hijoDerecho;
        auxiliar.hijoDerecho=c;
        c.fe=Math.max(obtenerFE(c.hijoIzquierdo), obtenerFE(c.hijoDerecho))+1;//el factor de equilibrio es el nivel +1
        auxiliar.fe=Math.max(obtenerFE(auxiliar.hijoIzquierdo), obtenerFE(auxiliar.hijoDerecho))+1;//con math.max obtenemos el maximo
        return auxiliar;//retornamos el aux
    }
    
    //Rotacion simple derecha
    public NodoArbolAVL rotacionDerecha(NodoArbolAVL c){
        NodoArbolAVL auxiliar=c.hijoDerecho;
        c.hijoDerecho=auxiliar.hijoIzquierdo;
        auxiliar.hijoIzquierdo=c;
        c.fe=Math.max(obtenerFE(c.hijoIzquierdo), obtenerFE(c.hijoDerecho))+1;//obtenme el maximo
        auxiliar.fe=Math.max(obtenerFE(auxiliar.hijoIzquierdo), obtenerFE(auxiliar.hijoDerecho))+1;//numero mayor
        return auxiliar;//retornamos el aux
    }
    
    //Rotacion doble a la izquierda
     public NodoArbolAVL rotacionDobleIzquierda(NodoArbolAVL c){
         NodoArbolAVL temporal;//creamos una variable temp
         c.hijoIzquierdo=rotacionDerecha(c.hijoIzquierdo);//c de hijoIzq lo vamos a apuntar a lo que nos de rotacion derecha de c de hijoIzq
         temporal=rotacionIzquierda(c);//a aux le asignamos lo que nos devuelva rotacionizq 
         return temporal;//lo retornamos
     }
     
     //Rotacion doble a la derecha
     public NodoArbolAVL rotacionDobleDerecha(NodoArbolAVL c){
         NodoArbolAVL temporal;
         c.hijoDerecho=rotacionIzquierda(c.hijoDerecho);//c de hijoDer lo vamos a apuntar a lo que nos de rotacion izquierda de c de hijoDer
         temporal=rotacionDerecha(c);//rotacionderecha de c lo asignamos a auxiliar
         return temporal;
     }
     
     //Metodo para insertar AVL(de forma balanceada)
     public NodoArbolAVL insertarAVL(NodoArbolAVL nuevo, NodoArbolAVL subArb){//recibe de parametros dos nodos
         NodoArbolAVL nuevoPadre=subArb;
         if(nuevo.dato<subArb.dato){//si nuevo de dato es menor al sub arbol de dato
             if(subArb.hijoIzquierdo==null){
                 subArb.hijoIzquierdo=nuevo;//va a ser igual al que nos acaban de insertar
             }else{//en otro caso
                 subArb.hijoIzquierdo=insertarAVL(nuevo, subArb.hijoIzquierdo);//recursividad
                 if((obtenerFE(subArb.hijoIzquierdo) - obtenerFE(subArb.hijoDerecho)==2)){//estamos ante un desbalanceo, para el caso que la resta sea = 2
                     if(nuevo.dato<subArb.hijoIzquierdo.dato){
                         nuevoPadre=rotacionIzquierda(subArb);
                     }else{
                         nuevoPadre=rotacionDobleIzquierda(subArb);
                     }
                     
                 }
             }//termina el primer condicionante
         }else if(nuevo.dato>subArb.dato){//ahora, si es mayor...
             if(subArb.hijoDerecho==null){
                 subArb.hijoDerecho=nuevo;
             }else{
                 subArb.hijoDerecho=insertarAVL(nuevo, subArb.hijoDerecho);
                 if((obtenerFE(subArb.hijoIzquierdo)- obtenerFE(subArb.hijoDerecho)==2)){
                     if(nuevo.dato>subArb.hijoDerecho.dato){//si es mayor
                         nuevoPadre=rotacionDerecha(subArb);//rotacion simple
                     }else{//en otro caso necesita una rotacion doble
                         nuevoPadre=rotacionDobleDerecha(subArb);
                     }
                 }
             }
         }else{//si no es mayor ni menor entonces es duplicado
             System.out.println("Nodo duplicado");
         }
         
         //Actualizar la altura(FE)
         if((subArb.hijoIzquierdo==null) && (subArb.hijoDerecho!=null)){//si sub arb hijoizq es = null y subarb hijoder es != null
             subArb.fe=subArb.hijoDerecho.fe+1;//actulizamos el fe
         }else if((subArb.hijoDerecho==null) && (subArb.hijoIzquierdo!=null)){//actualizamos el fe
             subArb.fe=subArb.hijoIzquierdo.fe+1;
         }else{
             subArb.fe=Math.max(obtenerFE(subArb.hijoIzquierdo), obtenerFE(subArb.hijoDerecho))+1;//va a obtener al maximo y le va a sumar 1
         }
         return nuevoPadre;
        
     }
     
     //Metodo para insertar
     public void insertar(int d){
         NodoArbolAVL nuevo=new NodoArbolAVL(d);
         if(raiz==null){//si no hay nodo
             raiz=nuevo;
         }else{//en otro caso
             raiz=insertarAVL(nuevo, raiz);//desde aqui mandamos a llamra al metodo de insertar avl
         }
     }
     
     //Recorridos
     
     //Metodo para recorrer el arbol InOrden
    public void inOrden(NodoArbolAVL r){
        if(r!=null){//si raiz es diferente de null recorre en inOrden
            inOrden(r.hijoIzquierdo);//a partir de la raiz hijo izquierdo
            System.out.print(r.dato + ", ");//nos muestra el dato que tiene la raiz
            inOrden(r.hijoDerecho);//por ultimo el hijo derecho
            
        }
    }
    //Metodo para recorrer el arbol PreOrden
    public void preOrden(NodoArbolAVL r){
        if(r!=null){//si el arbol no esta vacio
            System.out.print(r.dato + ", ");//primero mostramos la raiz
            preOrden(r.hijoIzquierdo);//mostramos el hijo izquierdo
            preOrden(r.hijoDerecho);//por ultimo el derecho
        }
    }
    //Metodo para recorrer el arbol PostOrden
    public void postOrden(NodoArbolAVL r){
        if(r!=null){
           postOrden(r.hijoIzquierdo);//mostramos primero el hijo izquierdo
           postOrden(r.hijoDerecho);//despues el derecho
           System.out.print(r.dato + ", ");//al ultimo mostramos la raiz
        }
    }
    
    //Metodo para eliminar un nodo del Arbol
    public boolean eliminar(int d){
        NodoArbolAVL auxiliar = raiz;
        NodoArbolAVL padre = raiz;
        boolean esHijoIzq = true;//cuando sea hijoIzq va inicializar en True, y en caso contrario(false) sera hijoDer
        while(auxiliar.dato!=d){//cuando sea diferente de d quiere decir que aun no lo encuentra
            padre=auxiliar;
            if(d<auxiliar.dato){//cuando d sea menor a aux.dato
                esHijoIzq=true;//se va a la izquiera
                auxiliar=auxiliar.hijoIzquierdo;//apuntamos para ir recorriendo hacia la izqauerda
            }else{//en caso contrario buscamos por la derecha
                esHijoIzq=false;
                auxiliar=auxiliar.hijoDerecho;
            }
            if(auxiliar==null){//si aux llega a null quiere decir que no encontro el nodo
                return false;//entonces retornamos un false
            }
        }//Fin del while
        if(auxiliar.hijoIzquierdo==null && auxiliar.hijoDerecho==null){//cuando ambos hijos apuntan a nulo quiere decir que es hoja
            if(auxiliar==raiz){
                raiz=null;
            }else if(esHijoIzq){
                padre.hijoIzquierdo=null;//padre de hijoizq lo vamos a apuntar a nulo
            }else{
                padre.hijoDerecho=null;//en otro caso padre hijoder lo apuntamos a null
            }
        }else if(auxiliar.hijoDerecho==null){
            if(auxiliar==raiz){//si auxiliar es igual a raiz
                raiz=auxiliar.hijoIzquierdo;//aux lo vamos a apuntar a hijoizq
            }else if(esHijoIzq){
                padre.hijoIzquierdo=auxiliar.hijoIzquierdo;//padre de hijoizq lo vamos a apuntar a hijoizq
            }else{
                padre.hijoDerecho=auxiliar.hijoIzquierdo;//
            }
        }else if(auxiliar.hijoIzquierdo==null){//reacomodamos los punteros
            if(auxiliar==raiz){//si auxiliar es igual a raiz
                raiz=auxiliar.hijoDerecho;//raiz = aux de hijo derecho
            }else if(esHijoIzq){
                padre.hijoIzquierdo=auxiliar.hijoDerecho;
            }else{
                padre.hijoDerecho=auxiliar.hijoIzquierdo;//
            }
        }else{//si no entra ninguno
            NodoArbolAVL reemplazo = obtenerNodoReemplazo(auxiliar);//este metodo nos va retornar el nodo que va a reemplazar al nodo que queremos eliminar
            if(auxiliar==raiz){
                raiz=reemplazo;
            }else if(esHijoIzq){//si es hijo izq
                padre.hijoIzquierdo=reemplazo;
            }else{//si no
                padre.hijoDerecho=reemplazo;
            }
            reemplazo.hijoIzquierdo=auxiliar.hijoIzquierdo;
        }
        return true;//si llega hasta esta parte quiere decir que si encontro a nuestro nodo a eliminar
    }
    
    //Metodo para devolvernos el nodo reemplazo
    public NodoArbolAVL obtenerNodoReemplazo(NodoArbolAVL nodoReemp){
        NodoArbolAVL reemplazarPadre = nodoReemp;
        NodoArbolAVL reemplazo = nodoReemp;
        NodoArbolAVL auxiliar = nodoReemp.hijoDerecho;
        
        while(auxiliar!=null){//vamos a recorrer para encontrar el nodo candidato a reemplaxar
            reemplazarPadre = reemplazo;
            reemplazo = auxiliar;
            auxiliar=auxiliar.hijoIzquierdo;
        }
        if(reemplazo!=nodoReemp.hijoDerecho){
            reemplazarPadre.hijoIzquierdo=reemplazo.hijoDerecho;//para reacomodar los enlaces
            reemplazo.hijoDerecho=nodoReemp.hijoDerecho;
        }
        return reemplazo;
    }
}
