
package multithreadingg;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;


class Result {
    double totalAmount = 0;
    int    count       = 0;
    double max         = Double.NEGATIVE_INFINITY;   // ← تم تصحيحه
    double min         = Double.MAX_VALUE;

    int depositCount  = 0;
    int withdrawCount = 0;
    long executionTime = 0;                          // ← جديد

    Map<Integer, Double> userTotals = new HashMap<>();

    public void merge(Result other) {
        this.totalAmount   += other.totalAmount;
        this.count         += other.count;
        this.max            = Math.max(this.max, other.max);
        this.min            = Math.min(this.min, other.min);
        this.depositCount  += other.depositCount;
        this.withdrawCount += other.withdrawCount;

        for (Map.Entry<Integer, Double> entry : other.userTotals.entrySet())
            userTotals.merge(entry.getKey(), entry.getValue(), Double::sum);
    }
}

class TransactionTask implements Callable<Result> {
    private List<String> lines;

    public TransactionTask(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public Result call() {
        Result result = new Result();

        for (String line : lines) {
            try {
                String[] parts = line.split(",");

                int userId = Integer.parseInt(parts[1]);
                double amount = Double.parseDouble(parts[2]);
                String type = parts[3];

                result.totalAmount += amount;
                result.count++;

                result.max = Math.max(result.max, amount);
                result.min = Math.min(result.min, amount);

                if (type.equalsIgnoreCase("deposit")) {
                    result.depositCount++;
                } else if (type.equalsIgnoreCase("withdraw")) {
                    result.withdrawCount++;
                }

                result.userTotals.merge(userId, amount, Double::sum);

            } catch (Exception e) {
                // ignore bad rows
            }
        }

        return result;
    }
}

public class Multithreadingg {

    public static void main(String[] args) throws Exception {

        String filePath = "transactions.csv"; 
        int numThreads = 4;

        List<String> allLines = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;

        boolean isHeader = true;
        while ((line = br.readLine()) != null) {
            if (isHeader) {
                isHeader = false;
                continue;
            }
            allLines.add(line);
        }
        br.close();

        int chunkSize = allLines.size() / numThreads;
        List<List<String>> chunks = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? allLines.size() : start + chunkSize;
            chunks.add(allLines.subList(start, end));
        }

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Result>> futures = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        for (List<String> chunk : chunks) {
            futures.add(executor.submit(new TransactionTask(chunk)));
        }

        Result finalResult = new Result();

        for (Future<Result> future : futures) {
            finalResult.merge(future.get());
        }

        long endTime = System.currentTimeMillis();
        executor.shutdown();

        double average = finalResult.count == 0 ? 0 : finalResult.totalAmount / finalResult.count;

        System.out.println("===== FINAL RESULTS =====");
        System.out.println("Total Transactions: " + finalResult.count);
        System.out.println("Total Amount: " + finalResult.totalAmount);
        System.out.println("Average Transaction: " + average);
        System.out.println("Max Transaction: " + finalResult.max);
        System.out.println("Min Transaction: " + finalResult.min);

        System.out.println("Deposits: " + finalResult.depositCount);
        System.out.println("Withdrawals: " + finalResult.withdrawCount);

        System.out.println("\n--- Top Users ---");
        finalResult.userTotals.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(10)
                .forEach(e -> System.out.println("User " + e.getKey() + " ? " + e.getValue()));

        System.out.println("\nExecution Time: " + (endTime - startTime) + " ms");
    }
}
