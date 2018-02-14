JFLAG = -cp
JCFLAG = -d
JAVA = java
JC = javac
VI = vi
RM = rm -rf

SRC= src/
BIN = bin/
RES = resources/
OUT = out/
MS = minisat/
BOO = bool2cnf/
ECHO = @echo

default:
	$(ECHO) "Choisissez une execution	:	leapFrog, minisat, bool2cnf, cleanminisat, cleanbool2cnf, clean"
	$(ECHO) "Si vous voulez compiler minisat	:	make minisat"
	$(ECHO) "Si premiere, compiler bool2cnf	:	make bool2cnf"
	$(ECHO) "Apres				: 	make leapFrog  n=3 s=7 (nbr grenoilles et nbr etapes)"
	$(ECHO) "				ou"
	$(ECHO) "				:	make leapFrogQ7 s=5 in=xyVxxyy out=xyxyVyx (par ex.)"

leapFrog:
	$(RM) $(OUT)formula.out
	$(RM) $(OUT)formula.cnf
	$(JC) $(JCFLAG) $(BIN) $(SRC)/LeapFrog.java
	$(ECHO) "==============================================================";
	$(ECHO) "                   Debut de la simulation";
	$(ECHO) "==============================================================";
	$(JAVA) $(JFLAG) $(BIN) LeapFrog $(n) $(s) 2>&1
	$(ECHO) "==============================================================";
	$(ECHO) "                    Fin de la simulation";
	$(ECHO) "==============================================================";

leapFrogQ7:
	$(RM) $(OUT)formula.out
	$(RM) $(OUT)formula.cnf
	$(JC) $(JCFLAG) $(BIN) $(SRC)/LeapFrog.java
	$(ECHO) "==============================================================";
	$(ECHO) "                  Debut de la simulation Q7";
	$(ECHO) "==============================================================";
	$(JAVA) $(JFLAG) $(BIN) LeapFrog $(s) $(in) $(out) 2>&1
	$(ECHO) "==============================================================";
	$(ECHO) "                   Fin de la simulation Q7";
	$(ECHO) "==============================================================";

minisat:
	cd $(RES)$(MS) && $(MAKE)
	cd ../../

bool2cnf:
	cd $(RES)$(BOO) && $(MAKE) all
	flex scan.l;
	bison parse.y;
	cc parse.tab.c trans.c;
	cd ../../

cleanminisat:
	cd $(RES)$(MS) && $(MAKE) clean
	cd ../../

cleanbool2cnf:
	cd $(RES)$(BOO) && $(MAKE) clean
	cd ../../

clean:
	$(RM) $(BIN)*.class
	$(RM) $(OUT)*
