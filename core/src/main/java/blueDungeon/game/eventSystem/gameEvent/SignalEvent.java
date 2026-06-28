package blueDungeon.game.eventSystem.gameEvent;

/**
 * Evenement générique transportant un signal positif/négatif sur un canal nommé.
 * Permet à des composants de case (plaque de pression, levier, bouton...) de
 * communiquer avec d'autres composants (porte...) sans se connaitre directement.
 * @author Romain Vandooren
 */
public class SignalEvent implements GameEvent {

    private final String channel;
    private final boolean value;

    /**
     * @param channel le nom du canal sur lequel le signal est émis.
     * @param value l'état du signal (true = positif, false = négatif).
     */
    public SignalEvent(String channel, boolean value){
        this.channel = channel;
        this.value = value;
    }

    public String getChannel(){
        return channel;
    }

    public boolean getValue(){
        return value;
    }
}
