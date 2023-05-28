import socket
import os
import datetime


PORT = 12345
NUM_CLINETS = 5
FORMAT = 'utf-8'
SIZE = 1024


def check_email(email_address):
    username = email_address.split("@")[0]
    root = email_address.split("@")[1].split(".")[1]
    company = email_address.split("@")[1].split(".")[0]

    path = os.getcwd() + "\\" + root + "\\" + company + "\\" + username

    return os.path.isdir(path)


def create_email(email_from, email_to, email_data):
    email = "<email_from> %s </email_from>\n" % email_from
    email = email + "<email_to> %s </email_to>\n" % email_to
    email = email + "<email_data>\n%s</email_data>" % email_data

    username = email_to.split("@")[0]
    root = email_to.split("@")[1].split(".")[1]
    company = email_to.split("@")[1].split(".")[0]

    path = os.getcwd() + "\\" + root + "\\" + company + "\\" + username + "\\Inbox"

    return email, path


s = socket.socket()
print("[CREATED] Socket successfully created.")

s.bind(('', PORT))
print("[BINDED] socket binded to %s" % (PORT))

s.listen(NUM_CLINETS)
print("[LISTENING] socket is listening")

while True:

    c, addr = s.accept()
    print("")
    print('[NEW CONNECTION] Got connection from', addr)

    while True:

        email_from = ""
        email_to = ""
        email_data = ""
        command = c.recv(SIZE).decode(FORMAT).split()

        if command[0] == "QUIT":
            break

        if command[0] == 'HELLO':
            msg = "250 Hello %s, pleased to meet you." % command[1]
            c.send(msg.encode(FORMAT))

        elif command[0] == 'MAIL':
            if check_email(command[2][1:-1]):
                email_from = command[2][1:-1]
                msg = "250 %s ... Sender OK" % email_from
                c.send(msg.encode(FORMAT))
            else:
                msg = "Error, Entered email address does not exist."
                c.send(msg.encode(FORMAT))

        elif command[0] == "RCPT":
            if check_email(command[2][1:-1]):
                email_to = command[2][1:-1]
                msg = "250 %s ... Recipient OK" % email_to
                c.send(msg.encode(FORMAT))
            else:
                msg = "Error, Entered email address does not exist."
                c.send(msg.encode(FORMAT))

        elif command[0] == "DATA":
            msg = "354 Enter mail, end with '.' on a line by itself"
            c.send(msg.encode(FORMAT))
            while True:
                par = c.recv(SIZE).decode(FORMAT)
                if par == ".":
                    break
                else:
                    email_data = email_data + par + "\n"
            msg = "250 Message accepted for delivery"
            c.send(msg.encode(FORMAT))

        elif command[0] == "SEND":
            if email_to == "" or email_from == "" or email_data == "":
                msg = "Error, Some attributes are missing."
                c.send(msg.encode(FORMAT))
            else:
                email, path = create_email(email_from, email_to, email_data)
                filename = str(datetime.datetime.now()
                               ).replace(":", "-") + ".txt"
                path = path + "\\" + str(filename)
                file = open(path, "w")
                file.write(email)
                file.close()
                msg = "250, Email sent."
                c.send(msg.encode(FORMAT))

    c.close()
    print("[CONNECTION CLOSED]")
    print("")
