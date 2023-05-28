from tkinter import *
import os


class Setting(object):
    GBN = False
    WS = 1


def start():
    sender_list.delete(0, 'end')
    recevier_list.delete(0, 'end')
    os.system('javac *.java')
    f = open("./input.txt", "w")
    f.write(str(Setting.WS) + "\n" + str(Setting.GBN))
    f.close()
    os.system('java App')

    f = open("./sender.txt", "r")
    for line in f:
        sender_list.insert(END, line)
    f.close()
    f = open("./recevier.txt", "r")
    for line in f:
        recevier_list.insert(END, line)


def sel_GBN():
    Setting.GBN = GBN_var.get()


def sel_WS():
    Setting.WS = WS_var.get()


top = Tk()

GBN_var = BooleanVar()

GBN1 = Radiobutton(top, text="Selective Repeat ARQ", variable=GBN_var, value=False,
                   command=sel_GBN)
GBN1.pack(anchor=W, side=LEFT)

GBN2 = Radiobutton(top, text="GBN", variable=GBN_var, value=True,
                   command=sel_GBN)
GBN2.pack(anchor=W, side=LEFT)

WS_var = IntVar()

WS1 = Radiobutton(top, text="1", variable=WS_var, value=1,
                  command=sel_WS)
WS1.pack(anchor=W, side=RIGHT)

WS2 = Radiobutton(top, text="2", variable=WS_var, value=2,
                  command=sel_WS)
WS2.pack(anchor=W, side=RIGHT)

WS4 = Radiobutton(top, text="4", variable=WS_var, value=4,
                  command=sel_WS)
WS4.pack(anchor=W, side=RIGHT)

WS8 = Radiobutton(top, text="8", variable=WS_var, value=8,
                  command=sel_WS)
WS8.pack(anchor=W, side=RIGHT)

WS16 = Radiobutton(top, text="16", variable=WS_var, value=16,
                   command=sel_WS)
WS16.pack(anchor=W, side=RIGHT)

B = Button(top, text="Start", command=start)
B.pack(side=TOP)

Sender_L = Label(top, text="Sender", font=50)
Sender_L.pack(side=LEFT)

Recevier_L = Label(top, text="Recevier", font=50)
Recevier_L.pack(side=RIGHT)

scrollbar_sender = Scrollbar(top)
scrollbar_sender.pack(side=LEFT, fill=Y)
sender_list = Listbox(top, yscrollcommand=scrollbar_sender.set, width=58)
sender_list.pack(side=LEFT, fill=BOTH)
scrollbar_sender.config(command=sender_list.yview)

scrollbar_recevier = Scrollbar(top)
scrollbar_recevier.pack(side=RIGHT, fill=Y)
recevier_list = Listbox(top, yscrollcommand=scrollbar_recevier.set, width=58)
recevier_list.pack(side=RIGHT, fill=BOTH)
scrollbar_recevier.config(command=recevier_list.yview)

top.mainloop()
