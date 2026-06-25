package blueDungeon.logic.common.grid.cell;

public enum CellPriority {
    
    /**
     * La priorité absolu est réservé au composant qui doivent passé avant tous les autres.
     * Typiquement ceux dont le rôle est de filtré des évenements.
     * (ex : un verrou (qui dont donc passer avant une porte))
     */
    ABSOLUTE(5),
    /**
     * La priorité importante est réservé au composant qui peuvent annulé un evenement
     * et qui doivent donc passé avant les composant basic.
     * (ex : une porte peut empéché le joueur d'aller sur la case,
     * cette vérification doit donc se faire avant le déclanchement d'un piège)
     */
    IMPORTANT(4),
    /**
     * La priorité attribué par default si elle n'est pas précisé.
     * Elle est utilisé lorsque le composant ne peut pas annulé d'evenement.
     * (ex : un piège à pics déclanché par onEnter ne vas pas annulé le déplacement du joueur)
     */
    DEFAULT(3),
    /**
     * Cette priorité veut dire que le composant peut aussi bien être le premier que le dernier à être éxécuté
     * sans que cela est d'impact.
     * (ex : un emmetteur de lumière)
     * Dans la pratique il sera exécuté après la priorité par défault mais avant les derniers.
     * Il désigne aussi les composant qui ne réagisse à aucun evenement.
     */
    NO_DEPENDENCY(2),
    /**
     * Cette priorité désigne les composant qui doivent être éxécuté en dernier.
     * En particulier les composant qui se base sur des élement suceptible d'être changé par les priorités supérieurs.
     * (ex : calculateur de pv :
     * Imaginons un composant qui envoit un signal contenant l'information du nombre de pv du joueur à l'entré dans la case.
     * Les pv peuvent être modifié par des pièges qui sont eux même en priorité par défault.
     * Le composant doit donc passer après les pièges)
     */
    LAST(1);

    private final int value;

    private CellPriority(int value){
        this.value = value;
    }

    /**
     * @return int
     */
    public int getValue(){
        return value;
    }
}
