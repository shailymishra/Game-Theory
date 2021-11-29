# IGT Assignment 1

- __Team no.:__ 14
- __Team:__ 
    - Aishwarya Srivastava (20171046)
    - Shaily Mishra (2019701007)
- __Problem no. 3:__ Find​ ​ all ​ pure strategy nash equilibria for an n-person game

## Requirements for running
- Python2.7
- gambit==15.1.1
- itertools

## Solution:
    
    For each player i:
        
        For each action a in set of actions of player i:
            
            Find set of strategies, S_ia, such that if strategy S is in S_ia, player i plays action a and a is the best response to S_(-i)
        
        PS_i be the union of all the sets S_ia of player i. 
        // PS_i represents set of all strategies where i is playing it's best response. 

    // Now if a strategy S is in the set PS_i of all players then S is in best_response(S). Therefore S is a nash equilibrium.

    Set of pure strategy nash equilibriums = Common elements across all PS_i

    // As we explore each action of all the players and we look at all S_(-i)s for them it ensures that we don't miss a pure strategy nash equilibrium.

## Time Complexity:
Let there be n players and m_i be the number of strategies each player i has the time complexity of the solution is:

- Worst Case:
    
    O(T) = O(Σ_{i}(π_{j!=i}m_j) + min(m_0, m_1,...,m_n))

- Best Case:

    O(T) = O(Σ_{i}(π_{j!=i}m_j))