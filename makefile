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
BOO = bool2cnf/

default:
	@echo "Choisissez une execution	:	leapFrog, bool2cnf, cleanbool2cnf, clean"
	@echo "Si premiere execution		:	make bool2cnf"
	@echo "Apres				: 	make leapFrog  n=3 s=7 (nbr grenoilles et nbr etapes)"
	@echo "			ou"
	@echo "				:	make clean"

leapFrog:
	$(RM) $(OUT)formula.out
	$(RM) $(OUT)formula.cnf
	$(JC) $(JCFLAG) $(BIN) $(SRC)/LeapFrog.java
	$(JAVA) $(JFLAG) $(BIN) LeapFrog $(n) $(s) >> $(OUT)formula.out
	./$(RES)$(BOO)bool2cnf < $(OUT)formula.out > $(OUT)formula.cnf
	$(RM) $(OUT)result.out
	minisat $(OUT)formula.cnf  2>&1 | tee $(OUT)result.out
	@echo "==============================================================";
	@echo "Fin de la simulation avec $(n) Grenouilles et $(s) Etapes ";
	@echo "==============================================================";

bool2cnf:
	cd $(RES)$(BOO) && $(MAKE) all
	flex scan.l;
	bison parse.y;
	cc parse.tab.c trans.c;
	cd ../../

cleanbool2cnf:
	cd $(RES)$(BOO) && $(MAKE) clean
	cd ../../

clean:
	$(RM) $(BIN)*.class
	$(RM) $(OUT)*
