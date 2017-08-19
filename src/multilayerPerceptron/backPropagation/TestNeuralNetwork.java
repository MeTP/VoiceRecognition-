package multilayerPerceptron.backPropagation;
import multilayerPerceptron.data.DataBase;
import multilayerPerceptron.neuralNetwork.NeuralNetwork;

import java.io.IOException;
import java.util.ArrayList;

public class TestNeuralNetwork {
    private NeuralNetwork neuralNetwork;
    private ArrayList<DataBase> dataBases;


    public TestNeuralNetwork(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
        this.dataBases = new ArrayList<>();
    }

    public void initDataBase(final String... args) throws IOException {
        for (String filename : args) {
            dataBases.add(neuralNetwork.initData(filename, null));
        }
    }

    public void start() {
        try {
            for (DataBase data : dataBases) {
                neuralNetwork.initData(data);
                neuralNetwork.forwardPass();
                System.out.println(data.getName() + " :" + neuralNetwork);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
