package multilayerPerceptron.neuralNetwork;

import multilayerPerceptron.node.HiddenNode;
import multilayerPerceptron.node.InputNode;
import multilayerPerceptron.node.OutputNode;
import multilayerPerceptron.other.SigmoidFunction;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataNeuralNetwork {
    private InputNode[] inputLayer;
    private HiddenNode[] hiddenLayer;
    private OutputNode[] outputLayer;

    private double[][] IHWeight;
    private double[][] HOWeight;

    public DataNeuralNetwork(int input, int hidden, int output) {
        inputLayer = new InputNode[input];
        for (int i = 0; i < input; i++) inputLayer[i] = new InputNode(0);

        hiddenLayer = new HiddenNode[hidden];
        for (int i = 0; i < hidden; i++) hiddenLayer[i] = new HiddenNode(0);

        outputLayer = new OutputNode[output];
        for (int i = 0; i < output; i++) outputLayer[i] = new OutputNode(0);

        IHWeight = new double[input][hidden];
        initIH_Weights();

        HOWeight = new double[hidden][output];
        initHO_Weights();
    }

    private void initHO_Weights() {
        HOWeight = new double[hiddenLayer.length][outputLayer.length];
        for (int i = 0; i < hiddenLayer.length; i++)
            for (int j = 0; j < outputLayer.length; j++)
                HOWeight[i][j] = (Math.random() - 0.5F);
        double biasVal = (0.7F * Math.pow(hiddenLayer.length, 1D / outputLayer.length));
        double H_O_weightLength[] = new double[hiddenLayer.length];
        double buff;
        for (int i = 0; i < hiddenLayer.length; i++) {
            buff = 0;
            for (int j = 0; j < outputLayer.length; j++)
                buff += Math.pow(HOWeight[i][j], 2);
            H_O_weightLength[i] = Math.sqrt(buff);
        }
        for (int i = 0; i < hiddenLayer.length; i++)
            for (int j = 0; j < outputLayer.length; j++)
                HOWeight[i][j] = (biasVal * HOWeight[i][j] / H_O_weightLength[i]);

        for (OutputNode anOutputLayer : outputLayer) anOutputLayer.setBias(Math.random() - biasVal);
    }

    private void initIH_Weights() {
        IHWeight = new double[inputLayer.length][hiddenLayer.length];
        for (int i = 0; i < inputLayer.length; i++)
            for (int j = 0; j < hiddenLayer.length; j++)
                IHWeight[i][j] = Math.random() - 0.5F;
        double biasVal = (0.7F * Math.pow(hiddenLayer.length, 1D / outputLayer.length));
        double I_H_weightLength[] = new double[inputLayer.length];
        double buff;
        for (int i = 0; i < inputLayer.length; i++) {
            buff = 0;
            for (int j = 0; j < hiddenLayer.length; j++)
                buff += Math.pow(IHWeight[i][j], 2);
            I_H_weightLength[i] = Math.sqrt(buff);
        }
        for (int i = 0; i < inputLayer.length; i++)
            for (int j = 0; j < hiddenLayer.length; j++)
                IHWeight[i][j] = (biasVal * IHWeight[i][j] / I_H_weightLength[i]);

        for (HiddenNode elem : hiddenLayer) {
            elem.setBias(Math.random() - biasVal);
        }
    }

    public void saveFile(FileWriter writer) throws IOException {
        for (int i = 0; i < inputLayer.length; i++) {
            for (int j = 0; j < hiddenLayer.length; j++)
                writer.write(IHWeight[i][j] + ";");
            writer.write("\r\n");
        }

        for (int i = 0; i < hiddenLayer.length; i++) {
            for (int j = 0; j < outputLayer.length; j++)
                writer.write(HOWeight[i][j] + ";");
            writer.write("\r\n");
        }

        for (HiddenNode elem: hiddenLayer) {
            writer.write( elem.getBias() + ";");
        }
        writer.write("\r\n");

        for (OutputNode elem: outputLayer) {
            writer.write( elem.getBias() + ";");
        }
        writer.write("\r\n");
        writer.flush();
    }

    public void loadFile(BufferedReader br) throws IOException {
        String strLine;
        double[] buffer;
        String[] bufferStr;

        for (int i = 0; i < inputLayer.length; i++) {
            strLine = br.readLine();
            bufferStr = strLine.split(";");
            buffer = new double[hiddenLayer.length];
            for (int j = 0; j < buffer.length; j++)
                buffer[j] = Double.parseDouble(bufferStr[j]);
            IHWeight[i] = buffer;
        }

        for (int i = 0; i < hiddenLayer.length; i++) {
            strLine = br.readLine();
            bufferStr = strLine.split(";");
            buffer = new double[outputLayer.length];
            for (int j = 0; j < buffer.length; j++)
                buffer[j] = Double.parseDouble(bufferStr[j]);
            HOWeight[i] = buffer;
        }

        strLine = br.readLine();
        bufferStr = strLine.split(";");
        for (int i = 0; i < hiddenLayer.length; i++)
            hiddenLayer[i].setBias(Double.parseDouble(bufferStr[i]));

        strLine = br.readLine();
        bufferStr = strLine.split(";");
        for (int i = 0; i < outputLayer.length; i++)
            outputLayer[i].setBias(Double.parseDouble(bufferStr[i]));
    }

    public InputNode getInputNode(int i) {
        if (i > inputLayer.length) return null;
        return inputLayer[i];
    }

    public HiddenNode getHiddenNode(int i) {
        if (i > hiddenLayer.length) return null;
        return hiddenLayer[i];
    }

    public OutputNode getOutputNode(int i) {
        if (i > outputLayer.length) return null;
        return outputLayer[i];
    }

    public OutputNode getOutputNode() {
        if (outputLayer.length == 1) return outputLayer[0];
        else return null;
    }

    public int getInputLayerLength(){
        return inputLayer.length;
    }

    public int getHiddenLayerLength(){
        return hiddenLayer.length;
    }

    public int getOutputLayerLength(){
        return outputLayer.length;
    }

    public void setInputLayer(double[] data) throws Exception {
        if (data.length != inputLayer.length) throw new Exception(
                this.getClass().getName() + " разные размеры массивов");
        for (int i = 0; i < inputLayer.length; i++){
            inputLayer[i].setValue(data[i]);
        }
    }

    public void calculateHiddenLayer() {
        for (int m = 0; m < hiddenLayer.length; m++) {
            double buff = 0;
            for (int n = 0; n < inputLayer.length; n++)
                buff += (inputLayer[n].getValue() * IHWeight[n][m]);
            buff += hiddenLayer[m].getBias();
            hiddenLayer[m].setValue(SigmoidFunction.sigmoid(buff));
            hiddenLayer[m].setVal(buff);
        }
    }

    public void calculateOutputLayer() {
        for (int m = 0; m < outputLayer.length; m++) {
            double buff = 0;
            for (int n = 0; n < hiddenLayer.length; n++)
                buff += (hiddenLayer[n].getValue() * HOWeight[n][m]);
            buff += outputLayer[m].getBias();
            outputLayer[m].setValue(SigmoidFunction.sigmoid(buff));
            outputLayer[m].setVal(buff);
        }
    }

    public void backPropagation(int[] expect, float learningRate, float momentum) {
        if (expect != null) {
            for (int i = 0; i < outputLayer.length; i++) {
                double detS = SigmoidFunction.detSigma(outputLayer[i].getValue());
                outputLayer[i].setError(
                        -(expect[i] - outputLayer[i].getValue()) * detS);
                outputLayer[i].setBias(
                        outputLayer[i].getBias() - learningRate * momentum * outputLayer[i].getError());
            }
        }

        double errorSum;
        for (int i = 0; i < hiddenLayer.length; i++) {
            errorSum = 0;
            for (int j = 0; j < outputLayer.length; j++) {
                errorSum += outputLayer[j].getError() * HOWeight[i][j];
            }
            hiddenLayer[i].setError(
                    SigmoidFunction.detSigma(hiddenLayer[i].getValue()) * errorSum);
            hiddenLayer[i].setBias(
                    hiddenLayer[i].getBias() - learningRate * momentum * hiddenLayer[i].getError());
        }

        for (int i = 0; i < inputLayer.length; i++) {
            for (int j = 0; j < hiddenLayer.length; j++) {
                IHWeight[i][j] -= learningRate * momentum * hiddenLayer[j].getError() * inputLayer[i].getValue();
                for (int k = 0; k < outputLayer.length; k++)
                    HOWeight[j][k] -= learningRate * momentum * outputLayer[k].getError() * hiddenLayer[j].getValue();
            }
        }
    }

    @Override
    public String toString() {
        String str = "[";
        for (OutputNode out : outputLayer) {
            str += " ";
            str += out.getValue() > 0 ? "TRUE" : "FALSE";
            str += " ";
        }
        str += "][";
        for (OutputNode out : outputLayer) {
            str += " ";
            str += out.getValue();
            str += " ";
        }
        str += "]";
        return str;
    }
}