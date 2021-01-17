# Invoice generator
> 
This application allows you to generate invoices for a Salomon store. It only needs the most important information. It makes work easier.

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Features](#features)
* [Status](#status)
* [Guide](#guide)
* [Contact](#contact)

## General info

Invoice generator is the result of my java programming language learning. 
Plus, she became useful for my previous employer. 
Thanks to it, you can easily generate an invoice and have full control over their numbering as well as the saving path. 
By making small changes, I can easily adapt the generator to your activity.

## Technologies
* Java - version 11.0.8
* iText - version 7.1.3
* JavaFX - version 11.0.2


## Code Examples
RadioButtons in sample.fxml:
```
   <RadioButton GridPane.columnIndex="6" GridPane.rowIndex="1" alignment="CENTER" text="Karta" toggleGroup="$paymentType" selected="true"/>
   <RadioButton GridPane.columnIndex="6" GridPane.rowIndex="2" alignment="CENTER" text="Gotówka" toggleGroup="$paymentType"/>
   <RadioButton GridPane.columnIndex="6" GridPane.rowIndex="3" alignment="CENTER" text="Karta/Gotówka" toggleGroup="$paymentType"/>
```
Enum type to select payment
```
public enum PaymentType {
    card,money,both,noSelected
}
```
Select payment in controller
```
    private String paymentMethod() {
        if(paymentMethod == PaymentType.both)
            return "karta/gotówka";
        else if (paymentMethod == PaymentType.money)
            return "gotówka";
        else
            return "karta";
    }
```
Use information about payment to generate invoice
```
public class PDFGenerator {
    (...)
    private final PaymentType paymentMethod;
    (...)
```
## Features
List of features ready and TODOs for future development
* Generating an invoice (logo,information,items,resume)
* Simple GUI
* Info about errors in dialog
* Variable number of items and add more
* Useful user function (clear,test,exit)
* Remember information about save path and number last invoice
* Usable .jar version to the client

To-do list:
* Webservice with BIR (REGON) to get information by NIP number
* Unit tests

## Status
Project is: in progress (current version can be used)

Possibility of modification for the client.

## Guide
![Example screenshot](./img_README/screenshot.png)

## Inspiration

The application was written while I was working in a sports store. I had to create invoices in an ineffective way.
I decided to simplify the task for myself and my colleagues.

In my project I used NumberTranslation found on the internet.

## Contact
Created by Jarosław Czerniak [@jikoso2](https://github.com/jikoso2) - See my GitHub!