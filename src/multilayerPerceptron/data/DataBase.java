package multilayerPerceptron.data;

import multilayerPerceptron.other.NormalizeData;

public class DataBase {
    private final double[][] data;
    private final int[] expect;
    private NormalizeData normalize;
    private String name;

    public DataBase(final double[][] data,final String name, final int[] expect) {
        this.normalize = new NormalizeData();
        this.data = normalize.doNormalize(data);
        this.expect = expect;
        this.name = name;
    }

    public double deNormalize(double x){
        return normalize.doDenormalize(x);
    }

    public double[][] getData() {
        return data;
    }

    public int[] getExpect() {
        return expect;
    }

    public String getName() {
        return name;
    }
}
