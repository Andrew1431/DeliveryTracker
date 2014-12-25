package com.personal.deliverytracker;

import android.content.Context;
import android.text.format.DateFormat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Andrew on 2014-12-23.
 */
public class Day extends ArrayList<Delivery> {
    private String fileName;
    private long startTimeInMilliseconds;
    private Context context;

    public Day(Context context) {
        log("A new day!");
        int counter = 0;
        String[] temp = new String[4];
        BufferedReader reader;
        BufferedWriter writer;
        this.context = context;
        this.startTimeInMilliseconds = System.currentTimeMillis();
        log(String.valueOf(Integer.valueOf(DateFormat.format("hh", System.currentTimeMillis()).toString())));

        log(String.valueOf(DateFormat.format("hh", System.currentTimeMillis())));

        if (Integer.valueOf(DateFormat.format("hh", System.currentTimeMillis()).toString()) < 4) {
            this.fileName = DateFormat.format("yy-MM-" + String.valueOf(Integer.valueOf(DateFormat.format("dd", System.currentTimeMillis()).toString()) - 1), System.currentTimeMillis()).toString();
        } else {
            this.fileName = DateFormat.format("yy-MM-dd", System.currentTimeMillis()).toString();
        }


        File f = new File(context.getFilesDir(), fileName + ".txt");
        log("File is: " + context.getFilesDir().toString() + "/" + fileName + ".txt");

        if (f.exists()) {
            log("File exists during construction!");
            try {
                reader = new BufferedReader(new FileReader(context.getFilesDir() + "/" + fileName + ".txt"));
                log(context.getFilesDir().toString() + "/" + fileName + ".txt");
                String t = reader.readLine();
                if (t != null) {
                    log("Start time is not null from text file: " + t);
                    startTimeInMilliseconds = Long.valueOf(t);
                } else {
                    log("Start time is null :( " + t);
                }
                log("This is the line before the while loop.");
                while ((temp[counter] = reader.readLine()) != null) {
                    log("While loop started; c is " + String.valueOf(counter));
                    log("DNumber:" + temp[0] + " Float(tip): " + temp[1] + " Payment Option: " + temp[2] + " Out: " + temp[3]);
                    counter++;
                    log("Counter increased to " + String.valueOf(counter));
                    if (counter == 4) {
                        log("Counter reset; delivery object added to list!");
                        counter = 0;
                        super.add(new Delivery(Integer.valueOf(temp[0]), Float.valueOf(temp[1]), Integer.valueOf(temp[2]), Boolean.valueOf(temp[3])));
                        log("Delivery successfully added!");
                    }
                }
            } catch (IOException ioEx) {
                log("Is this running?");
            }
        } else {
            log("File does not exist!");
            try {
                f.createNewFile();
                writer = new BufferedWriter(new FileWriter(context.getFilesDir() + "/" + fileName + ".txt"));
                writer.write(String.valueOf(startTimeInMilliseconds));
                writer.close();
            } catch (IOException ioEx) {

            }
        }
    }

    public void Save() {
        log("Saving...");
        File f = new File(context.getFilesDir(), fileName + ".txt");
        if (f.exists()) {
            log("File exists and is named: " + fileName + ".txt");
            try {
                f.delete();
                log("File deleted");
                f.createNewFile();
                log("File restored");
                log(String.valueOf(System.currentTimeMillis()));
                log(String.valueOf(startTimeInMilliseconds));
                BufferedWriter writer = new BufferedWriter(new FileWriter(context.getFilesDir() + "/" + fileName + ".txt"));
                writer.write(String.valueOf(startTimeInMilliseconds));
                writer.newLine();
                log(String.valueOf(startTimeInMilliseconds));
                int counter = 0;
                for (int x = 0; x < super.size(); x++) {
                    log("Writing object #" + counter);
                    writer.write(String.valueOf(super.get(x).getDeliveryNumber()));
                    log(String.valueOf(super.get(x).getDeliveryNumber()));
                    writer.newLine();
                    writer.write(String.valueOf(super.get(x).getTip()));
                    log(String.valueOf(super.get(x).getTip()));
                    writer.newLine();
                    writer.write(String.valueOf(super.get(x).getPaymentOption()));
                    log(String.valueOf(super.get(x).getPaymentOption()));
                    writer.newLine();
                    writer.write(String.valueOf(super.get(x).isOut()));
                    log(String.valueOf(super.get(x).isOut()));
                    writer.newLine();
                    counter++;
                }
                log(String.valueOf(counter));
                writer.close();
                log("Holy shit i forgot to close the file!!!! :@");
            } catch (IOException ioEx) {

            }
        }
    }

    public long getStartTimeInMilliseconds() {
        return startTimeInMilliseconds;
    }

    public String getFileName() {
        return fileName;
    }

    private void log(String s) {
        System.out.println(s);
    }
}
