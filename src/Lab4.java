import mpi.MPI;

public class Lab4 {

    public static void main(String args[]) throws Exception {

        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();

        if (rank == 0) {
            char[] sendArray = new char[]{'2', '3', '1'};
            char[] recArray = new char[3];
            MPI.COMM_WORLD.Send(sendArray, 0, 3, MPI.CHAR, 1, 1);
            System.out.println("Посылка данных (" + sendArray[0] + sendArray[1] + sendArray[2] + ")  в цикл …");
            double starttime = System.currentTimeMillis();
            MPI.COMM_WORLD.Recv(recArray, 0, 3, MPI.CHAR, 3, 0);
            double endtime = System.currentTimeMillis();
            System.out.printf("Процесс номер %d получил ("+ recArray[0] + recArray[1] + recArray[2] + ") от процесса %d  ... %f сек\n", 0, 3,(endtime-starttime)/1000);
        }
        else {
            char[] array = new char[3];
            double starttime = System.currentTimeMillis();
            MPI.COMM_WORLD.Recv(array, 0, 3, MPI.CHAR, rank-1, rank);
            double endtime = System.currentTimeMillis();
            System.out.printf("Процесс номер %d получил ("+ array[0] + array[1] + array[2] + ") от процесса %d  ... %f сек\n", rank, rank-1, (endtime-starttime)/1000);
            if (rank == 3) {
                MPI.COMM_WORLD.Send(array, 0, 3, MPI.CHAR, 0, 0);
            }
            else {
                MPI.COMM_WORLD.Send(array, 0, 3, MPI.CHAR, rank+1, rank+1);
            }
        }

        MPI.Finalize();
    }
}
