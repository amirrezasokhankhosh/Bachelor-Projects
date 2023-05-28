import os

email_address = input()

username = email_address.split("@")[0]
root = email_address.split("@")[1].split(".")[1]
company = email_address.split("@")[1].split(".")[0]

path = os.getcwd() + "\\" + root + "\\" + company + "\\" + username

print(os.path.isdir(path))