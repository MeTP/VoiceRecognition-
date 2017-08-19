package multilayerPerceptron.node;

/**
 * Created by Waldemarus on 29.07.2017.
 */
public abstract class Node {

    private double value;
    private double val;

    public Node(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getVal() {
        return val;
    }

    public void setVal(double val) {
        this.val = val;
    }
}
