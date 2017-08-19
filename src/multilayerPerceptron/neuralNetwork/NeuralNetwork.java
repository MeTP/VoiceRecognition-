package multilayerPerceptron.neuralNetwork;


import multilayerPerceptron.data.DataBase;

import java.io.IOException;

public interface NeuralNetwork {

    void forwardPass() throws Exception;
    void backPropagation(float learningRate, float momentum);
    void saveFile(final String filename);
    void loadOFFile(final String filename);
    double getRMSE();
    DataBase initData(String filename, int[] expect) throws IOException;
    void initData(DataBase dataBase);
}