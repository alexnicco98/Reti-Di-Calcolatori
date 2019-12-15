public class Intervention {
    private String SpeakerName ="default";

    public Intervention(){}

    // modifico il nome dello speaker
    public void setSpeakerName(String nome){
        this.SpeakerName =nome;
    }

    // rimuove lo speaker
    public void removeSpeaker(){
        this.SpeakerName = "default";
    }

    // restituisce il nome dello speaker
    public String getName(){
        return SpeakerName;
    }
}
