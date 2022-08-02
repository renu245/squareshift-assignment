import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class AirplaneSeating {
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
            ArrayList<Seat> seatRow = new ArrayList<>();
            for(int i=0; i<x; i++){    
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
    
    static ArrayList<Section> layout = new ArrayList<Section>();
    static int passengerCount;
    
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
            for(int i=0; i<sections; i++){
                if(sc.hasNext()){
                    rows = sc.nextInt();
                    columns =sc.nextInt();
                    System.out.println("Row :"+i+"  " +rows+ "  "+columns);
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
                System.out.println("Passengers : "+passengerCount);
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
        //TODO 
        if(layout.size() == 1){
            return passengerNo;
        }
        for(int i=0; i < layout.size(); i++){
            if(i==0){
                Section first = layout.get(i);
                for(int j=0; j<first.rows; j++){
                    first.getSeats().get(j).get(first.columns-1).setPassenger(passengerNo);
                    first.getSeats().get(j).get(first.columns-1).setPosition('A');
                    passengerNo++;
                }
            }
            else if(i== layout.size()-1){
                Section last = layout.get(layout.size()-1);
                for(int j=0; j<last.rows; j++){
                    last.getSeats().get(j).get(0).setPassenger(passengerNo);
                    last.getSeats().get(j).get(0).setPosition('A');
                    passengerNo++;
                }
            } else {
                Section middle = layout.get(i);
                for(int j=0; j<middle.rows; j++){
                    middle.getSeats().get(j).get(0).setPassenger(passengerNo++);
                    middle.getSeats().get(j).get(middle.columns-1).setPassenger(passengerNo++);
                    middle.getSeats().get(j).get(0).setPosition('A');
                    middle.getSeats().get(j).get(middle.columns-1).setPosition('A');
                }
            }
        }
        printLayout();
        return passengerNo;
    }
    public static int fillWindowSeats(int passengerNo){
        if(layout.size()>1){
            Section first = layout.get(0);
            for(int i=0; i<first.rows; i++){
                first.getSeats().get(i).get(0).setPassenger(passengerNo);
                first.getSeats().get(i).get(0).setPosition('W');
                passengerNo++;
            }
            Section last = layout.get(layout.size()-1);
            for(int i=0; i<last.rows; i++){
                last.getSeats().get(i).get(last.columns-1).setPassenger(passengerNo);
                last.getSeats().get(i).get(last.columns-1).setPosition('W');
                passengerNo++;
            }
        } 
        return passengerNo;
    }
    public static int fillCenterSeats(int passengerNo){
        for(int i=0; i<layout.size(); i++){
            Section sec = layout.get(i);
            if(sec.columns < 2){
                return passengerNo;
            } 
            for(int r=0; r<sec.rows; r++){
                for(int c=1 ; c<sec.columns-1; c++){
                    sec.getSeats().get(r).get(c).setPassenger(passengerNo++);
                    sec.getSeats().get(r).get(c).setPosition('C');
                }
            }
        }
        return passengerNo;
    }

    public static void printLayout() {
        for (Section sec : layout) {
            for(int r=0; r<sec.rows; r++){
                for(int c=0; c<sec.columns; c++){
                    System.out.print(""+sec.getSeats().get(r).get(c).getPosition()+" "+ sec.getSeats().get(r).get(c).getPassenger()+ "  ");
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
        //printLayout();

    }
}
