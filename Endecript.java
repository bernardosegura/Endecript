import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import javax.crypto.spec.GCMParameterSpec;

import java.io.InputStream;
import java.io.FileInputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class Endecript {
    public static void main(String[] args) {
    	try {
        // Encriptacion AES GSM 128 bits key
            String secretKey = new String("");
            String data = new String("");
            boolean contieneF = false;
            boolean contieneFP = false;
            boolean verbose = true;
            String[] input_data = new String[args.length]; 
            int i = 0;
            for (String elemento : args) {
                if (elemento.equals("-v") || elemento.equals("-V")) {
                    verbose = false;
                }else{
                    input_data[i] = elemento;
                    if (elemento.equals("-f") || elemento.equals("-F")) {
                        if(args.length > 2)
                            contieneFP = true; 
                        contieneF = true; 
                       // break; 
                    }
                    i++;
                }    
            }
            if(!verbose)
                input_data[i] = "";


            if(contieneF && contieneFP){
                secretKey = getSecretKey(input_data[3]);
            }else{
                secretKey = getSecretKey("");
            }

            if(input_data.length > 0 && !secretKey.equals("")){
                if(input_data.length > 1)
                    data = input_data[1];
                if("-e".equals(input_data[0]) || "-E".equals(input_data[0])){
                    if(verbose){
                        if(contieneF && contieneFP)
                            whit_verbose(openssl_encrypt(data, secretKey),data,input_data[3]);
                        else
                            whit_verbose(openssl_encrypt(data, secretKey),data,"");
                    }
                    else
                       whitout_verbose(openssl_encrypt(data, secretKey)); 
                }else{
                    if("-d".equals(input_data[0]) || "-D".equals(input_data[0])){
                        if(verbose){
                            if(contieneF && contieneFP)
                                whit_verbose(openssl_decrypt(data, secretKey),data,input_data[3]);
                            else
                               whit_verbose(openssl_decrypt(data, secretKey),data,"");
                        }
                        else
                           whitout_verbose(openssl_decrypt(data, secretKey));
                    }else{
                        show_ayuda();
                    }
                }
            }else{
                show_ayuda();
            }
	    } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
    }
    private static void whit_verbose(String data, String origen, String filePath) throws Exception {
        System.out.println("");
        System.out.println(" --------------------------------------- ");
        System.out.println("|           Endecript v0.1              |");
        System.out.println(" --------------------------------------- ");
        System.out.println(" Origen: " + origen);
        if(!filePath.equals(""))
            System.out.println(" Archivo con secretKey: " + filePath);
        System.out.println(" Resultado: " + data);
        System.out.println("");
    }
    private static void whitout_verbose(String data) throws Exception {
        System.out.println(data);
    }

    private static void show_ayuda() throws Exception {
        System.out.println("");
        System.out.println(" --------------------------------------- ");
        System.out.println("|           Endecript v0.1              |");
        System.out.println(" --------------------------------------- ");
        System.out.println(" Endecript (metodo) texto (opcion)");
        System.out.println(" Metodo");
        System.out.println("    -e Encripta texto");
        System.out.println("    -d Desencripta texto");
        System.out.println(" Opcion");
        System.out.println("    -f Archivo XML con secretKey");
        System.out.println("    -v Muestra la salida simple");
        System.out.println("");
    }

    private static String getSecretKey(String filePath) throws Exception {
        InputStream inStream;
        if(filePath.equals("")){
            return "f0faf61ca6ae4f733e97a4626db4d61a";
        }else{
            inStream = new FileInputStream(filePath);
            JAXBContext contextObj = JAXBContext.newInstance(CSecretKey.class);
            Unmarshaller unmarshallerObj = contextObj.createUnmarshaller();
            CSecretKey configuracion = (CSecretKey)unmarshallerObj.unmarshal(inStream);
            inStream.close();
            return configuracion.getKey();
        }
    }

    private static String openssl_encrypt(String data, String strKey) throws Exception {
        String strIv = new String("AES/GCM/NoPadding"); //String("AES/CBC/PKCS5Padding");
        Cipher ciper = Cipher.getInstance(strIv);
        SecretKeySpec key = new SecretKeySpec(strKey.getBytes(), "AES");
        //IvParameterSpec iv = new IvParameterSpec(strIv.getBytes(), 0, ciper.getBlockSize());
         byte[] iv = new byte[ciper.getBlockSize()];

        //La especificación de GCM (Galios/Counter Mode) en sí no admite una longitud de etiqueta autenticada (TLen) de 256 bits. En GCM, la longitud de la etiqueta autenticada es fija y no se puede modificar. Las longitudes de etiqueta autenticada admitidas en GCM son 128, 120, 112, 104 y 96 bits. 
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
        // Encrypt
        ciper.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
        byte[] encryptedCiperBytes = ciper.doFinal(data.getBytes());

        byte[] encodedBytes = Base64.getEncoder().encode(encryptedCiperBytes);

        String s = new String(encodedBytes);
        return s;
    }

    private static String openssl_decrypt(String data, String strKey) throws Exception {
        String strIv = new String("AES/GCM/NoPadding"); //String("AES/CBC/PKCS5Padding");
        Cipher ciper = Cipher.getInstance(strIv);
        SecretKeySpec key = new SecretKeySpec(strKey.getBytes(), "AES");
        //IvParameterSpec iv = new IvParameterSpec(strIv.getBytes(), 0, ciper.getBlockSize());
         byte[] iv = new byte[ciper.getBlockSize()];

        //La especificación de GCM (Galios/Counter Mode) en sí no admite una longitud de etiqueta autenticada (TLen) de 256 bits. En GCM, la longitud de la etiqueta autenticada es fija y no se puede modificar. Las longitudes de etiqueta autenticada admitidas en GCM son 128, 120, 112, 104 y 96 bits. 
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
        // Encrypt
        ciper.init(Cipher.DECRYPT_MODE, key, parameterSpec);
        byte[] decodedBytes = Base64.getDecoder().decode(data.getBytes());
        
        byte[] decryptedCiperBytes = ciper.doFinal(decodedBytes);

        String s = new String(decryptedCiperBytes);
        return s;
    }
}