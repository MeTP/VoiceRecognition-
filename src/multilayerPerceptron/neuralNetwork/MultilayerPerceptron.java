package multilayerPerceptron.neuralNetwork;

import multilayerPerceptron.data.DataBase;
import multilayerPerceptron.other.Assets;

import java.io.*;
import java.util.ArrayList;

public class MultilayerPerceptron implements NeuralNetwork {
    private DataNeuralNetwork bigNeuralNetwork;
    private ArrayList<DataNeuralNetwork> listSmallNeuralNetwork;
    private double[][] data;
    private int[] expect;

    public MultilayerPerceptron() {
        initialize();
    }

    private void initialize(){
        bigNeuralNetwork = new DataNeuralNetwork(
                Assets.BIG_INPUT_COUNT, Assets.BIG_HIDDEN_COUNT, Assets.BIG_OUTPUT_COUNT);
        listSmallNeuralNetwork = new ArrayList<>();
        for (int i = 0; i < Assets.BIG_INPUT_COUNT; i++)
            listSmallNeuralNetwork.add(new DataNeuralNetwork(
                    Assets.SMALL_INPUT_COUNT, Assets.SMALL_HIDDEN_COUNT, Assets.SMALL_OUTPUT_COUNT));
    }

    @Override
    public void forwardPass() throws Exception {
        int index = 0;
        for (DataNeuralNetwork elem : listSmallNeuralNetwork) {
            if(data != null) elem.setInputLayer(data[index]);
            elem.calculateHiddenLayer();
            elem.calculateOutputLayer();
            bigNeuralNetwork.getInputNode(index).setOutputNode(elem.getOutputNode());
            index++;
        }
        bigNeuralNetwork.calculateHiddenLayer();
        bigNeuralNetwork.calculateOutputLayer();
    }

    @Override
    public void backPropagation(float learningRate, float momentum) {
        bigNeuralNetwork.backPropagation(expect,learningRate,momentum);
        int index = 0;
        for (DataNeuralNetwork elem : listSmallNeuralNetwork){
            elem.getOutputNode().setHiddenNode(bigNeuralNetwork.getHiddenNode(index));
            elem.backPropagation(null,learningRate,momentum);
            index++;
        }
    }

    @Override
    public double getRMSE(){
        double result = 0;
        for (int i = 0; i < expect.length; i++)
            result += Math.pow(bigNeuralNetwork.getOutputNode(i).getValue() - expect[i], 2);
        return Math.sqrt(result / bigNeuralNetwork.getInputLayerLength());
    }

    @Override
    public void saveFile(final String filename) {
        //TODO создать окно сохранения файла
        try (FileWriter writer = new FileWriter(filename + ".txt", false)) {
            bigNeuralNetwork.saveFile(writer);
            for (DataNeuralNetwork elem : listSmallNeuralNetwork) elem.saveFile(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadOFFile(final String filename) {
        //TODO создать окно загрузки файла
        initialize();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(filename + ".txt")))){
            bigNeuralNetwork.loadFile(br);
            for (DataNeuralNetwork elem : listSmallNeuralNetwork) elem.loadFile(br);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DataBase initData(String filename, int[] expect) throws IOException {
        double[][] data = new double[bigNeuralNetwork.getInputLayerLength()]
                [listSmallNeuralNetwork.get(0).getInputLayerLength()];
        String PATH = "voices/";
        FileInputStream fstream = new FileInputStream(PATH + filename +".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        double[] buffer2;
        for (int i = 0; i < data.length; i++) {
            strLine = br.readLine();
            String[] buffer = strLine.split(";");
            buffer2 = new double[data[0].length];
            for (int j = 0; j < buffer2.length; j++)
                buffer2[j] = Double.parseDouble(buffer[j+1]);
            data[i] = buffer2;
        }
        return new DataBase(data, filename, expect);
    }

    @Override
    public void initData(DataBase dataBase) {
        this.data = dataBase.getData();
        this.expect = dataBase.getExpect();
    }


    @Override
    public String toString() {
        return bigNeuralNetwork.toString();
    }
}