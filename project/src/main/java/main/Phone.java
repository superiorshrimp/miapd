package main;

import java.util.ArrayList;
import java.util.List;

public class Phone{
    private final int price; //euro
    private final int features; //dual sim, ssd slot, 5g, stereo speakers... etc
    private final int ram; //gbs
    private final int cpu; //benchmark
    private final int storage; //gbs
    private final int battery; //idk czy sot czy mah
    private final int charging; //Ws
    private final int software;
    private static final int n = 7; //trzeba wybrać
    private final ArrayList<Integer> attributes = new ArrayList<>();
    private final static ArrayList<String> labels = new ArrayList<>(List.of("price", "features", "ram", "cpu", "storage", "battery", "charging"));

    public Phone(int price, int features, int ram, int cpu, int storage, int battery, int charging, int software){
        this.price = price;
        this.features = features;
        this.ram = ram;
        this.cpu = cpu;
        this.storage = storage;
        this.battery = battery;
        this.charging = charging;
        this.software = software;

        this.attributes.addAll(List.of(this.price, this.features, this.ram, this.cpu, this.storage, this.battery, this.charging));
    }

    public static int getNumberOfAttributes(){return n;}
    public ArrayList<Integer> getAttributes(){return this.attributes;}
    public static ArrayList<String> getLabels(){return labels;}

    public String toString(){
        return "Price" + this.price + "\n" +
                "Features" + this.features + "\n" +
                "RAM" + this.ram + "\n" +
                "CPU" + this.cpu + "\n" +
                "Storage" + this.storage + "\n" +
                "Battery" + this.battery + "\n" +
                "Charging" + this.charging + "\n" +
                "Software" + this.software + "\n";
    }
}
