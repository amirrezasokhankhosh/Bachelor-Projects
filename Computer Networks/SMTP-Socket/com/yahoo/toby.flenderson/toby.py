import socket
import os
import colorama


colorama.init()


USERNAME = os.getcwd().split("\\")[-1]
PORT = 12345
IP = '127.0.0.1'
FORMAT = 'utf-8'
SIZE = 1024


def connect_SMTP():
    s = socket.socket()
    s.connect((IP, PORT))
    msg = "HELLO %s" % USERNAME
    print(colorama.Fore.BLUE + ">>> %s" % msg)
    s.send(msg.encode(FORMAT))
    while True:
        message = s.recv(SIZE).decode(FORMAT)
        print(colorama.Fore.RED + ">>> %s" % message)
        print(colorama.Fore.BLUE + ">>> ", end="")
        command = input()
        s.send(command.encode(FORMAT))
        if command == "DATA":
            message = s.recv(SIZE).decode(FORMAT)
            print(colorama.Fore.RED + ">>> %s" % message)
            while True:
                print(colorama.Fore.BLUE + ">>> ", end="")
                command = input()
                s.send(command.encode(FORMAT))
                if command == ".":
                    break
        if command == "QUIT":
            s.close()
            break


while True:
    print(colorama.Fore.WHITE + "> ", end="")
    command = input()
    if command == "SMTP()":
        connect_SMTP()
    if command == "quit()":
        break
