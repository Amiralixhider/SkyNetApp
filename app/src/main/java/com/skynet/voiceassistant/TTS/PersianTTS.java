
import com.google.cloud.texttospeech.v1beta1.*;
import com.google.protobuf.ByteString;

public class PersianTTS {
    public void speakPersian(String text) {
        try {
            // Set up Google Cloud TTS client
            TextToSpeechClient textToSpeechClient = TextToSpeechClient.create();

            // Set the text input
            SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();

            // Set voice parameters (language, gender)
            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode("fa-IR")
                    .setSsmlGender(SsmlVoiceGender.FEMALE)
                    .build();

            // Set audio encoding
            AudioConfig audioConfig = AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.MP3)
                    .build();

            // Perform text-to-speech
            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

            // Get audio content (audio data)
            ByteString audioContents = response.getAudioContent();

            // Play audio or save it as needed
            // E.g., save the audio or play using Android AudioManager

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
