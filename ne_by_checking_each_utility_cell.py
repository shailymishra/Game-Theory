import gambit
from itertools import product
import sys

g = gambit.Game.read_game(sys.argv[1])

no_of_players = len(list(g.players))
sgs = []

for i in range(no_of_players):
    sgs.append(len(g.players[i].strategies))
sgs = tuple(sgs)

ps = []
for i in g.contingencies:
    flag = 0
    for j in range(no_of_players):
        for k in range(sgs[j]):
            if i[j] == k:
                continue
            l = list(i)
            l[j] = k
            l = tuple(l)
            if g[i][j] < g[l][j]:
                flag = 1
                break
        if flag == 1:
            break
    if flag == 0:
        ps.append(i)

for ne in ps:
    for i in range(no_of_players):
        print ne[i],
    print