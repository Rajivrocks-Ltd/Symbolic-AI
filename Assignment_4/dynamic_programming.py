#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Dynamic Programming 
Practical for course 'Symbolic AI'
2020, Leiden University, The Netherlands
By Thomas Moerland
"""

import numpy as np
from world import World

class Dynamic_Programming:

    def __init__(self):
        self.V_s = None # will store a potential value solution table
        self.Q_sa = None # will store a potential action-value solution table

    def value_iteration(self, env, gamma=1.0, theta=0.001):
        print("Starting Value Iteration (VI)")
        V_s = np.zeros(env.n_states)
        delta = theta + 1
        num_iter = 0
        while delta > theta:
            delta = 0.0
            V_s_prev = V_s.copy()
            for s in env.states:
                x = V_s[s]
                max_value = -float("inf")
                for a in env.actions:
                    next_s, reward = env.transition_function(s, a)
                    exp_value = (reward + gamma * V_s[next_s])
                    max_value = max(max_value, exp_value)
                V_s[s] = max_value
                delta = max(delta, abs(V_s[s] - x))
            # print the error in each iteration
            print("Iteration: {}, Error: {}".format(num_iter, delta))
            print(V_s)
            print("------------------------------------")
            print(V_s_prev)
            # increment the iteration counter
            num_iter += 1
        self.V_s = V_s
        return

    def Q_value_iteration(self, env, gamma=1.0, theta=0.001):
        ''' Executes Q-value iteration on env.
        gamma is the discount factor of the MDP
        theta is the acceptance threshold for convergence '''

        print("Starting Q-value Iteration (QI)")
        # initialize state-action value table
        Q_sa = np.zeros([env.n_states, env.n_actions])

        # initialize the delta variable to track convergence
        delta = theta + 1
        # initialize counter for number of iterations
        num_iter = 1
        # keep updating value table until convergence
        while delta > theta:
            delta = 0.0
            Q_sa_prev = Q_sa.copy()
            for s in env.states:
                max_value = -float("inf")
                for a in range(len(env.actions)):
                    x = Q_sa[s][a]
                    action = env.actions[a]
                    next_s, reward = env.transition_function(s, action)
                    exp_value = (reward + gamma * max(Q_sa[next_s]))
                    max_value = max(max_value, exp_value)
                    Q_sa[s][a] = max_value
                    delta = max(delta, abs(Q_sa[s][a] - x))
            # print the error in each iteration
            print("Iteration: {}, Error: {}".format(num_iter, delta))
            # print(Q_sa)
            print("------------------------------------")
            # print(Q_sa_prev)
            # increment the iteration counter
            num_iter += 1
        self.Q_sa = Q_sa
        return

                
    def execute_policy(self,env,table='V'):
        ## Execute the greedy action, starting from the initial state
        env.reset_agent()
        print("Start executing. Current map:") 
        env.print_map()
        while not env.terminal:
            current_state = env.get_current_state() # this is the current state of the environment, from which you will act
            # print(current_state)
            available_actions = env.actions
            # Compute action values
            if table == 'V' and self.V_s is not None:
                ## IMPLEMENT ACTION VALUE ESTIMATION FROM self.V_s HERE !!!
                # print("You still need to implement greedy action selection from the value table self.V_s!")
                action_dict = {}
                for a in available_actions:
                    s_prime, reward = env.transition_function(current_state, a)
                    action_dict[a] = reward + (self.V_s[s_prime])

                max_key, max_value = np.argmax(action_dict, key=lambda x: x[1])

                greedy_action = max_key





            elif table == 'Q' and self.Q_sa is not None:
                ## IMPLEMENT ACTION VALUE ESTIMATION FROM self.Q_sa here !!!
                
                # print("You still need to implement greedy action selection from the state-action value table self.Q_sa!")
                # greedy_action = None # replace this!
                action_values = self.Q_sa[current_state]
                greedy_action = env.actions[np.argmax(action_values)]
                
            else:
                print("No optimal value table was detected. Only manual execution possible.")
                greedy_action = None


            # ask the user what he/she wants
            while True:
                if greedy_action is not None:
                    print('Greedy action= {}'.format(greedy_action))    
                    your_choice = input('Choose an action by typing it in full, then hit enter. Just hit enter to execute the greedy action:')
                else:
                    your_choice = input('Choose an action by typing it in full, then hit enter. Available are {}'.format(env.actions))
                    
                if your_choice == "" and greedy_action is not None:
                    executed_action = greedy_action
                    env.act(executed_action)
                    break
                else:
                    try:
                        executed_action = your_choice
                        env.act(executed_action)
                        break
                    except:
                        print('{} is not a valid action. Available actions are {}. Try again'.format(your_choice,env.actions))
            print("Executed action: {}".format(executed_action))
            print("--------------------------------------\nNew map:")
            env.print_map()
        print("Found the goal! Exiting \n ...................................................................... ")
    

def get_greedy_index(action_values):
    ''' Own variant of np.argmax, since np.argmax only returns the first occurence of the max. 
    Optional to uses '''
    return np.where(action_values == np.max(action_values))
    
if __name__ == '__main__':
    env = World('prison.txt') 
    DP = Dynamic_Programming()

    # Run value iteration
    # input('Press enter to run value iteration')
    # optimal_V_s = DP.value_iteration(env)
    # input('Press enter to start execution of optimal policy according to V')
    # DP.execute_policy(env, table='V') # execute the optimal policy
    
    # Once again with Q-values:
    input('Press enter to run Q-value iteration')
    optimal_Q_sa = DP.Q_value_iteration(env)
    input('Press enter to start execution of optimal policy according to Q')
    DP.execute_policy(env, table='Q') # execute the optimal policy

