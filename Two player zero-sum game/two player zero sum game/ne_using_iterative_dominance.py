from itertools import combinations
import copy
con = [
    (0, 0),
    (0, 1),
    (0, 2),
    (0, 3),
    (0, 4),
    (0, 5),

    (1, 0),
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),

    (2, 0),
    (2, 1),
    (2, 2),
    (2, 3),
    (2, 4),
    (2, 5),

    (3, 0),
    (3, 1),
    (3, 2),
    (3, 3),
    (3, 4),
    (3, 5),

    (4, 0),
    (4, 1),
    (4, 2),
    (4, 3),
    (4, 4),
    (4, 5)]

g = {
    (0, 0): {0: 0, 1: 0},
    (0, 1): {0: 8, 1: 0},
    (0, 2): {0: 2, 1: 3},
    (0, 3): {0: 1, 1: 1},
    (0, 4): {0: 5, 1: 6},
    (0, 5): {0: -1, 1: 2},

    (1, 0): {0: 5, 1: 1},
    (1, 1): {0: 2, 1: 2},
    (1, 2): {0: 3, 1: 2},
    (1, 3): {0: 6, 1: 4},
    (1, 4): {0: 7, 1: 5},
    (1, 5): {0: 9, 1: 6},


    (2, 0): {0: 3, 1: 4},
    (2, 1): {0: -4, 1: 4},
    (2, 2): {0: 3, 1: -4},
    (2, 3): {0: 2, 1: 1},
    (2, 4): {0: 1, 1: 2},
    (2, 5): {0: 8, 1: 5},

    (3, 0): {0: 1, 1: 0},
    (3, 1): {0: -1, 1: 0},
    (3, 2): {0: 4, 1: 0},
    (3, 3): {0: 2, 1: 6},
    (3, 4): {0: 3, 1: 4},
    (3, 5): {0: 1, 1: 6},


    (4, 0): {0: 3, 1: 7},
    (4, 1): {0: 8, 1: 7},
    (4, 2): {0: 2, 1: 7},
    (4, 3): {0: 3, 1: 7},
    (4, 4): {0: 4, 1: 7},
    (4, 5): {0: 5, 1: 2}

}


no_of_players = 2
sgs = [5, 6]
ps = []
player_strategies_segregated = []
for player_id in range(no_of_players):
    temp_dict = {}
    for strategy in range(sgs[player_id]):
        temp_dict[strategy] = []

    for value in con:
        temp_dict[value[player_id]].append(value)

    player_strategies_segregated.append(temp_dict)

# print('-----------------------------------------------------------------')
# print('player_strategies_segregated')
# print(player_strategies_segregated)
# print('-----------------------------------------------------------------')


def removeStrategy(player_id, strategy_id, player_strategies_segregat):
    if(strategy_id in player_strategies_segregat[player_id]):

        player_strategies_segregat[player_id].pop(strategy_id)

        for playerid in range(no_of_players):
            if(playerid != player_id):
                strategykeys = list(
                    player_strategies_segregat[playerid].keys())
                firstkey = strategykeys[0]
                remove_indexs = [i for i in range(len(player_strategies_segregat[playerid][firstkey]))
                                 if player_strategies_segregat[playerid][firstkey][i][player_id] == strategy_id]
                remove_indexs = sorted(remove_indexs, reverse=True)

                for i in remove_indexs:
                    for strategy in strategykeys:
                        player_strategies_segregat[playerid][strategy].pop(i)

        return player_strategies_segregat


def findDominance(strategy_i, strategy_j, player_id, player_strategies_segregat):

    lenOfStrategiesLeft = len(
        player_strategies_segregat[player_id][strategy_i])

    less = 0
    greater = 0
    equal = 0
    for i in range(lenOfStrategiesLeft):
        valuei = g[player_strategies_segregat[player_id]
                   [strategy_i][i]][player_id]
        valuej = g[player_strategies_segregat[player_id]
                   [strategy_j][i]][player_id]
        if(valuei > valuej):
            greater = 1
        elif(valuei == valuej):
            equal = 1
        else:
            less = 1

    # 1 , 1 , 1 => no relation
    # 1 , 0 , 1 => no relation

    # 0 , 0 , 1 => i strongly dominates j
    # 0 , 1 , 1 => i weakly dominates j

    # 1 , 0 , 0 => j strongly dominates i
    # 1 , 1 , 0 => j weakly dominates
    if(less == 1 and greater == 1):
        return None, None
    elif (greater == 1 and equal == 0):
        return (strategy_i, strategy_j), 0
    elif (greater == 1 and equal == 1):
        return (strategy_i, strategy_j), 1
    elif (less == 1 and equal == 0):
        return (strategy_j, strategy_i), 0
    elif (less == 1 and equal == 1):
        return (strategy_j, strategy_i), 1


