package multilayerPerceptron.main;

import multilayerPerceptron.backPropagation.TestNeuralNetwork;
import multilayerPerceptron.backPropagation.TrainingNeuralNetwork;
import multilayerPerceptron.neuralNetwork.MultilayerPerceptron;
import multilayerPerceptron.neuralNetwork.NeuralNetwork;

import java.io.IOException;

public class Main {
	private NeuralNetwork neuralNetwork = new MultilayerPerceptron();
	private TrainingNeuralNetwork train = new TrainingNeuralNetwork(neuralNetwork, 0.20F, 0.20F);
	private TestNeuralNetwork test = new TestNeuralNetwork(neuralNetwork);

	public static void main(String[] args) {
         new Main();
    }

    private Main() {
        try {
            training();
            testing();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	private void testing() throws IOException {
		System.out.println("\t<<<<<< TESTS >>>>>>");
		neuralNetwork.loadOFFile("Test2");

		test.initDataBase("Vova1", "Vova2", "Vova3", "Vova4");
		test.initDataBase("Ayrat1","Ayrat2","Ayrat3","Ayrat4");
		test.initDataBase("Vera1","Vera2","Vera3","Vera4");

		test.start();


	}
    private void training() throws IOException {
	    train.initDataBase(0,"Vova1", "Vova2", "Vova3");
	    train.initDataBase(1,"Ayrat1","Ayrat2","Ayrat3");
	    train.initDataBase(2,"Vera1", "Vera2", "Vera3");

	    train.start();

	    neuralNetwork.saveFile("Test2");
	    System.out.println("\n");
    }

}
