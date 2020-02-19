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
        File file = new File("C:\\Users\\mehrab\\IdeaProjects\\project0\\American Stock Exchange 20200206_Names_ClosedVal.csv");
        BufferedReader br = new BufferedReader(new FileReader(file));
        Scanner scanner = new Scanner(System.in);
        String st;
        RocksDB.loadLibrary();
        // the Options class contains a set of configurable DB options
        // that determines the behaviour of the database.
        try (final Options options = new Options().setCreateIfMissing(true)) {
            // a factory method that returns a RocksDB instance
            try (final RocksDB db = RocksDB.open(options, "./data")) {

                while ((st = br.readLine()) != null) {
                    key = st.substring(0, st.indexOf(",")).getBytes();
                    value = st.substring(st.indexOf(",") + 1).getBytes();
//                    System.out.println(key);
//                    System.out.println(value);
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
                    System.out.println();
                    break;
                default:
                    System.out.println("ERROR");
            }
            System.out.println();
        }
                // do something
            }
        } catch (RocksDBException e) {
            // do some error handling
        }







    }
}

