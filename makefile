JFLAG = -cp
JCFLAG = -d
JAVA = java
JC = javac
JVM = java
VI = vi
RM = rm -rf

SRC= src/
BIN = bin/
RES = resources/
OUT = out/
BOO = bool2cnf/

SIM = peersim.Simulator

default:
	@echo "Choisissez une execution : leapFrog, bool2cnf, cleanbool2cnf, clean"
	@echo "Si premiere execution 	:  make bool2cnf"
	@echo "Apres 		     	: make leapFrog  n=3"
	@echo "ou"
	@echo "			     	:make clean"

leapFrog:
	$(RM) $(OUT)formula.out
	$(RM) $(OUT)formula.cnf
	touch $(OUT)formula.out
	touch $(OUT)formula.cnf
	$(JC) $(JCFLAG) $(BIN) $(SRC)/LeapFrog.java
	$(JAVA) $(JFLAG) $(BIN) LeapFrog $(n) >> $(OUT)formula.out
	./$(RES)$(BOO)bool2cnf < $(OUT)formula.out > $(OUT)formula.cnf
	minisat $(OUT)formula.cnf

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
