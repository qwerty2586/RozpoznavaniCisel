package cz.zcu.qwerty;


public class Classification {
    public static final int LOWEST_DISTANCE = 0;
    public static final int NAIVE_BAYES = 1;

    public static final int MANHATTAN = 0;
    public static final int COUNT = 2;


    public static int lowestDistance(int type, int[] sample, int[][] etalons) {
        long sum;
        int lowest= -1;
        long lowest_sum = Long.MAX_VALUE;
        for (int i = 0; i < etalons.length; i++) {
            sum = 0;
            for (int u = 0; u < sample.length; u++) {
                sum += Math.abs(etalons[i][u] - sample[u]);
            }
      //      System.out.println("sum "+i+" "+sum);

            if (sum<lowest_sum) {
                lowest = i;
                lowest_sum=sum;
            }
        }
        return lowest;

    }

    public static int naive_bayes( int[] sample, int[][] etalons, int[] resultMap) {
        double chances[] = new double[10]; // vysledny vektor ale nejdriv si sem dam pocty vzorku k jednotlivym vysledkum
        for (int i = 0; i < resultMap.length; i++) {
            chances[resultMap[i]] += 1.0;
        }

        for (int i = 0; i < 10; i++) {
            double chance = 1.0; // sem budeme postupne nasobit sanci pro toto cislo

            for (int j = 0; j < sample.length; j++) { // prvek vektoru
                double rate = 0.0; // mira kolikrat se vyskytuje prvek z vektoru v etalonech pro toto cislo
                for (int k = 0; k < resultMap.length; k++) { // cislo etalonu
                    if ((sample[j]==etalons[k][j])&&(resultMap[k]==i)) rate += 1.0;
                }
                chance *= (rate/chances[i]);

            }
            chances[i] = chance*(chances[i]/(double)resultMap.length);
        }

        double biggest = 0.0;
        int bigget_index = -1;
        for (int i = 0; i < 10; i++) {
            if (chances[i]>biggest) {
                bigget_index = i;
                biggest = chances[i];
            }
        }
        // System.out.println(Arrays.toString(chances));
        return bigget_index;
        
    }
    static int classes = 3;
    static int before = 255;
    public static int[] divide(int[] input) {
        int[] r =  new int[input.length];
        for (int i = 0; i < input.length; i++) {
            r[i] = (input[i]*classes)/before;
        }
        return r;
    }
    public static int[][] divide(int[][] input) {
        int[][] r =  new int[input.length][input[0].length];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                r[i][j] = (input[i][j]*classes)/before;
            }
        }
        return r;
    }

}
