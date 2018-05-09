/*
 * 
 */
package backendexample;

import org.json.simple.*;
import org.json.simple.parser.*;
import java.util.UUID;

import java.io.*;
import java.io.File;
/**
 * Class simulating a the storing and management of transactions represented
 * in JSON.
 * You can add a transaction, show one, list transactions from one user and display
 * the sum of the "amount" field.
 * @author zimmydoom
 */
public class BackEndTransaction {
  private File datos; 
    
  

    
    public BackEndTransaction(){
        
            datos = new File("data.dat");
        
    }
    
    
    /**
     * @param args the command line arguments
     *  index 0 is always <userID>
     */
    public static void main(String[] args) {
        
        BackEndTransaction bet = new BackEndTransaction();
        
        int numArgs = args.length;
        if(numArgs < 2){
            System.out.println("Bad Command");
            return;
        }
        //userID
        String userid = args[0];
        //chosen command
        String command = args[1];
        //transaction from command line
        String transacstring;
        
        switch(command.toLowerCase()){
            case "add":
                 //<user_id> add <transaction_json>
                transacstring = composeJSONString(args);
                bet.addTransaction(userid, transacstring);
              break;
            case "list":
                bet.listTransactions(userid);
                break;
            case "sum":
                bet.sumTransactions(userid);
                break;
                // show
            default:
                String transacId = args[1];
                bet.showTransaction(userid, transacId);
                
        }
        
        
        
        
        
    }
    
    private void addTransaction(String userID, String jsonstring){
        String transacID = UUID.randomUUID().toString();
        JSONParser parser = new JSONParser();
        //System.out.println(jsonstring);
        //System.out.println(userID);
        try{
            JSONObject transaccion = (JSONObject) parser.parse(jsonstring);
            transaccion.put("transaction_id",transacID);
            int inputuserID = Integer.parseInt(userID);
            long transuserID = (long)transaccion.get("user_id");
            if (inputuserID != transuserID){
                System.out.println("user_id did not match");
                return;
            }else{
                //we write transaction to file
                BufferedWriter bw = new BufferedWriter(new FileWriter(datos,true));
                String jsontoWrite = userID + " " + transaccion.toJSONString() + "\n";
                bw.write(jsontoWrite);
                bw.close();
                System.out.println(jsontoWrite);
            }
        } catch (ParseException pe){
            System.out.println("JSON badly formed");
            pe.printStackTrace();
        }catch (IOException ioe){
            System.out.println("Error opening data file");
        }
        
        System.out.println("add transaction");
    }
    
    private void showTransaction(String userID, String transacID){
        try{
            BufferedReader br = new BufferedReader(new FileReader(datos));
            while(br.ready()){
               String line  = br.readLine();
               int firstspace = line.indexOf(' ');
               String user = line.substring(0,firstspace);
               if(userID.equals(user)){
                   String jsontransaction = line.substring(firstspace + 1);
                   JSONParser parser = new JSONParser();
                   JSONObject transaccion = (JSONObject) parser.parse(jsontransaction);
                   String transJSONid = (String)transaccion.get("transaction_id");
                   if( transJSONid.equals(transacID)){
                       System.out.println(transaccion.toJSONString());
                       return;
                   }
               }
            }
        
        br.close();
        System.out.println("Transaction not found");
        }catch (FileNotFoundException fnf){
            System.out.println("No hay datos en el archivo");
        }catch (IOException ioe){
            System.out.println("Error al leer los datos");
        }catch(ParseException pe){
            System.out.println("Error reading json");
            pe.printStackTrace();
        }
    }
    
    private void listTransactions(String userID){
        try{
            String result = "[";
            BufferedReader br = new BufferedReader(new FileReader(datos));
            while(br.ready()){
               String line  = br.readLine();
               int firstspace = line.indexOf(' ');
               String user = line.substring(0,firstspace);
            
               if(userID.equals(user)){
                   String jsontransaction = line.substring(firstspace + 1);
                   //System.out.println(jsontransaction);
                   result += jsontransaction;
                
                 
               }
            }
            result += "]";
            System.out.println(result);
        
        
        }catch (FileNotFoundException fnf){
            System.out.println("No hay datos en el archivo");
        }catch (IOException ioe){
            System.out.println("Error al leer los datos");
        }
        
        System.out.println("list Transactions");
    }
    
    private void sumTransactions(String userID){
         try{
            double sum = 0;
            BufferedReader br = new BufferedReader(new FileReader(datos));
            while(br.ready()){
               String line  = br.readLine();
               int firstspace = line.indexOf(' ');
               String user = line.substring(0,firstspace);
               if(userID.equals(user)){
                   String jsontransaction = line.substring(firstspace + 1);
                   JSONParser parser = new JSONParser();
                   JSONObject transaccion = (JSONObject) parser.parse(jsontransaction);
                   sum += (double) transaccion.get("amount");
                   System.out.println(sum);
               }
            }
             System.out.println("{ \"user_id\":"+userID+",\"sum\":"+sum+"  }");
        
        
        
        }catch (FileNotFoundException fnf){
            System.out.println("No hay datos en el archivo");
        }catch (IOException ioe){
            System.out.println("Error al leer los datos");
        }catch(ParseException pe){
            System.out.println("Error reading json");
            pe.printStackTrace();
        }
        
        System.out.println("sum Transaction");
    }
    
    private static String composeJSONString(String[] transaction){
        String transacString = "";
        for (int i = 2; i<transaction.length;i++){
            transacString += transaction[i];
        }
        
        return transacString;
    }
    
    
    
    
}
