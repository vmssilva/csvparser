package com.github.vmssilva.csvparser;

import com.github.vmssilva.csvparser.config.CsvConfig;
import com.github.vmssilva.csvparser.exception.CsvParseException;
import com.github.vmssilva.csvparser.model.CsvDocument;
import com.github.vmssilva.csvparser.model.Document;

public class App {

  public static void main(String[] args) {

    String csv = """
        Id,Name,Description,Brand,Category,Price
        1,Galaxy X1 Smartphone,6.5-inch display smartphone with 128GB storage,TechMobile,Electronics,1499.90
        2,UltraPro 15 Laptop,15-inch laptop with 16GB RAM and 512GB SSD,CompMax,Computers,4599.00
        3,Bluetooth Headphones,Wireless headphones with noise cancellation,SoundBeat,Accessories,299.99
        4,50-Inch Smart TV,4K UHD Smart TV with HDR and built-in Wi-Fi,VisionPlus,Electronics,2799.90
        5,Espresso Coffee Maker,Automatic espresso machine with milk frother,HomeBrew,Home Appliances,899.50
        6,Smart Fitness Watch,Smartwatch with heart rate monitor and GPS,FitnessPro,Wearables,799.00
        7,Gaming Chair Comfort,Ergonomic gaming chair with adjustable height and recline,MaxComfort,Furniture,1299.99
        8,RGB Gaming Mouse,16000 DPI optical mouse with RGB lighting,ClickTech,Computers,199.90
        9,Pro Mechanical Keyboard,Mechanical keyboard with blue switches and backlight,KeyMaster,Computers,349.90
        10,Plus 10 Tablet,10-inch tablet with 64GB storage and 8MP camera,TechMobile,Electronics,1199.00
        11,ZoomX Digital Camera,24MP camera with 10x optical zoom,FotoSnap,Electronics,1899.90
        12,All-in-One Printer,Printer with scanner and Wi-Fi connectivity,PrintAll,Computers,999.00""";

  }

}
