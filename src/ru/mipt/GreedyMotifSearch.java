package ru.mipt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GreedyMotifSearch {
    public static int a = 0;

    public static List<String> greedyMotifSearch(String[] dna, int k, int t) {
        List<String> bestMotif = new ArrayList<>();
        List<String> toProfile = new ArrayList<>();
        for (int i = 0; i < t; i++) {
            bestMotif.add(i, dna[i].substring(0, k));
        }
        for (int i = 0; i < dna[0].length() - k + 1; i++) {
            toProfile.clear();
            toProfile.add(0, dna[0].substring(i, i + k));
            for (int j = 1; j < t; j++) {
                toProfile.add(j, mostProbableKMer(dna[j], k, profile(toProfile, k)));
            }
            System.out.println(bestMotif);
            int d = score(bestMotif, k);
            int lokh = score(toProfile, k);
            if (d > lokh) {
                bestMotif.clear();
                for (String s : toProfile) {
                    bestMotif.add(s);
                }
                System.out.println("%%%%%%%%%%%%%");
                System.out.println(bestMotif);
                System.out.println(toProfile);
                System.out.println("%%%%%%%%%%%%%%");

            }
        }
        return (bestMotif);
    }

    public static double[][] profile(List<String> motifs, int k) {
        float n = motifs.size();
        double[][] profile = new double[4][k];
        int[] counter = new int[4];
        for (int i = 0; i < k; i++) {
            for (int z = 0; z < counter.length; z++) {
                counter[z] = 0;
            }
            for (int j = 0; j < motifs.size(); j++) {
                switch (motifs.get(j).charAt(i)) {
                    case 'A':
                        counter[0]++;
                        profile[0][i] = counter[0] / n;
                        break;
                    case 'G':
                        counter[1]++;
                        profile[1][i] = counter[1] / n;
                        break;
                    case 'T':
                        counter[2]++;
                        profile[2][i] = counter[2] / n;
                        break;
                    case 'C':
                        counter[3]++;
                        profile[3][i] = counter[3] / n;
                        break;
                }
            }
        }
        //       for (int i = 0; i < 4; i++) {
        //           for (int j = 0; j < k; j++) {
        //               System.out.print(profile[i][j] + " ");
        //           }
//            System.out.println();
        //       }
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
            if (max < points || mostProbableKMer == null) {
                max = points;
                mostProbableKMer = kMer;
            }
        }
        return (mostProbableKMer);
    }

    public static int score(List<String> toProfile, int k) {
        int counter = 0;
        int score1 = 0;
        int[] counter1 = new int[4];
        for (int i = 0; i < k; i++) {
            for (int l = 0; l < counter1.length; l++) {
                counter1[l] = 0;
            }
            for (int j = 0; j < toProfile.size(); j++) {
                switch (toProfile.get(j).charAt(i)) {
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
            //    System.out.println(toProfile.size()+ "-" + Math.max(Math.max(counter1[0], counter1[1]), Math.max(counter1[2], counter1[3])));
            counter = toProfile.size() - Math.max(Math.max(counter1[0], counter1[1]), Math.max(counter1[2], counter1[3]));
            score1 += counter;
            //       System.out.println(score);
        }
        return (score1);
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String Filename = scanner.nextLine();
        int k = scanner.nextInt();
        int t = scanner.nextInt();
        FileReader stream = new FileReader(Filename);
        BufferedReader input = new BufferedReader(stream);
        String[] dna = new String[t];
        for (int i = 0; i < t; i++) {
            dna[i] = input.readLine();
        }
        List<String> result;
        result = greedyMotifSearch(dna, k, t);
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
    }
}
