package multilayerPerceptron.backPropagation;

import multilayerPerceptron.data.DataBase;
import multilayerPerceptron.neuralNetwork.NeuralNetwork;
import multilayerPerceptron.other.Assets;

import java.io.IOException;
import java.util.ArrayList;

public class TrainingNeuralNetwork {
    private NeuralNetwork neuralNetwork;
    private float learningRate;
    private float momentum;
    private ArrayList<DataBase> dataBases;

    public TrainingNeuralNetwork(NeuralNetwork neuralNetwork, final float learningRate, final float momentum) {
        this.neuralNetwork = neuralNetwork;
        this.learningRate = learningRate;
        this.momentum = momentum;
        this.dataBases = new ArrayList<>();
    }

    public void initDataBase(final int index, final String... args ) throws IOException {
        int[] expect = new int[Assets.BIG_OUTPUT_COUNT];
        if (index >= expect.length) throw new IndexOutOfBoundsException(
                "ExpectNum = " + index + ", expect.length = " + expect.length);
        for (int i = 0; i < expect.length; i++){
            if (i == index) expect[i] = 1;
            else expect[i] = -1;
        }

        for (String filename : args) {
            dataBases.add(neuralNetwork.initData(filename, expect));
        }
    }

    public void start() {
        try {
            double error;
            int index = 0;
            System.out.print("\nОбучение");
            do {
                error = 0;
                for (DataBase data : dataBases) {
                    neuralNetwork.initData(data);
                    neuralNetwork.forwardPass();
                    error += neuralNetwork.getRMSE();
                    neuralNetwork.backPropagation(learningRate, momentum);
                }
                System.out.print(".");
                index++;
            }while (error > 0);
            System.out.println("\nЗа " + index + " эпох.\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}

