package com.personal.deliverytracker;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Andrew on 2014-12-19.
 */
public class Delivery implements Parcelable {
    private int deliveryNumber, paymentOption;
    private float tip;
    private boolean isOut;

    public int getDeliveryNumber() {
        return this.deliveryNumber;
    }

    public int getPaymentOption() {
        return paymentOption;
    }

    public float getTip() {
        return tip;
    }

    public boolean isOut() {
        return isOut;
    }

    public Delivery(int dNumber, float tip, int paymentOption, boolean isOut) {
        this.deliveryNumber = dNumber;
        this.tip = tip;
        this.paymentOption = paymentOption;
        this.isOut = isOut;
    }

    public String toString() {
        String type;
        if (paymentOption == 0) type = "Cash";
        else if (paymentOption == 1) type = "Receipt";
        else if (paymentOption == 4) type = "Debit Receipt";
        else type = "Credit Card";
        String isOutString = "";
        if (this.isOut) isOutString = "*OUT*";
        return "Tipped $" + String.format("%.2f", tip) + " using " + type + ". " + isOutString;
    }

    public Delivery(Parcel in) {
        this.deliveryNumber = in.readInt();
        this.tip = in.readFloat();
        this.paymentOption = in.readInt();
        this.isOut = ((in.readInt()!=0)?true:false);
    }

    @Override
    public void writeToParcel(Parcel dest, int flag) {
        dest.writeInt(deliveryNumber);
        dest.writeFloat(tip);
        dest.writeInt(paymentOption);
        dest.writeInt((isOut) ? 1 : 0);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Parcelable.Creator<Delivery> CREATOR = new Parcelable.Creator<Delivery>() {
        public Delivery createFromParcel(Parcel in) {
            return new Delivery(in);
        }

        public Delivery[] newArray(int size) {
            return new Delivery[size];
        }
    };
}