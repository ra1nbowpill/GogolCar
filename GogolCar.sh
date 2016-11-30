

if test "$1" == "-c"
then
javac -d bin src/*
exit
fi

java -cp "bin" UI $@
