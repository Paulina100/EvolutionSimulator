package evolutionSimulation;

import java.util.Arrays;

public class Genotype {
    int[] genotype = new int [32];  // nie private?
    
    public Genotype(){
        for (int i = 0; i < 32; i++) {
            genotype[i] = (int) (Math.random()*8);
        }
        Arrays.sort(genotype);
    }

    public int getRandomGen(){  // gene
        return genotype[(int)(Math.random()*32)];
    }
}
