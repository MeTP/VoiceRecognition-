package multilayerPerceptron.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class RecordAudio extends Thread    {
	private TargetDataLine          m_line;
	private AudioInputStream        m_audioInputStream;
	private File                    m_outputFile;

	public RecordAudio(TargetDataLine m_line, File m_outputFile) {
		this.m_line = m_line;
		this.m_audioInputStream = new AudioInputStream(m_line);
		this.m_outputFile = m_outputFile;
	}

	public void start() {
		m_line.start();
		super.start();
	}

	public void stopRecording() {
		m_line.stop();
		m_line.close();
	}


	public void run() {
		try {
			AudioSystem.write(m_audioInputStream, AudioFileFormat.Type.WAVE, m_outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// преобразовать непослушные биты в набор осмысленных значений — амплитуд сигнала
	// http://stackoverflow.com/questions/26574326/how-to-calculate-the-level-amplitude-db-of-audio-signal-in-java
	static public AudioFormat getFormat() {
		float sampleRate = 8000f;
		int sampleSizeInBits = 16;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = false;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}


}
