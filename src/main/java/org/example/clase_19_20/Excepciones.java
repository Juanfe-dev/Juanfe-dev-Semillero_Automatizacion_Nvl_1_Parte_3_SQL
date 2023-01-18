package org.example.clase_19_20;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Excepciones {

    public static void main(String[] args) {

        //El cuando caputa una excepcion ejecuta lo de su respectivo catch y ya no continuar con el resto
        String[] arreglo = {"1","2","3"};
        try{
            System.out.println(arreglo[3]); //catch 1
            FileReader fileReader = new FileReader("Ruta_erronea_de_archivo"); //catch 2
        } catch (ArrayIndexOutOfBoundsException e){ //catch 1
            e.printStackTrace();
        } catch (FileNotFoundException e){ //catch 2
            System.out.println("Capturando error en filereader");
        }
        System.out.println("No se ejecuta si el codigo explota y es capturado por un catch");


        //Otra forma por creacion de Metodo sin crear el throws en el metodo que lo llama
        try {
            lanzarExcepcion();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void lanzarExcepcion() throws FileNotFoundException {
        FileReader fileReader = new FileReader("Ruta_erronea_de_archivo");
    }

}
