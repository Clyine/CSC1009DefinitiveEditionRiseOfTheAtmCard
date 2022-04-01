# python file to communicate with raspberry IOT RFID sensor
# to acquire RFID details from the client
# import libraries
import sys
import RPi.GPIO as GPIO
from mfrc522 import SimpleMFRC522

# get input from system in buffer
s = sys.stdin.readline().strip()
# init RFID module
reader = SimpleMFRC522()

# exit conditions to exit program
while s not in ['break', 'quit']:
    # conditions from input from java
    if s == 'presentcard':
        # get id and text from RFID card
        try:
            id, text = reader.read()
            # write ID of RFID to system 
            sys.stdout.write("{}".format(text) + '\n')
        except:
            # write null if there is an error
            sys.stdout.write("null" + '\n')
    else:
        # write null if there is an error
        sys.stdout.write("null" + '\n')
    # flush system flush
    sys.stdout.flush()
    # read input from buffer
    s = sys.stdin.readline().strip()