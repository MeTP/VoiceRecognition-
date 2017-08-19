package multilayerPerceptron.node;

/**
 * Created by Waldemarus on 29.07.2017.
 */
public class HiddenNode extends Node {

    private double bias;
    private double bestBias;
    private double error;

    public HiddenNode(double value) {
        super(value);
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public double getBestBias() {
        return bestBias;
    }

    public void setBestBias(double bestBias) {
        this.bestBias = bestBias;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }
}
