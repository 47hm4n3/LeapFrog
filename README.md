# Mini-projetSF Leap-Frog

Cree par :

	Athmane BENTAHAR (3410322)

Fichiers :

    *   Le dossiee src/ contient la classe LeapFrog.java contenant le main()
    *   Le dossier bin/ contient la classe LeapFrog.class
    *   Le dossier ressources/ contient bool2cnf et minisat
    *   Le dossier out/ contient les fichiers
        -   formula.out : contient la formule booleeenne generee
        -   formula.cnf : contient la formule au format cnf
        -   result.out  : contient le resultat de l'execution genere par minisat

Instructions :

    Pour pouvoir executer le programme il faut avoir prealablement installe
    le SAT solver minisat (si necessaire modifier la ligne qui appel minisat
    lors de l'execution)
    Un dossier minisat se trouve dans ressources si besoin

make :

    Permet de lancer le menu des commandes possibles

minisat:

    Permet de compiler minisat necessaire a l'execution du programme,
    executee une seule fois a la premiere utilisation
    
bool2cnf:

    Permet de compiler le convertisseur bool2cnf necessaire a l'execution du
    programme, executee une seule fois a la premiere utilisation

leapFrog:

    Permet de lancer l'execution du programme LeapFrog avec :
        *   n= un nombre de grenouilles et
        *   s= nombre d'etapes a executer
    avec la commande (pour n = 3 et s = 7 par ex.)
    $> make leapFrog n=3 s=7
    
leapFrogQ7:

    Permet de lancer l'execution du programme LeapFrog avec :
        *   s= nombre d'etapes a executer
        *   in= configuration d'entree et
        *   out= configuration de sortie
    avec la commande (pour s = 5 et in = xyxVyyx et out = xxyyxyV par ex.)
    $> make leapFrogQ7 s=5 in=xyxVyyx out=xxyyxyV

cleanminisat:

    Supprime les fichiers generes par la compilation de minisat

cleanbool2cnf:

    Supprime les fichiers generes par la compilation de bool2cnf

clean:

    Vide les dossiers out/ et bin/ a la racine du projet des fichiers generes
