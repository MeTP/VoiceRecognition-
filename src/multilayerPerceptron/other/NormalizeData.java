package multilayerPerceptron.other;

public class NormalizeData {

    private double maxValue;

    public double[][] doNormalize(double[][] mass) {
        double maxValue = 0;
        for (double[] mas : mass)
            for (int j = 0; j < mass[0].length; j++)
                if (maxValue < Math.abs(mas[j]))
                    maxValue = Math.abs(mas[j]);
        if (maxValue != 0) {
            this.maxValue = maxValue;
            for (int i = 0; i < mass.length; i++)
                for (int j = 0; j < mass[0].length; j++)
                    mass[i][j] /= maxValue;
        }
        return mass;
    }

    public double doDenormalize(double x) {
        return maxValue * x;
    }
}
