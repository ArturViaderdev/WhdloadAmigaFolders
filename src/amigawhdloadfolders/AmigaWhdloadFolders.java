/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amigawhdloadfolders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author djvat
 */
public class AmigaWhdloadFolders {

    final static String rutaorigen = "p:/roms/amiga";
    final static String destino = "j:/emuladors/amiga/juegoswhd";
    final static String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final static String info = "j:/emuladors/amiga/juegos/archivo.info";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        File file = new File(rutaorigen);
        File infof = new File(info);
        
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        new File(destino + "/0").mkdirs();
        for (int cont = 0; cont < abc.length(); cont++) {
            new File(destino + "/" + abc.charAt(cont)).mkdirs();
        }
        for (String directorio : directories) {
            if (directorio.charAt(0) == '0' || directorio.charAt(0) == '1' || directorio.charAt(0) == '2' || directorio.charAt(0) == '3' || directorio.charAt(0) == '4' || directorio.charAt(0) == '5' || directorio.charAt(0) == '6' || directorio.charAt(0) == '7' || directorio.charAt(0) == '8' || directorio.charAt(0) == '9') {
                File srcDir = new File(rutaorigen + "/" + directorio);
                String destination = destino + "/" + "0" + "/" + directorio;
                File destDir = new File(destination);
                copyFolder(srcDir, destDir);
                
                File infoj;
                infoj = new File(destination + ".info");
                try {
                    copyFileUsingStream(infof,infoj);
                } catch (IOException ex) {
                    Logger.getLogger(AmigaWhdloadFolders.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } else {
                File srcDir = new File(rutaorigen + "/" + directorio);
                String destination = destino + "/" + Character.toUpperCase(directorio.charAt(0)) + "/" + directorio;
                File destDir = new File(destination);
                copyFolder(srcDir, destDir);
                
                File infoj;
                infoj = new File(destination + ".info");
                try {
                    copyFileUsingStream(infof,infoj);
                } catch (IOException ex) {
                    Logger.getLogger(AmigaWhdloadFolders.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
          
        }
         
        File infop;
        infop = new File(destino + "/" + "0.info");
        try {
            copyFileUsingStream(infof, infop);
        } catch (IOException ex) {
            Logger.getLogger(AmigaWhdloadFolders.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int cont = 0; cont < abc.length(); cont++) {
            infop = new File(destino + "/" + abc.charAt(cont)+ ".info");
            try {
                copyFileUsingStream(infof, infop);
            } catch (IOException ex) {
                Logger.getLogger(AmigaWhdloadFolders.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("Hecho");

    }

    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    public static void copyFolder(File source, File destination) {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdirs();
            }

            String files[] = source.list();

            for (String file : files) {
                File srcFile = new File(source, file);
                File destFile = new File(destination, file);

                copyFolder(srcFile, destFile);
            }
        } else {
            InputStream in = null;
            OutputStream out = null;

            try {
                in = new FileInputStream(source);
                out = new FileOutputStream(destination);

                byte[] buffer = new byte[1024];

                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            } catch (Exception e) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

}
