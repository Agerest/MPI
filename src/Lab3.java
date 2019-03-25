import mpi.MPI;

public class Lab3 {

    public static final int[] DIGITS = new int[]{1024, 100000, 0};

    public static final double PI25DT = 3.141592653589793238462643;

    public static void main(String args[]) throws Exception {
        MPI.Init(args);

        System.out.printf("Процесс номер %d запущен на компьютере: '%s'\n", MPI.COMM_WORLD.Rank(), MPI.Get_processor_name());

        int i = 0;
        int[] n = new int[1];
        double[] mypi = new double[1];
        double startwtime = 0.0;
        double endwtime;
        do {
            if (MPI.COMM_WORLD.Rank() == 0) {
                System.out.printf("Введите количество интервалов (n): (0 для выхода): %d\n", DIGITS[i]);
                n[0] = DIGITS[i++];
                startwtime = System.currentTimeMillis();
            }
            MPI.COMM_WORLD.Barrier();
            MPI.COMM_WORLD.Bcast(n, 0, 1, MPI.INT, 0);
            if (n[0] != 0) {
                double[] sum = new double[1];
                double h = 2*n[0];
                int I = MPI.COMM_WORLD.Rank()+1;
                while (I<=n[0]) {
                    double x = (2*I-1)/h;
                    sum[0] += 4/(1+x*x);
                    I += 4;
                }
                sum[0] = 1.0/n[0]*sum[0];
                MPI.COMM_WORLD.Reduce(sum,0,mypi,0,1,MPI.DOUBLE,MPI.SUM,0);
                MPI.COMM_WORLD.Barrier();
                if (MPI.COMM_WORLD.Rank() == 0) {
                    System.out.printf("резульат: pi=%.23f, Расхождение с эталоном %.23f\n", mypi[0], mypi[0] - PI25DT);
                    endwtime = System.currentTimeMillis();
                    System.out.printf("время работы %f секунд\n", (endwtime - startwtime)/1000);
                }
            }
        } while (n[0]!=0);

        MPI.Finalize();
    }
}