def somemethod(value, i):
    print(' -----------------------------------------------')
    print(i)
    print(' -----------------------------------------------')
    for player_id in range(no_of_players):
        remainingstrategies = list(value[player_id].keys())
        print(' remaining strategy ', remainingstrategies)
    print(' -----------------------------------------------')


def getPairsOfStrategies(player_strategies_segregat):
    # can even reduce it further , using transivity property
    dominancepairdict = {'strong': [], 'weak': []}
    temp_ps = []
    flagcount = 0
    for player_id in range(no_of_players):
        remainingstrategies = list(
            player_strategies_segregat[player_id].keys())
        if(len(remainingstrategies) == 1):
            flagcount += 1
            temp_ps.append(remainingstrategies[0])
        allcombinations = list(combinations(remainingstrategies, 2))
        for combo in allcombinations:
            dominancepair, equal = findDominance(
                combo[0], combo[1], player_id, player_strategies_segregat.copy())
            if(dominancepair != None):
                pair = [player_id, dominancepair[1]]
                if(equal == 1):
                    if(pair not in dominancepairdict['weak']):
                        dominancepairdict['weak'].append(pair)
                else:
                    if(pair not in dominancepairdict['strong']):
                        dominancepairdict['strong'].append(pair)

    if(flagcount == no_of_players):
        print('  ps found ', temp_ps)
        ps.append(temp_ps)
        return
    if(len(dominancepairdict['strong']) != 0):
        for _dominancepair in dominancepairdict['strong']:
            [player_id, weaker_strategy_id] = _dominancepair
            print('....found strong strategy for player', player_id,
                  ' and strategy is ', weaker_strategy_id)
            player_strategies_segregat = removeStrategy(
                player_id, weaker_strategy_id, player_strategies_segregat.copy())
        getPairsOfStrategies(player_strategies_segregat.copy())

    elif(len(dominancepairdict['weak']) != 0):
        # Collect all weak strategies and then choose the order
        # actually do this later
        temp_storage = []
        length_storage = []
        for i in range(len(dominancepairdict['weak'])):
            temp_storage.append(copy.deepcopy(player_strategies_segregat))

            # temp_storage.append(player_strategies_segregat.deepcopy())
            length_storage.append(len(temp_storage[i]))

        print('****************************************************')
        print(dominancepairdict)
        print('****************************************************')


        for i, _dominancepair in enumerate(dominancepairdict['weak']):
            print('..pair       ', i, '...', _dominancepair)
            [player_id, weaker_strategy_id] = _dominancepair
            removeStrategy( player_id, weaker_strategy_id, temp_storage[i])

            somemethod(temp_storage[i], i)
            getPairsOfStrategies(temp_storage[i])




getPairsOfStrategies(player_strategies_segregated.copy())
#     ## You dont have strong strategy

print(' ps ', ps)
# def test(somelist):
#     somelist.pop(0)
#     print('...', somelist)

# klist = list(range(9))
# for i in range(9):
#     test(klist.copy())



# for i in con:
#     flag= 0
#     for j in range(no_of_players):
#         for k in range(sgs[j]):
#             if i[j] == k:
#                 continue
#             l= list(i)
#             l[j]= k
#             l= tuple(l)
#             if g[i][j] < g[l][j]:
#                 flag= 1
#                 break
#         if flag == 1:
#             break
#     if flag == 0:
#         ps.append(i)

# temp_con = [(0,0,0),
#             (0,0,1),
#             (0,1,0),
#             (0,1,1),

#             (1,0,0),
#             (1,0,1),
#             (1,1,0),
#             (1,1,1),

#             (2,0,0),
#             (2,0,1),
#             (2,1,0),
#             (2,1,1)
# ]

# no_of_players= 3
# sgs = [3,2,2]
# for value in temp_con:
#     print(value, '...', value[player_id])
#     player_id_strategies[value[player_id]].append(value)

# print('player_id_strategies', player_id_strategies)


# for all the players first separate out the strategy
# values are in g
# to access g i n
