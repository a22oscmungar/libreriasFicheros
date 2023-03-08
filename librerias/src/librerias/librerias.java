/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librerias;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Llibreria d'utilitats
 *
 * @author author
 * @version version
 *
 */
public class librerias {
// <editor-fold defaultstate="collapsed" desc="Implementació de LlegirInt()">

    private static Scanner scan = null;

    
    static class Cliente {

        int codi;
        String nom;
        String cognoms;
        int DiaNaixement;
        int MesNaixement;
        int AnyNaixement;
        String AdrecaPostal;
        String mail;
        boolean VIP;
    }

    static class index {

        long posicio;
        int codi;
        boolean marca;
    }

    public static int LlegirInt() {
        int result;

        if (scan == null) {
            scan = new Scanner(System.in);
        }
        result = LlegirInt(scan);

        return result;
    }

    public static int LlegirInt(String missatge) {
        int result;

        if (scan == null) {
            scan = new Scanner(System.in);
        }
        result = LlegirInt(scan, missatge);

        return result;
    }

    public static int LlegirInt(Scanner scan) {
        return LlegirInt(scan, null);
    }

    public static int LlegirInt(Scanner scan, String missatge, int valorMin, int valorMax) {
        int result = 0;
        do {
            result = LlegirInt(scan, missatge);
        } while (result < valorMin || result > valorMax);

        return result;
    }

    public static int LlegirInt(Scanner scan, String missatge) {
        boolean dadesCorrectes;
        int result = 0;
        do {
            if (missatge != null) {
                System.out.print(missatge);
            }
            dadesCorrectes = scan.hasNextInt();
            if (dadesCorrectes) {
                result = scan.nextInt();
            } else if (scan.hasNext()) {
                scan.nextLine();
            }
        } while (!dadesCorrectes);

        return result;
    }

    //EMPIEZA LA MOVIDA DE FICHEROS
    //FICHEROS DE TEXTO
    /**
     * Esta funcion nos servirá para crear el archivo en caso de que no exista o
     * para abrirlo si es que ya existe, el boolean "crear" nos sirve para
     * decidir si, en caso de no existir el fichero, lo cree (true) o no (false)
     *
     * @param nomFichero tendremos que pasarle un String con el nombre que tiene
     * el fichero
     * @param crear este boolean nos sirve para decidir si queremos crear el
     * archivo o no
     * @return nos devuelve el archivo que queremos
     */
    public static File AbrirFichero(String nomFichero, boolean crear) {
        File result = null;

        result = new File(nomFichero);

        if (!result.exists()) {
            if (crear) {
                try {
                    result.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(librerias.class.getName()).log(Level.SEVERE, null, ex);
                    result = null;
                }
            } else {
                result = null;
            }
        }

        return result;
    }

     /**
     * Esta funcion nos sirve para leer una linea de un archivo de texto
     *
     * @param br le pasamos el bufferedReader que hemos abierto antes
     * @return nos devuelve el contenido de la linea en un String
     */
    public static String LeerLinea(BufferedReader br) {
        String linea = null;

        try {
            linea = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(librerias.class.getName()).log(Level.SEVERE, null, ex);
        }

        return linea;
    }
    
    //PARA LEER EL ARCHIVO DE TEXTO:
    /**
     * Esta funcion nos sirve para leer un archivo de texto linea por linea
     */
    public static void LeerFichero() {
        // Creamos el enlace con el fichero en el disco
        BufferedReader buf = AbrirFicheroLectura("Aqui va el nombre del fichero", true);

        String linea = LeerLinea(buf);
        while (linea != null) {
            System.out.println(linea);
            linea = LeerLinea(buf);
        }

        CerrarFichero(buf);

    }

