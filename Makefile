runBDTests:
	javac -cp .:../junit5.jar *.java
	java -jar ../junit5.jar --classpath=. --select-class=BackendDeveloperTests
clean:
	rm -f *.class
runFDTests: 
	javac -cp ../junit5.jar *.java
	java -jar ../junit5.jar --class-path=. --select-class=FrontendDeveloperTests
compile:
	javac -cp .:../junit5.jar *.java
run: compile
	java Backend
