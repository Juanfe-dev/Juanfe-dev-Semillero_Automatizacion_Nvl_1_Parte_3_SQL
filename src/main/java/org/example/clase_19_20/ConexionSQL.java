package org.example.clase_19_20;

import java.sql.*;

public class ConexionSQL {
    public static void main(String[] args) {

        //CONNECTION AND STATEMENT
        // Lo que este en el comienzo del parentesis del try se convierte en autocloseable
        try (//Guardamos la conexion en connection
             Connection connection = DriverManager.getConnection("jdbc:h2:./src/main/resources/test", "sa", "");
             //Creamos el statement para realizar la consulta
             Statement statement = connection.createStatement();) {
            //Creamos el string con la consulta
            String queryInsert = "Insert into TEST values (12,'Gomez')";
            //Ejecutamos la consulta
            statement.execute(queryInsert); //El execute solo no espera que responda un resultSet, solo se ejecuta y ya
            //Cerramos la conexion, no es necesario en este caso porque en el try tenemos la conexion en autocloseable
            //statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //RESULTSET EJEMPLO 0 PARA PEDIR EL DATO CON UN NOMBRE QUE LE ASIGNE

        try (Connection connection = DriverManager.getConnection("jdbc:h2:./src/main/resources/test", "sa", "");
             Statement statement = connection.createStatement()) {
            //String queryInsert = "Insert into TEST values (6,'Gomez')";
            ResultSet resultSet = statement.executeQuery("SELECT ID as Identificacion, Name as Nombre FROM TEST");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("Identificacion"));
                System.out.println(resultSet.getString("Nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //RESULTSET EJEMPLO 1 PARA PEDIR EL DATO APUNTANDO AL INDICE

        try (Connection connection = DriverManager.getConnection("jdbc:h2:./src/main/resources/test", "sa", "");
             Statement statement = connection.createStatement()) {
            //String queryInsert = "Insert into TEST values (6,'Gomez')";
            ResultSet resultSet = statement.executeQuery("SELECT ID FROM TEST");

            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //RESULTSET EJEMPLO 2 PARA PEDIR EL DATO CON UN ID DE BUSQUEDA
        String idBusqueda = "";
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./src/main/resources/test", "sa", "");
             Statement statement = connection.createStatement()) {
            //String queryInsert = "Insert into TEST values (6,'Gomez')";
            ResultSet resultSet = statement.executeQuery("SELECT * FROM TEST WHERE Name = '" + idBusqueda + "'");
            //Concatenando variables en el query usando statement es mala practica, es mejor usar prepared statement, ver ejemplo 4.1

            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        ////RESULTSET EJEMPLO 3 CON EL USO DE EXECUTEQUERY PARA RETORNO DE INFORMACION
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./src/main/resources/test", "sa", "");
             Statement statement = connection.createStatement()) {
            //String queryInsert = "Insert into TEST values (6,'Gomez')";
            ResultSet resultSet = statement.executeQuery("SELECT * FROM TEST");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //RESULTSET EJEMPLO 4.1 USANDO PREPARED STATEMENT VER EJEMPLO 4.2
        insertarFila(13,"Camilo");
        //EJEMPLO 5.1 UPDATE VER EJEMPLO 5.2
        actualizarFila(13,"Albeiro");
        //EJEMPLO 6.1 DELETE VER EJEMPLO 5.2
        eliminarFila(2);
    }

    //RESULTSET EJEMPLO 4.2 INSERTAR FILA USANDO UN PREPARE STATEMENT, METODO LLAMADO EN EL EJEMPLO 4.1
    public static void insertarFila(int id, String name) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./src/main/resources/test", "sa", "");
             PreparedStatement preparedStatement = connection.prepareStatement("Insert into TEST values(?,?)")) {
            // Otro ejemplo de query, tener en cuenta que se usa el executeQuery por ser un select, retorna data
            // SELECT * FROM TEST WHERE id=? AND name=?
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //EJEMPLO 5.2 UPDATE
    public static void actualizarFila(int id, String name) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./src/main/resources/test", "sa", "");
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE TEST SET Name=? WHERE ID=?")) {
            preparedStatement.setInt(2, id);
            preparedStatement.setString(1, name);
            int rows = preparedStatement.executeUpdate();
            System.out.println("Filas afectadas: " + rows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //EJEMPLO 6.2 DELETE
    public static void eliminarFila(int id) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./src/main/resources/test", "sa", "");
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM TEST WHERE ID=?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}