    /**
     * Esta funcion nos sirve para abrir el buffered reader de un archivo, que
     * es lo que nos permitirá leerlo más adelante, lo hace abriendo el fichero
     * con la funcion "AbrirFichero", siempre que no se le devuelva un archivo
     * nulo esta funcion abrirá el bufferedReader (abriendo primero el
     * FileReader)
     *
     * @param nomFichero se le pasa el nombre del fichero que queremos leer
     * @param crear booleano para decidir si el archivo que queremos leer se
     * crea en caso de no exisitr
     * @return nos devuelve el bufferedReader para leer el archivo de texto
     */
    public static BufferedReader AbrirFicheroLectura(String nomFichero, boolean crear) {
        BufferedReader br = null;
        File f = AbrirFichero(nomFichero, crear);

        if (f != null) {
            // Declarar el reader para poder leer el fichero¡
            FileReader reader;
            try {
                reader = new FileReader(f);
                // Buffered reader para poder leer más comodamente
                br = new BufferedReader(reader);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(librerias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return br;
    }

   
    /**
     * Esta funcion nos sirve para, una vez hemos acabado de leer el archivo de
     * texto, cerrarlo y que deje de consumir
     *
     * @param br tenemos que pasarle el bufferedReader que hemos usado para leer
     */
    public static void CerrarFichero(BufferedReader br) {
        try {
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(librerias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * funcion que simplemente hace un scan de la informacion que quiere
     * introducir el usuario, podemos usarla o simplemente hacer el scan
     *
     * @return nos devuelve el string con lo que el usuario ha introducido
     */
    public static String PedirLineaTeclado() {
        return scan.nextLine();
    }
    
    /**
     * funcion que usa el printWriter para escribir la información en el
     * archivo, podemos hacer directamente el print en la otra funcion pero como
     * queramos
     *
     * @param pw
     * @param linea
     */
    public static void EscribirLinea(PrintWriter pw, String linea) {
        pw.println(linea);
    }
    
    
    //PARA ESCRIBIR EN UN ARCHIVO DE TEXTO:
    /**
     * Esta es la funcion base para escribir en un fichero de texto, que lo que
     * hace es pedir al usuario una linea de texto y mientras no haga un intro
     * sin ninguna información, la escribirá en el archivo
     *
     * @param blnAnyadir este boolean decide si la información se escribirá al
     * final del archivo (true) o sobreescribirá lo antiguo (false)
     */
    public static void EscribirFichero(boolean blnAnyadir) {
        // Creamos el enlace con el fichero en el disco
        PrintWriter pw = AbrirFicheroEscritura("Aqui va el nombre del archivo", true, blnAnyadir);

        String linea = PedirLineaTeclado();
        while (!linea.equals("")) {
            EscribirLinea(pw, linea);
            linea = PedirLineaTeclado();
        }

        CerrarFichero(pw);
    }

    /**
     * Esta fucnion nos abre el PrintWriter, que es lo que nos permite escribir
     * en el fichero de texto, primero abre el fichero usando la funcion de
     * abrirFichero a la que le pasamos el nombre y si queremos crearlo o no en
     * caso de no existir, y luego inicia primero el FileWriter y luego el
     * PrintWriter y nos devuelve este
     *
     * @param nomFichero nombre del archivo en el que queremos escribir
     * @param crear boolean para si queremos que se cree en caso de no existir
     * @param blnAnyadir boolean para decidir si sobreescrimos (false) o
     * añadimos (true)
     * @return nos devuelve el PrintWriter para poder escribir en el fichero
     */
    public static PrintWriter AbrirFicheroEscritura(String nomFichero, boolean crear, boolean blnAnyadir) {
        PrintWriter pw = null;
        File f = AbrirFichero(nomFichero, crear);

        if (f != null) {
            // Declarar el writer para poder escribir en el fichero
            FileWriter writer;
            try {
                writer = new FileWriter(f, blnAnyadir);
                // PrintWriter para poder escribir más comodamente
                pw = new PrintWriter(writer);
            } catch (IOException ex) {
                Logger.getLogger(librerias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return pw;
    }
    

    /**
     * funcion que nos sirve para cerrar el PrintWriter que hemos usado para
     * escribir en el fichero de texto
     *
     * @param pw
     */
    public static void CerrarFichero(PrintWriter pw) {
        pw.flush();
        pw.close();
    }

    //PARA BORRAR UNA LINEA DE UN FICHERO DE TEXTO
    /**
     * Esta funcion nos permite borrar una linea de un archivo de texto, lo que
     * hacemos es pedirle al usuario cual es la linea que queremos borrar.
     * Iremos leyendo linea por linea, si la linea no es la que quiere borrar la
     * escribiremos en un nuevo archivo, y si es la que queremos nos la
     * saltaremos Una vez teniendo el nuevo archivo con la información sin la
     * linea que queremos borrar, borraremos el archivo original y lo
     * renombraremos
     */
    public static void BorrarLinea() {
        int numLineaBorrar = scan.nextInt();
        int numLinea = 1;
        // Creamos el enlace con el fichero en el disco para leer
        BufferedReader buf = AbrirFicheroLectura("Aqui va el nombre del archivo", true);
        // Creamos el enlace con el fichero en el disco para escribir
        PrintWriter pw = AbrirFicheroEscritura("Aqui va el nombre del archivo" + "_copia", true, false);

        String linea = LeerLinea(buf);
        while (linea != null) {
            if (numLinea != numLineaBorrar) {
                EscribirLinea(pw, linea);
            }
            linea = LeerLinea(buf);
            numLinea++;
        }

        CerrarFichero(buf);
        CerrarFichero(pw);

        BorrarFichero("Aqui va el nombre del archivo");
        RenombrarFichero("Aqui va el nombre del archivo" + "_copia", "Aqui va el nombre del archivo");
    }

    /**
     * Esta funcion sirve para borrar el archivo con el nombre que le pasemos
     *
     * @param filename
     */
    public static void BorrarFichero(String filename) {
        File f = new File(filename);
        f.delete();
    }

    /**
     * Esta funcion sirve para renombrar un fichero, le pasaremos primero el
     * nombre que tiene y luego al que queremos cambiarselo, importante primero
     * haber borrado el archivo original
     *
     * @param filename_origen
     * @param filename_final
     */
    public static void RenombrarFichero(String filename_origen, String filename_final) {
        File f = new File(filename_origen);
        File f2 = new File(filename_final);
        f.renameTo(f2);
    }

    //AÑADIR UNA CLASE A UN FICHERO DE TEXTO
    /**
     * esta es la funcion base para añadir una clase a un fichero de texto,
     * primero iniciamos el printWriter
     *
     * @param blnAnyadir
     */
    public static void AnyadirClientes(boolean blnAnyadir) {
        // Creamos el enlace con el fichero en el disco
        PrintWriter pw = AbrirFicheroEscritura("Aqui va el nombre del archivo", true, blnAnyadir);

        Cliente cli = PedirDatosCliente();
        while (cli != null) {
            String linea = FormatearClienteFicheroSep(cli);
            EscribirLinea(pw, linea);
            cli = PedirDatosCliente();
        }

        CerrarFichero(pw);
    }

    /**
     * Con esta funcion le pedimos al usuario que introduzca los datos que
     * necesitamos del cliente que quiere añadir, la clase "cliente" habrá que
     * cambiarla por la clase que estemos utilizando y los datos que usemos
     *
     * @return
     */
    public static Cliente PedirDatosCliente() {
        Cliente c = new Cliente();
        System.out.print("Codi: ");
        c.codi = scan.nextInt();
        if (c.codi != 0) {
            System.out.print("Nom: ");
            c.nom = scan.nextLine();
            System.out.print("Cognoms: ");
            c.cognoms = scan.nextLine();
            System.out.print("DiaNaixement: ");
            c.DiaNaixement = scan.nextInt();
            System.out.print("MesNaixement: ");
            c.MesNaixement = scan.nextInt();
            System.out.print("AnyNaixement: ");
            c.AnyNaixement = scan.nextInt();
            scan.nextLine();
            System.out.print("Adreça postal: ");
            c.AdrecaPostal = scan.nextLine();
            System.out.print("e-Mail: ");
            c.mail = scan.nextLine();
            System.out.print("VIP: ");
            c.VIP = scan.nextBoolean();
        } else {
            c = null;
        }
        return c;
    }

    /**
     * esta funcion le dará formato a los datos del cliente, dejandolo todo en
     * una sola String separando los datos por el caracter que queramos
     *
     * @param cli
     * @return
     */
    public static String FormatearClienteFicheroSep(Cliente cli) {
        String result = "";

        result += cli.codi + "_";
        result += cli.nom + "_";
        result += cli.cognoms + "_";
        result += cli.DiaNaixement + "_";
        result += cli.MesNaixement + "_";
        result += cli.AnyNaixement + "_";
        result += cli.AdrecaPostal + "_";
        result += cli.mail + "_";
        result += cli.VIP;

        return result;
    }

    //LEER CLIENTES DE UN ARCHIVO DE TEXTO
    /**
     * esta funcion nos permite leer un cliente (teniendolos formateados por
     * separador), lo que hace es ir leyendo de manera secuencial hasta tener el
     * codigo que hemos pedido anteriormente, separa los datos y los muestra por
     * pantalla
     */
    public static void LeerClientesCodigoSep() {
        System.out.print("Introduce el código del cliente a buscar: ");
        int codigoBuscar = scan.nextInt();

        // Creamos el enlace con el fichero en el disco para leer
        BufferedReader buf = AbrirFicheroLectura("Aqui va el nombre del fichero", true);

        String linea = LeerLinea(buf);
        Cliente cli = FormatearFicheroClienteSep(linea);
        while (cli != null && cli.codi != 0) {
            linea = LeerLinea(buf);
            cli = FormatearFicheroClienteSep(linea);
        }

        if (cli != null && cli.codi != codigoBuscar) {
            EscribirDatosCliente(cli);
        }

        CerrarFichero(buf);
    }

    /**
     * Esta funcion nos separa el cliente que hayamos usado teniendo en cuenta
     * el separador que hayamos utilizado. Escribe los datos sin el separador (#)
     *
     * @param str
     * @return
     */
    public static Cliente FormatearFicheroClienteSep(String str) {
        Cliente cli;

        if (!str.equals("")) {
            String[] campos = str.split("_");
            cli = new Cliente();

            cli.codi = Integer.parseInt(campos[0]);
            cli.nom = campos[1];
            cli.cognoms = campos[2];
            cli.DiaNaixement = Integer.parseInt(campos[3]);
            cli.MesNaixement= Integer.parseInt(campos[4]);
            cli.AnyNaixement= Integer.parseInt(campos[5]);
            cli.AdrecaPostal = campos[6];
            cli.mail = campos[7];
        } else {
            cli = null;
        }
        return cli;
    }

    /**
     * Esta funcion nos sirve para, teniendo ya separados los datos del cleinte,
     * mostrarlos por pantalla
     */
    public static void EscribirDatosCliente(Cliente c) {
        System.out.println("Codi: " + c.codi);
        System.out.println("Nom: " + c.nom);
        System.out.println("Cognoms: " + c.cognoms);
        System.out.println("Dia Naixement: " + c.DiaNaixement);
        System.out.println("Mes Naixement: " + c.MesNaixement);
        System.out.println("Any Naixement: " + c.AnyNaixement);
        System.out.println("Adreça postal: " + c.AdrecaPostal);
        System.out.println("e-mail: " + c.mail);
        System.out.println("VIP: " + c.VIP);

    }

    //ARCHIVOS BINARIOS
    //LEER REGISTROS
    /**
     * Esta funcion nos permite leer todas las lineas de un archivo binario,
     * primero abrimos el DataInputStream, que nos sirve para leer, y luego
     * vamos leyendo linea por linea hasta que no haya mas. Por ultimo cerramos
     * el fichero
     */
    public static void LeerClientesBinario() {
        DataInputStream dis = AbrirFicheroLecturaBinario("Aqui va el nombre del archivo", true);

        Cliente cli = LeerDatosClienteBinario(dis);
        while (cli != null) {
            EscribirDatosCliente(cli);
            cli = LeerDatosClienteBinario(dis);
        }

        CerrarFicheroBinario(dis);
    }

    /**
     * Esta funcion nos sirve para abrir el DataInputStream y poder leer un
     * archivo binario. Primero abrimos el fichero pasandole el nombre y si
     * queremos que se cree en caso de que no exista. Después iniciamos primero
     * el FileInputStream y luego el DataInputStream y lo devolvemos
     *
     * @param nomFichero
     * @param crear
     * @return
     */
    public static DataInputStream AbrirFicheroLecturaBinario(String nomFichero, boolean crear) {
        DataInputStream dis = null;
        File f = AbrirFichero(nomFichero, crear);

        if (f != null) {
            // Declarar el writer para poder escribir en el fichero¡
            FileInputStream reader;
            try {
                reader = new FileInputStream(f);
                // PrintWriter para poder escribir más comodamente
                dis = new DataInputStream(reader);
            } catch (IOException ex) {
                Logger.getLogger(librerias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return dis;
    }

    /**
     * esta funcion nos sirve para leer con el DataInput todos los datos que
     * haya en un archivo binario (UTF --> String)
     *
     * @param dis
     * @return
     */
    public static Cliente LeerDatosClienteBinario(DataInputStream dis) {
        Cliente cli = new Cliente();

        try {
            cli.codi = dis.readInt();
            cli.nom = dis.readUTF();
            cli.cognoms = dis.readUTF();
            cli.DiaNaixement = dis.readInt();
            cli.MesNaixement = dis.readInt();
            cli.AnyNaixement = dis.readInt();
            cli.AdrecaPostal = dis.readUTF();
            cli.mail = dis.readUTF();
            cli.VIP = dis.readBoolean();

        } catch (IOException ex) {
            cli = null;
        }
        return cli;
    }

    /**
     * esta funcion nos sirve para cerrar el dataInput una vez hemos acabado de
     * leer el fichero
     *
     * @param dis
     */
    public static void CerrarFicheroBinario(DataInputStream dis) {
        try {
            dis.close();
        } catch (IOException ex) {
            Logger.getLogger(librerias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //ESCRIBIR EN UN FICHERO BINARIO
    /**
     * esta es la funcion base para escribir en un archivo binario, primero
     * iniciamos el dataOutput, que nos servirá para escribir, luego pedimos los
     * datos que queremos introducir en el archivo, los introducimos y cerramos
     */
    public static void GrabarClientesBinario() {
        DataOutputStream dos = AbrirFicheroEscrituraBinario("Aqui va el nombre del archivo", true, true);

        Cliente cli = PedirDatosCliente();
        while (cli != null) {
            GrabarDatosClienteBinario(dos, cli);
            cli = PedirDatosCliente();
        }

        CerrarFicheroBinario(dos);

    }

    /**
     * Funcion para escribir en el archivo binario los datos que se nos han
     * introducido del cliente
     *
     * @param dos
     * @param cli
     */
    public static void GrabarDatosClienteBinario(DataOutputStream dos, Cliente cli) {

        try {
            dos.writeInt(cli.codi);
            dos.writeUTF(cli.nom);
            dos.writeUTF(cli.cognoms);
            dos.writeInt(cli.DiaNaixement);
            dos.writeInt(cli.MesNaixement);
            dos.writeInt(cli.AnyNaixement);
            dos.writeUTF(cli.AdrecaPostal);
            dos.writeUTF(cli.mail);
            dos.writeBoolean(cli.VIP);
        } catch (IOException ex) {
            Logger.getLogger(librerias.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * esta funcion nos sirve para iniciar el DataOutput y poder escribir en un
     * archivo binario
     *
     * @param nomFichero
     * @param crear
     * @param blnAnyadir
     * @return
     */
    public static DataOutputStream AbrirFicheroEscrituraBinario(String nomFichero, boolean crear, boolean blnAnyadir) {
        DataOutputStream dos = null;
        File f = AbrirFichero(nomFichero, crear);

        if (f != null) {
            // Declarar el writer para poder escribir en el fichero¡
            FileOutputStream writer;
            try {
                writer = new FileOutputStream(f, blnAnyadir);
                // PrintWriter para poder escribir más comodamente
                dos = new DataOutputStream(writer);
            } catch (IOException ex) {
                Logger.getLogger(librerias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return dos;
    }

    /**
     * esta funcion sirve para cerrar el DataOutput una vez hemos acabado de
     * escribir en el fichero
     *
     * @param dos
     */
    public static void CerrarFicheroBinario(DataOutputStream dos) {
        try {
            dos.flush();
            dos.close();
        } catch (IOException ex) {
            Logger.getLogger(librerias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //BORRAR UNA LINEA EN ARCHIVO BINARIO
    /**
     * Esta es la funcion base para borrar una linea de un archivo binario. Lo
     * que haremos es pedir la linea que quiere borrar, leeremos linea por
     * linea, copiando todas en un nuevo archivo (excepto la que el usuario
     * quiere borrar), luego borraremos el archivo original y renombraremos la
     * copia
     */
    public static void BorrarLineaBinario() {
        System.out.println("Posicion del cliente a borrar: ");
        int numLineaBorrar = scan.nextInt();
        int numLinea = 1;
        // Creamos el enlace con el fichero en el disco para leer
        DataInputStream dis = AbrirFicheroLecturaBinario("Aqui va el nombre del fichero", true);
        // Creamos el enlace con el fichero en el disco para escribir
        DataOutputStream dos = AbrirFicheroEscrituraBinario("Aqui va el nombre del fichero" + "_copia", true, true);

        Cliente cli = LeerDatosClienteBinario(dis);
        while (cli != null) {
            if (numLinea != numLineaBorrar) {
                GrabarDatosClienteBinario(dos, cli);

            }
            cli = LeerDatosClienteBinario(dis);
            numLinea++;
        }

        CerrarFicheroBinario(dis);
        CerrarFicheroBinario(dos);

        BorrarFichero("Aqui va el nombre del fichero");
        RenombrarFichero("Aqui va el nombre del fichero" + "_copia", "Aqui va el nombre del fichero");
    }
    //MODIFICAR LINEA EN ARCHIVO BINARIO

    /**
     * Esta funcion nos permite modificar una linea de un archivo binario.
     * Haremos exactamente lo mismo que al borrar una linea, pero cuando
     * encontramos la posicion de la linea que queremos modificar, volveremos a
     * pedir la informacion que quiere introducirse y la escribiremos en el
     * fichero
     */
    public static void ModificarClienteBinario() {
        System.out.println("Posicion del cliente a modificar: ");
        int numLineaModificar = scan.nextInt();
        int numLinea = 1;
        // Creamos el enlace con el fichero en el disco para leer
        DataInputStream dis = AbrirFicheroLecturaBinario("Aqui va el nombre del fichero", true);
        // Creamos el enlace con el fichero en el disco para escribir
        DataOutputStream dos = AbrirFicheroEscrituraBinario("Aqui va el nombre del fichero" + "_copia", true, true);

        Cliente cli = LeerDatosClienteBinario(dis);
        while (cli != null) {
            if (numLinea != numLineaModificar) {
                GrabarDatosClienteBinario(dos, cli);
            } else {
                cli = PedirDatosCliente();
                GrabarDatosClienteBinario(dos, cli);
            }
            cli = LeerDatosClienteBinario(dis);
            numLinea++;
        }
        CerrarFicheroBinario(dis);
        CerrarFicheroBinario(dos);

        BorrarFichero("Aqui va el nombre del fichero");
        RenombrarFichero("Aqui va el nombre del fichero" + "_copia", "Aqui va el nombre del fichero");
    }

    //ACCESO DIRECTO (RandomAccessFile)
    //ACCEDER DIRECTO POR POSICION
    /**
     * esta funcion nos permite acceder directamente a una posicion del fichero
     * principal. Lo que haremos será ir leyendo el fichero de index donde
     * tendremos
     */
    public static void SaberPosicionBinario() {
        try {
            System.out.print("Introdueix la posicion del client a buscar: ");
            int posicionBuscar = scan.nextInt();

            DataInputStream dis = AbrirFicheroLecturaBinario("Aqui va el nombre del indice", true);
            RandomAccessFile RAF = new RandomAccessFile("Aqui va el nombre del fichero", "rw");
            index idx = LeerDatosIndexBinario(posicionBuscar);

            while (idx != null) {

                try {
                    Cliente cli = new Cliente();
                    dis = AbrirFicheroLecturaBinario("Aqui va el nombre del archivo", true);
                    RAF = new RandomAccessFile("Aqui va el nombre del fichero", "rw");
                    RAF.seek(idx.posicio);
                    cli = LeerDatosClienteIndice(RAF);
                    EscribirDatosCliente(cli);
                    idx = null;

                } catch (IOException ex) {
                    Logger.getLogger(librerias.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            CerrarFicheroBinario(dis);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(librerias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * esta funcion nos permite leer los datos que hemos guardado en el indice
     *
     * @param posicio
     * @return
     */
    public static index LeerDatosIndexBinario(int posicio) {
        index idx = new index();
        try {
            RandomAccessFile RAF = new RandomAccessFile("Aqui va el nombre del indice", "rw");
            RAF.seek((posicio - 1) * 13);
            try {
                idx.posicio = RAF.readLong();
                idx.codi = RAF.readInt();
                idx.marca = RAF.readBoolean();

            } catch (IOException ex) {
                idx = null;
            }

        } catch (IOException ex) {
            Logger.getLogger(librerias.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idx;
    }

    /**
     * Una vez nos hemos movido a la posicion que queremos, vamos a usar el raf
     * para leer el cliente que nos interesa
     *
     * @param RAF le pasamos el raf para que pueda leer en la posicion indicada
     * @return nos devolverá el registro cliente con toda la informacion
     */
    public static Cliente LeerDatosClienteIndice(RandomAccessFile RAF) {
        Cliente cli = new Cliente();

        try {
            cli.codi = RAF.readInt();
            cli.nom = RAF.readUTF();
            cli.cognoms = RAF.readUTF();
            cli.DiaNaixement = RAF.readInt();
            cli.MesNaixement = RAF.readInt();
            cli.AnyNaixement = RAF.readInt();
            cli.AdrecaPostal = RAF.readUTF();
            cli.mail = RAF.readUTF();
            cli.VIP = RAF.readBoolean();

        } catch (IOException ex) {
            cli = null;
        }
        return cli;
    }

    public static void SaberCodigoBinario() {

        try {

            System.out.print("Introdueix el codi del client a buscar: ");
            int codigoBuscar = scan.nextInt();

            DataInputStream dis = AbrirFicheroLecturaBinario("Aqui va el nombre del indice", true);
            RandomAccessFile RAF = new RandomAccessFile("Aqui va el nombre del fichero", "rw");
            index idx = LeerDatosIndexBinario(codigoBuscar);
            while (idx != null) {
                if (idx.codi != codigoBuscar) {
                    idx = LeerDatosCliCodiIndex(dis);
                } else {
                    try {
                        Cliente cli = new Cliente();
                        dis = AbrirFicheroLecturaBinario("Aqui va el nombre del archivo", true);
                        RAF = new RandomAccessFile("Aqui va el nombre del fichero", "rw");
                        RAF.seek(idx.posicio);
                        cli = LeerDatosClienteIndice(RAF);
                        EscribirDatosCliente(cli);
                        idx = LeerDatosCliCodiIndex(dis);

                    } catch (IOException ex) {
                        Logger.getLogger(librerias.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

            CerrarFicheroBinario(dis);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(librerias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static index LeerDatosCliCodiIndex(DataInputStream dis) {
        index idx = new index();

        try {
            RandomAccessFile RAF = new RandomAccessFile("Aqui va el nombre del fichero", "rw");
            idx.posicio = RAF.readLong();
            idx.codi = RAF.readInt();
            idx.marca = RAF.readBoolean();

        } catch (IOException ex) {
            idx = null;
        }

        return idx;
    }
// </editor-fold>
}
