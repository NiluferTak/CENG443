class Data{
    //So the header of the csv file is:Age,Date,A,B,C,D,E
    Double age;
    String date;
    Double A;
    Double B;
    Double C;
    Double D;
    Double E;
    Data (double ag, String da, double a, double b, double c, double d, double e){
        age = ag;
        date = da;
        A = a;
        B = b;
        C = c;
        D = d;
        E = e;



    }

    public String getDate() {
        return date;}
}