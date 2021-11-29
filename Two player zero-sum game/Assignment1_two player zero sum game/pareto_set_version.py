import gambit
from itertools import product
import sys

g = gambit.Game.read_game(sys.argv[1])

no_of_players = len(list(g.players))
sgs = []

for i in range(no_of_players):
    sgs.append(len(g.players[i].strategies))

sgs.append(no_of_players)
sgs = tuple(sgs)

par_str = []
for i in range(no_of_players):
    par_str.append([])

for i in range(no_of_players):
    lits = []
    for j in range(no_of_players):
        if j != i:
            lits.append(list(range(sgs[j])))
        else:
            lits.append([0])
    lits = list(product(*lits))    
    for k in lits:
        u = -99999
        for j in range(sgs[i]):
            l = list(k)
            l[i] = j
            l = tuple(l)
            u = max(u, g[l][i])
        for j in range(sgs[i]):
            l = list(k)
            l[i] = j
            l = tuple(l)
            if g[l][i] == u:
                par_str[i].append(l)

x = set(par_str[0])


for i in range(no_of_players):
    x = x & set(par_str[i])

if len(x) == 0:
    print "No Pure Strategy Nash Equilibria exist"
else:
    print len(x)

    for i in x:
        for j in i:
            print j,
        print