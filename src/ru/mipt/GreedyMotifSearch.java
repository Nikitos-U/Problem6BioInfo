package ru.mipt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GreedyMotifSearch {
    public static int a = 0;
    public static String[] greedyMotifSearch(String[] dna, int k, int t) {
        String[] bestMotif = new String[dna.length];
        for (int i = 0; i < t; i++) {
            bestMotif[i] = dna[i].substring(0, k);
        }
        String[] toProfile = new String[t];
        for (int i = 0; i < dna[0].length() - k + 1; i++) {
            toProfile[0] = dna[0].substring(i, i + k);
            for (int j = 1; j < t; j++) {
                a = j;
                toProfile[j] = mostProbableKMer(dna[j], k, profile(toProfile));
                System.out.println("rabotau");
            }
            if (score(toProfile) > score(bestMotif)) {
                bestMotif = toProfile;
            }
        }
        return (bestMotif);
    }

    public static double[][] profile(String[] motifs) {
        float n = motifs.length;
        double[][] profile = new double[4][motifs[0].length()];
        int[] counter = new int[4];
        for (int i = 0; i < motifs[0].length(); i++) {
            for (int k = 0; k < counter.length; k++) {
                counter[k] = 0;
            }
            for (int j = 0; j < a; j++) {
                switch (motifs[j].charAt(i)) {
                    case 'A':
                        counter[0]++;
                        profile[0][i] = counter[0] / a;
                        break;
                    case 'G':
                        counter[1]++;
                        profile[1][i] = counter[1] / a;
                        break;
                    case 'T':
                        counter[2]++;
                        profile[2][i] = counter[2] / a;
                        break;
                    case 'C':
                        counter[3]++;
                        profile[3][i] = counter[3] / a;
                        break;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < motifs[0].length(); j++) {
                System.out.print(profile[i][j] + " ");
            }
            System.out.println();
        }
        return (profile);
    }

    public static String mostProbableKMer(String dnai, int k, double[][] profile) {
        String mostProbableKMer = null;
        double max = 0.0;
        for (int i = 0; i < dnai.length() - k + 1; i++) {
            String kMer = dnai.substring(i, k + i);
            double points = 1;
            for (int j = 0; j < k; j++) {
                switch (kMer.charAt(j)) {
                    case 'A':
                        points *= profile[0][j];
                        break;
                    case 'G':
                        points *= profile[1][j];
                        break;
                    case 'T':
                        points *= profile[2][j];
                        break;
                    case 'C':
                        points *= profile[3][j];
                        break;
                }
            }
            if (max < points) {
                max = points;
                mostProbableKMer = kMer;
            }
        }
        return (mostProbableKMer);
    }

    public static int score(String[] toProfile) {
        int counter = 0;
        int score = 0;
        int[] counter1 = new int[4];
        for (int i = 0; i < toProfile[0].length(); i++) {
            for (int j = 0; j < toProfile.length; j++) {
                switch (toProfile[i].charAt(j)) {
                    case 'A':
                        counter1[0]++;
                        break;
                    case 'G':
                        counter1[1]++;
                        break;
                    case 'T':
                        counter1[2]++;
                        break;
                    case 'C':
                        counter1[3]++;
                        break;
                }
            }
            counter = toProfile[i].length() - Math.max(Math.max(counter1[0], counter1[1]), Math.max(counter1[2], counter1[3]));
            score += counter;
        }
        return (score);
    }

    public static void main(String[] args) throws IOException {
            Scanner scanner = new Scanner(System.in);
            String Filename = scanner.nextLine();
            int k = scanner.nextInt();
            int t = scanner.nextInt();
            FileReader stream = new FileReader(Filename);
            BufferedReader input = new BufferedReader(stream);
            String[] dna = new String[t];
            for(int i = 0; i < t; i++) {
                dna[i] = input.readLine();
            }
        String[] result;
        result = greedyMotifSearch(dna, k, t);
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }
}
