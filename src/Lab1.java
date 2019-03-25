import mpi.*;

public class Lab1 {
    public static void main(String args[]) throws Exception {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        String name = MPI.Get_processor_name();
        System.out.printf("Hello world from process %d of %d at %s\n", rank, size, name);
        MPI.Finalize();
    }
}