package multilayerPerceptron.main;

import multilayerPerceptron.audio.RecordAudio;

import javax.sound.sampled.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MainFrame extends JFrame {
	private JButton buttonRecordVoice1;
	private JButton buttonRecordVoice2;
	private JButton buttonRecordVoice3;
	private JLabel status;
	private JLabel labelNameOfFile;
	private JTextField textNameOfFile;
	private static final int RECORD_TIME = 4800;

	private MainFrame() {
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(new Dimension(234, 250));
		this.setLocationRelativeTo(null);

		this.initialize();
		this.setContent();
	}

	private void initButtonRecordVoise(String filename){
		File outputFile = new File(filename + ".wav");

		if (!outputFile.exists() && !outputFile.isFile()) {
			recordAudio(outputFile);
		}
		else status.setText("File exist!");
	}

	private void initialize(){
		status = new JLabel("");
		labelNameOfFile = new JLabel("Your login: ");

		textNameOfFile = new JTextField("");

		buttonRecordVoice1 = new JButton("Record voice 1");
		buttonRecordVoice1.addActionListener(e -> {
			if (!textNameOfFile.getText().equals("")) {
				textNameOfFile.setEnabled(false);
				String filename = textNameOfFile.getText() + "1";
				initButtonRecordVoise(filename);
				buttonRecordVoice2.setEnabled(true);
				buttonRecordVoice1.setEnabled(false);
			}else status.setText("Enter Your login!");
		});

		buttonRecordVoice2 = new JButton("Record voice 2");
		buttonRecordVoice2.setEnabled(false);
		buttonRecordVoice2.addActionListener(e -> {
			if (!textNameOfFile.getText().equals("")) {
				String filename = textNameOfFile.getText() + "2";
				initButtonRecordVoise(filename);
				buttonRecordVoice2.setEnabled(false);
				buttonRecordVoice3.setEnabled(true);
			}else status.setText("Enter Your login!");
		});

		buttonRecordVoice3 = new JButton("Record voice 3");
		buttonRecordVoice3.setEnabled(false);
		buttonRecordVoice3.addActionListener(e -> {
			if (!textNameOfFile.getText().equals("")) {
				String filename = textNameOfFile.getText() + "3";
				initButtonRecordVoise(filename);
				buttonRecordVoice3.setEnabled(false);
			}else status.setText("Enter Your login!");
		});

	}
	private void setContent() {
		GroupLayout gl = new GroupLayout(this.getContentPane());
		this.setLayout(gl);
		gl.setVerticalGroup(
				gl.createSequentialGroup()
						.addGap(8)
						.addGroup(gl.createParallelGroup()
								.addComponent(labelNameOfFile, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textNameOfFile, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
						)
						.addGap(8)
						.addComponent(buttonRecordVoice1, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE)
						.addGap(8)
						.addComponent(buttonRecordVoice2, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE)
						.addGap(8)
						.addComponent(buttonRecordVoice3, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE)
						.addGap(8)
						.addComponent(status, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
		);
		gl.setHorizontalGroup(
				gl.createSequentialGroup()
						.addGap(8)
						.addGroup(gl.createParallelGroup()
								.addGroup(gl.createSequentialGroup()
										.addComponent(labelNameOfFile, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(8)
										.addComponent(textNameOfFile, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
								)
								.addComponent(buttonRecordVoice1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
								.addComponent(buttonRecordVoice2, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
								.addComponent(buttonRecordVoice3, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
								.addComponent(status, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
						)
						.addGap(8)

		);
		setVisible(true);
	}

	private void recordAudio(File outputFile) {
		AudioFormat audioFormat = RecordAudio.getFormat();
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
		TargetDataLine targetDataLine = null;
		try {
			targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
			targetDataLine.open(audioFormat);
		} catch (LineUnavailableException e) {
			System.out.println("unable to get a recording line");
			e.printStackTrace();
			System.exit(1);
		}

		RecordAudio recordAudio = new RecordAudio(targetDataLine, outputFile);
		recordAudio.start();

		status.setText("Start recording");
		System.out.println("Recording started.");

		try {
			Thread.currentThread().sleep(RECORD_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		recordAudio.stopRecording();

		System.out.println("Recording stopped.");
		status.setText("Stop recording");

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(MainFrame::new);
	}

}

