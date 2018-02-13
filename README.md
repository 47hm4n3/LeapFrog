# Mini-projetSF Leap-Frog

Cree par :

	Athmane BENTAHAR(3410322)

Instructions :

    Pour pouvoir executer le programme il faut avoir prealablement installe le SAT solver minisat (si necessaire modifier la ligne qui qppel minisat lors de l'execution)
    Un dossier minisat se trouve dans ressources si besoin

Make :

    Permet de lancer le menu des commandes possibles
    
bool2cnf:

    Permet de compiler le convertisseur bool2cnf necessaire a l'execution du programme
    executee une seule fois a la premiere utilisation

leapFrog:

    Permet de lancer l'execution du programme LeapFrog avec un nombre de grenouilles "n" et un nombre d'etapes "s" a executer saisis au clavier, avec la commande (pour n = 3 et s = 7 par exemple)
    make leapFrog n=3 s=7

cleanbool2cnf:

    Supprime les fichiers generes par la compilation de bool2cnf

clean:

    Vide les fichiers formula.out et formula.cnf dans le dossier out a la racine du projet
