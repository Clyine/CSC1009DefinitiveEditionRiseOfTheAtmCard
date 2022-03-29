import sys
import time
import RPi.GPIO as GPIO
from mfrc522 import SimpleMFRC522

s = sys.stdin.readline().strip()
reader = SimpleMFRC522()

while s not in ['break', 'quit']:
    if s == 'presentcard':
        try:
            id, text = reader.read()
            sys.stdout.write("id: {} text:{}".format(id,text) + '\n')
        except:
            sys.stdout.write("null" + '\n')
    else:
        sys.stdout.write("null" + '\n')
    sys.stdout.flush()
    s = sys.stdin.readline().strip()