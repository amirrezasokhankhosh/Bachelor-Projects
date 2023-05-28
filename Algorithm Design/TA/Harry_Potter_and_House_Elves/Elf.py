class Elf:
    def __init__(self, id, personality):
        self.id = id
        self.personality = personality

    def __str__(self):
        return "Elf ID : " + str(self.id) + " Elf Personality : " + str(self.personality)