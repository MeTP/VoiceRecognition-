package multilayerPerceptron.node;

/**
 * Created by Waldemarus on 29.07.2017.
 */
public class InputNode extends Node {

    public InputNode(double value) {
        super(value);
    }

    public void setOutputNode(OutputNode node){
        setValue(node.getValue());
        setVal(node.getVal());
    }

}
