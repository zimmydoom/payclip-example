# payclip-example

La manera más fácil de correr el ejemplo es usando el jar BackEndExample.jar . Este requiere tener como lib al jar json-simple-1.1.1.jar
el archivo dist.zip tiene todo lo necesario, pero aún asi se incluyen los 2 jar por separado.

Una vez en el directorio del jar puede ejecutar los ejercicios como sigue:

java -jar "BackEndExample.jar" 343 add { \"amount\": 17.23, \"description\": \"Chongos\", \"date\":\"2018-11-30\", \"user_id\": 343 } 

java -jar "BackEndExample.jar" 345 add { \"amount\": 1.23, \"description\": \"Joes Tacos\", \"date\":\"2018-12-30\", \"user_id\": 345 } 

java -jar "BackEndExample.jar" 343 list

java -jar "BackEndExample.jar" 343 sum

java -jar "BackEndExample.jar" 343 a1e9dd8e-4d0d-492c-86fb-5d37170c5894



En add se tiene que usar el caracter de escape \ para la cadena de JSON ya que java considera el " como delimitador de parametros.

El código fuente esta src .
