import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

class WordProcessor {
    private Set<String> distinctWords;

    public WordProcessor() {
        distinctWords = new HashSet<>();
    }

    public void readFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+"); // Split by whitespace
                for (String word : words) {
                    distinctWords.add(word.toLowerCase()); // Convert to lowercase for case-insensitive comparison
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public Set<String> getDistinctWords() {
        return distinctWords;
    }
}

class SimilarityCalculator {
    public double calculateSimilarity(Set<String> set1, Set<String> set2) {
        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        return (double) intersection.size() / union.size();
    }
}

public class Main {
    public static void main(String[] args) {
        WordProcessor wordProcessor = new WordProcessor();
        SimilarityCalculator similarityCalculator = new SimilarityCalculator();

        String filePath1 = "src/file1.txt"; // Replace with the actual file path
        String filePath2 = "src/file2.txt"; // Replace with the actual file path

        wordProcessor.readFile(filePath1);
        Set<String> distinctWords1 = wordProcessor.getDistinctWords();

        wordProcessor = new WordProcessor(); // Reset wordProcessor for the second file
        wordProcessor.readFile(filePath2);
        Set<String> distinctWords2 = wordProcessor.getDistinctWords();

        similarityCalculator = new SimilarityCalculator();
        double similarity = similarityCalculator.calculateSimilarity(distinctWords1, distinctWords2);

        System.out.println("Jaccard similarity coefficient: " + similarity);
    }
}
