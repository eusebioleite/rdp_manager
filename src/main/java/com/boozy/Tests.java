package com.boozy;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Tests {
    
    // Function to add x in arr
   public static String[] addString(int n, String array[], String new_string)
   {
       int i;
   
       // create a new array of size n+1
       String new_array[] = new String[n + 1];
   
       // insert the elements from
       // the old array into the new array
       // insert all elements till n
       // then insert x at n+1
       for (i = 0; i < n; i++)
       new_array[i] = array[i];
   
       new_array[n] = new_string;
   
       return new_array;
   }

    public static void main(String[] args) {
        
        try{

            /* execute command */
            
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory(new File(System.getProperty("user.home")));
            String[] commands = "cmd /c echo @gro@007 | anydesk_path 685130652 --with-password".split(" ");
            for (int i = 0; i < commands.length; i++) {
                System.out.println(commands[i]);
                if(commands[i].equals("anydesk_path")) commands[i] = "C:\\Program Files\\anydesk\\anydesk.exe";
            }

            processBuilder.command(commands);
            Process process = processBuilder.start();

            /* display output */
            StringBuilder output_input = new StringBuilder();
            BufferedReader reader_input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader_input.readLine()) != null) {

                output_input.append(line + "\n");

            }
            System.out.println(output_input);
            
            /* display error */
            StringBuilder output_error = new StringBuilder();
            BufferedReader reader_error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line_error;
            while ((line_error = reader_error.readLine()) != null) {

                output_error.append(line_error + "\n");

            }
            System.out.println(output_error);

            process.waitFor();

            } catch (IOException e) {

                System.out.println(e.getMessage());

            } catch (InterruptedException e) {

                System.out.println(e.getMessage());

            }

    }

}
