package aut.db;

import org.rocksdb.RocksDB;
import org.rocksdb.Options;
import org.rocksdb.RocksDBException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {

        byte[] key = null;
        byte[] value = null;
        String query;
        String input;
        String result;
        File file = new File("American Stock Exchange 20200206_Names_ClosedVal.csv");
        BufferedReader br = new BufferedReader(new FileReader(file));
        Scanner scanner = new Scanner(System.in);
        String st;
        RocksDB.loadLibrary();
        try (final Options options = new Options().setCreateIfMissing(true)) {
            try (final RocksDB db = RocksDB.open(options, "./data")) {

                while ((st = br.readLine()) != null) {
                    key = st.substring(0, st.indexOf(",")).getBytes();
                    value = st.substring(st.indexOf(",") + 1).getBytes();
                    db.put(key,value);
                }
        while (true) {
            input = scanner.nextLine();
            query = input.substring(0,input.indexOf(" "));
            if (query.equals("fetch") || query.equals("delet")){
                key = input.substring(input.indexOf(" ") + 1).getBytes();
            }
            else {
                key = input.substring(input.indexOf(" ") + 1, input.lastIndexOf(" ")).getBytes();
                value = input.substring(input.lastIndexOf(" ") + 1).getBytes();
            }
            switch (query) {
                case "creat":
                    if (db.get(key) == null){
                        db.put(key,value);
                        System.out.println(true);
                    }
                    else
                        System.out.println(false);
                    break;
                case "fetch":
                    if (db.get(key) == null) {
                        System.out.println(false);
                    }
                    else {
                        System.out.println(true);
                        result = new String(db.get(key));
                        System.out.println(result);
                    }
                    break;
                case "delet":
                    if (db.get(key) == null) {
                        System.out.println(false);
                    }
                    else {
                        db.remove(key);
                        System.out.println(true);
                    }
                    break;
                case "update":
                    if (db.get(key) == null){
                        System.out.println(false);
                    }
                    else{
                        db.put(key,value);
                        System.out.println(true);
                    }
                    break;
                default:
                    System.out.println("ERROR");
            }
            System.out.println();
        }
            }
        } catch (RocksDBException e) {
        }
    }
}

