#!/usr/bin/env python



def mavenbuild(path):
    path = "/Users/mawei/work_java_code/cwms"
    output = os.popen('mvn clean install package -Dmaven.test.skip=true')
    print output.read()

def readServicePathFile(path):
    f = open(path)
    line = f.readline()
    while line:
        strList = line.split(":");
        print strList[0]
        print '\n'
        print strList[1]
        line = f.readline()
    f.close()

readServicePathFile("servicepath")