from Elf import Elf
from Group import Group
import random


def get_elves():
    n = int(input("Enter the count of elves: "))
    k = random.randint(1, n - 1)
    if k > (n / 2):
        honest = k
    else:
        honest = n - k
    elves = []
    for i in range(1, n + 1, 1):
        if honest > 0:
            elf = Elf(i, True)
            honest = honest - 1
        else:
            elf = Elf(i, False)
        elves.append(elf)
    random.shuffle(elves)
    return elves


def evaluate(group):
    if group.elves[0].personality:
        elf_1_response = group.elves[1].personality
    else:
        lie = random.randint(1, 10)
        if lie % 2 == 0:
            elf_1_response = group.elves[1].personality
        else:
            elf_1_response = not group.elves[1].personality
    if group.elves[1].personality:
        elf_2_response = group.elves[0].personality
    else:
        lie = random.randint(1, 10)
        if lie % 2 == 0:
            elf_2_response = group.elves[0].personality
        else:
            elf_2_response = not group.elves[0].personality
    group.responses = [elf_1_response, elf_2_response]


def find_an_honest(elves):
    if len(elves) == 1 or len(elves) == 2:
        return elves[0]
    groups = group_elves(elves)
    for group in groups:
        if len(group.elves) > 1:
            evaluate(group)
    groups = elimination(groups)
    count_elves = count_elves_func(groups)
    if count_elves % 2 == 0:
        new_elves = get_half_of_elves(groups)
        return find_an_honest(new_elves)
    single_elf = find_single_elf(groups)
    groups.remove(single_elf)
    count_groups = len(groups)
    new_elves = get_half_of_elves(groups)
    if count_groups % 2 == 0:
        new_elves.append(single_elf)
    return find_an_honest(new_elves)


def get_half_of_elves(groups):
    elves = []
    for group in groups:
        elves.append(group.elves[0])
    return elves


def elimination(groups):
    must_delete = []
    for group in groups:
        if False in group.responses:
            must_delete.append(group)
    for group in must_delete:
        groups.remove(group)
    return groups


def count_elves_func(groups):
    count = 0
    for group in groups:
        count = count + len(group.elves)
    return count


def group_elves(elves):
    count = len(elves)
    groups = []
    for i in range(0, 2 * int(count / 2), 2):
        group = Group([elves[i], elves[i + 1]], [])
        groups.append(group)
    if count % 2 == 1:
        group = Group([elves[-1]], [])
        groups.append(group)
    return groups


def find_single_elf(groups):
    for group in groups:
        if len(group.elves) == 1:
            return group


if __name__ == '__main__':
    elves = get_elves()
    honest_elf = find_an_honest(elves)
    print("Honest Elf : " + str(honest_elf))
