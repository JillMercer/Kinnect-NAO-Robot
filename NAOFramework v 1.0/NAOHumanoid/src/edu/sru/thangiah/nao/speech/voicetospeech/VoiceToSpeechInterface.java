package edu.sru.thangiah.nao.speech.voicetospeech;

public interface VoiceToSpeechInterface {

	String WordsToRelay();

	boolean HasWordsToRelay();

	void relayVoiceInput() throws Exception;

	void getVoiceInput(int seconds);

	void clearHeardWordsBuffer();

}
