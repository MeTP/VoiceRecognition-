package multilayerPerceptron.other;

public interface SigmoidFunction {

    static double sigmoid(double x) {
        return Math.tanh(x);
    }

    static double detSigma(double x) {
        return (1 + Math.tanh(x)) * (1 - Math.tanh(x));
    }
}
