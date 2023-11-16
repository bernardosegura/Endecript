# <img src="https://github.com/bernardosegura/Endecript/blob/master/logo.png" height="50px" width="50px" /> Endecript
Es una aplicación multiplataforma para encriptar información con el metodo AES(Advanced Encryption Standard) utilizando el modo GCM (Galios/Counter Mode).
Se cuenta con una versión ya compilada y empaquetada en jar.
# ¿Cómo compilar?
  __Para crear las clases__
```bash
javac Endecript.java
```
# ¿Como ejecutar?
```bash
java Endecript -e "hola mundo"
``` 
# ¿Como crear jar?
  __Para crear jar es necesario el manifest para especificar la clase principal.__
```bash
jar cvfm Endecript.jar MANIFEST.MF Endecript.class CSecretKey.class
```
# ¿Como ejecutar jar?
```bash
java -jar Endecript.jar -d 4st9FUkTvsz58/zBRjpa1A+uRmw38jhl/HE=
```
# ¿Como agrego el secret key de manera independiente?
  __Al ejecutar con la opción <s>-f</s> se indica el nombre del archivo xml que contiene el secret key a utilizar, este debe llevar una estructura específica.__
```bash
java -jar Endecript.jar -d 4st9FUkTvsz58/zBRjpa1A+uRmw38jhl/HE= -f secretkey.xml
```
