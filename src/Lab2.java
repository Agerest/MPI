import mpi.MPI;

public class Lab2 {
    public static final int[] DIGITS = new int[]{256, 245, -100};

    public static void main(String args[]) throws Exception {
        MPI.Init(args);

        int i = 0;
        int[] digit = new int[1];
        do {
            if (MPI.COMM_WORLD.Rank() == 0) {
                System.out.println("Enter a digit (0 or less to exit): " + DIGITS[i]);
                digit[0] = DIGITS[i++];
            }
            MPI.COMM_WORLD.Barrier();
            MPI.COMM_WORLD.Bcast(digit, 0, 1, MPI.INT, 0);
            System.out.printf("Process %d (%s) got %d\n", MPI.COMM_WORLD.Rank(), MPI.Get_processor_name(), digit[0]);
            System.out.flush();
        } while (digit[0] > 0);

        MPI.Finalize();
    }
}
