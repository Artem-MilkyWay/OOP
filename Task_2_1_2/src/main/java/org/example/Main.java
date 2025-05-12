package org.example;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java -jar Task_2_1_2-1.0-SNAPSHOT-all.jar [master|worker] [numbers|port] [worker_addresses...]");
            return;
        }

        String mode = args[0];
        if ("worker".equals(mode)) {
            if (args.length != 2) {
                System.out.println("Worker mode requires port number");
                return;
            }
            try {
                int port = Integer.parseInt(args[1]);
                Worker.run(port);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port number");
            }
        } else if ("master".equals(mode)) {
            if (args.length < 3) {
                System.out.println("Master mode requires numbers and at least one worker address");
                return;
            }
            String[] numbers = args[1].split(",");
            String[] workerAddresses = new String[args.length - 2];
            System.arraycopy(args, 2, workerAddresses, 0, args.length - 2);

            Master master = new Master(workerAddresses);
            boolean result = false;
            try {
                result = master.processNumbers(numbers);
                System.out.println(String.valueOf(result));
            } finally {
                master.cleanup();
            }
        } else {
            System.out.println("Invalid mode. Use 'master' or 'worker'");
        }
    }
}
