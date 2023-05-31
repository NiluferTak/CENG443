import java.util.*;
import java.util.stream.*;
import java.io.*;
import java.nio.Buffer;
import java.util.function.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Queries {
    
    public static void main(String[] args) throws FileNotFoundException{

        
        if(args.length == 0){
            System.out.println("No filename is given");
            System.exit(0);
        }
        String filename = args[0];
        
        // Take question number from command line
        if(args.length == 1){
            System.out.println("No question number is given");
            System.exit(0);
        }
        int question = Integer.parseInt(args[1]);
        //System.out.println("Question: " + question);
        //int question = 6;
        //int question = Integer.parseInt(args[1]);
        BufferedReader br = new BufferedReader(new FileReader(filename));
        
        Function<String, Data> lineToData = (line) -> {
            String[] p = new String[7];
            //initialize all values to 0
            for (int i = 0; i < p.length; i++) {
                p[i] = "0";
            }
            //split the line
            p = line.split(",");
            double age = 0.0;
            String date = "";
            double a = 0.0;
            double b = 0.0;
            double c = 0.0;
            double d = 0.0;
            double e = 0.0;
    

            if (p.length > 0 && !p[0].isEmpty()) {
                age = Double.parseDouble(p[0]);
            }
            if (p.length > 1) {
                date = p[1];
            }
            if (p.length > 2 && !p[2].isEmpty()) {
                a = Double.parseDouble(p[2]);
            }
            if (p.length > 3 && !p[3].isEmpty()) {
                b = Double.parseDouble(p[3]);
            }
            if (p.length > 4 && !p[4].isEmpty()) {
                c = Double.parseDouble(p[4]);
            }
            if (p.length > 5 && !p[5].isEmpty()) {
                d = Double.parseDouble(p[5]);
            }
            if (p.length > 6 && !p[6].isEmpty()) {
                e = Double.parseDouble(p[6]);
            }

            return new Data(age, date, a, b, c, d, e);
                
                };

        List<Data> data = br.lines().skip(1).map(lineToData).collect(Collectors.toList());

        
        Double maxPriceA = data.stream().map((d) -> d.A).max(Double::compare).get();
        //System.out.println("Max price of A: " + maxPriceA);

        //Q1
        if (question == 1) {

            //What was the price of most expensive product sold,products sold are either A,B,C,D or E.

            Double maxPrice = data.stream().mapToDouble(d -> Math.max(Math.max(Math.max(Math.max(d.A, d.B), d.C), d.D), d.E)).max().orElse(0.0);
            System.out.println("the price of most expensive product sold: " + maxPrice);
            
        }
        

        //Q2
        if(question == 2){

            List<SumWithDate> sums = data.stream()
            .map(d -> new SumWithDate(d.A + d.B + d.C + d.D + d.E, d.date))
            .collect(Collectors.toList());

            Optional<String> maxDate = sums.stream()
            .max(Comparator.comparingDouble(SumWithDate::getSum))
            .map(SumWithDate::getDate);

            String dateOfMaxSum = maxDate.get();
            System.out.println("The date of the highest paid purchase by a customer: " + dateOfMaxSum);

        }
        

        //Q3
        if(question == 3){
            //What was the most popular product before 2000?
            

            int productA = (int) data.stream().filter(d -> d.A > 0).filter(d->Integer.parseInt(d.date.split("-")[0]) < 2000).count();
            int productB = (int) data.stream().filter(d -> d.B > 0).filter(d->Integer.parseInt(d.date.split("-")[0]) < 2000).count();
            int productC = (int) data.stream().filter(d -> d.C > 0).filter(d->Integer.parseInt(d.date.split("-")[0]) < 2000).count();
            int productD = (int) data.stream().filter(d -> d.D > 0).filter(d->Integer.parseInt(d.date.split("-")[0]) < 2000).count();
            int productE = (int) data.stream().filter(d -> d.E > 0).filter(d->Integer.parseInt(d.date.split("-")[0]) < 2000).count();
            int maxproduct = max(max(max(productA, productB),max(productC, productD)),productE);

                    //System.out.println("Max product before 2000: " + maxproduct);
            Map<String, Double> productSales = new HashMap<>();
            productSales.put("A", (double) productA);
            productSales.put("B", (double) productB);
            productSales.put("C", (double) productC);
            productSales.put("D", (double) productD);
            productSales.put("E", (double) productE);
            String mostPopularProduct = productSales.entrySet().stream().filter(e -> e.getValue() == maxproduct).map(e -> e.getKey()).findFirst().orElse("");

            System.out.println("Most popular product before 2000: " + mostPopularProduct);

            
                    
        }



        //Q4
        if(question == 4){

            //What was the least popular product after and including 2000?

            int productA2 = (int) data.stream().filter(d -> d.A > 0).filter(d->Integer.parseInt(d.date.split("-")[0]) >= 2000).count();
            int productB2 = (int) data.stream().filter(d -> d.B > 0).filter(d->Integer.parseInt(d.date.split("-")[0]) >= 2000).count();
            int productC2 = (int) data.stream().filter(d -> d.C > 0).filter(d->Integer.parseInt(d.date.split("-")[0]) >= 2000).count();
            int productD2 = (int) data.stream().filter(d -> d.D > 0).filter(d->Integer.parseInt(d.date.split("-")[0]) >= 2000).count();
            int productE2 = (int) data.stream().filter(d -> d.E > 0).filter(d->Integer.parseInt(d.date.split("-")[0]) >= 2000).count();
            double minproduct = min(min(min(productA2, productB2),min(productC2, productD2)),productE2);

            //System.out.println("Min product after and including 2000: " + minproduct);
            Map<String, Double> productSales2 = new HashMap<>();
            productSales2.put("A", (double) productA2);
            productSales2.put("B", (double) productB2);
            productSales2.put("C", (double) productC2);
            productSales2.put("D", (double) productD2);
            productSales2.put("E", (double) productE2);
            String leastPopularProduct = productSales2.entrySet().stream().filter(e -> e.getValue() == minproduct).map(e -> e.getKey()).findFirst().orElse("");

            System.out.println("Least popular product after and including 2000: " + leastPopularProduct);

        }
        
        //Q5
        if (question == 5){
            //What was the most popular product among teens? (Hint: find the product with the smallest average customer age)
            int sumA = (int) data.stream().filter(d -> d.A > 0).count();
            double averageAgeA = (double) data.stream().filter(d -> d.A > 0).mapToDouble(d -> d.age).sum() / sumA;

            int sumB = (int) data.stream().filter(d -> d.B > 0).count();
            double averageAgeB = (double) data.stream().filter(d -> d.B > 0).mapToDouble(d -> d.age).sum() / sumB;

            int sumC = (int) data.stream().filter(d -> d.C > 0).count();
            double averageAgeC = (double) data.stream().filter(d -> d.C > 0).mapToDouble(d -> d.age).sum() / sumC;

            int sumD = (int) data.stream().filter(d -> d.D > 0).count();
            double averageAgeD = (double) data.stream().filter(d -> d.D > 0).mapToDouble(d -> d.age).sum() / sumD;

            int sumE = (int) data.stream().filter(d -> d.E > 0).count();
            double averageAgeE = (double) data.stream().filter(d -> d.E > 0).mapToDouble(d -> d.age).sum() / sumE;

            double minAge = min(min(min(averageAgeA, averageAgeB),min(averageAgeC, averageAgeD)),averageAgeE);

            
            Map<String, Double> productSales3 = new HashMap<>();
            productSales3.put("A", (double) averageAgeA);
            productSales3.put("B", (double) averageAgeB);
            productSales3.put("C", (double) averageAgeC);
            productSales3.put("D", (double) averageAgeD);
            productSales3.put("E", (double) averageAgeE);
            String mostPopularProductAmongTeens = productSales3.entrySet().stream().filter(e -> e.getValue() == minAge).map(e -> e.getKey()).findFirst().orElse("");

            System.out.println("Most popular product among teens: " + mostPopularProductAmongTeens);
        }
        
        
        //Q6

        if (question == 6){

             //What was the most inflated product on the data?
        // Add data elements to the 'data' list
        
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            Data oldestPurchase = data.stream()
                    .min(Comparator.comparing(d -> {
                        try {
                            return dateFormat.parse(d.date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }))
                    .orElse(null);
            
            Data newestPurchase = data.stream()
                    .max(Comparator.comparing(d -> {
                        try {
                            return dateFormat.parse(d.date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }))
                    .orElse(null);
            
            if (oldestPurchase != null && newestPurchase != null) {
                // Calculate the price difference for each product
                Map<String, Double> priceDifferences = new HashMap<>();
                if (oldestPurchase.A != null && newestPurchase.A != null) {
                    priceDifferences.put("A", Math.abs(newestPurchase.A - oldestPurchase.A)/oldestPurchase.A);
                }
                if (oldestPurchase.B != null && newestPurchase.B != null) {
                    priceDifferences.put("B", Math.abs(newestPurchase.B - oldestPurchase.B)/oldestPurchase.B);
                }
                if (oldestPurchase.C != null && newestPurchase.C != null) {
                    priceDifferences.put("C", Math.abs(newestPurchase.C - oldestPurchase.C)/oldestPurchase.C);
                }
                if (oldestPurchase.D != null && newestPurchase.D != null) {
                    priceDifferences.put("D", Math.abs(newestPurchase.D - oldestPurchase.D)/oldestPurchase.D);
                }
                if (oldestPurchase.E != null && newestPurchase.E != null) {
                    priceDifferences.put("E", Math.abs(newestPurchase.E - oldestPurchase.E)/oldestPurchase.E);
                }
            
                // Find the most inflated product
                String mostInflatedProduct = Collections.max(priceDifferences.entrySet(), Map.Entry.comparingByValue()).getKey();
                System.out.println("The most inflated product: " + mostInflatedProduct);
            } else {
                System.out.println("No data available");
            }

            




        }
        
       
        

    
        
        
    }
    public static int max(int a, int b){
        if(a>b){
            return a;
        }
        else{
            return b;
        }
    }

    public static double min(double a, double b){
        if(a<b){
            return a;
        }
        else{
            return b;
        }
    }
}
