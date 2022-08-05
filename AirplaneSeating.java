import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class AirplaneSeating {
    static ArrayList<Section> layout = new ArrayList<Section>();
    static int passengerCount;
    public static class Seat {
        char position;
        int passenger;

        int getPassenger (){
            return this.passenger;
        }
        void setPassenger(int x) {
            this.passenger = x;
        }

        char getPosition(){
            return this.position;
        }
        void setPosition (char x){
            this.position = x;
        }
    };

    public static class Section {
        int rows;
        int columns;
        ArrayList<ArrayList<Seat>> seats = new ArrayList<ArrayList<Seat>>();

        Section (int x, int y){
            this.rows = x;
            this.columns = y; 
            for(int i=0; i<x; i++){   
                ArrayList<Seat> seatRow = new ArrayList<>(); 
                for(int j=0; j<y; j++){
                    seatRow.add(new Seat());
                }
                this.seats.add(seatRow);
            }
        }
        ArrayList<ArrayList<Seat>> getSeats(){
            return this.seats;
        }
    };
    

    
    public static void readAndValidateInput(){
        File file = new File("input.txt");
        Scanner sc;
        try {
            sc = new Scanner (file);
            if(!sc.hasNext()){
                System.out.println("Incorrect input file. Exiting the program");
                System.exit(100);
            }
            int sections = Integer.valueOf(sc.nextLine());
            System.out.println("Sections : " + sections);
            int rows, columns;
            int totalSeats=0;
            for(int i=0; i<sections; i++){
                if(sc.hasNext()){
                    rows = sc.nextInt();
                    columns =sc.nextInt();
                    //System.out.println("Row :"+i+"  " +rows+ "  "+columns);
                    totalSeats = totalSeats + rows*columns;
                    Section sec = new Section(rows, columns);
                    layout.add(sec);
                } else {
                    System.out.println("Invalid input. Exiting the program");
                    System.exit(100);
                }

            }
            //printLayout();

            if(sc.hasNext()){
                passengerCount = sc.nextInt();
                if(passengerCount > totalSeats){
                    System.out.println("Cannot accodmodate all the passengers. Exiting");
                    System.exit(100);
                }
                // System.out.println("Passengers : "+passengerCount);
            } else {
                System.out.println("No input for passengers. Exiting the program");
                System.exit(100);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    public static int fillAisleSeats(int passengerNo){
        if(layout.size() == 1){
            return passengerNo;
        }
        for(int i=0; i < layout.size(); i++){
            if(i==0){
                Section first = layout.get(i);
                for(int j=0; j<first.rows; j++){
                    first.getSeats().get(j).get(first.columns-1).setPassenger(passengerNo>passengerCount? 0: passengerNo++);
                    first.getSeats().get(j).get(first.columns-1).setPosition('A');
                }
            }
            else if(i== layout.size()-1){
                Section last = layout.get(layout.size()-1);
                for(int j=0; j<last.rows; j++){
                    last.getSeats().get(j).get(0).setPassenger(passengerNo>passengerCount? 0: passengerNo++);
                    last.getSeats().get(j).get(0).setPosition('A');
                }
            } else {
                Section middle = layout.get(i);
                for(int j=0; j<middle.rows; j++){
                    middle.getSeats().get(j).get(0).setPassenger(passengerNo>passengerCount? 0: passengerNo++);
                    middle.getSeats().get(j).get(middle.columns-1).setPassenger(passengerNo>passengerCount? 0: passengerNo++);
                    middle.getSeats().get(j).get(0).setPosition('A');
                    middle.getSeats().get(j).get(middle.columns-1).setPosition('A');
                }
            }
        }
        //printLayout();
        return passengerNo;
    }
    public static int fillWindowSeats(int passengerNo){
        if(layout.size()>1){
            Section first = layout.get(0);
            for(int i=0; i<first.rows; i++){
                first.getSeats().get(i).get(0).setPassenger(passengerNo>passengerCount? 0: passengerNo++);
                first.getSeats().get(i).get(0).setPosition('W');
            }
            Section last = layout.get(layout.size()-1);
            for(int i=0; i<last.rows; i++){
                last.getSeats().get(i).get(last.columns-1).setPassenger(passengerNo>passengerCount? 0: passengerNo++);
                last.getSeats().get(i).get(last.columns-1).setPosition('W');
            }
        } 
        return passengerNo;
    }
    public static int fillCenterSeats(int passengerNo){
        for(int i=0; i<layout.size(); i++){
            Section sec = layout.get(i);
            if(sec.columns <= 2){
                continue;
            } 
            for(int r=0; r<sec.rows; r++){
                for(int c=1 ; c<sec.columns-1; c++){
                    sec.getSeats().get(r).get(c).setPassenger(passengerNo>passengerCount? 0: passengerNo++);
                    sec.getSeats().get(r).get(c).setPosition('C');
                }
            }
        }
        return passengerNo;
    }

    public static void printLayout() {
        String formattedNumber;
        for (Section sec : layout) {
            for(int r=0; r<sec.rows; r++){
                for(int c=0; c<sec.columns; c++){
                    formattedNumber = String.format("%02d", sec.getSeats().get(r).get(c).getPassenger());
                    System.out.print(""+sec.getSeats().get(r).get(c).getPosition()+" "+ formattedNumber+ "  ");
                }
                System.out.println(" ");
            }

            System.out.println(" ");
            System.out.println(" ");
        }
    }

    public static void main(String args[]){
        readAndValidateInput();
        int currentPassenger;
        currentPassenger = fillAisleSeats(1);
        currentPassenger = fillWindowSeats(currentPassenger);
        currentPassenger = fillCenterSeats(currentPassenger);
        printLayout();

    }
}
