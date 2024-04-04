package org.example;

import java.net.URL;

public class Main {
    public static void main(String[] args) {

        if(args.length < 1){
            System.err.println("No FilePath was provided!");
            return;
        }

        String pathToFile = args[0];
        System.out.println(JsonVerifier.verifyJson(pathToFile));
    }
}