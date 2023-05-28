class Group:
    def __init__(self, elves, responses):
        self.elves = elves
        self.responses = responses

    def __str__(self):
        return "Elves : " + str(self.elves)